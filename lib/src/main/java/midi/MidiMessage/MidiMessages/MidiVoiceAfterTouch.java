package midi.MidiMessage.MidiMessages;

import midi.MidiMessage.MidiMessage;
import midi.MidiMessage.MidiMessageVisitor;

public class MidiVoiceAfterTouch extends MidiMessage {
    public int channel;
    public byte noteId;
    public byte noteVelocity;

    @Override
    public void acceptVisitor(MidiMessageVisitor visitor) {
        visitor.visit(this);
    }
}