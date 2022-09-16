package midi.MidiMessage;

public abstract class MidiMessage {
    public long timeDelta;

    public abstract void acceptVisitor(MidiMessageVisitor visitor);
}
