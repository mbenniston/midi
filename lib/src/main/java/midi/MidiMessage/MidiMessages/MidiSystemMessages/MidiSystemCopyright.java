package midi.MidiMessage.MidiMessages.MidiSystemMessages;

import midi.MidiMessage.MidiSystemMessageVisitor;
import midi.MidiMessage.MidiMessages.MidiSystemExclusive;

public class MidiSystemCopyright extends MidiSystemExclusive {
    public String copyright;

    @Override
    public void acceptVisitor(MidiSystemMessageVisitor visitor) {
        visitor.visit(this);
    }
}
