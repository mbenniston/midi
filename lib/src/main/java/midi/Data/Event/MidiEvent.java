package midi.Data.Event;

import midi.Data.Event.Callbacks.MidiEventVisitor;

public abstract class MidiEvent {
    public static final long BYTES_PER_SINGLE = 1;
    public static final long BYTES_PER_USHORT = 2;
    public static final long BYTES_PER_USHORT_PLUS = 3;
    public static final long BYTES_PER_UINT = 4;

    public long timeDelta;

    public abstract void acceptVisitor(MidiEventVisitor visitor);

    public abstract long getLengthInBytes();
}
