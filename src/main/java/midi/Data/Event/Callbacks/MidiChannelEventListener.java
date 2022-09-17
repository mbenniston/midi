package midi.Data.Event.Callbacks;

import midi.Data.Event.MidiEvents.MidiChannelEvent;

public interface MidiChannelEventListener {
    void onRecieve(MidiChannelEvent event);
}
