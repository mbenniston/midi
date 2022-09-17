package midi.Data.Event.Callbacks;

import midi.Data.Event.MidiEvents.MidiMetaEvent;

public interface MidiMetaEventListener {
    void onRecieve(MidiMetaEvent event);
}
