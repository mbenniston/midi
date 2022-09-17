package midi.Data.Message.MidiMessages.MidiSystemMessages;

import midi.Data.Message.MidiMessage;
import midi.Data.Message.MidiSystemMessageVisitor;
import midi.Data.Message.MidiMessages.MidiSystemExclusive;

public class MidiSystemTimeSignature extends MidiSystemExclusive {
    public int timeSignitureUpper;
    public int timeSignitureLower;
    public int clocksPerTick;
    public int ticks32per24Clocks;

    @Override
    public void acceptVisitor(MidiSystemMessageVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public long getLengthInBytes() {
        return MidiMessage.BYTES_PER_SINGLE + MidiMessage.BYTES_PER_SINGLE
                + MidiMessage.BYTES_PER_SINGLE + MidiMessage.BYTES_PER_SINGLE;
    }
}
