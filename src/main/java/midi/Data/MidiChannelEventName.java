package midi.Data;

public enum MidiChannelEventName {
    VoiceNoteOff(0x80),
    VoiceNoteOn(0x90),
    VoiceAfterTouch(0xA0),
    VoiceControlChange(0xB0),
    VoiceProgramChange(0xC0),
    VoiceChannelPressure(0xD0),
    VoicePitchBend(0xE0);

    public final byte value;

    MidiChannelEventName(int value) {
        this.value = (byte) (value & 0xFF);
    }

    public static MidiChannelEventName getNameFromID(byte id) {
        for (MidiChannelEventName name : MidiChannelEventName.values()) {
            if (id == name.value) {
                return name;
            }
        }
        throw new IllegalArgumentException("Event does not exist");
    }
}
