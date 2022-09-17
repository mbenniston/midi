package midi.Writing;

import java.io.IOException;
import java.io.OutputStream;

import midi.Data.MidiEventName;
import midi.Data.Message.MidiMessage;
import midi.Data.Message.MidiMessageVisitor;
import midi.Data.Message.MidiMessages.MidiSystemExclusive;
import midi.Data.Message.MidiMessages.MidiVoiceAfterTouch;
import midi.Data.Message.MidiMessages.MidiVoiceChannelPressure;
import midi.Data.Message.MidiMessages.MidiVoiceControlChange;
import midi.Data.Message.MidiMessages.MidiVoiceNoteOff;
import midi.Data.Message.MidiMessages.MidiVoiceNoteOn;
import midi.Data.Message.MidiMessages.MidiVoicePitchBend;
import midi.Data.Message.MidiMessages.MidiVoiceProgramChange;
import static midi.Writing.WritingUtils.*;

public class MidiMessageWriter extends MidiMessageVisitor {
    private OutputStream outputStream;
    private IOException exception = null;
    private final MidiSystemMessageWriter systemMessageWriter;

    public MidiMessageWriter(OutputStream outputStream) {
        this.outputStream = outputStream;
        systemMessageWriter = new MidiSystemMessageWriter(outputStream);
    }

    @Override
    public void visit(MidiVoiceNoteOff message) {
        try {
            writeStatus(MidiEventName.VoiceNoteOff, message.channel);
            writeByte(outputStream, message.noteId);
            writeByte(outputStream, message.noteVelocity);
        } catch (IOException e) {
            setError(e);
        }
    }

    @Override
    public void visit(MidiVoiceNoteOn message) {
        try {
            writeStatus(MidiEventName.VoiceNoteOn, message.channel);
            writeByte(outputStream, message.noteId);
            writeByte(outputStream, message.noteVelocity);
        } catch (IOException e) {
            setError(e);
        }
    }

    @Override
    public void visit(MidiVoiceAfterTouch message) {
        try {
            writeStatus(MidiEventName.VoiceAfterTouch, message.channel);
            writeByte(outputStream, message.noteId);
            writeByte(outputStream, message.noteVelocity);
        } catch (IOException e) {
            setError(e);
        }
    }

    @Override
    public void visit(MidiVoiceControlChange message) {
        try {
            writeStatus(MidiEventName.VoiceControlChange, message.channel);
            writeByte(outputStream, message.noteId);
            writeByte(outputStream, message.noteVelocity);
        } catch (IOException e) {
            setError(e);
        }
    }

    @Override
    public void visit(MidiVoiceProgramChange message) {
        try {
            writeStatus(MidiEventName.VoiceProgramChange, message.channel);
            writeByte(outputStream, message.programId);
        } catch (IOException e) {
            setError(e);
        }
    }

    @Override
    public void visit(MidiVoiceChannelPressure message) {
        try {
            writeStatus(MidiEventName.VoiceChannelPressure, message.channel);
            writeByte(outputStream, message.channelPressure);
        } catch (IOException e) {
            setError(e);
        }
    }

    @Override
    public void visit(MidiVoicePitchBend message) {
        try {
            writeStatus(MidiEventName.VoicePitchBend, message.channel);
            writeByte(outputStream, message.nLS7B);
            writeByte(outputStream, message.nMS7B);
        } catch (IOException e) {
            setError(e);
        }
    }

    @Override
    public void visit(MidiSystemExclusive message) {
        systemMessageWriter.visit(message);

        resetRunningStatus();

        if (systemMessageWriter.hasError()) {
            setError(systemMessageWriter.getError());
            systemMessageWriter.resetError();
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

    private int lastStatus = 0;

    public void resetRunningStatus() {
        lastStatus = 0;
    }

    private void writeStatus(int nextStatus) throws IOException {
        // running status
        if (nextStatus == lastStatus) {

        } else {
            lastStatus = nextStatus;
        }

        writeByte(outputStream, nextStatus | 0x80);
    }

    private void writeStatus(MidiEventName name, int channel) throws IOException {
        if (channel < 0 || channel > 16) {
            throw new IOException();
        }

        writeStatus((name.value & 0xFF) | channel);
    }

    @Override
    public void visit(MidiMessage message) {
        try {
            writeVariableLength(outputStream, message.timeDelta);
            // writeByte(outputStream, message.status);
        } catch (IOException e) {
            setError(e);
            return;
        }

        super.visit(message);
    }

    public void write(MidiMessage message) throws IOException {
        visit(message);

        if (hasError()) {
            IOException error = getError();
            resetError();
            error.printStackTrace();
            throw error;
        }
    }
}
