package midi.Data.Message.MidiMessages;

import midi.Data.Message.MidiMessage;
import midi.Data.Message.MidiMessageVisitor;

public class MidiVoiceNoteOff extends MidiMessage {
    public int channel;
    public int noteId;
    public int noteVelocity;

    @Override
    public void acceptVisitor(MidiMessageVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public long getLengthInBytes() {
        return MidiMessage.BYTES_PER_SINGLE + MidiMessage.BYTES_PER_SINGLE;
    }

}