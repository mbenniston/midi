package midi.MidiMessage.MidiMessages;

import midi.MidiMessage.MidiMessage;
import midi.MidiMessage.MidiMessageVisitor;

public class MidiVoiceChannelPressure extends MidiMessage {
    public int channel;
    public byte channelPressure;

    @Override
    public void acceptVisitor(MidiMessageVisitor visitor) {
        visitor.visit(this);
    }
}
