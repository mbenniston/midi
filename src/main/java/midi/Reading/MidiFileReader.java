package midi.Reading;

import midi.Data.MidiFile;
import midi.Data.MidiHeader;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.ArrayList;

import static midi.Reading.ReadingUtils.readUnsignedInt;
import static midi.Reading.ReadingUtils.readUnsignedShort;

/**
 * Reads a whole midi file from a stream.
 * Reads all its tracks and events.
 */
public class MidiFileReader {
    public static final long MIDI_HEADER_CHUNK_ID = 0x4D546864L;

    private final PushbackInputStream source;
    private final MidiTrackReader trackReader;

    public MidiFileReader(InputStream source) {
        this.source = new PushbackInputStream(source);
        this.trackReader = new MidiTrackReader(this.source);
    }

    public MidiFile readFile() throws MidiLoadError {
        MidiFile file = new MidiFile();

        try {
            file.header = readFileHeader();
            file.tracks = new ArrayList<>();
            for (int i = 0; i < file.header.numberOfTracks; i++) {
                file.tracks.add(trackReader.readTrack());
            }
        } catch (IOException e) {
            throw new MidiLoadError();
        }

        return file;
    }

    public MidiHeader readFileHeader() throws IOException, MidiLoadError {
        MidiHeader header = new MidiHeader();
        header.magicNumber = readUnsignedInt(source);
        if (header.magicNumber != MIDI_HEADER_CHUNK_ID) {
            throw new MidiLoadError();
        }

        header.headerLength = readUnsignedInt(source);
        header.format = readUnsignedShort(source);
        header.numberOfTracks = readUnsignedShort(source);
        header.divisions = readUnsignedShort(source);

        return header;
    }

    public static class MidiLoadError extends Exception {

        public MidiLoadError() {
            super("Could not load midi");
        }
    }

    public static MidiFile load(InputStream stream) throws MidiLoadError {
        return new MidiFileReader(stream).readFile();
    }
}
