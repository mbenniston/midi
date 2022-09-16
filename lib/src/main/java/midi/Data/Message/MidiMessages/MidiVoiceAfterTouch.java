package midi.Data.Message.MidiMessages;

import midi.Data.Message.MidiMessage;
import midi.Data.Message.MidiMessageVisitor;

public class MidiVoiceAfterTouch extends MidiMessage {
    public int channel;
    public byte noteId;
    public byte noteVelocity;

    @Override
    public void acceptVisitor(MidiMessageVisitor visitor) {
        visitor.visit(this);
    }
}