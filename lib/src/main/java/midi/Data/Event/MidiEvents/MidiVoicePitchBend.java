package midi.Data.Event.MidiEvents;

import midi.Data.Event.MidiEvent;
import midi.Data.Event.MidiEventVisitor;

public class MidiVoicePitchBend extends MidiEvent {
    public int channel;
    public int nLS7B;
    public int nMS7B;

    @Override
    public void acceptVisitor(MidiEventVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public long getLengthInBytes() {
        return MidiEvent.BYTES_PER_SINGLE + MidiEvent.BYTES_PER_SINGLE;
    }
}
