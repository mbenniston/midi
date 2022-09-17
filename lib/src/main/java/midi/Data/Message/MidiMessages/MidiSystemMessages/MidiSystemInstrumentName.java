package midi.Data.Message.MidiMessages.MidiSystemMessages;

import midi.Data.Message.MidiSystemMessageVisitor;
import midi.Data.Message.MidiMessages.MidiSystemExclusive;

public class MidiSystemInstrumentName extends MidiSystemExclusive {
    public String instrumentName;

    @Override
    public void acceptVisitor(MidiSystemMessageVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public long getLengthInBytes() {
        return instrumentName.length();
    }

}
