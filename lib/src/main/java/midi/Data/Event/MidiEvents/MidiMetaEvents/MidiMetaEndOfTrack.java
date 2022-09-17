package midi.Data.Event.MidiEvents.MidiMetaEvents;

import midi.Data.Event.MidiMetaEventVisitor;
import midi.Data.Event.MidiEvents.MidiMetaEvent;

public class MidiMetaEndOfTrack extends MidiMetaEvent {

    @Override
    public void acceptVisitor(MidiMetaEventVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public long getLengthInBytes() {
        return 0;
    }
}
