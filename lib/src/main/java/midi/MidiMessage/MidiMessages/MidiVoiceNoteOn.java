package midi.MidiMessage.MidiMessages;

import midi.MidiMessage.MidiMessage;
import midi.MidiMessage.MidiMessageVisitor;

public class MidiVoiceNoteOn extends MidiMessage {
    public int channel;
    public byte noteId;
    public byte noteVelocity;

    @Override
    public void acceptVisitor(MidiMessageVisitor visitor) {
        visitor.visit(this);
    }
}
