package midi.Playback;

import midi.Data.MidiFile;

public class MidiSequencer {
    private final MidiTiming timing;
    private final MidiEventExecutor receiver;

    private MidiFile file;
    private MidiTrackSequencer[] trackSequencers;

    public MidiSequencer(
            MidiFile file,
            MidiEventExecutor receiver,
            MidiTiming timing) {
        this.timing = timing;
        this.receiver = receiver;

        setFile(file);
    }

    public void step(double currentTime) {
        timing.update(currentTime);

        // execute until stalls
        for (MidiTrackSequencer seq : trackSequencers) {
            seq.executeUntilStall();
        }

        // clear stalls and execute zero delta events
        MidiTrackSequencer stalledSequencer = getEarliestStalledSequencer(currentTime);
        while (stalledSequencer != null) {
            // progress all track times until the stalling event is unstalled
            double stallTime = stalledSequencer.getStallUntil();
            stalledSequencer.updateCurrentTime(stallTime);
            stalledSequencer.executeUntilStall();

            for (MidiTrackSequencer seq : trackSequencers) {
                seq.updateCurrentTime(stallTime);
            }

            stalledSequencer = getEarliestStalledSequencer(currentTime);
        }

        // catch all sequencers up with the current time
        for (MidiTrackSequencer seq : trackSequencers) {
            seq.updateCurrentTime(currentTime);
        }
    }

    private MidiTrackSequencer getEarliestStalledSequencer(double stepEndTime) {
        MidiTrackSequencer stalledSequencer = null;
        double minStallUntil = 0;

        for (MidiTrackSequencer sequencer : trackSequencers) {
            if (!sequencer.isStalled()) {
                continue;
            }

            double stalledUntil = sequencer.getStallUntil();

            boolean stallForTimeStep = stalledUntil > stepEndTime;
            if (stallForTimeStep) {
                continue;
            }

            if (stalledSequencer == null || stalledUntil < minStallUntil) {
                minStallUntil = stalledUntil;
                stalledSequencer = sequencer;
            }
        }

        return stalledSequencer;
    }

    public void setFile(MidiFile file) {
        this.file = file;
        trackSequencers = new MidiTrackSequencer[file.tracks.size()];

        for (int i = 0; i < file.tracks.size(); i++) {
            trackSequencers[i] = new MidiTrackSequencer(
                    file.tracks.get(i),
                    receiver,
                    timing);
        }

        timing.reset();
        timing.setTicksPerBeat(file.header.divisions);
    }

    public MidiFile getFile() {
        return file;
    }

    public boolean isFinished() {
        for (MidiTrackSequencer s : trackSequencers) {
            if (!s.isFinished()) {
                return false;
            }
        }

        return true;
    }

    public void seek(double seekTime) {
        seek(seekTime, false);
    }

    public void seek(double seekTime, boolean forceReplay) {
        if (seekTime < timing.getCurrentTime() || forceReplay) {
            // if seeking back in time we need to replay sequencer from start
            timing.reset();
            timing.setTicksPerBeat(file.header.divisions);
            resetSequencers();
        }

        timing.update(seekTime);
    }

    private void resetSequencers() {
        for (MidiTrackSequencer sequencer : trackSequencers) {
            sequencer.reset();
        }
    }
}
