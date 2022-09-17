package midi.Data.Event.MidiEvents.MidiMetaEvents;

import midi.Data.Event.MidiMetaEventVisitor;
import midi.Data.Event.MidiEvents.MidiMetaEvent;

public class MidiMetaSequencerSpecific extends MidiMetaEvent {
    public String specific;

    @Override
    public void acceptVisitor(MidiMetaEventVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public long getLengthInBytes() {
        return specific.length();
    }

}
