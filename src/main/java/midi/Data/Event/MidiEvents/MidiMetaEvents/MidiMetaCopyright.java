package midi.Data.Event.MidiEvents.MidiMetaEvents;

import midi.Data.Event.Callbacks.MidiMetaEventVisitor;
import midi.Data.Event.MidiEvents.MidiMetaEvent;

public class MidiMetaCopyright extends MidiMetaEvent {
    public String copyright;

    @Override
    public void acceptVisitor(MidiMetaEventVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public long getLengthInBytes() {
        return copyright.length();
    }
}
