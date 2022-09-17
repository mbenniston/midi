package midi.Data.Event.Callbacks;

import midi.Data.Event.MidiEvent;

public interface MidiEventListener {
    void onRecieve(MidiEvent event);
}