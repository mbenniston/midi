package midi.Writing;

import java.io.IOException;
import java.io.OutputStream;

import midi.Data.MidiTrack;
import midi.Data.MidiTrackHeader;
import midi.Data.Message.MidiMessage;

import static midi.Writing.WritingUtils.*;

public class MidiTrackWriter {
    private final MidiMessageWriter messageWriter;
    private final OutputStream outputStream;

    public MidiTrackWriter(OutputStream outputStream) {
        this.outputStream = outputStream;
        this.messageWriter = new MidiMessageWriter(outputStream);
    }

    public void writeTrack(MidiTrack track) throws IOException {
        writeHeader(track.header);
        // messageReader.resetStatusByte();

        for (MidiMessage message : track.messages) {
            messageWriter.write(message);
        }
    }

    public void writeHeader(MidiTrackHeader header) throws IOException {
        writeUnsignedInt(outputStream, MidiTrackHeader.MIDI_TRACK_CHUNK_ID);
        writeUnsignedInt(outputStream, header.trackLengthInBytes);
    }

}
