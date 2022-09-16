package midi.Playback;

import midi.Data.MidiFile;
import midi.MidiMessage.MidiMessageVisitor;

// Controls the real time timing of a midi file
public class MidiSequencer {
    private MidiTrackSequencer[] trackSequences;

    public MidiSequencer(
            MidiFile file,
            MidiMessageVisitor reciever,
            MidiTiming timing) {
        trackSequences = new MidiTrackSequencer[file.tracks.size()];
        for (int i = 0; i < file.tracks.size(); i++) {
            trackSequences[i] = new MidiTrackSequencer(file.tracks.get(i), reciever, timing);
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
