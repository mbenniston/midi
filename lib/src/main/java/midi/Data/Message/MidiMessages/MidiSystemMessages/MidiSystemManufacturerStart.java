package midi.Data.Message.MidiMessages.MidiSystemMessages;

import midi.Data.Message.MidiSystemMessageVisitor;
import midi.Data.Message.MidiMessages.MidiSystemExclusive;

public class MidiSystemManufacturerStart extends MidiSystemExclusive {
    public byte[] dataBlob;

    @Override
    public void acceptVisitor(MidiSystemMessageVisitor midiSystemExclusiveVistor) {
        midiSystemExclusiveVistor.visit(this);
    }

    @Override
    public long getLengthInBytes() {
        return dataBlob.length;
    }
}