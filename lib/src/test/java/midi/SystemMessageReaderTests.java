package midi;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import midi.Reading.MidiSystemMessageReader;
import midi.Reading.MidiMessageReader.MidiMessageHeader;

public class SystemMessageReaderTests {

    @Test
    void readSystemMessageEmptyStream() {
        MidiMessageHeader messageHeader = new MidiMessageHeader();
        messageHeader.status = (byte) 0xFF;
        messageHeader.timeDelta = 0;

        assertThrows(IOException.class, () -> {
            new MidiSystemMessageReader(
                    new ByteArrayInputStream(new byte[] {}))
                    .readSystemMessage(messageHeader);
        });
    }

    @Test
    void readSystemMessagePickedExample() {
        MidiMessageHeader messageHeader = new MidiMessageHeader();
        messageHeader.status = (byte) 0xFF;
        messageHeader.timeDelta = 0;

        byte[] bytes = new byte[] {

        };

        assertThrows(IOException.class, () -> {
            new MidiSystemMessageReader(
                    new ByteArrayInputStream(bytes))
                    .readSystemMessage(messageHeader);
        });
    }

}