package midi.Reading;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import midi.Data.Event.MidiEvents.MidiSystemExclusiveEvent;
import midi.Data.Event.MidiEvents.MidiSystemExclusiveEvents.MidiSystemExlusiveManufacturerMessage;
import midi.Reading.MidiEventReader.MidiEventHeader;

import static midi.Reading.ReadingUtils.*;

public class MidiSystemExclusiveReader {
    private final InputStream source;

    public MidiSystemExclusiveReader(InputStream source) {
        this.source = source;
    }

    public MidiSystemExclusiveEvent readSystemEvent(MidiEventHeader eventHeader) throws IOException {
        if (eventHeader.status == 0xF0) {
            return readSystemManufacturerMessage(eventHeader, true);
        } else if (eventHeader.status == 0xF7) {
            return readSystemManufacturerMessage(eventHeader, false);
        }

        throw new IOException("Unrecognised system event type");
    }

    public MidiSystemExlusiveManufacturerMessage readSystemManufacturerMessage(
            MidiEventHeader eventHeader,
            boolean includesPreamble)
            throws IOException {
        MidiSystemExlusiveManufacturerMessage event = new MidiSystemExlusiveManufacturerMessage();

        ArrayList<Byte> bytes = new ArrayList<>();

        if (includesPreamble) {
            bytes.add((byte) 0xF0);
        }

        byte currentByte;
        do {
            currentByte = readByte(source);
            if ((currentByte & 0xFF) != 0xF7)
                bytes.add(currentByte);
        } while ((currentByte & 0xFF) != 0xF7);

        event.dataBlob = listToByteArray(bytes);
        event.timeDelta = eventHeader.timeDelta;
        event.includesPreambleByte = includesPreamble;

        return event;
    }
}
