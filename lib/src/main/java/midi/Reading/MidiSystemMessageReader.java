
package midi.Reading;

import static midi.Reading.ReadingUtils.*;

import java.io.IOException;
import java.io.InputStream;

import midi.Data.Message.MidiMessages.MidiSystemExclusive;
import midi.Data.Message.MidiMessages.MidiSystemMessages.*;
import midi.Reading.MidiFileReader.MidiLoadError;
import midi.Reading.MidiMessageReader.MidiMessageHeader;

public class MidiSystemMessageReader {
    private final InputStream source;

    public MidiSystemMessageReader(InputStream source) {
        this.source = source;
    }

    public MidiSystemExclusive readSystemMessage(MidiMessageHeader messageHeader) throws IOException {
        int statusInt = byteToInt(messageHeader.status);

        if (statusInt == 0xFF) {
            byte type = readByte(source);
            long length = readVariableLength(source);

            MidiSystemMessageHeader systemMessageHeader = new MidiSystemMessageHeader();
            systemMessageHeader.messageHeader = messageHeader;
            systemMessageHeader.length = length;

            MidiSystemEventName systemEventName;
            try {
                systemEventName = MidiSystemEventName.getNameFromID(type);
            } catch (IllegalArgumentException e) {
                readString(source, length);
                return null;
            }

            MidiSystemExclusive systemMessage;

            switch (systemEventName) {
                case MetaSequence: {
                    systemMessage = readSystemSequence(systemMessageHeader);
                    break;
                }
                case MetaText: {
                    systemMessage = readSystemText(systemMessageHeader);
                    break;
                }
                case MetaCopyright: {
                    systemMessage = readSystemCopyright(systemMessageHeader);
                    break;
                }
                case MetaTrackName: {
                    systemMessage = readSystemTrackName(systemMessageHeader);
                    break;
                }
                case MetaInstrumentName: {
                    systemMessage = readSystemInstrumentName(systemMessageHeader);
                    break;
                }
                case MetaLyrics: {
                    systemMessage = readSystemLyrics(systemMessageHeader);
                    break;
                }
                case MetaMarker: {
                    systemMessage = readSystemMarker(systemMessageHeader);
                    break;
                }
                case MetaCuePoint: {
                    systemMessage = readSystemCuePoint(systemMessageHeader);
                    break;
                }
                case MetaChannelPrefix: {
                    systemMessage = readSystemChannelPrefix(systemMessageHeader);
                    break;
                }
                case MetaEndOfTrack: {
                    systemMessage = readSystemEndOfTrack(systemMessageHeader);
                    break;
                }
                case MetaSetTempo: {
                    systemMessage = readSystemSetTempo(systemMessageHeader);
                    break;
                }
                case MetaSMPTEOffset: {
                    systemMessage = readSystemSMPTEOffset(systemMessageHeader);
                    break;
                }
                case MetaTimeSignature: {
                    systemMessage = readSystemTimeSignature(systemMessageHeader);
                    break;
                }
                case MetaKeySignature: {
                    systemMessage = readSystemKeySignature(systemMessageHeader);
                    break;
                }
                case MetaSequencerSpecific: {
                    systemMessage = readSystemSequencerSpecific(systemMessageHeader);
                    break;
                }

                default:
                    throw new MidiLoadError();
            }

            return systemMessage;
        } else if (statusInt == 0xF0) {
            System.out.println("data start");
            System.out.println(readString(source));

        } else if (statusInt == 0xF7) {
            System.out.println("data end");
            System.out.println(readString(source));
        }

        return null;
    }

    public MidiSystemSequence readSystemSequence(MidiSystemMessageHeader systemMessageHeader) throws IOException {
        MidiSystemSequence message = new MidiSystemSequence();
        message.timeDelta = systemMessageHeader.messageHeader.timeDelta;
        message.sequence1 = readByte(source);
        message.sequence2 = readByte(source);
        return message;
    }

    public MidiSystemText readSystemText(MidiSystemMessageHeader systemMessageHeader) throws IOException {
        MidiSystemText message = new MidiSystemText();
        message.text = readString(source, systemMessageHeader.length);
        message.timeDelta = systemMessageHeader.messageHeader.timeDelta;
        return message;
    }

    public MidiSystemCopyright readSystemCopyright(MidiSystemMessageHeader systemMessageHeader) throws IOException {
        MidiSystemCopyright message = new MidiSystemCopyright();
        message.copyright = readString(source, systemMessageHeader.length);

        message.timeDelta = systemMessageHeader.messageHeader.timeDelta;
        return message;
    }

