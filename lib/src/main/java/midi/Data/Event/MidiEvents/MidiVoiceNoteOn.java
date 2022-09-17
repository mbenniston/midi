package midi.Data.Event.MidiEvents;

import midi.Data.Event.MidiEvent;
import midi.Data.Event.MidiEventVisitor;

public class MidiVoiceNoteOn extends MidiEvent {
    public int channel;
    public int noteId;
    public int noteVelocity;

    @Override
    public void acceptVisitor(MidiEventVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public long getLengthInBytes() {
        return MidiEvent.BYTES_PER_SINGLE + MidiEvent.BYTES_PER_SINGLE;
    }
}
