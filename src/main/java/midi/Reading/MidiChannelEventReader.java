package midi.Reading;

import midi.Data.Event.MidiEvents.MidiChannelEvent;
import midi.Data.Event.MidiEvents.MidiChannelEvents.*;
import midi.Data.MidiChannelEventName;
import midi.Reading.MidiEventReader.MidiEventHeader;
import midi.Reading.MidiFileReader.MidiLoadError;

import java.io.IOException;
import java.io.PushbackInputStream;

import static midi.Reading.ReadingUtils.byteToInt;
import static midi.Reading.ReadingUtils.readByte;

/**
 * Reads midi channel event messages from a stream.
 */
public class MidiChannelEventReader {
    private final PushbackInputStream source;

    public MidiChannelEventReader(PushbackInputStream source) {
        this.source = source;
    }

    public MidiChannelEvent readChannelEvent(MidiEventHeader eventHeader) throws IOException, MidiLoadError {
        MidiChannelEventName eventName = MidiChannelEventName.getNameFromID((byte) (eventHeader.status & 0xF0));

        switch (eventName) {
            case VoiceNoteOff:
                return readVoiceNoteOff(eventHeader);
            case VoiceNoteOn:
                return readVoiceNoteOn(eventHeader);
            case VoiceAfterTouch:
                return readVoiceAfterTouch(eventHeader);
            case VoiceControlChange:
                return readVoiceControlChange(eventHeader);
            case VoiceProgramChange:
                return readVoiceProgramChange(eventHeader);
            case VoiceChannelPressure:
                return readVoiceChannelPressure(eventHeader);
            case VoicePitchBend:
                return readVoicePitchBend(eventHeader);
            default:
                throw new MidiLoadError();
        }
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
        event.controlId = byteToInt(readByte(source));
        event.controlValue = byteToInt(readByte(source));
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

    private static int getChannelFromStatus(int status) {
        return status & 0x0F;
    }
}
