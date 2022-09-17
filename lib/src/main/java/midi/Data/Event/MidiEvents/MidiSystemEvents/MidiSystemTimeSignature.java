package midi.Data.Event.MidiEvents.MidiSystemEvents;

import midi.Data.Event.MidiEvent;
import midi.Data.Event.MidiSystemEventVisitor;
import midi.Data.Event.MidiEvents.MidiSystemExclusive;

public class MidiSystemTimeSignature extends MidiSystemExclusive {
    public int timeSignitureUpper;
    public int timeSignitureLower;
    public int clocksPerTick;
    public int ticks32per24Clocks;

    @Override
    public void acceptVisitor(MidiSystemEventVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public long getLengthInBytes() {
        return MidiEvent.BYTES_PER_SINGLE + MidiEvent.BYTES_PER_SINGLE
                + MidiEvent.BYTES_PER_SINGLE + MidiEvent.BYTES_PER_SINGLE;
    }
}
