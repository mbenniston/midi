
package midi.Reading;

import static midi.Reading.ReadingUtils.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import midi.Data.MidiSystemEventName;
import midi.Data.Event.MidiEvents.MidiSystemExclusive;
import midi.Data.Event.MidiEvents.MidiSystemEvents.*;
import midi.Reading.MidiFileReader.MidiLoadError;
import midi.Reading.MidiEventReader.MidiEventHeader;

public class MidiSystemEventReader {
    private final InputStream source;

    public MidiSystemEventReader(InputStream source) {
        this.source = source;
    }

    public MidiSystemExclusive readSystemEvent(MidiEventHeader eventHeader) throws IOException {
        int statusInt = byteToInt(eventHeader.status);

        if (statusInt == 0xFF) {
            byte type = readByte(source);
            long length = readVariableLength(source);

            MidiSystemEventHeader systemEventHeader = new MidiSystemEventHeader();
            systemEventHeader.eventHeader = eventHeader;
            systemEventHeader.length = length;

            MidiSystemExclusive systemEvent;

            try {
                MidiSystemEventName systemEventName = MidiSystemEventName.getNameFromID(type);

                switch (systemEventName) {
                    case MetaSequence: {
                        systemEvent = readSystemSequence(systemEventHeader);
                        break;
                    }
                    case MetaText: {
                        systemEvent = readSystemText(systemEventHeader);
                        break;
                    }
                    case MetaCopyright: {
                        systemEvent = readSystemCopyright(systemEventHeader);
                        break;
                    }
                    case MetaTrackName: {
                        systemEvent = readSystemTrackName(systemEventHeader);
                        break;
                    }
                    case MetaInstrumentName: {
                        systemEvent = readSystemInstrumentName(systemEventHeader);
                        break;
                    }
                    case MetaLyrics: {
                        systemEvent = readSystemLyrics(systemEventHeader);
                        break;
                    }
                    case MetaMarker: {
                        systemEvent = readSystemMarker(systemEventHeader);
                        break;
                    }
                    case MetaCuePoint: {
                        systemEvent = readSystemCuePoint(systemEventHeader);
                        break;
                    }
                    case MetaChannelPrefix: {
                        systemEvent = readSystemChannelPrefix(systemEventHeader);
                        break;
                    }
                    case MetaEndOfTrack: {
                        systemEvent = readSystemEndOfTrack(systemEventHeader);
                        break;
                    }
                    case MetaSetTempo: {
                        systemEvent = readSystemSetTempo(systemEventHeader);
                        break;
                    }
                    case MetaSMPTEOffset: {
                        systemEvent = readSystemSMPTEOffset(systemEventHeader);
                        break;
                    }
                    case MetaTimeSignature: {
                        systemEvent = readSystemTimeSignature(systemEventHeader);
                        break;
                    }
                    case MetaKeySignature: {
                        systemEvent = readSystemKeySignature(systemEventHeader);
                        break;
                    }
                    case MetaSequencerSpecific: {
                        systemEvent = readSystemSequencerSpecific(systemEventHeader);
                        break;
                    }
                    default:
                        throw new MidiLoadError();
                }
            } catch (IllegalArgumentException e) {
                systemEvent = readSystemUnknown(type, length);
            }

            return systemEvent;
        } else if (statusInt == 0xF0) {
            // long length = readVariableLength(source);

            // MidiSystemEventHeader systemEventHeader = new MidiSystemEventHeader();
            // systemEventHeader.eventHeader = eventHeader;
            // systemEventHeader.length = length;
            return readSystemManufacturerStart();
        } else if (statusInt == 0xF7) {
            // long length = readVariableLength(source);

            // MidiSystemEventHeader systemEventHeader = new MidiSystemEventHeader();
            // systemEventHeader.eventHeader = eventHeader;
            // systemEventHeader.length = length;
            return readSystemManufacturerStart();
        }

        throw new IOException("Unrecognised system event type");
    }

    public MidiSystemSequence readSystemSequence(MidiSystemEventHeader systemEventHeader) throws IOException {
        MidiSystemSequence event = new MidiSystemSequence();
        event.timeDelta = systemEventHeader.eventHeader.timeDelta;
        event.sequence1 = byteToInt(readByte(source));
        event.sequence2 = byteToInt(readByte(source));
        return event;
    }

    public MidiSystemText readSystemText(MidiSystemEventHeader systemEventHeader) throws IOException {
        MidiSystemText event = new MidiSystemText();
        event.text = readString(source, systemEventHeader.length);
        event.timeDelta = systemEventHeader.eventHeader.timeDelta;
        return event;
    }

    public MidiSystemCopyright readSystemCopyright(MidiSystemEventHeader systemEventHeader) throws IOException {
        MidiSystemCopyright event = new MidiSystemCopyright();
        event.copyright = readString(source, systemEventHeader.length);
        event.timeDelta = systemEventHeader.eventHeader.timeDelta;
        return event;
    }

    public MidiSystemTrackName readSystemTrackName(MidiSystemEventHeader systemEventHeader) throws IOException {
        MidiSystemTrackName event = new MidiSystemTrackName();
        event.trackName = readString(source, systemEventHeader.length);
        event.timeDelta = systemEventHeader.eventHeader.timeDelta;
        return event;
    }

    public MidiSystemInstrumentName readSystemInstrumentName(MidiSystemEventHeader systemEventHeader)
            throws IOException {
        MidiSystemInstrumentName event = new MidiSystemInstrumentName();
        event.instrumentName = readString(source, systemEventHeader.length);
        event.timeDelta = systemEventHeader.eventHeader.timeDelta;
        return event;
    }

