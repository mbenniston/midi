package midi.Data.Event.MidiEvents.MidiMetaEvents;

import midi.Data.Event.MidiEvent;
import midi.Data.Event.MidiMetaEventVisitor;
import midi.Data.Event.MidiEvents.MidiMetaEvent;

public class MidiMetaSequence extends MidiMetaEvent {
    public int sequence1;
    public int sequence2;

    @Override
    public void acceptVisitor(MidiMetaEventVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public long getLengthInBytes() {
        return MidiEvent.BYTES_PER_SINGLE + MidiEvent.BYTES_PER_SINGLE;
    }
}
