package midi.Writing;

import midi.Data.Event.Callbacks.MidiSystemExclusiveEventVisitor;
import midi.Data.Event.MidiEvents.MidiSystemExclusiveEvent;
import midi.Data.Event.MidiEvents.MidiSystemExclusiveEvents.MidiSystemExclusiveManufacturerMessage;

import java.io.IOException;
import java.io.OutputStream;

import static midi.Writing.WritingUtils.writeByte;

public class MidiSystemExclusiveEventWriter extends MidiSystemExclusiveEventVisitor {
    private IOException exception;
    private final OutputStream outputStream;

    public MidiSystemExclusiveEventWriter(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public void visit(MidiSystemExclusiveManufacturerMessage manufacturerMessage) {
        try {
            if (!manufacturerMessage.includesPreambleByte) {
                writeByte(outputStream, 0xF7);
            }
            outputStream.write(manufacturerMessage.dataBlob);
            writeByte(outputStream, 0xF7);
        } catch (IOException e) {
            setError(e);
        }
    }

    public void write(MidiSystemExclusiveEvent event) throws IOException {
        visit(event);

        if (hasError()) {
            IOException error = getError();
            resetError();
            error.printStackTrace();
            throw error;
        }
    }

    public void setError(IOException e) {
        exception = e;
    }

    public void resetError() {
        exception = null;
    }

    public boolean hasError() {
        return exception != null;
    }

    public IOException getError() {
        return exception;
    }
}
