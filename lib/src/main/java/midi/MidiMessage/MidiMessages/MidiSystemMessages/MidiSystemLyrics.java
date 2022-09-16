package midi.MidiMessage.MidiMessages.MidiSystemMessages;

import midi.MidiMessage.MidiSystemMessageVisitor;
import midi.MidiMessage.MidiMessages.MidiSystemExclusive;

public class MidiSystemLyrics extends MidiSystemExclusive {
    public String lyrics;

    @Override
    public void acceptVisitor(MidiSystemMessageVisitor visitor) {
        visitor.visit(this);
    }

}
