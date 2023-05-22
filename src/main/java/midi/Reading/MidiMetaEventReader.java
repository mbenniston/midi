package midi.Reading;

import midi.Data.Event.MidiEvents.MidiMetaEvent;
import midi.Data.Event.MidiEvents.MidiMetaEvents.*;
import midi.Data.MidiMetaEventName;
import midi.Reading.MidiEventReader.MidiEventHeader;
import midi.Reading.MidiFileReader.MidiLoadError;

import java.io.IOException;
import java.io.InputStream;

import static midi.Reading.ReadingUtils.*;

/**
 * Reads midi meta event messages from a stream.
 */
public class MidiMetaEventReader {
    private final InputStream source;

    public MidiMetaEventReader(InputStream source) {
        this.source = source;
    }

    public MidiMetaEvent readMetaEvent(MidiEventHeader eventHeader) throws IOException, MidiLoadError {
        byte type = readByte(source);
        long length = readVariableLength(source);

        MidiMetaEventHeader metaEventHeader = new MidiMetaEventHeader();
        metaEventHeader.eventHeader = eventHeader;
        metaEventHeader.length = length;

        MidiMetaEvent metaEvent;

        try {
            MidiMetaEventName metaEventName = MidiMetaEventName.getNameFromID(type);

            switch (metaEventName) {
                case Sequence: {
                    metaEvent = readMetaSequence(metaEventHeader);
                    break;
                }
                case Text: {
                    metaEvent = readMetaText(metaEventHeader);
                    break;
                }
                case Copyright: {
                    metaEvent = readMetaCopyright(metaEventHeader);
                    break;
                }
                case TrackName: {
                    metaEvent = readMetaTrackName(metaEventHeader);
                    break;
                }
                case InstrumentName: {
                    metaEvent = readMetaInstrumentName(metaEventHeader);
                    break;
                }
                case Lyrics: {
                    metaEvent = readMetaLyrics(metaEventHeader);
                    break;
                }
                case Marker: {
                    metaEvent = readMetaMarker(metaEventHeader);
                    break;
                }
                case CuePoint: {
                    metaEvent = readMetaCuePoint(metaEventHeader);
                    break;
                }
                case ChannelPrefix: {
                    metaEvent = readMetaChannelPrefix(metaEventHeader);
                    break;
                }
                case EndOfTrack: {
                    metaEvent = readMetaEndOfTrack(metaEventHeader);
                    break;
                }
                case SetTempo: {
                    metaEvent = readMetaSetTempo(metaEventHeader);
                    break;
                }
                case SMPTEOffset: {
                    metaEvent = readMetaSMPTEOffset(metaEventHeader);
                    break;
                }
                case TimeSignature: {
                    metaEvent = readMetaTimeSignature(metaEventHeader);
                    break;
                }
                case KeySignature: {
                    metaEvent = readMetaKeySignature(metaEventHeader);
                    break;
                }
                case SequencerSpecific: {
                    metaEvent = readMetaSequencerSpecific(metaEventHeader);
                    break;
                }
                default:
                    throw new MidiLoadError();
            }
        } catch (IllegalArgumentException e) {
            metaEvent = readMetaUnknown(type, metaEventHeader);
        }

        return metaEvent;
    }

    public MidiMetaSequence readMetaSequence(MidiMetaEventHeader metaEventHeader) throws IOException {
        MidiMetaSequence event = new MidiMetaSequence();
        event.timeDelta = metaEventHeader.eventHeader.timeDelta;
        event.sequence1 = byteToInt(readByte(source));
        event.sequence2 = byteToInt(readByte(source));
        return event;
    }

    public MidiMetaText readMetaText(MidiMetaEventHeader metaEventHeader) throws IOException {
        MidiMetaText event = new MidiMetaText();
        event.text = readString(source, metaEventHeader.length);
        event.timeDelta = metaEventHeader.eventHeader.timeDelta;
        return event;
    }

    public MidiMetaCopyright readMetaCopyright(MidiMetaEventHeader metaEventHeader) throws IOException {
        MidiMetaCopyright event = new MidiMetaCopyright();
        event.copyright = readString(source, metaEventHeader.length);
        event.timeDelta = metaEventHeader.eventHeader.timeDelta;
        return event;
    }

    public MidiMetaTrackName readMetaTrackName(MidiMetaEventHeader metaEventHeader) throws IOException {
        MidiMetaTrackName event = new MidiMetaTrackName();
        event.trackName = readString(source, metaEventHeader.length);
        event.timeDelta = metaEventHeader.eventHeader.timeDelta;
        return event;
    }

    public MidiMetaInstrumentName readMetaInstrumentName(MidiMetaEventHeader metaEventHeader)
            throws IOException {
        MidiMetaInstrumentName event = new MidiMetaInstrumentName();
        event.instrumentName = readString(source, metaEventHeader.length);
        event.timeDelta = metaEventHeader.eventHeader.timeDelta;
        return event;
    }

