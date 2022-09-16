package midi.Data.Message.MidiMessages.MidiSystemMessages;

import midi.Data.Message.MidiSystemMessageVisitor;
import midi.Data.Message.MidiMessages.MidiSystemExclusive;

public class MidiSystemTimeSignature extends MidiSystemExclusive {
    public byte timeSignitureUpper;
    public int timeSignitureLower;
    public byte clocksPerTick;
    public byte ticks32per24Clocks;

    @Override
    public void acceptVisitor(MidiSystemMessageVisitor visitor) {
        visitor.visit(this);
    }

}
