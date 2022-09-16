package midi.Reading;

import static midi.Reading.ReadingUtils.*;

import java.io.IOException;
import java.io.PushbackInputStream;

import midi.Data.Message.MidiMessage;
import midi.Data.Message.MidiMessages.*;
import midi.Reading.MidiFileReader.MidiLoadError;

public class MidiMessageReader {
    private final PushbackInputStream source;

    private final MidiSystemMessageReader systemMessageReader;

    private byte lastStatusByte = 0;

    public MidiMessageReader(PushbackInputStream source) {
        this.source = source;
        this.systemMessageReader = new MidiSystemMessageReader(source);
    }

    public void resetStatusByte() {
        lastStatusByte = 0;
    }

    public MidiMessage readMessage() throws IOException {
        long statusTimeDelta = readVariableLength(source);
        byte status = readByte(source);

        if ((byteToInt(status) & 0x80) == 0) {
            // use last status
            source.unread(status);
            status = lastStatusByte;
        } else {
            lastStatusByte = status;
        }

        MidiEventName eventName = MidiEventName.getNameFromID((byte) (byteToInt(status) & 0xF0));

        MidiMessageHeader messageHeader = new MidiMessageHeader();
        messageHeader.status = status;
        messageHeader.timeDelta = statusTimeDelta;

        MidiMessage message;

        switch (eventName) {
            case VoiceNoteOff: {
                message = readVoiceNoteOff(messageHeader);
                break;
            }
            case VoiceNoteOn: {
                message = readVoiceNoteOn(messageHeader);
                break;
            }
            case VoiceAfterTouch: {
                message = readVoiceAfterTouch(messageHeader);
                break;
            }
            case VoiceControlChange: {
                message = readVoiceControlChange(messageHeader);
                break;
            }
            case VoiceProgramChange: {
                message = readVoiceProgramChange(messageHeader);
                break;
            }
            case VoiceChannelPressure: {
                message = readVoiceChannelPressure(messageHeader);
                break;
            }
            case VoicePitchBend: {
                message = readVoicePitchBend(messageHeader);
                break;
            }
            case SystemExclusive: {
                message = readSystemExclusive(messageHeader);
                break;
            }
            default:
                throw new MidiLoadError();
        }

        return message;
    }

    public MidiVoiceNoteOff readVoiceNoteOff(MidiMessageHeader messageHeader) throws IOException {
        MidiVoiceNoteOff message = new MidiVoiceNoteOff();
        message.channel = getChannelFromStatus(messageHeader.status);
        message.noteId = readByte(source);
        message.noteVelocity = readByte(source);
        message.timeDelta = messageHeader.timeDelta;
        return message;
    }

    public MidiVoiceNoteOn readVoiceNoteOn(MidiMessageHeader messageHeader) throws IOException {
        MidiVoiceNoteOn message = new MidiVoiceNoteOn();
        message.channel = getChannelFromStatus(messageHeader.status);
        message.noteId = readByte(source);
        message.noteVelocity = readByte(source);
        message.timeDelta = messageHeader.timeDelta;
        return message;
    }

    public MidiVoiceAfterTouch readVoiceAfterTouch(MidiMessageHeader messageHeader) throws IOException {
        MidiVoiceAfterTouch message = new MidiVoiceAfterTouch();
        message.channel = getChannelFromStatus(messageHeader.status);
        message.noteId = readByte(source);
        message.noteVelocity = readByte(source);
        message.timeDelta = messageHeader.timeDelta;
        return message;
    }

    public MidiVoiceControlChange readVoiceControlChange(MidiMessageHeader messageHeader) throws IOException {
        MidiVoiceControlChange message = new MidiVoiceControlChange();
        message.channel = getChannelFromStatus(messageHeader.status);
        message.noteId = readByte(source);
        message.noteVelocity = readByte(source);
        message.timeDelta = messageHeader.timeDelta;
        return message;
    }

    public MidiVoiceProgramChange readVoiceProgramChange(MidiMessageHeader messageHeader) throws IOException {
        MidiVoiceProgramChange message = new MidiVoiceProgramChange();
        message.channel = getChannelFromStatus(messageHeader.status);
        message.programId = readByte(source);
        message.timeDelta = messageHeader.timeDelta;
        return message;
    }

    public MidiVoiceChannelPressure readVoiceChannelPressure(MidiMessageHeader messageHeader) throws IOException {
        MidiVoiceChannelPressure message = new MidiVoiceChannelPressure();
        message.channel = getChannelFromStatus(messageHeader.status);
        message.channelPressure = readByte(source);
        message.timeDelta = messageHeader.timeDelta;
        return message;
    }

    public MidiVoicePitchBend readVoicePitchBend(MidiMessageHeader messageHeader) throws IOException {
        MidiVoicePitchBend message = new MidiVoicePitchBend();
        message.channel = getChannelFromStatus(messageHeader.status);
        message.nLS7B = readByte(source);
        message.nMS7B = readByte(source);
        message.timeDelta = messageHeader.timeDelta;
        return message;
    }

    public MidiSystemExclusive readSystemExclusive(MidiMessageHeader messageHeader) throws IOException {
        return systemMessageReader.readSystemMessage(messageHeader);
    }

    private static enum MidiEventName {
        VoiceNoteOff(0x80),
        VoiceNoteOn(0x90),
        VoiceAfterTouch(0xA0),
        VoiceControlChange(0xB0),
        VoiceProgramChange(0xC0),
        VoiceChannelPressure(0xD0),
        VoicePitchBend(0xE0),
        SystemExclusive(0xF0);

        private final byte value;

        private MidiEventName(int value) {
            this.value = (byte) (value & 0xFF);
        }

        public static MidiEventName getNameFromID(byte id) {
            for (MidiEventName name : MidiEventName.values()) {
                if (id == name.value) {
                    return name;
                }
            }
            throw new IllegalArgumentException("Event does not exist");
        }
    }

    public static class MidiMessageHeader {
        public byte status;
        public long timeDelta;
    }

    private static int getChannelFromStatus(byte status) {
        return byteToInt(status) & 0x0F;
    }
}
