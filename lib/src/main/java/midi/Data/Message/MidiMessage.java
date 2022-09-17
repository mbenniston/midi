package midi.Data.Message;

public abstract class MidiMessage {
    public static final long BYTES_PER_SINGLE = 1;
    public static final long BYTES_PER_USHORT = 2;
    public static final long BYTES_PER_USHORT_PLUS = 3;
    public static final long BYTES_PER_UINT = 4;

    public long timeDelta;

    public abstract void acceptVisitor(MidiMessageVisitor visitor);

    public abstract long getLengthInBytes();
}
