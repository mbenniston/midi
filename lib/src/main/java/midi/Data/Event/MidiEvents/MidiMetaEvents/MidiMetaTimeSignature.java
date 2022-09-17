package midi.Data.Event.MidiEvents.MidiMetaEvents;

import midi.Data.Event.MidiEvent;
import midi.Data.Event.Callbacks.MidiMetaEventVisitor;
import midi.Data.Event.MidiEvents.MidiMetaEvent;

public class MidiMetaTimeSignature extends MidiMetaEvent {
    public int timeSignitureUpper;
    public int timeSignitureLower;
    public int clocksPerTick;
    public int ticks32per24Clocks;

    @Override
    public void acceptVisitor(MidiMetaEventVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public long getLengthInBytes() {
        return MidiEvent.BYTES_PER_SINGLE + MidiEvent.BYTES_PER_SINGLE
                + MidiEvent.BYTES_PER_SINGLE + MidiEvent.BYTES_PER_SINGLE;
    }
}
