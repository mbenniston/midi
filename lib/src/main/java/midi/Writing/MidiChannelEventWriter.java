package midi.Writing;

import java.io.IOException;
import java.io.OutputStream;

import midi.Data.MidiChannelEventName;
import midi.Data.Event.Callbacks.MidiChannelEventVisitor;
import midi.Data.Event.MidiEvents.MidiChannelEvent;
import midi.Data.Event.MidiEvents.MidiChannelEvents.MidiVoiceAfterTouch;
import midi.Data.Event.MidiEvents.MidiChannelEvents.MidiVoiceChannelPressure;
import midi.Data.Event.MidiEvents.MidiChannelEvents.MidiVoiceControlChange;
import midi.Data.Event.MidiEvents.MidiChannelEvents.MidiVoiceNoteOff;
import midi.Data.Event.MidiEvents.MidiChannelEvents.MidiVoiceNoteOn;
import midi.Data.Event.MidiEvents.MidiChannelEvents.MidiVoicePitchBend;
import midi.Data.Event.MidiEvents.MidiChannelEvents.MidiVoiceProgramChange;

import static midi.Writing.WritingUtils.*;

public class MidiChannelEventWriter extends MidiChannelEventVisitor {
    private int lastStatus = 0;
    private OutputStream outputStream;
    private IOException exception = null;

    public MidiChannelEventWriter(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public void visit(MidiVoiceNoteOff event) {
        try {
            writeStatus(MidiChannelEventName.VoiceNoteOff, event.channel);
            writeByte(outputStream, event.noteId);
            writeByte(outputStream, event.noteVelocity);
        } catch (IOException e) {
            setError(e);
        }
    }

    @Override
    public void visit(MidiVoiceNoteOn event) {
        try {
            writeStatus(MidiChannelEventName.VoiceNoteOn, event.channel);
            writeByte(outputStream, event.noteId);
            writeByte(outputStream, event.noteVelocity);
        } catch (IOException e) {
            setError(e);
        }
    }

    @Override
    public void visit(MidiVoiceAfterTouch event) {
        try {
            writeStatus(MidiChannelEventName.VoiceAfterTouch, event.channel);
            writeByte(outputStream, event.noteId);
            writeByte(outputStream, event.noteVelocity);
        } catch (IOException e) {
            setError(e);
        }
    }

    @Override
    public void visit(MidiVoiceControlChange event) {
        try {
            writeStatus(MidiChannelEventName.VoiceControlChange, event.channel);
            writeByte(outputStream, event.noteId);
            writeByte(outputStream, event.noteVelocity);
        } catch (IOException e) {
            setError(e);
        }
    }

    @Override
    public void visit(MidiVoiceProgramChange event) {
        try {
            writeStatus(MidiChannelEventName.VoiceProgramChange, event.channel);
            writeByte(outputStream, event.programId);
        } catch (IOException e) {
            setError(e);
        }
    }

    @Override
    public void visit(MidiVoiceChannelPressure event) {
        try {
            writeStatus(MidiChannelEventName.VoiceChannelPressure, event.channel);
            writeByte(outputStream, event.channelPressure);
        } catch (IOException e) {
            setError(e);
        }
    }

    @Override
    public void visit(MidiVoicePitchBend event) {
        try {
            writeStatus(MidiChannelEventName.VoicePitchBend, event.channel);
            writeByte(outputStream, event.nLS7B);
            writeByte(outputStream, event.nMS7B);
        } catch (IOException e) {
            setError(e);
        }
    }

    public void resetRunningStatus() {
        lastStatus = 0;
    }

    private void writeStatus(int nextStatus) throws IOException {
        // running status
        if (nextStatus == lastStatus) {

        } else {
            lastStatus = nextStatus;
        }

        WritingUtils.writeByte(outputStream, nextStatus | 0x80);
    }

    private void writeStatus(MidiChannelEventName name, int channel) throws IOException {
        if (channel < 0 || channel > 16) {
            throw new IOException();
        }

        writeStatus((name.value & 0xFF) | channel);
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

    public void write(MidiChannelEvent event) throws IOException {
        visit(event);

        if (hasError()) {
            IOException error = getError();
            resetError();
            error.printStackTrace();
            throw error;
        }
    }
}
