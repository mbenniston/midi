package midi.Data.Event;

import midi.Data.Event.MidiEvents.MidiMetaEvent;

public interface MidiMetaEventListener {
    void onRecieve(MidiMetaEvent event);
}
