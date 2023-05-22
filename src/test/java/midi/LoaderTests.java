package midi;

import midi.Data.MidiFile;
import midi.Reading.MidiFileReader;
import midi.Reading.MidiFileReader.MidiLoadError;
import midi.Writing.MidiFileWriter;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class LoaderTests {
    private static byte[] createRandomBytes(int length) {
        byte[] bytes = new byte[length];
        new Random().nextBytes(bytes);
        return bytes;
    }

    @Test
    void LoadEmptyStream() {
        InputStream randomStream = new ByteArrayInputStream(new byte[]{});

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
    void LoadScale() throws MidiLoadError {
        final String[] FILE_PATHS = new String[]{
                "scale.mid"
        };

        for (String s : FILE_PATHS) {

            assertNotNull(LoaderTests.class.getClassLoader()
                    .getResourceAsStream(s), "could not find midi: {" + s + "}");

            final MidiFile file = MidiFileReader.load(LoaderTests.class.getClassLoader()
                    .getResourceAsStream(s));

            OutputStream byteForByteMatch = new OutputStream() {
                final InputStream checkStream = LoaderTests.class.getClassLoader()
                        .getResourceAsStream(s);

                @Override
                public void write(int actual) throws IOException {
                    int expected = checkStream.read();
                    if (expected != actual) {
                        assertEquals(expected, actual);
                    }
                }
            };

            assertDoesNotThrow(() -> MidiFileWriter.dump(byteForByteMatch, file));
        }
    }
}
