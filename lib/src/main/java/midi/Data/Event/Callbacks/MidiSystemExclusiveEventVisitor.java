package midi.Data.Event.Callbacks;

import midi.Data.Event.MidiEvents.MidiSystemExclusiveEvent;
import midi.Data.Event.MidiEvents.MidiSystemExclusiveEvents.MidiSystemExlusiveManufacturerMessage;

public abstract class MidiSystemExclusiveEventVisitor implements MidiSystemExclusiveEventListener {

    public abstract void visit(MidiSystemExlusiveManufacturerMessage manufacturerMessage);

    public void visit(MidiSystemExclusiveEvent event) {
        event.acceptVisitor(this);
    }

    @Override
    public void onRecieve(MidiSystemExclusiveEvent event) {
        event.acceptVisitor(this);
    }
}
