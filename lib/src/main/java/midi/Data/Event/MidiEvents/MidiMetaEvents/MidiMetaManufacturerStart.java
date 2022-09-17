package midi.Data.Event.MidiEvents.MidiMetaEvents;

import midi.Data.Event.MidiMetaEventVisitor;
import midi.Data.Event.MidiEvents.MidiMetaEvent;

public class MidiMetaManufacturerStart extends MidiMetaEvent {
    public byte[] dataBlob;

    @Override
    public void acceptVisitor(MidiMetaEventVisitor midiSystemExclusiveVistor) {
        midiSystemExclusiveVistor.visit(this);
    }

    @Override
    public long getLengthInBytes() {
        return dataBlob.length;
    }
}