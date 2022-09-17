package midi.Data.Event.Callbacks;

import midi.Data.Event.MidiEvents.MidiSystemExclusiveEvent;

public interface MidiSystemExclusiveEventListener {
    void onRecieve(MidiSystemExclusiveEvent event);
}
