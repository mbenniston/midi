package midi.Reading;

import midi.Data.Event.MidiEvent;
import midi.Data.Event.MidiEvents.MidiMetaEvents.MidiMetaEndOfTrack;
import midi.Data.MidiTrack;
import midi.Data.MidiTrackHeader;
import midi.Reading.MidiFileReader.MidiLoadError;

import java.io.IOException;
import java.io.PushbackInputStream;
import java.util.ArrayList;

import static midi.Reading.ReadingUtils.readUnsignedInt;

/**
 * Reads a midi track and all its events from a stream.
 */
public class MidiTrackReader {
    private final PushbackInputStream source;
    private final MidiEventReader eventReader;

    public MidiTrackReader(PushbackInputStream source) {
        this.source = source;
        this.eventReader = new MidiEventReader(source);
    }

    public MidiTrack readTrack() throws IOException, MidiLoadError {
        return readTrack(readTrackHeader());
    }

    public MidiTrack readTrack(MidiTrackHeader trackHeader) throws IOException, MidiLoadError {
        MidiTrack track = new MidiTrack();
        track.header = trackHeader;
        track.events = new ArrayList<>();

        MidiEvent lastEventRead = null;

        eventReader.resetStatusByte();

        while (!hasReachedEndOfStream() && !(lastEventRead instanceof MidiMetaEndOfTrack)) {
            lastEventRead = eventReader.readEvent();
            if (lastEventRead != null) {
                track.events.add(lastEventRead);
            }
        }

        return track;
    }

    public MidiTrackHeader readTrackHeader() throws IOException, MidiLoadError {
        MidiTrackHeader trackHeader = new MidiTrackHeader();
        trackHeader.magicNumber = readUnsignedInt(source);
        if (trackHeader.magicNumber != MidiTrackHeader.MIDI_TRACK_CHUNK_ID) {
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
