package midi.MidiMessage.MidiMessages;

import midi.MidiMessage.MidiMessage;
import midi.MidiMessage.MidiMessageVisitor;

public class MidiVoiceProgramChange extends MidiMessage {
    public int channel;
    public byte programId;

    @Override
    public void acceptVisitor(MidiMessageVisitor visitor) {
        visitor.visit(this);
    }
}