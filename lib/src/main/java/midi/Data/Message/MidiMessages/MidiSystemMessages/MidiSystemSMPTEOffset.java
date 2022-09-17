package midi.Data.Message.MidiMessages.MidiSystemMessages;

import midi.Data.Message.MidiMessage;
import midi.Data.Message.MidiSystemMessageVisitor;
import midi.Data.Message.MidiMessages.MidiSystemExclusive;

public class MidiSystemSMPTEOffset extends MidiSystemExclusive {
    public int hours;
    public int minutes;
    public int seconds;
    public int FR;
    public int FF;

    @Override
    public void acceptVisitor(MidiSystemMessageVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public long getLengthInBytes() {
        return MidiMessage.BYTES_PER_SINGLE + MidiMessage.BYTES_PER_SINGLE
                + MidiMessage.BYTES_PER_SINGLE + MidiMessage.BYTES_PER_SINGLE
                + MidiMessage.BYTES_PER_SINGLE;
    }

}
