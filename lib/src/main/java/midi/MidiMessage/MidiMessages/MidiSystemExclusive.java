package midi.MidiMessage.MidiMessages;

import midi.MidiMessage.MidiMessage;
import midi.MidiMessage.MidiMessageVisitor;
import midi.MidiMessage.MidiSystemMessageVisitor;

public abstract class MidiSystemExclusive extends MidiMessage {
    @Override
    public void acceptVisitor(MidiMessageVisitor visitor) {
        visitor.visit(this);
    }

    public abstract void acceptVisitor(MidiSystemMessageVisitor midiSystemExclusiveVistor);
}
