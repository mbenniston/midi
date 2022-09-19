package midi.Data.Event.MidiEvents.MidiChannelEvents;

import midi.Data.Event.MidiEvent;
import midi.Data.Event.Callbacks.MidiChannelEventVisitor;
import midi.Data.Event.MidiEvents.MidiChannelEvent;

public class MidiVoiceProgramChange extends MidiChannelEvent {
    public int programId;

    @Override
    public void acceptVisitor(MidiChannelEventVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public long getLengthInBytes() {
        return MidiEvent.BYTES_PER_SINGLE;
    }
}