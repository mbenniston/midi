package midi.Writing;

import java.io.IOException;
import java.io.OutputStream;

import midi.Data.MidiFile;
import midi.Data.MidiHeader;
import midi.Data.MidiTrack;
import static midi.Writing.WritingUtils.*;

public class MidiFileWriter {
    private final MidiTrackWriter trackWriter;
    private final OutputStream outputStream;

    public MidiFileWriter(OutputStream outputStream) {
        this.outputStream = outputStream;
        this.trackWriter = new MidiTrackWriter(outputStream);
    }

    public void writeFile(MidiFile file) throws IOException {
        writeHeader(file.header);

        for (MidiTrack track : file.tracks) {
            trackWriter.writeTrack(track);
        }
    }

    public void writeHeader(MidiHeader header) throws IOException {
        writeUnsignedInt(outputStream, header.magicNumber);
        writeUnsignedInt(outputStream, header.headerLength);
        writeUnsignedShort(outputStream, header.format);
        writeUnsignedShort(outputStream, header.numberOfTracks);
        writeUnsignedShort(outputStream, header.divisions);
    }

    public static void dump(OutputStream outputStream, MidiFile file) throws IOException {
        new MidiFileWriter(outputStream).writeFile(file);
    }
}
