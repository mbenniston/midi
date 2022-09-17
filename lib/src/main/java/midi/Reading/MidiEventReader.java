package midi.Reading;

import static midi.Reading.ReadingUtils.*;

import java.io.IOException;
import java.io.PushbackInputStream;

import midi.Data.MidiEventName;
import midi.Data.Event.MidiEvent;
import midi.Data.Event.MidiEvents.*;
import midi.Reading.MidiFileReader.MidiLoadError;

public class MidiEventReader {
    private final PushbackInputStream source;

    private final MidiSystemEventReader systemEventReader;

    private byte lastStatusByte = 0;

    public MidiEventReader(PushbackInputStream source) {
        this.source = source;
        this.systemEventReader = new MidiSystemEventReader(source);
    }

    public void resetStatusByte() {
        lastStatusByte = 0;
    }

    public MidiEvent readEvent() throws IOException {
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

        MidiEventHeader eventHeader = new MidiEventHeader();
        eventHeader.status = status;
        eventHeader.timeDelta = statusTimeDelta;

        MidiEvent event;

        switch (eventName) {
            case VoiceNoteOff: {
                event = readVoiceNoteOff(eventHeader);
                break;
            }
            case VoiceNoteOn: {
                event = readVoiceNoteOn(eventHeader);
                break;
            }
            case VoiceAfterTouch: {
                event = readVoiceAfterTouch(eventHeader);
                break;
            }
            case VoiceControlChange: {
                event = readVoiceControlChange(eventHeader);
                break;
            }
            case VoiceProgramChange: {
                event = readVoiceProgramChange(eventHeader);
                break;
            }
            case VoiceChannelPressure: {
                event = readVoiceChannelPressure(eventHeader);
                break;
            }
            case VoicePitchBend: {
                event = readVoicePitchBend(eventHeader);
                break;
            }
            case SystemExclusive: {
                event = readSystemExclusive(eventHeader);
                break;
            }
            default:
                throw new MidiLoadError();
        }

        return event;
    }

    public MidiVoiceNoteOff readVoiceNoteOff(MidiEventHeader eventHeader) throws IOException {
        MidiVoiceNoteOff event = new MidiVoiceNoteOff();
        event.channel = getChannelFromStatus(eventHeader.status);
        event.noteId = byteToInt(readByte(source));
        event.noteVelocity = byteToInt(readByte(source));
        event.timeDelta = eventHeader.timeDelta;
        return event;
    }

    public MidiVoiceNoteOn readVoiceNoteOn(MidiEventHeader eventHeader) throws IOException {
        MidiVoiceNoteOn event = new MidiVoiceNoteOn();
        event.channel = getChannelFromStatus(eventHeader.status);
        event.noteId = byteToInt(readByte(source));
        event.noteVelocity = byteToInt(readByte(source));
        event.timeDelta = eventHeader.timeDelta;
        return event;
    }

    public MidiVoiceAfterTouch readVoiceAfterTouch(MidiEventHeader eventHeader) throws IOException {
        MidiVoiceAfterTouch event = new MidiVoiceAfterTouch();
        event.channel = getChannelFromStatus(eventHeader.status);
        event.noteId = byteToInt(readByte(source));
        event.noteVelocity = byteToInt(readByte(source));
        event.timeDelta = eventHeader.timeDelta;
        return event;
    }

    public MidiVoiceControlChange readVoiceControlChange(MidiEventHeader eventHeader) throws IOException {
        MidiVoiceControlChange event = new MidiVoiceControlChange();
        event.channel = getChannelFromStatus(eventHeader.status);
        event.noteId = byteToInt(readByte(source));
        event.noteVelocity = byteToInt(readByte(source));
        event.timeDelta = eventHeader.timeDelta;
        return event;
    }

    public MidiVoiceProgramChange readVoiceProgramChange(MidiEventHeader eventHeader) throws IOException {
        MidiVoiceProgramChange event = new MidiVoiceProgramChange();
        event.channel = getChannelFromStatus(eventHeader.status);
        event.programId = byteToInt(readByte(source));
        event.timeDelta = eventHeader.timeDelta;
        return event;
    }

    public MidiVoiceChannelPressure readVoiceChannelPressure(MidiEventHeader eventHeader) throws IOException {
        MidiVoiceChannelPressure event = new MidiVoiceChannelPressure();
        event.channel = getChannelFromStatus(eventHeader.status);
        event.channelPressure = byteToInt(readByte(source));
        event.timeDelta = eventHeader.timeDelta;
        return event;
    }

    public MidiVoicePitchBend readVoicePitchBend(MidiEventHeader eventHeader) throws IOException {
        MidiVoicePitchBend event = new MidiVoicePitchBend();
        event.channel = getChannelFromStatus(eventHeader.status);
        event.nLS7B = byteToInt(readByte(source));
        event.nMS7B = byteToInt(readByte(source));
        event.timeDelta = eventHeader.timeDelta;
        return event;
    }

    public MidiSystemExclusive readSystemExclusive(MidiEventHeader eventHeader) throws IOException {
        return systemEventReader.readSystemEvent(eventHeader);
    }

    private static int getChannelFromStatus(byte status) {
        return byteToInt(status) & 0x0F;
    }

    public static class MidiEventHeader {
        public byte status;
        public long timeDelta;
    }
}
