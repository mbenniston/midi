package midi.Data.Event.MidiEvents.MidiChannelEvents;

import midi.Data.Event.MidiEvent;
import midi.Data.Event.Callbacks.MidiChannelEventVisitor;
import midi.Data.Event.MidiEvents.MidiChannelEvent;

public class MidiVoiceControlChange extends MidiChannelEvent {
    public int controlId;
    public int controlValue;

    @Override
    public void acceptVisitor(MidiChannelEventVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public long getLengthInBytes() {
        return MidiEvent.BYTES_PER_SINGLE + MidiEvent.BYTES_PER_SINGLE;
    }

}
