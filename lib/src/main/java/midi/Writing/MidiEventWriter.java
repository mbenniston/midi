package midi.Writing;

import java.io.IOException;
import java.io.OutputStream;

import midi.Data.MidiEventName;
import midi.Data.Event.MidiEvent;
import midi.Data.Event.MidiEventVisitor;
import midi.Data.Event.MidiEvents.MidiMetaEvent;
import midi.Data.Event.MidiEvents.MidiVoiceAfterTouch;
import midi.Data.Event.MidiEvents.MidiVoiceChannelPressure;
import midi.Data.Event.MidiEvents.MidiVoiceControlChange;
import midi.Data.Event.MidiEvents.MidiVoiceNoteOff;
import midi.Data.Event.MidiEvents.MidiVoiceNoteOn;
import midi.Data.Event.MidiEvents.MidiVoicePitchBend;
import midi.Data.Event.MidiEvents.MidiVoiceProgramChange;

import static midi.Writing.WritingUtils.*;

public class MidiEventWriter extends MidiEventVisitor {
    private OutputStream outputStream;
    private IOException exception = null;
    private final MidiSystemEventWriter systemEventWriter;

    public MidiEventWriter(OutputStream outputStream) {
        this.outputStream = outputStream;
        systemEventWriter = new MidiSystemEventWriter(outputStream);
    }

    @Override
    public void visit(MidiVoiceNoteOff event) {
        try {
            writeStatus(MidiEventName.VoiceNoteOff, event.channel);
            writeByte(outputStream, event.noteId);
            writeByte(outputStream, event.noteVelocity);
        } catch (IOException e) {
            setError(e);
        }
    }

    @Override
    public void visit(MidiVoiceNoteOn event) {
        try {
            writeStatus(MidiEventName.VoiceNoteOn, event.channel);
            writeByte(outputStream, event.noteId);
            writeByte(outputStream, event.noteVelocity);
        } catch (IOException e) {
            setError(e);
        }
    }

    @Override
    public void visit(MidiVoiceAfterTouch event) {
        try {
            writeStatus(MidiEventName.VoiceAfterTouch, event.channel);
            writeByte(outputStream, event.noteId);
            writeByte(outputStream, event.noteVelocity);
        } catch (IOException e) {
            setError(e);
        }
    }

    @Override
    public void visit(MidiVoiceControlChange event) {
        try {
            writeStatus(MidiEventName.VoiceControlChange, event.channel);
            writeByte(outputStream, event.noteId);
            writeByte(outputStream, event.noteVelocity);
        } catch (IOException e) {
            setError(e);
        }
    }

    @Override
    public void visit(MidiVoiceProgramChange event) {
        try {
            writeStatus(MidiEventName.VoiceProgramChange, event.channel);
            writeByte(outputStream, event.programId);
        } catch (IOException e) {
            setError(e);
        }
    }

    @Override
    public void visit(MidiVoiceChannelPressure event) {
        try {
            writeStatus(MidiEventName.VoiceChannelPressure, event.channel);
            writeByte(outputStream, event.channelPressure);
        } catch (IOException e) {
            setError(e);
        }
    }

    @Override
    public void visit(MidiVoicePitchBend event) {
        try {
            writeStatus(MidiEventName.VoicePitchBend, event.channel);
            writeByte(outputStream, event.nLS7B);
            writeByte(outputStream, event.nMS7B);
        } catch (IOException e) {
            setError(e);
        }
    }

    @Override
    public void visit(MidiMetaEvent event) {
        systemEventWriter.visit(event);

        resetRunningStatus();

        if (systemEventWriter.hasError()) {
            setError(systemEventWriter.getError());
            systemEventWriter.resetError();
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
    public void visit(MidiEvent event) {
        try {
            writeVariableLength(outputStream, event.timeDelta);
            // writeByte(outputStream, event.status);
        } catch (IOException e) {
            setError(e);
            return;
        }

        super.visit(event);
    }

    public void write(MidiEvent event) throws IOException {
        visit(event);

        if (hasError()) {
            IOException error = getError();
            resetError();
            error.printStackTrace();
            throw error;
        }
    }
}
