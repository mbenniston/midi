package midi.Playback;

import midi.Data.MidiFile;
import midi.Data.Message.MidiMessageListener;

public class MidiSequencer {
    private final MidiTrackSequencer[] trackSequences;

    public MidiSequencer(
            MidiFile file,
            MidiMessageListener reciever,
            MidiTiming timing) {
        trackSequences = new MidiTrackSequencer[file.tracks.size()];

        for (int i = 0; i < file.tracks.size(); i++) {
            trackSequences[i] = new MidiTrackSequencer(
                    file.tracks.get(i),
                    reciever,
                    timing);
        }
    }

    public void dispatchEvents() {
        for (MidiTrackSequencer s : trackSequences) {
            s.dispatchEvents();
        }
    }

    public boolean isFinished() {
        for (MidiTrackSequencer s : trackSequences) {
            if (!s.isFinished()) {
                return false;
            }
        }

        return true;
    }
}
