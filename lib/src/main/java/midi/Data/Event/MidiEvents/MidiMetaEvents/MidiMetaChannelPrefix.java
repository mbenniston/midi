package midi.Data.Event.MidiEvents.MidiMetaEvents;

import midi.Data.Event.MidiEvent;
import midi.Data.Event.MidiMetaEventVisitor;
import midi.Data.Event.MidiEvents.MidiMetaEvent;

public class MidiMetaChannelPrefix extends MidiMetaEvent {
    public int prefix;

    @Override
    public void acceptVisitor(MidiMetaEventVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public long getLengthInBytes() {
        return MidiEvent.BYTES_PER_SINGLE;
    }
}
