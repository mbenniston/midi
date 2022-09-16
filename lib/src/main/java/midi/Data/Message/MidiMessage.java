package midi.Data.Message;

public abstract class MidiMessage {
    public long timeDelta;
    public byte status;

    public abstract void acceptVisitor(MidiMessageVisitor visitor);
}
