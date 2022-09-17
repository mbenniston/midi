package midi.Data.Event.MidiEvents.MidiMetaEvents;

import midi.Data.Event.MidiEvent;
import midi.Data.Event.MidiMetaEventVisitor;
import midi.Data.Event.MidiEvents.MidiMetaEvent;

public class MidiMetaSMPTEOffset extends MidiMetaEvent {
    public int hours;
    public int minutes;
    public int seconds;
    public int frameRate;
    public int fractionalFrames;

    @Override
    public void acceptVisitor(MidiMetaEventVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public long getLengthInBytes() {
        return MidiEvent.BYTES_PER_SINGLE + MidiEvent.BYTES_PER_SINGLE
                + MidiEvent.BYTES_PER_SINGLE + MidiEvent.BYTES_PER_SINGLE
                + MidiEvent.BYTES_PER_SINGLE;
    }

}
