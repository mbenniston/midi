
package midi.Reading;

import static midi.Reading.ReadingUtils.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import midi.Data.MidiMetaEventName;
import midi.Data.Event.MidiEvents.MidiMetaEvent;
import midi.Data.Event.MidiEvents.MidiMetaEvents.*;
import midi.Reading.MidiFileReader.MidiLoadError;
import midi.Reading.MidiEventReader.MidiEventHeader;

public class MidiSystemEventReader {
    private final InputStream source;

    public MidiSystemEventReader(InputStream source) {
        this.source = source;
    }

    public MidiMetaEvent readSystemEvent(MidiEventHeader eventHeader) throws IOException {
        int statusInt = byteToInt(eventHeader.status);

        if (statusInt == 0xFF) {
            byte type = readByte(source);
            long length = readVariableLength(source);

            MidiSystemEventHeader systemEventHeader = new MidiSystemEventHeader();
            systemEventHeader.eventHeader = eventHeader;
            systemEventHeader.length = length;

            MidiMetaEvent systemEvent;

            try {
                MidiMetaEventName systemEventName = MidiMetaEventName.getNameFromID(type);

                switch (systemEventName) {
                    case Sequence: {
                        systemEvent = readSystemSequence(systemEventHeader);
                        break;
                    }
                    case Text: {
                        systemEvent = readSystemText(systemEventHeader);
                        break;
                    }
                    case Copyright: {
                        systemEvent = readSystemCopyright(systemEventHeader);
                        break;
                    }
                    case TrackName: {
                        systemEvent = readSystemTrackName(systemEventHeader);
                        break;
                    }
                    case InstrumentName: {
                        systemEvent = readSystemInstrumentName(systemEventHeader);
                        break;
                    }
                    case Lyrics: {
                        systemEvent = readSystemLyrics(systemEventHeader);
                        break;
                    }
                    case Marker: {
                        systemEvent = readSystemMarker(systemEventHeader);
                        break;
                    }
                    case CuePoint: {
                        systemEvent = readSystemCuePoint(systemEventHeader);
                        break;
                    }
                    case ChannelPrefix: {
                        systemEvent = readSystemChannelPrefix(systemEventHeader);
                        break;
                    }
                    case EndOfTrack: {
                        systemEvent = readSystemEndOfTrack(systemEventHeader);
                        break;
                    }
                    case SetTempo: {
                        systemEvent = readSystemSetTempo(systemEventHeader);
                        break;
                    }
                    case SMPTEOffset: {
                        systemEvent = readSystemSMPTEOffset(systemEventHeader);
                        break;
                    }
                    case TimeSignature: {
                        systemEvent = readSystemTimeSignature(systemEventHeader);
                        break;
                    }
                    case KeySignature: {
                        systemEvent = readSystemKeySignature(systemEventHeader);
                        break;
                    }
                    case SequencerSpecific: {
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

    public MidiMetaSequence readSystemSequence(MidiSystemEventHeader systemEventHeader) throws IOException {
        MidiMetaSequence event = new MidiMetaSequence();
        event.timeDelta = systemEventHeader.eventHeader.timeDelta;
        event.sequence1 = byteToInt(readByte(source));
        event.sequence2 = byteToInt(readByte(source));
        return event;
    }

    public MidiMetaText readSystemText(MidiSystemEventHeader systemEventHeader) throws IOException {
        MidiMetaText event = new MidiMetaText();
        event.text = readString(source, systemEventHeader.length);
        event.timeDelta = systemEventHeader.eventHeader.timeDelta;
        return event;
    }

    public MidiMetaCopyright readSystemCopyright(MidiSystemEventHeader systemEventHeader) throws IOException {
        MidiMetaCopyright event = new MidiMetaCopyright();
        event.copyright = readString(source, systemEventHeader.length);
        event.timeDelta = systemEventHeader.eventHeader.timeDelta;
        return event;
    }

    public MidiMetaTrackName readSystemTrackName(MidiSystemEventHeader systemEventHeader) throws IOException {
        MidiMetaTrackName event = new MidiMetaTrackName();
        event.trackName = readString(source, systemEventHeader.length);
        event.timeDelta = systemEventHeader.eventHeader.timeDelta;
        return event;
    }

    public MidiMetaInstrumentName readSystemInstrumentName(MidiSystemEventHeader systemEventHeader)
            throws IOException {
        MidiMetaInstrumentName event = new MidiMetaInstrumentName();
        event.instrumentName = readString(source, systemEventHeader.length);
        event.timeDelta = systemEventHeader.eventHeader.timeDelta;
        return event;
    }

    public MidiMetaLyrics readSystemLyrics(MidiSystemEventHeader systemEventHeader) throws IOException {
        MidiMetaLyrics event = new MidiMetaLyrics();
        event.lyrics = readString(source, systemEventHeader.length);
        event.timeDelta = systemEventHeader.eventHeader.timeDelta;
        return event;
    }

    public MidiMetaMarker readSystemMarker(MidiSystemEventHeader systemEventHeader) throws IOException {
        MidiMetaMarker event = new MidiMetaMarker();
        event.marker = readString(source, systemEventHeader.length);
        event.timeDelta = systemEventHeader.eventHeader.timeDelta;
        return event;
    }

    public MidiMetaCuePoint readSystemCuePoint(MidiSystemEventHeader systemEventHeader) throws IOException {
        MidiMetaCuePoint event = new MidiMetaCuePoint();
        event.cue = readString(source, systemEventHeader.length);
        event.timeDelta = systemEventHeader.eventHeader.timeDelta;
        return event;
    }

    public MidiMetaChannelPrefix readSystemChannelPrefix(MidiSystemEventHeader systemEventHeader)
            throws IOException {
        MidiMetaChannelPrefix event = new MidiMetaChannelPrefix();
        event.prefix = readByte(source);
        event.timeDelta = systemEventHeader.eventHeader.timeDelta;
        return event;
    }

    public MidiMetaEndOfTrack readSystemEndOfTrack(MidiSystemEventHeader systemEventHeader) throws IOException {
        MidiMetaEndOfTrack event = new MidiMetaEndOfTrack();
        event.timeDelta = systemEventHeader.eventHeader.timeDelta;
        return event;
    }

    public MidiMetaSetTempo readSystemSetTempo(MidiSystemEventHeader systemEventHeader) throws IOException {
        MidiMetaSetTempo event = new MidiMetaSetTempo();
        event.microsecondsPerQuarterNote = readUnsignedIntFrom3Bytes(source);
        event.timeDelta = systemEventHeader.eventHeader.timeDelta;
        return event;
    }

    public MidiMetaSMPTEOffset readSystemSMPTEOffset(MidiSystemEventHeader systemEventHeader) throws IOException {
        MidiMetaSMPTEOffset event = new MidiMetaSMPTEOffset();
        event.hours = byteToInt(readByte(source));
        event.minutes = byteToInt(readByte(source));
        event.seconds = byteToInt(readByte(source));
        event.frameRate = byteToInt(readByte(source));
        event.fractionalFrames = byteToInt(readByte(source));

        event.timeDelta = systemEventHeader.eventHeader.timeDelta;
        return event;
    }

    public MidiMetaTimeSignature readSystemTimeSignature(MidiSystemEventHeader systemEventHeader)
            throws IOException {
        MidiMetaTimeSignature event = new MidiMetaTimeSignature();
        event.timeSignitureUpper = byteToInt(readByte(source));
        event.timeSignitureLower = byteToInt(readByte(source));
        event.clocksPerTick = byteToInt(readByte(source));
        event.ticks32per24Clocks = byteToInt(readByte(source));

        event.timeDelta = systemEventHeader.eventHeader.timeDelta;
        return event;
    }

    public MidiMetaKeySignature readSystemKeySignature(MidiSystemEventHeader systemEventHeader)
            throws IOException {
        MidiMetaKeySignature event = new MidiMetaKeySignature();
        event.keySigniture = byteToInt(readByte(source));
        event.minorKey = byteToInt(readByte(source));
        event.timeDelta = systemEventHeader.eventHeader.timeDelta;
        return event;
    }

    public MidiMetaSequencerSpecific readSystemSequencerSpecific(MidiSystemEventHeader systemEventHeader)
            throws IOException {
        MidiMetaSequencerSpecific event = new MidiMetaSequencerSpecific();
        event.specific = readString(source, systemEventHeader.length);
        event.timeDelta = systemEventHeader.eventHeader.timeDelta;
        return event;
    }

    public MidiMetaManufacturerStart readSystemManufacturerStart()
            throws IOException {
        MidiMetaManufacturerStart event = new MidiMetaManufacturerStart();

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

    public MidiMetaManufacturerEnd readSystemManufacturerEnd()
            throws IOException {
        MidiMetaManufacturerEnd event = new MidiMetaManufacturerEnd();
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

    public MidiMetaUnknown readSystemUnknown(byte type, long length)
            throws IOException {
        MidiMetaUnknown event = new MidiMetaUnknown();
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