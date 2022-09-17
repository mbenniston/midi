package midi.Writing;

import java.io.IOException;
import java.io.OutputStream;

import midi.Data.Event.MidiEvent;
import midi.Data.Event.Callbacks.MidiEventVisitor;
import midi.Data.Event.MidiEvents.MidiChannelEvent;
import midi.Data.Event.MidiEvents.MidiMetaEvent;
import midi.Data.Event.MidiEvents.MidiSystemExclusiveEvent;
import static midi.Writing.WritingUtils.*;

public class MidiEventWriter extends MidiEventVisitor {
    private OutputStream outputStream;
    private IOException exception = null;
    private final MidiChannelEventWriter channelEventWriter;
    private final MidiMetaEventWriter metaEventWriter;
    private final MidiSystemExclusiveEventWriter systemEventWriter;

    public MidiEventWriter(OutputStream outputStream) {
        this.outputStream = outputStream;
        channelEventWriter = new MidiChannelEventWriter(outputStream);
        metaEventWriter = new MidiMetaEventWriter(outputStream);
        systemEventWriter = new MidiSystemExclusiveEventWriter(outputStream);
    }

    @Override
    public void visit(MidiChannelEvent event) {
        try {
            channelEventWriter.write(event);
        } catch (IOException e) {
            setError(e);
        }
    }

    @Override
    public void visit(MidiSystemExclusiveEvent event) {
        try {
            channelEventWriter.resetRunningStatus();
            systemEventWriter.write(event);
        } catch (IOException e) {
            setError(e);
        }

    }

    @Override
    public void visit(MidiMetaEvent event) {
        try {
            channelEventWriter.resetRunningStatus();
            metaEventWriter.write(event);
        } catch (IOException e) {
            setError(e);
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

    public void write(MidiEvent event) throws IOException {
        writeVariableLength(outputStream, event.timeDelta);
        visit(event);

        if (hasError()) {
            IOException error = getError();
            resetError();
            error.printStackTrace();
            throw error;
        }
    }
}
