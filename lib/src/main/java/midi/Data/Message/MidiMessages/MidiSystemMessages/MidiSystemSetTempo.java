package midi.Data.Message.MidiMessages.MidiSystemMessages;

import midi.Data.Message.MidiMessage;
import midi.Data.Message.MidiSystemMessageVisitor;
import midi.Data.Message.MidiMessages.MidiSystemExclusive;

public class MidiSystemSetTempo extends MidiSystemExclusive {
    public long microsecondsPerQuarterNote;

    @Override
    public void acceptVisitor(MidiSystemMessageVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public long getLengthInBytes() {
        return MidiMessage.BYTES_PER_USHORT_PLUS;
    }
}
