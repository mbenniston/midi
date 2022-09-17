package midi.Data.Event.MidiEvents.MidiSystemEvents;

import midi.Data.Event.MidiSystemEventVisitor;
import midi.Data.Event.MidiEvents.MidiSystemExclusive;

public class MidiSystemManufacturerStart extends MidiSystemExclusive {
    public byte[] dataBlob;

    @Override
    public void acceptVisitor(MidiSystemEventVisitor midiSystemExclusiveVistor) {
        midiSystemExclusiveVistor.visit(this);
    }

    @Override
    public long getLengthInBytes() {
        return dataBlob.length;
    }
}