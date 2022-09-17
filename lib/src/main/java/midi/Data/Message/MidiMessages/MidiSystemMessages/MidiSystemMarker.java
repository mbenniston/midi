package midi.Data.Message.MidiMessages.MidiSystemMessages;

import midi.Data.Message.MidiSystemMessageVisitor;
import midi.Data.Message.MidiMessages.MidiSystemExclusive;

public class MidiSystemMarker extends MidiSystemExclusive {
    public String marker;

    @Override
    public void acceptVisitor(MidiSystemMessageVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public long getLengthInBytes() {
        return marker.length();
    }

}
