package midi.Reading;

import static midi.Reading.ReadingUtils.*;

import java.io.IOException;
import java.io.PushbackInputStream;
import java.util.ArrayList;

import midi.Data.MidiTrack;
import midi.Data.MidiTrackHeader;
import midi.Data.Message.MidiMessage;
import midi.Data.Message.MidiMessages.MidiSystemMessages.MidiSystemEndOfTrack;
import midi.Reading.MidiFileReader.MidiLoadError;

public class MidiTrackReader {
    public static final long MIDI_TRACK_CHUNK_ID = 0x4D54726BL;

    private final PushbackInputStream source;
    private final MidiMessageReader messageReader;

    public MidiTrackReader(PushbackInputStream source) {
        this.source = source;
        this.messageReader = new MidiMessageReader(source);
    }

    public MidiTrack readTrack() throws IOException, MidiLoadError {
        return readTrack(readTrackHeader());
    }

    public MidiTrack readTrack(MidiTrackHeader trackHeader) throws IOException, MidiLoadError {
        MidiTrack track = new MidiTrack();
        track.header = trackHeader;
        track.messages = new ArrayList<>();

        MidiMessage lastMessageRead = null;

        messageReader.resetStatusByte();

        while (!hasReachedEndOfStream() && !(lastMessageRead instanceof MidiSystemEndOfTrack)) {
            lastMessageRead = messageReader.readMessage();
            if (lastMessageRead != null) {
                track.messages.add(lastMessageRead);
            }
        }

        return track;
    }

    public MidiTrackHeader readTrackHeader() throws IOException, MidiLoadError {
        MidiTrackHeader trackHeader = new MidiTrackHeader();
        trackHeader.magicNumber = readUnsignedInt(source);
        if (trackHeader.magicNumber != MIDI_TRACK_CHUNK_ID) {
            throw new MidiLoadError();
        }

        trackHeader.trackLengthInBytes = readUnsignedInt(source);
        return trackHeader;
    }

    public boolean hasReachedEndOfStream() {
        try {
            int nextByte = source.read();
            source.unread(nextByte);
            return nextByte == -1;
        } catch (IOException e) {
            return true;
        }
    }
}
