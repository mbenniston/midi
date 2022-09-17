package midi.Data.Event.MidiEvents;

import midi.Data.Event.MidiEvent;
import midi.Data.Event.Callbacks.MidiEventVisitor;
import midi.Data.Event.Callbacks.MidiSystemExclusiveEventVisitor;

public abstract class MidiSystemExclusiveEvent extends MidiEvent {

    public abstract void acceptVisitor(MidiSystemExclusiveEventVisitor visitor);

    @Override
    public void acceptVisitor(MidiEventVisitor visitor) {
        visitor.visit(this);
    }
}
