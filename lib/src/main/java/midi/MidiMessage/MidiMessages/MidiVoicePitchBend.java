package midi.MidiMessage.MidiMessages;

import midi.MidiMessage.MidiMessage;
import midi.MidiMessage.MidiMessageVisitor;

public class MidiVoicePitchBend extends MidiMessage {
    public int channel;
    public byte nLS7B;
    public byte nMS7B;

    @Override
    public void acceptVisitor(MidiMessageVisitor visitor) {
        visitor.visit(this);
    }
}
