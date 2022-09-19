package midi.Playback;

import midi.Data.MidiFile;

public class MidiSequencer {
    private MidiTiming timing;
    private MidiTrackSequencer[] tracks;

    public MidiSequencer(
            MidiFile file,
            MidiEventExecutor reciever,
            MidiTiming timing) {
        this.timing = timing;

        tracks = new MidiTrackSequencer[file.tracks.size()];

        for (int i = 0; i < file.tracks.size(); i++) {
            tracks[i] = new MidiTrackSequencer(
                    file.tracks.get(i),
                    reciever,
                    timing);
        }
    }

    public boolean isFinished() {
        for (MidiTrackSequencer s : tracks) {
            if (!s.isFinished()) {
                return false;
            }
        }

        return true;
    }

    public void step() {
        // execute until stalls
        for (MidiTrackSequencer seq : tracks) {
            seq.executeUntilStall();
        }

        // clear stalls and execute zero delta events
        double currentTime = timing.getCurrentTime();

        MidiTrackSequencer stalledSequencer = getEarliestStalledSequencer(currentTime);
        while (stalledSequencer != null) {
            // progress all track times until the stalling event is unstalled
            double stallTime = stalledSequencer.getStallUntil();
            stalledSequencer.updateCurrentTime(stallTime);
            stalledSequencer.executeUntilStall();

            for (MidiTrackSequencer seq : tracks) {
                seq.updateCurrentTime(stallTime);
            }

            stalledSequencer = getEarliestStalledSequencer(currentTime);
        }

        // catch all sequencers up with the current time
        for (MidiTrackSequencer seq : tracks) {
            seq.updateCurrentTime(currentTime);
        }
    }

    private MidiTrackSequencer getEarliestStalledSequencer(double stepEndTime) {
        MidiTrackSequencer stalledSequencer = null;
        double minStallUntil = 0;

        for (MidiTrackSequencer sequencer : tracks) {
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
}
