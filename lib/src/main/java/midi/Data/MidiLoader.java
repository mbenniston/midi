package midi.Data;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.ArrayList;

import midi.MidiMessage.MidiMessage;
import midi.MidiMessage.MidiMessages.*;
import midi.MidiMessage.MidiMessages.MidiSystemMessages.*;

import static midi.Data.ReadingUtils.*;

public class MidiLoader {

    public static MidiFile load(InputStream inputStream) throws MidiLoadError {
        PushbackInputStream stream = new PushbackInputStream(inputStream);

        MidiFile midiFile = new MidiFile();

        MidiHeader header;
        try {
            header = readFileHeader(stream);
        } catch (IOException e) {
            throw new MidiLoadError();
        }

        midiFile.header = header;

        for (int i = 0; i < header.numberOfTracks; i++) {
            MidiTrack track = new MidiTrack();

            MidiTrackHeader trackHeader;

            try {
                trackHeader = readTrackHeader(stream);
            } catch (IOException e) {
                throw new MidiLoadError();
            }

            ArrayList<MidiMessage> messages = new ArrayList<>();
            boolean endOfTrack = false;
            int nextByte;
            byte lastStatusByte = 0;
            try {
                while (!endOfTrack && (nextByte = stream.read()) != -1) {
                    stream.unread(nextByte);

                    long statusTimeDelta = readVariableLength(stream);
                    byte status = readByte(stream);

                    if ((byteToInt(status) & 0x80) == 0) {
                        // use last status
                        stream.unread(status);
                        status = lastStatusByte;
                    } else {
                        lastStatusByte = status;
                    }

                    int channel = (byteToInt(status) & 0x0F);

                    MidiEventName eventName = MidiEventName.getNameFromID((byte) (byteToInt(status) & 0xF0));

                    switch (eventName) {
                        case VoiceNoteOff: {
                            MidiVoiceNoteOff message = new MidiVoiceNoteOff();
                            message.channel = channel;
                            message.noteId = readByte(stream);
                            message.noteVelocity = readByte(stream);
                            message.timeDelta = statusTimeDelta;
                            messages.add(message);
                        }
                            break;
                        case VoiceNoteOn: {
                            MidiVoiceNoteOn message = new MidiVoiceNoteOn();
                            message.channel = channel;
                            message.noteId = readByte(stream);
                            message.noteVelocity = readByte(stream);
                            message.timeDelta = statusTimeDelta;
                            messages.add(message);
                        }
                            break;
                        case VoiceAfterTouch: {
                            MidiVoiceAfterTouch message = new MidiVoiceAfterTouch();
                            message.channel = channel;
                            message.noteId = readByte(stream);
                            message.noteVelocity = readByte(stream);
                            message.timeDelta = statusTimeDelta;
                            messages.add(message);
                        }
                            break;
                        case VoiceControlChange: {
                            MidiVoiceControlChange message = new MidiVoiceControlChange();
                            message.channel = channel;
                            message.noteId = readByte(stream);
                            message.noteVelocity = readByte(stream);
                            message.timeDelta = statusTimeDelta;
                            messages.add(message);
                        }
                            break;
                        case VoiceProgramChange: {
                            MidiVoiceProgramChange message = new MidiVoiceProgramChange();
                            message.channel = channel;
                            message.programId = readByte(stream);
                            message.timeDelta = statusTimeDelta;
                            messages.add(message);
                        }
                            break;
                        case VoiceChannelPressure: {
                            MidiVoiceChannelPressure message = new MidiVoiceChannelPressure();
                            message.channel = channel;
                            message.channelPressure = readByte(stream);
                            message.timeDelta = statusTimeDelta;
                            messages.add(message);
                        }
                            break;
                        case VoicePitchBend: {
                            MidiVoicePitchBend message = new MidiVoicePitchBend();
                            message.channel = channel;
                            message.nLS7B = readByte(stream);
                            message.nMS7B = readByte(stream);
                            message.timeDelta = statusTimeDelta;
                            messages.add(message);
                        }
                            break;
                        case SystemExclusive: {
                            if (byteToInt(status) == 0xFF) {

                                byte type = readByte(stream);
                                long length = readVariableLength(stream);

                                MidiSystemEventName systemEventName;

                                try {
                                    systemEventName = MidiSystemEventName.getNameFromID(type);

                                    switch (systemEventName) {
                                        case MetaSequence: {
                                            MidiSystemSequence message = new MidiSystemSequence();
                                            message.timeDelta = statusTimeDelta;
                                            message.sequence1 = readByte(stream);
                                            message.sequence2 = readByte(stream);
                                            messages.add(message);
                                        }
                                            break;
                                        case MetaText: {
                                            MidiSystemText message = new MidiSystemText();
                                            message.text = readString(stream, length);
                                            message.timeDelta = statusTimeDelta;
                                            messages.add(message);
                                        }
                                            break;
                                        case MetaCopyright: {
                                            MidiSystemCopyright message = new MidiSystemCopyright();
                                            message.copyright = readString(stream, length);

                                            message.timeDelta = statusTimeDelta;
                                            messages.add(message);
                                        }
                                            break;
                                        case MetaTrackName: {
                                            MidiSystemTrackName message = new MidiSystemTrackName();
                                            message.trackName = readString(stream, length);

                                            message.timeDelta = statusTimeDelta;
                                            messages.add(message);
                                        }
                                            break;
                                        case MetaInstrumentName: {
                                            MidiSystemInstrumentName message = new MidiSystemInstrumentName();
                                            message.instrumentName = readString(stream, length);

                                            message.timeDelta = statusTimeDelta;
                                            messages.add(message);
                                        }
                                            break;
                                        case MetaLyrics: {
                                            MidiSystemLyrics message = new MidiSystemLyrics();
                                            message.lyrics = readString(stream, length);

                                            message.timeDelta = statusTimeDelta;
                                            messages.add(message);
                                        }
                                            break;
                                        case MetaCuePoint: {
                                            MidiSystemCuePoint message = new MidiSystemCuePoint();
                                            message.cue = readString(stream, length);

                                            message.timeDelta = statusTimeDelta;
                                            messages.add(message);
                                        }
                                            break;
                                        case MetaChannelPrefix: {
                                            MidiSystemChannelPrefix message = new MidiSystemChannelPrefix();
                                            message.prefix = readByte(stream);

                                            message.timeDelta = statusTimeDelta;
                                            messages.add(message);
                                        }
                                            break;

                                        case MetaEndOfTrack: {
                                            MidiSystemEndOfTrack message = new MidiSystemEndOfTrack();
                                            endOfTrack = true;
                                            message.timeDelta = statusTimeDelta;
                                            messages.add(message);
                                        }
                                            break;
                                        case MetaSetTempo: {
                                            MidiSystemSetTempo message = new MidiSystemSetTempo();
                                            message.microsecondsPerQuarterNote = readUnsignedIntFrom3Bytes(stream);
                                            message.timeDelta = statusTimeDelta;
                                            messages.add(message);
                                        }
                                            break;
                                        case MetaSMPTEOffset: {
                                            MidiSystemSMPTEOffset message = new MidiSystemSMPTEOffset();
                                            message.H = readByte(stream);
                                            message.M = readByte(stream);
                                            message.S = readByte(stream);
                                            message.FR = readByte(stream);
                                            message.FF = readByte(stream);

                                            message.timeDelta = statusTimeDelta;
                                            messages.add(message);
                                        }
                                            break;
                                        case MetaTimeSignature: {
                                            MidiSystemTimeSignature message = new MidiSystemTimeSignature();
                                            message.timeSignitureUpper = readByte(stream);
                                            message.timeSignitureLower = 2 << byteToInt(readByte(stream));
                                            message.clocksPerTick = readByte(stream);
                                            message.ticks32per24Clocks = readByte(stream);

                                            message.timeDelta = statusTimeDelta;
                                            messages.add(message);
                                        }
                                            break;
                                        case MetaKeySignature: {
                                            MidiSystemKeySignature message = new MidiSystemKeySignature();
                                            message.keySigniture = readByte(stream);
                                            message.minorKey = readByte(stream);
                                            message.timeDelta = statusTimeDelta;
                                            messages.add(message);
                                        }
                                            break;
                                        case MetaSequencerSpecific: {
                                            MidiSystemSequencerSpecific message = new MidiSystemSequencerSpecific();
                                            message.specific = readString(stream, length);

                                            message.timeDelta = statusTimeDelta;
                                            messages.add(message);
                                        }
                                            break;
                                        case MetaMarker: {
                                            MidiSystemMarker message = new MidiSystemMarker();
                                            message.marker = readString(stream, length);

                                            message.timeDelta = statusTimeDelta;
                                            messages.add(message);
                                        }
                                            break;

                                        default:
                                            break;
                                    }
                                } catch (IllegalArgumentException e) {
                                }

                            }

                            if (byteToInt(status) == 0xF0) {
                                System.out.println(readString(stream));
                            }
                            if (byteToInt(status) == 0xF7) {
                                System.out.println(readString(stream));
                            }
                        }
                            break;
                    }
                }
                track.header = trackHeader;
                track.messages = messages;
                midiFile.tracks.add(track);
            } catch (IOException e) {
                throw new MidiLoadError();
            }
        }

        return midiFile;
    }

    private static MidiTrackHeader readTrackHeader(InputStream stream) throws IOException {
        MidiTrackHeader trackHeader = new MidiTrackHeader();
        trackHeader.trackID = readUnsignedInt(stream);
        trackHeader.trackLength = readUnsignedInt(stream);
        return trackHeader;
    }

    private static MidiHeader readFileHeader(InputStream stream) throws IOException {
        MidiHeader header = new MidiHeader();
        header.magicNumber = readUnsignedInt(stream);
        header.headerLength = readUnsignedInt(stream);
        header.format = readUnsignedShort(stream);
        header.numberOfTracks = readUnsignedShort(stream);
        header.divisions = readUnsignedShort(stream);

        return header;
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

    public static class MidiLoadError extends RuntimeException {

        public MidiLoadError() {
            super("Could not load midi");
        }

    }
}
