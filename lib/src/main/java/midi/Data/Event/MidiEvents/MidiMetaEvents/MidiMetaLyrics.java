package midi.Data.Event.MidiEvents.MidiMetaEvents;

import midi.Data.Event.MidiMetaEventVisitor;
import midi.Data.Event.MidiEvents.MidiMetaEvent;

public class MidiMetaLyrics extends MidiMetaEvent {
    public String lyrics;

    @Override
    public void acceptVisitor(MidiMetaEventVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public long getLengthInBytes() {
        return lyrics.length();
    }

}