    public MidiSystemLyrics readSystemLyrics(MidiSystemEventHeader systemEventHeader) throws IOException {
        MidiSystemLyrics event = new MidiSystemLyrics();
        event.lyrics = readString(source, systemEventHeader.length);
        event.timeDelta = systemEventHeader.eventHeader.timeDelta;
        return event;
    }

    public MidiSystemMarker readSystemMarker(MidiSystemEventHeader systemEventHeader) throws IOException {
        MidiSystemMarker event = new MidiSystemMarker();
        event.marker = readString(source, systemEventHeader.length);
        event.timeDelta = systemEventHeader.eventHeader.timeDelta;
        return event;
    }

    public MidiSystemCuePoint readSystemCuePoint(MidiSystemEventHeader systemEventHeader) throws IOException {
        MidiSystemCuePoint event = new MidiSystemCuePoint();
        event.cue = readString(source, systemEventHeader.length);
        event.timeDelta = systemEventHeader.eventHeader.timeDelta;
        return event;
    }

    public MidiSystemChannelPrefix readSystemChannelPrefix(MidiSystemEventHeader systemEventHeader)
            throws IOException {
        MidiSystemChannelPrefix event = new MidiSystemChannelPrefix();
        event.prefix = readByte(source);
        event.timeDelta = systemEventHeader.eventHeader.timeDelta;
        return event;
    }

    public MidiSystemEndOfTrack readSystemEndOfTrack(MidiSystemEventHeader systemEventHeader) throws IOException {
        MidiSystemEndOfTrack event = new MidiSystemEndOfTrack();
        event.timeDelta = systemEventHeader.eventHeader.timeDelta;
        return event;
    }

    public MidiSystemSetTempo readSystemSetTempo(MidiSystemEventHeader systemEventHeader) throws IOException {
        MidiSystemSetTempo event = new MidiSystemSetTempo();
        event.microsecondsPerQuarterNote = readUnsignedIntFrom3Bytes(source);
        event.timeDelta = systemEventHeader.eventHeader.timeDelta;
        return event;
    }

    public MidiSystemSMPTEOffset readSystemSMPTEOffset(MidiSystemEventHeader systemEventHeader) throws IOException {
        MidiSystemSMPTEOffset event = new MidiSystemSMPTEOffset();
        event.hours = byteToInt(readByte(source));
        event.minutes = byteToInt(readByte(source));
        event.seconds = byteToInt(readByte(source));
        event.frameRate = byteToInt(readByte(source));
        event.fractionalFrames = byteToInt(readByte(source));

        event.timeDelta = systemEventHeader.eventHeader.timeDelta;
        return event;
    }

    public MidiSystemTimeSignature readSystemTimeSignature(MidiSystemEventHeader systemEventHeader)
            throws IOException {
        MidiSystemTimeSignature event = new MidiSystemTimeSignature();
        event.timeSignitureUpper = byteToInt(readByte(source));
        event.timeSignitureLower = byteToInt(readByte(source));
        event.clocksPerTick = byteToInt(readByte(source));
        event.ticks32per24Clocks = byteToInt(readByte(source));

        event.timeDelta = systemEventHeader.eventHeader.timeDelta;
        return event;
    }

    public MidiSystemKeySignature readSystemKeySignature(MidiSystemEventHeader systemEventHeader)
            throws IOException {
        MidiSystemKeySignature event = new MidiSystemKeySignature();
        event.keySigniture = byteToInt(readByte(source));
        event.minorKey = byteToInt(readByte(source));
        event.timeDelta = systemEventHeader.eventHeader.timeDelta;
        return event;
    }

    public MidiSystemSequencerSpecific readSystemSequencerSpecific(MidiSystemEventHeader systemEventHeader)
            throws IOException {
        MidiSystemSequencerSpecific event = new MidiSystemSequencerSpecific();
        event.specific = readString(source, systemEventHeader.length);
        event.timeDelta = systemEventHeader.eventHeader.timeDelta;
        return event;
    }

    public MidiSystemManufacturerStart readSystemManufacturerStart()
            throws IOException {
        MidiSystemManufacturerStart event = new MidiSystemManufacturerStart();

        ArrayList<Byte> bytes = new ArrayList<>();
        bytes.add((byte) 0xF0);

        byte currentByte;
        do {
            currentByte = readByte(source);
            if ((currentByte & 0xFF) != 0xF7)
                bytes.add(currentByte);
        } while ((currentByte & 0xFF) != 0xF7);

        event.dataBlob = listToByteArray(bytes);

        return event;
    }

    public MidiSystemManufacturerEnd readSystemManufacturerEnd()
            throws IOException {
        MidiSystemManufacturerEnd event = new MidiSystemManufacturerEnd();
        final ArrayList<Byte> bytes = new ArrayList<>();
        byte currentByte;
        do {
            currentByte = readByte(source);
            if ((currentByte & 0xFF) != 0xF7)
                bytes.add(currentByte);
        } while ((currentByte & 0xFF) != 0xF7);

        event.dataBlob = listToByteArray(bytes);

        return event;
    }

    public MidiSystemUnknown readSystemUnknown(byte type, long length)
            throws IOException {
        MidiSystemUnknown event = new MidiSystemUnknown();
        if (length > Integer.MAX_VALUE) {
            throw new IOException("Cannot allocate that many bytes");
        }

        event.dataBlob = source.readNBytes((int) length);
        event.type = type;

        return event;
    }

    public static class MidiSystemEventHeader {
        public MidiEventHeader eventHeader;
        public long length;
    }
}