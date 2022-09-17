package midi.Data;

public enum MidiSystemEventName {
    MetaSequence(0x00),
    MetaText(0x01),
    MetaCopyright(0x02),
    MetaTrackName(0x03),
    MetaInstrumentName(0x04),
    MetaLyrics(0x05),
    MetaMarker(0x06),
    MetaCuePoint(0x07),
    MetaChannelPrefix(0x20),
    MetaEndOfTrack(0x2F),
    MetaSetTempo(0x51),
    MetaSMPTEOffset(0x54),
    MetaTimeSignature(0x58),
    MetaKeySignature(0x59),
    MetaSequencerSpecific(0x7F);

    public final byte value;

    private MidiSystemEventName(int value) {
        this.value = (byte) (value & 0xFF);
    }

    public static MidiSystemEventName getNameFromID(byte id) {
        for (MidiSystemEventName name : MidiSystemEventName.values()) {
            if (id == name.value) {
                return name;
            }
        }
        throw new IllegalArgumentException("Event does not exist");
    }
}