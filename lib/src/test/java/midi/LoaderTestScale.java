package midi;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.InputStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import midi.Data.MidiFile;
import midi.Data.MidiTrackHeader;
import midi.Reading.MidiFileReader;
import midi.Reading.MidiFileReader.MidiLoadError;

public class LoaderTestScale {
    private MidiFile file;

    @BeforeEach
    void LoadFile() throws MidiLoadError {
        InputStream fileInputStream = LoaderTestScale.class.getClassLoader()
                .getResourceAsStream("scale.mid");

        file = MidiFileReader.load(fileInputStream);
    }

    @Test
    void checkTrackCount() {
        assertEquals(1, file.header.numberOfTracks);
        assertEquals(1, file.tracks.size());
    }

    @Test
    void checkTrackHeader() {
        MidiTrackHeader trackHeader = file.tracks.get(0).header;
        assertEquals(451, trackHeader.trackLengthInBytes);
    }
}
