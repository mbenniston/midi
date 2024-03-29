package midi.Data.Event.MidiEvents.MidiChannelEvents;

import midi.Data.Event.MidiEvent;
import midi.Data.Event.Callbacks.MidiChannelEventVisitor;
import midi.Data.Event.MidiEvents.MidiChannelEvent;

public class MidiVoicePitchBend extends MidiChannelEvent {
    public int nLS7B;
    public int nMS7B;

    @Override
    public void acceptVisitor(MidiChannelEventVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public long getLengthInBytes() {
        return MidiEvent.BYTES_PER_SINGLE + MidiEvent.BYTES_PER_SINGLE;
    }
}
