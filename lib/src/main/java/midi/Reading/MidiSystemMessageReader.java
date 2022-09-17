
package midi.Reading;

import static midi.Reading.ReadingUtils.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import midi.Data.MidiSystemEventName;
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

            MidiSystemExclusive systemMessage;

            try {
                MidiSystemEventName systemEventName = MidiSystemEventName.getNameFromID(type);

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
            } catch (IllegalArgumentException e) {
                systemMessage = readSystemUnknown(type, length);
            }

            return systemMessage;
        } else if (statusInt == 0xF0) {
            // long length = readVariableLength(source);

            // MidiSystemMessageHeader systemMessageHeader = new MidiSystemMessageHeader();
            // systemMessageHeader.messageHeader = messageHeader;
            // systemMessageHeader.length = length;
            return readSystemManufacturerStart();
        } else if (statusInt == 0xF7) {
            // long length = readVariableLength(source);

            // MidiSystemMessageHeader systemMessageHeader = new MidiSystemMessageHeader();
            // systemMessageHeader.messageHeader = messageHeader;
            // systemMessageHeader.length = length;
            return readSystemManufacturerStart();
        }

        throw new IOException("Unrecognised system event type");
    }

    public MidiSystemSequence readSystemSequence(MidiSystemMessageHeader systemMessageHeader) throws IOException {
        MidiSystemSequence message = new MidiSystemSequence();
        message.timeDelta = systemMessageHeader.messageHeader.timeDelta;
        message.sequence1 = byteToInt(readByte(source));
        message.sequence2 = byteToInt(readByte(source));
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
        message.hours = byteToInt(readByte(source));
        message.minutes = byteToInt(readByte(source));
        message.seconds = byteToInt(readByte(source));
        message.FR = byteToInt(readByte(source));
        message.FF = byteToInt(readByte(source));

        message.timeDelta = systemMessageHeader.messageHeader.timeDelta;
        return message;
    }

    public MidiSystemTimeSignature readSystemTimeSignature(MidiSystemMessageHeader systemMessageHeader)
            throws IOException {
        MidiSystemTimeSignature message = new MidiSystemTimeSignature();
        message.timeSignitureUpper = byteToInt(readByte(source));
        message.timeSignitureLower = byteToInt(readByte(source));
        message.clocksPerTick = byteToInt(readByte(source));
        message.ticks32per24Clocks = byteToInt(readByte(source));

        message.timeDelta = systemMessageHeader.messageHeader.timeDelta;
        return message;
    }

    public MidiSystemKeySignature readSystemKeySignature(MidiSystemMessageHeader systemMessageHeader)
            throws IOException {
        MidiSystemKeySignature message = new MidiSystemKeySignature();
        message.keySigniture = byteToInt(readByte(source));
        message.minorKey = byteToInt(readByte(source));
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

    public MidiSystemManufacturerStart readSystemManufacturerStart()
            throws IOException {
        MidiSystemManufacturerStart message = new MidiSystemManufacturerStart();

        ArrayList<Byte> bytes = new ArrayList<>();
        bytes.add((byte) 0xF0);

        byte currentByte;
        do {
            currentByte = readByte(source);
            if ((currentByte & 0xFF) != 0xF7)
                bytes.add(currentByte);
        } while ((currentByte & 0xFF) != 0xF7);

        message.dataBlob = listToByteArray(bytes);

        return message;
    }

    public MidiSystemManufacturerEnd readSystemManufacturerEnd()
            throws IOException {
        MidiSystemManufacturerEnd message = new MidiSystemManufacturerEnd();
        final ArrayList<Byte> bytes = new ArrayList<>();
        byte currentByte;
        do {
            currentByte = readByte(source);
            if ((currentByte & 0xFF) != 0xF7)
                bytes.add(currentByte);
        } while ((currentByte & 0xFF) != 0xF7);

        message.dataBlob = listToByteArray(bytes);

        return message;
    }

    public MidiSystemUnknown readSystemUnknown(byte type, long length)
            throws IOException {
        MidiSystemUnknown message = new MidiSystemUnknown();
        if (length > Integer.MAX_VALUE) {
            throw new IOException("Cannot allocate that many bytes");
        }

        message.dataBlob = source.readNBytes((int) length);
        message.type = type;

        return message;
    }

    public static class MidiSystemMessageHeader {
        public MidiMessageHeader messageHeader;
        public long length;
    }
}