package midi.Data.Message.MidiMessages.MidiSystemMessages;

import midi.Data.Message.MidiSystemMessageVisitor;
import midi.Data.Message.MidiMessages.MidiSystemExclusive;

public class MidiSystemSequencerSpecific extends MidiSystemExclusive {
    public String specific;

    @Override
    public void acceptVisitor(MidiSystemMessageVisitor visitor) {
        visitor.visit(this);
    }

}
