package midi.Data.Event.MidiEvents;

import midi.Data.Event.MidiEvent;
import midi.Data.Event.MidiEventVisitor;
import midi.Data.Event.MidiSystemEventVisitor;

public abstract class MidiSystemExclusive extends MidiEvent {
    @Override
    public void acceptVisitor(MidiEventVisitor visitor) {
        visitor.visit(this);
    }

    public abstract void acceptVisitor(MidiSystemEventVisitor midiSystemExclusiveVistor);
}
