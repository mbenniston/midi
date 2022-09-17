package midi;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

import org.junit.jupiter.api.Test;

import midi.Data.MidiFile;
import midi.Reading.MidiFileReader;
import midi.Reading.MidiFileReader.MidiLoadError;
import midi.Writing.MidiFileWriter;

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
    void LoadScale() throws MidiLoadError {
        final String[] FILE_PATHS = new String[] {
                // "test-2-tracks-type-0.mid",
                // "test-2-tracks-type-1.mid",
                // "test-2-tracks-type-2.mid",
                // "test-all-gm-percussion.mid",
                // "test-all-gm-sounds.mid",
                // "test-all-gm2-sounds.mid",
                // "test-all-gs-sounds.mid",
                // "test-all-microsoft-gs-wavetable-synth-sounds.mid",
                // "test-all-xg-sounds.mid",
                // "test-c-major-scale.mid",
                // "test-control-00-20-bank-select.mid",
                // "test-control-40-damper.mid",
                // "test-control-41-portamento.mid",
                // "test-control-54-portamento-control.mid",
                // "test-control-7c-omni-mode-off.mid",
                // "test-control-7d-omni-mode-on.mid",
                // "test-control-7e-mono-mode-on.mid",
                // "test-control-7f-poly-mode-on.mid",
                // "test-corrupt-file-extra-byte.mid",
                // "test-corrupt-file-missing-byte.mid",
                // "test-empty.mid",
                // "test-gm2-doggy-78-00-38-4c.mid",
                // "test-gm2-doggy-79-01-7b.mid",
                // "test-gs-doggy-01-00-7b.mid",
                // "test-illegal-message-all.mid",
                // "test-illegal-message-f1-xx.mid",
                // "test-illegal-message-f2-xx-xx.mid",
                // "test-illegal-message-f3-xx.mid",
                // "test-illegal-message-f4.mid",
                // "test-illegal-message-f5.mid",
                // "test-illegal-message-f6.mid",
                // "test-illegal-message-f8.mid",
                // "test-illegal-message-f9.mid",
                // "test-illegal-message-fa.mid",
                // "test-illegal-message-fb.mid",
                // "test-illegal-message-fc.mid",
                // "test-illegal-message-fd.mid",
                // "test-illegal-message-fe.mid",
                // "test-karaoke-kar.mid",
                // "test-multichannel-chords-0.mid",
                // "test-multichannel-chords-1.mid",
                // "test-multichannel-chords-2.mid",
                // "test-multichannel-chords-3.mid",
                // "test-non-midi-track.mid",
                // "test-rpn-00-00-pitch-bend-range.mid",
                // "test-rpn-00-01-fine-tuning.mid",
                // "test-rpn-00-02-coarse-tuning.mid",
                // "test-rpn-00-05-modulation-depth-range.mid",
                // "test-smpte-offset.mid",
                // "test-sysex-7e-06-01-id-request.mid",
                // "test-sysex-7e-09-01-gm1-enable.mid",
                // "test-sysex-7e-09-02-gm-disable.mid",
                // "test-sysex-7e-09-03-gm2-enable.mid",
                // "test-sysex-7f-04-03-master-fine-tuning.mid",
                // "test-sysex-7f-04-04-master-coarse-tuning.mid",
                // "test-sysex-7x-08-0x-scale-tuning.mid",
                // "test-sysex-gs-40-1x-15-drum-part-change.mid",
                // "test-sysex-gs-40-1x-4x-scale-tuning.mid",
                // "test-track-length.mid",
                // "test-vlq-2-byte.mid",
                // "test-vlq-3-byte.mid",
                // "test-vlq-4-byte.mid",
                // "test-xg-doggy-40-00-30.mid",
                // "test-xg-doggy-7e-00-00-54.mid"
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
                        // throw new IOException();
                        assertEquals(expected, actual);
                    }
                }
            };

            assertDoesNotThrow(() -> {
                MidiFileWriter.dump(byteForByteMatch, file);
            });
        }
    }
}
