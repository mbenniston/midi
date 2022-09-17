package midi.Data;

public class MidiTrackHeader {
    public static final long MIDI_TRACK_CHUNK_ID = 0x4D54726BL;

    public long magicNumber;
    public long trackLengthInBytes;
}