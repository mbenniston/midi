package midi.Data.Event;

import midi.Data.Event.MidiEvents.MidiSystemExclusive;

public interface MidiSystemEventListener {
    void onRecieve(MidiSystemExclusive event);
}
