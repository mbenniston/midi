package midi.MidiMessage.MidiMessages.MidiSystemMessages;

import midi.MidiMessage.MidiSystemMessageVisitor;
import midi.MidiMessage.MidiMessages.MidiSystemExclusive;

public class MidiSystemInstrumentName extends MidiSystemExclusive {
    public String instrumentName;

    @Override
    public void acceptVisitor(MidiSystemMessageVisitor visitor) {
        visitor.visit(this);
    }

}