    public MidiSystemTrackName readSystemTrackName(MidiSystemMessageHeader systemMessageHeader) throws IOException {
        MidiSystemTrackName message = new MidiSystemTrackName();
        message.trackName = readString(source, systemMessageHeader.length);

        message.timeDelta = systemMessageHeader.messageHeader.timeDelta;
        return message;
    }

    public MidiSystemInstrumentName readSystemInstrumentName(MidiSystemMessageHeader systemMessageHeader)
            throws IOException {
        MidiSystemInstrumentName message = new MidiSystemInstrumentName();
        message.instrumentName = readString(source, systemMessageHeader.length);

        message.timeDelta = systemMessageHeader.messageHeader.timeDelta;
        return message;
    }

    public MidiSystemLyrics readSystemLyrics(MidiSystemMessageHeader systemMessageHeader) throws IOException {
        MidiSystemLyrics message = new MidiSystemLyrics();
        message.lyrics = readString(source, systemMessageHeader.length);

        message.timeDelta = systemMessageHeader.messageHeader.timeDelta;
        return message;
    }

    public MidiSystemMarker readSystemMarker(MidiSystemMessageHeader systemMessageHeader) throws IOException {
        MidiSystemMarker message = new MidiSystemMarker();
        message.marker = readString(source, systemMessageHeader.length);

        message.timeDelta = systemMessageHeader.messageHeader.timeDelta;
        return message;
    }

    public MidiSystemCuePoint readSystemCuePoint(MidiSystemMessageHeader systemMessageHeader) throws IOException {
        MidiSystemCuePoint message = new MidiSystemCuePoint();
        message.cue = readString(source, systemMessageHeader.length);

        message.timeDelta = systemMessageHeader.messageHeader.timeDelta;
        return message;
    }

    public MidiSystemChannelPrefix readSystemChannelPrefix(MidiSystemMessageHeader systemMessageHeader)
            throws IOException {
        MidiSystemChannelPrefix message = new MidiSystemChannelPrefix();
        message.prefix = readByte(source);

        message.timeDelta = systemMessageHeader.messageHeader.timeDelta;
        return message;
    }

    public MidiSystemEndOfTrack readSystemEndOfTrack(MidiSystemMessageHeader systemMessageHeader) throws IOException {
        MidiSystemEndOfTrack message = new MidiSystemEndOfTrack();
        message.timeDelta = systemMessageHeader.messageHeader.timeDelta;
        return message;
    }

    public MidiSystemSetTempo readSystemSetTempo(MidiSystemMessageHeader systemMessageHeader) throws IOException {
        MidiSystemSetTempo message = new MidiSystemSetTempo();
        message.microsecondsPerQuarterNote = readUnsignedIntFrom3Bytes(source);
        message.timeDelta = systemMessageHeader.messageHeader.timeDelta;
        return message;
    }

    public MidiSystemSMPTEOffset readSystemSMPTEOffset(MidiSystemMessageHeader systemMessageHeader) throws IOException {
        MidiSystemSMPTEOffset message = new MidiSystemSMPTEOffset();
        message.hours = readByte(source);
        message.minutes = readByte(source);
        message.seconds = readByte(source);
        message.FR = readByte(source);
        message.FF = readByte(source);

        message.timeDelta = systemMessageHeader.messageHeader.timeDelta;
        return message;
    }

    public MidiSystemTimeSignature readSystemTimeSignature(MidiSystemMessageHeader systemMessageHeader)
            throws IOException {
        MidiSystemTimeSignature message = new MidiSystemTimeSignature();
        message.timeSignitureUpper = readByte(source);
        message.timeSignitureLower = 2 << byteToInt(readByte(source));
        message.clocksPerTick = readByte(source);
        message.ticks32per24Clocks = readByte(source);

        message.timeDelta = systemMessageHeader.messageHeader.timeDelta;
        return message;
    }

    public MidiSystemKeySignature readSystemKeySignature(MidiSystemMessageHeader systemMessageHeader)
            throws IOException {
        MidiSystemKeySignature message = new MidiSystemKeySignature();
        message.keySigniture = readByte(source);
        message.minorKey = readByte(source);
        message.timeDelta = systemMessageHeader.messageHeader.timeDelta;
        return message;
    }

    public MidiSystemSequencerSpecific readSystemSequencerSpecific(MidiSystemMessageHeader systemMessageHeader)
            throws IOException {
        MidiSystemSequencerSpecific message = new MidiSystemSequencerSpecific();
        message.specific = readString(source, systemMessageHeader.length);

        message.timeDelta = systemMessageHeader.messageHeader.timeDelta;
        return message;
    }

    private static enum MidiSystemEventName {
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

        private final byte value;

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

    public static class MidiSystemMessageHeader {
        public MidiMessageHeader messageHeader;
        public long length;
    }

}