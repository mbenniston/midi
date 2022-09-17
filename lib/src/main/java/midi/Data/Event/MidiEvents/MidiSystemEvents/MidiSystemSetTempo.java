package midi.Data.Event.MidiEvents.MidiSystemEvents;

import midi.Data.Event.MidiEvent;
import midi.Data.Event.MidiSystemEventVisitor;
import midi.Data.Event.MidiEvents.MidiSystemExclusive;

public class MidiSystemSetTempo extends MidiSystemExclusive {
    public long microsecondsPerQuarterNote;

    @Override
    public void acceptVisitor(MidiSystemEventVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public long getLengthInBytes() {
        return MidiEvent.BYTES_PER_USHORT_PLUS;
    }
}
