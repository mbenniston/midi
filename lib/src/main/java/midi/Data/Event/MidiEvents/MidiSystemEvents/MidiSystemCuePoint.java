package midi.Data.Event.MidiEvents.MidiSystemEvents;

import midi.Data.Event.MidiSystemEventVisitor;
import midi.Data.Event.MidiEvents.MidiSystemExclusive;

public class MidiSystemCuePoint extends MidiSystemExclusive {
    public String cue;

    @Override
    public void acceptVisitor(MidiSystemEventVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public long getLengthInBytes() {
        return cue.length();
    }

}
