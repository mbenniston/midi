package midi.MidiMessage.MidiMessages.MidiSystemMessages;

import midi.MidiMessage.MidiSystemMessageVisitor;
import midi.MidiMessage.MidiMessages.MidiSystemExclusive;

public class MidiSystemSequence extends MidiSystemExclusive {
    public byte sequence1, sequence2;

    @Override
    public void acceptVisitor(MidiSystemMessageVisitor visitor) {
        visitor.visit(this);
    }

}
