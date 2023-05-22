package midi.Data.Event.Callbacks;

import midi.Data.Event.MidiEvents.MidiSystemExclusiveEvent;
import midi.Data.Event.MidiEvents.MidiSystemExclusiveEvents.MidiSystemExclusiveManufacturerMessage;

public abstract class MidiSystemExclusiveEventVisitor implements MidiSystemExclusiveEventListener {

    public abstract void visit(MidiSystemExclusiveManufacturerMessage manufacturerMessage);

    public void visit(MidiSystemExclusiveEvent event) {
        event.acceptVisitor(this);
    }

    @Override
    public void onRecieve(MidiSystemExclusiveEvent event) {
        event.acceptVisitor(this);
    }
}
