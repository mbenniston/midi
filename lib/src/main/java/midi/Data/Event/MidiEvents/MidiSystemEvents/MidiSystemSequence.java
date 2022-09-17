package midi.Data.Event.MidiEvents.MidiSystemEvents;

import midi.Data.Event.MidiEvent;
import midi.Data.Event.MidiSystemEventVisitor;
import midi.Data.Event.MidiEvents.MidiSystemExclusive;

public class MidiSystemSequence extends MidiSystemExclusive {
    public int sequence1;
    public int sequence2;

    @Override
    public void acceptVisitor(MidiSystemEventVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public long getLengthInBytes() {
        return MidiEvent.BYTES_PER_SINGLE + MidiEvent.BYTES_PER_SINGLE;
    }
}
