package midi.Data.Event.MidiEvents.MidiChannelEvents;

import midi.Data.Event.MidiEvent;
import midi.Data.Event.Callbacks.MidiChannelEventVisitor;
import midi.Data.Event.MidiEvents.MidiChannelEvent;

public class MidiVoiceChannelPressure extends MidiChannelEvent {
    public int channel;
    public int channelPressure;

    @Override
    public void acceptVisitor(MidiChannelEventVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public long getLengthInBytes() {
        return MidiEvent.BYTES_PER_SINGLE;
    }
}
