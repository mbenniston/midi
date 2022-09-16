package midi;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Random;

import org.junit.jupiter.api.Test;

import midi.Data.MidiFile;
import midi.Reading.MidiFileReader;
import midi.Reading.MidiFileReader.MidiLoadError;

public class LoaderTests {
    private static byte[] createRandomBytes(int length) {
        byte[] bytes = new byte[length];
        new Random().nextBytes(bytes);
        return bytes;
    }

    @Test
    void LoadEmptyStream() {
        InputStream randomStream = new ByteArrayInputStream(new byte[] {});

        assertThrows(MidiLoadError.class, () -> {
            MidiFileReader.load(randomStream);
        });
    }

    @Test
    void LoadRandomStream() {
        InputStream randomStream = new ByteArrayInputStream(createRandomBytes(1024));

        assertThrows(MidiLoadError.class, () -> {
            MidiFileReader.load(randomStream);
        });
    }

    @Test
    void LoadScale() {
        InputStream fileInputStream = LoaderTests.class.getClassLoader()
                .getResourceAsStream("scale.mid");

        MidiFile file = MidiFileReader.load(fileInputStream);
    }
}
