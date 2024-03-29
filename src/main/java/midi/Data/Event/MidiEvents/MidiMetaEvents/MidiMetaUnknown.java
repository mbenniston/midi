package midi.Data.Event.MidiEvents.MidiMetaEvents;

import midi.Data.Event.Callbacks.MidiMetaEventVisitor;
import midi.Data.Event.MidiEvents.MidiMetaEvent;

public class MidiMetaUnknown extends MidiMetaEvent {
    public byte[] dataBlob;
    public byte type;

    @Override
    public void acceptVisitor(MidiMetaEventVisitor midiSystemExclusiveVistor) {
        midiSystemExclusiveVistor.visit(this);
    }

    @Override
    public long getLengthInBytes() {
        return dataBlob.length;
    }
}
