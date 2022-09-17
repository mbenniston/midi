package midi.Data.Event.MidiEvents.MidiSystemExclusiveEvents;

import midi.Data.Event.Callbacks.MidiSystemExclusiveEventVisitor;
import midi.Data.Event.MidiEvents.MidiSystemExclusiveEvent;

public class MidiSystemExlusiveManufacturerMessage extends MidiSystemExclusiveEvent {
    public byte[] dataBlob;
    public boolean includesPreambleByte = false;

    @Override
    public void acceptVisitor(MidiSystemExclusiveEventVisitor midiSystemExclusiveVistor) {
        midiSystemExclusiveVistor.visit(this);
    }

    @Override
    public long getLengthInBytes() {
        return dataBlob.length;
    }
}
