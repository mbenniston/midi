package midi.Data.Event.MidiEvents;

import midi.Data.Event.MidiEvent;
import midi.Data.Event.Callbacks.MidiChannelEventVisitor;
import midi.Data.Event.Callbacks.MidiEventVisitor;

public abstract class MidiChannelEvent extends MidiEvent {
    public int channel;

    @Override
    public void acceptVisitor(MidiEventVisitor visitor) {
        visitor.visit(this);
    }

    public abstract void acceptVisitor(MidiChannelEventVisitor visitor);
}
