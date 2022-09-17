package midi.Data.Event.MidiEvents;

import midi.Data.Event.MidiEvent;
import midi.Data.Event.MidiEventVisitor;

public class MidiVoiceChannelPressure extends MidiEvent {
    public int channel;
    public int channelPressure;

    @Override
    public void acceptVisitor(MidiEventVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public long getLengthInBytes() {
        return MidiEvent.BYTES_PER_SINGLE;
    }
}
