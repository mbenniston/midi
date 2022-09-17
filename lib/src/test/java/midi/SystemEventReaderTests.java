package midi;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import midi.Reading.MidiMetaEventReader;
import midi.Reading.MidiEventReader.MidiEventHeader;

public class SystemEventReaderTests {

    @Test
    void readSystemEventEmptyStream() {
        MidiEventHeader eventHeader = new MidiEventHeader();
        eventHeader.status = (byte) 0xFF;
        eventHeader.timeDelta = 0;

        assertThrows(IOException.class, () -> {
            new MidiMetaEventReader(
                    new ByteArrayInputStream(new byte[] {}))
                    .readMetaEvent(eventHeader);
        });
    }

    @Test
    void readSystemEventPickedExample() {
        MidiEventHeader eventHeader = new MidiEventHeader();
        eventHeader.status = (byte) 0xFF;
        eventHeader.timeDelta = 0;

        byte[] bytes = new byte[] {

        };

        assertThrows(IOException.class, () -> {
            new MidiMetaEventReader(
                    new ByteArrayInputStream(bytes))
                    .readMetaEvent(eventHeader);
        });
    }

}