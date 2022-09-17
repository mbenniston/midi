package midi.Data.Event.MidiEvents.MidiMetaEvents;

import midi.Data.Event.Callbacks.MidiMetaEventVisitor;
import midi.Data.Event.MidiEvents.MidiMetaEvent;

public class MidiMetaText extends MidiMetaEvent {
    public String text;

    @Override
    public void acceptVisitor(MidiMetaEventVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public long getLengthInBytes() {
        return text.length();
    }
}