    public MidiMetaLyrics readMetaLyrics(MidiMetaEventHeader metaEventHeader) throws IOException {
        MidiMetaLyrics event = new MidiMetaLyrics();
        event.lyrics = readString(source, metaEventHeader.length);
        event.timeDelta = metaEventHeader.eventHeader.timeDelta;
        return event;
    }

    public MidiMetaMarker readMetaMarker(MidiMetaEventHeader metaEventHeader) throws IOException {
        MidiMetaMarker event = new MidiMetaMarker();
        event.marker = readString(source, metaEventHeader.length);
        event.timeDelta = metaEventHeader.eventHeader.timeDelta;
        return event;
    }

    public MidiMetaCuePoint readMetaCuePoint(MidiMetaEventHeader metaEventHeader) throws IOException {
        MidiMetaCuePoint event = new MidiMetaCuePoint();
        event.cue = readString(source, metaEventHeader.length);
        event.timeDelta = metaEventHeader.eventHeader.timeDelta;
        return event;
    }

    public MidiMetaChannelPrefix readMetaChannelPrefix(MidiMetaEventHeader metaEventHeader)
            throws IOException {
        MidiMetaChannelPrefix event = new MidiMetaChannelPrefix();
        event.prefix = readByte(source);
        event.timeDelta = metaEventHeader.eventHeader.timeDelta;
        return event;
    }

    public MidiMetaEndOfTrack readMetaEndOfTrack(MidiMetaEventHeader metaEventHeader) throws IOException {
        MidiMetaEndOfTrack event = new MidiMetaEndOfTrack();
        event.timeDelta = metaEventHeader.eventHeader.timeDelta;
        return event;
    }

    public MidiMetaSetTempo readMetaSetTempo(MidiMetaEventHeader metaEventHeader) throws IOException {
        MidiMetaSetTempo event = new MidiMetaSetTempo();
        event.microsecondsPerQuarterNote = readUnsignedIntFrom3Bytes(source);
        event.timeDelta = metaEventHeader.eventHeader.timeDelta;
        return event;
    }

    public MidiMetaSMPTEOffset readMetaSMPTEOffset(MidiMetaEventHeader metaEventHeader) throws IOException {
        MidiMetaSMPTEOffset event = new MidiMetaSMPTEOffset();
        event.hours = byteToInt(readByte(source));
        event.minutes = byteToInt(readByte(source));
        event.seconds = byteToInt(readByte(source));
        event.frameRate = byteToInt(readByte(source));
        event.fractionalFrames = byteToInt(readByte(source));

        event.timeDelta = metaEventHeader.eventHeader.timeDelta;
        return event;
    }

    public MidiMetaTimeSignature readMetaTimeSignature(MidiMetaEventHeader metaEventHeader)
            throws IOException {
        MidiMetaTimeSignature event = new MidiMetaTimeSignature();
        event.timeSignitureUpper = byteToInt(readByte(source));
        event.timeSignitureLower = byteToInt(readByte(source));
        event.clocksPerTick = byteToInt(readByte(source));
        event.ticks32per24Clocks = byteToInt(readByte(source));

        event.timeDelta = metaEventHeader.eventHeader.timeDelta;
        return event;
    }

    public MidiMetaKeySignature readMetaKeySignature(MidiMetaEventHeader metaEventHeader)
            throws IOException {
        MidiMetaKeySignature event = new MidiMetaKeySignature();
        event.keySigniture = byteToInt(readByte(source));
        event.minorKey = byteToInt(readByte(source));
        event.timeDelta = metaEventHeader.eventHeader.timeDelta;
        return event;
    }

    public MidiMetaSequencerSpecific readMetaSequencerSpecific(MidiMetaEventHeader metaEventHeader)
            throws IOException {
        MidiMetaSequencerSpecific event = new MidiMetaSequencerSpecific();
        event.specific = readString(source, metaEventHeader.length);
        event.timeDelta = metaEventHeader.eventHeader.timeDelta;
        return event;
    }

    public MidiMetaUnknown readMetaUnknown(byte type, MidiMetaEventHeader metaEventHeader)
            throws IOException {
        MidiMetaUnknown event = new MidiMetaUnknown();
        if (metaEventHeader.length > Integer.MAX_VALUE) {
            throw new IOException("Cannot allocate that many bytes");
        }

        event.dataBlob = source.readNBytes((int) metaEventHeader.length);
        event.type = type;
        event.timeDelta = metaEventHeader.eventHeader.timeDelta;

        return event;
    }

    public static class MidiMetaEventHeader {
        public MidiEventHeader eventHeader;
        public long length;
    }
}