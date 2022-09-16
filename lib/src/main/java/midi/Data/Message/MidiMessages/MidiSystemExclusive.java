package midi.Data.Message.MidiMessages;

import midi.Data.Message.MidiMessage;
import midi.Data.Message.MidiMessageVisitor;
import midi.Data.Message.MidiSystemMessageVisitor;

public abstract class MidiSystemExclusive extends MidiMessage {
    @Override
    public void acceptVisitor(MidiMessageVisitor visitor) {
        visitor.visit(this);
    }

    public abstract void acceptVisitor(MidiSystemMessageVisitor midiSystemExclusiveVistor);
}
