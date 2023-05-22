package midi.Data;

public enum MidiMetaEventName {
    Sequence(0x00),
    Text(0x01),
    Copyright(0x02),
    TrackName(0x03),
    InstrumentName(0x04),
    Lyrics(0x05),
    Marker(0x06),
    CuePoint(0x07),
    ChannelPrefix(0x20),
    EndOfTrack(0x2F),
    SetTempo(0x51),
    SMPTEOffset(0x54),
    TimeSignature(0x58),
    KeySignature(0x59),
    SequencerSpecific(0x7F);

    public final byte value;

    MidiMetaEventName(int value) {
        this.value = (byte) (value & 0xFF);
    }

    public static MidiMetaEventName getNameFromID(byte id) {
        for (MidiMetaEventName name : MidiMetaEventName.values()) {
            if (id == name.value) {
                return name;
            }
        }
        throw new IllegalArgumentException("Event does not exist");
    }
}