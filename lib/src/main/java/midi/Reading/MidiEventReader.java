package midi.Reading;

import java.io.IOException;
import java.io.PushbackInputStream;

import midi.Data.Event.MidiEvent;
import midi.Reading.MidiFileReader.MidiLoadError;

import static midi.Reading.ReadingUtils.*;

public class MidiEventReader {
    private final PushbackInputStream source;

    private final MidiChannelEventReader channelEventReader;
    private final MidiMetaEventReader metaEventReader;
    private final MidiSystemExclusiveReader systemEventReader;

    private byte lastStatusByte = 0;

    public MidiEventReader(PushbackInputStream source) {
        this.source = source;
        channelEventReader = new MidiChannelEventReader(source);
        metaEventReader = new MidiMetaEventReader(source);
        systemEventReader = new MidiSystemExclusiveReader(source);
    }

    public MidiEvent readEvent() throws IOException, MidiLoadError {
        long timeDelta = readVariableLength(source);
        int status = byteToInt(readStatus());

        MidiEventHeader eventHeader = new MidiEventHeader();
        eventHeader.status = status;
        eventHeader.timeDelta = timeDelta;

        switch (eventHeader.status) {
            case 0xFF:
                return metaEventReader.readMetaEvent(eventHeader);
            case 0xF0:
            case 0xF7:
                return systemEventReader.readSystemEvent(eventHeader);
            default:
                return channelEventReader.readChannelEvent(eventHeader);
        }
    }

    private byte readStatus() throws IOException {
        byte status = readByte(source);

        if ((byteToInt(status) & 0x80) == 0) {
            source.unread(status);
            status = lastStatusByte;
        } else {
            lastStatusByte = status;
        }

        return status;
    }

    public void resetStatusByte() {
        lastStatusByte = 0;
    }

    public static class MidiEventHeader {
        public int status;
        public long timeDelta;
    }
}
