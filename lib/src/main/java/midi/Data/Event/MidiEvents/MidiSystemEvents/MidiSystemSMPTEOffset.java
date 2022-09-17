package midi.Data.Event.MidiEvents.MidiSystemEvents;

import midi.Data.Event.MidiEvent;
import midi.Data.Event.MidiSystemEventVisitor;
import midi.Data.Event.MidiEvents.MidiSystemExclusive;

public class MidiSystemSMPTEOffset extends MidiSystemExclusive {
    public int hours;
    public int minutes;
    public int seconds;
    public int frameRate;
    public int fractionalFrames;

    @Override
    public void acceptVisitor(MidiSystemEventVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public long getLengthInBytes() {
        return MidiEvent.BYTES_PER_SINGLE + MidiEvent.BYTES_PER_SINGLE
                + MidiEvent.BYTES_PER_SINGLE + MidiEvent.BYTES_PER_SINGLE
                + MidiEvent.BYTES_PER_SINGLE;
    }

}
