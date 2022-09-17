package midi.Data.Event.MidiEvents;

import midi.Data.Event.MidiEvent;
import midi.Data.Event.MidiEventVisitor;
import midi.Data.Event.MidiMetaEventVisitor;

public abstract class MidiMetaEvent extends MidiEvent {
    @Override
    public void acceptVisitor(MidiEventVisitor visitor) {
        visitor.visit(this);
    }

    public abstract void acceptVisitor(MidiMetaEventVisitor midiSystemExclusiveVistor);
}
