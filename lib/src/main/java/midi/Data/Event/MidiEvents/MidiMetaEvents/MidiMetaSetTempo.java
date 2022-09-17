package midi.Data.Event.MidiEvents.MidiMetaEvents;

import midi.Data.Event.MidiEvent;
import midi.Data.Event.MidiMetaEventVisitor;
import midi.Data.Event.MidiEvents.MidiMetaEvent;

public class MidiMetaSetTempo extends MidiMetaEvent {
    public long microsecondsPerQuarterNote;

    @Override
    public void acceptVisitor(MidiMetaEventVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public long getLengthInBytes() {
        return MidiEvent.BYTES_PER_USHORT_PLUS;
    }
}
