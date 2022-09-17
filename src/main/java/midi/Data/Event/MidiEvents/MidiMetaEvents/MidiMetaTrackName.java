package midi.Data.Event.MidiEvents.MidiMetaEvents;

import midi.Data.Event.Callbacks.MidiMetaEventVisitor;
import midi.Data.Event.MidiEvents.MidiMetaEvent;

public class MidiMetaTrackName extends MidiMetaEvent {
    public String trackName;

    @Override
    public void acceptVisitor(MidiMetaEventVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public long getLengthInBytes() {
        return trackName.length();
    }
}
