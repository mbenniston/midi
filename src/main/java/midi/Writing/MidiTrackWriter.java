package midi.Writing;

import midi.Data.Event.MidiEvent;
import midi.Data.MidiTrack;
import midi.Data.MidiTrackHeader;

import java.io.IOException;
import java.io.OutputStream;

import static midi.Writing.WritingUtils.writeUnsignedInt;

/**
 * Writes midi track to a stream.
 * This includes writing all its events to the stream.
 */
public class MidiTrackWriter {
    private final MidiEventWriter eventWriter;
    private final OutputStream outputStream;

    public MidiTrackWriter(OutputStream outputStream) {
        this.outputStream = outputStream;
        this.eventWriter = new MidiEventWriter(outputStream);
    }

    public void writeTrack(MidiTrack track) throws IOException {
        writeHeader(track.header);

        for (MidiEvent event : track.events) {
            eventWriter.write(event);
        }
    }

    public void writeHeader(MidiTrackHeader header) throws IOException {
        writeUnsignedInt(outputStream, MidiTrackHeader.MIDI_TRACK_CHUNK_ID);
        writeUnsignedInt(outputStream, header.trackLengthInBytes);
    }

}
