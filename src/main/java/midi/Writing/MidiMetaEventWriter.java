package midi.Writing;

import midi.Data.Event.Callbacks.MidiMetaEventVisitor;
import midi.Data.Event.MidiEvents.MidiMetaEvent;
import midi.Data.Event.MidiEvents.MidiMetaEvents.*;
import midi.Data.MidiMetaEventName;

import java.io.IOException;
import java.io.OutputStream;

import static midi.Writing.WritingUtils.*;

/**
 * Writes midi meta events as bytes to a stream.
 */
public class MidiMetaEventWriter extends MidiMetaEventVisitor {
    private OutputStream outputStream;

    private IOException exception = null;

    public MidiMetaEventWriter(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public void visit(MidiMetaSequence event) {
        try {
            writeByte(outputStream, MidiMetaEventName.Sequence.value);
            writeVariableLength(outputStream, event.getLengthInBytes());
            writeByte(outputStream, event.sequence1);
            writeByte(outputStream, event.sequence2);
        } catch (IOException e) {
            setError(e);
        }
    }

    @Override
    public void visit(MidiMetaText event) {
        try {
            writeByte(outputStream, MidiMetaEventName.Text.value);
            writeVariableLength(outputStream, event.getLengthInBytes());

            writeString(outputStream, event.text);
        } catch (IOException e) {
            setError(e);
        }
    }

    @Override
    public void visit(MidiMetaCopyright event) {
        try {
            writeByte(outputStream, MidiMetaEventName.Copyright.value);
            writeVariableLength(outputStream, event.getLengthInBytes());

            writeString(outputStream, event.copyright);
        } catch (IOException e) {
            setError(e);
        }
    }

    @Override
    public void visit(MidiMetaTrackName event) {
        try {
            writeByte(outputStream, MidiMetaEventName.TrackName.value);
            writeVariableLength(outputStream, event.getLengthInBytes());

            writeString(outputStream, event.trackName);
        } catch (IOException e) {
            setError(e);
        }
    }

    @Override
    public void visit(MidiMetaInstrumentName event) {
        try {
            writeByte(outputStream, MidiMetaEventName.InstrumentName.value);
            writeVariableLength(outputStream, event.getLengthInBytes());

            writeString(outputStream, event.instrumentName);
        } catch (IOException e) {
            setError(e);
        }
    }

    @Override
    public void visit(MidiMetaLyrics event) {
        try {
            writeByte(outputStream, MidiMetaEventName.Lyrics.value);
            writeVariableLength(outputStream, event.getLengthInBytes());

            writeString(outputStream, event.lyrics);
        } catch (IOException e) {
            setError(e);
        }

    }

    @Override
    public void visit(MidiMetaMarker event) {
        try {
            writeByte(outputStream, MidiMetaEventName.Marker.value);
            writeVariableLength(outputStream, event.getLengthInBytes());

            writeString(outputStream, event.marker);
        } catch (IOException e) {
            setError(e);
        }
    }

    @Override
    public void visit(MidiMetaCuePoint event) {
        try {
            writeByte(outputStream, MidiMetaEventName.CuePoint.value);
            writeVariableLength(outputStream, event.getLengthInBytes());

            writeString(outputStream, event.cue);
        } catch (IOException e) {
            setError(e);
        }
    }

    @Override
    public void visit(MidiMetaChannelPrefix event) {
        try {
            writeByte(outputStream, MidiMetaEventName.ChannelPrefix.value);
            writeVariableLength(outputStream, event.getLengthInBytes());

            writeByte(outputStream, event.prefix);
        } catch (IOException e) {
            setError(e);
        }
    }

    @Override
    public void visit(MidiMetaEndOfTrack event) {
        try {
            writeByte(outputStream, MidiMetaEventName.EndOfTrack.value);
            writeVariableLength(outputStream, event.getLengthInBytes());
        } catch (IOException e) {
            setError(e);
        }
    }

    @Override
    public void visit(MidiMetaSetTempo event) {
        try {
            writeByte(outputStream, MidiMetaEventName.SetTempo.value);
            writeVariableLength(outputStream, event.getLengthInBytes());

            writeUnsignedIntTo3Bytes(outputStream, event.microsecondsPerQuarterNote);
        } catch (IOException e) {
            setError(e);
        }
    }

    @Override
    public void visit(MidiMetaSMPTEOffset event) {
        try {
            writeByte(outputStream, MidiMetaEventName.SMPTEOffset.value);
            writeVariableLength(outputStream, event.getLengthInBytes());

            writeByte(outputStream, event.hours);
            writeByte(outputStream, event.minutes);
            writeByte(outputStream, event.seconds);
            writeByte(outputStream, event.frameRate);
            writeByte(outputStream, event.fractionalFrames);
        } catch (IOException e) {
            setError(e);
        }
    }

    @Override
    public void visit(MidiMetaTimeSignature event) {
        try {
            writeByte(outputStream, MidiMetaEventName.TimeSignature.value);
            writeVariableLength(outputStream, event.getLengthInBytes());

            writeByte(outputStream, event.timeSignitureUpper);
            writeByte(outputStream, event.timeSignitureLower);
            writeByte(outputStream, event.clocksPerTick);
            writeByte(outputStream, event.ticks32per24Clocks);
        } catch (IOException e) {
            setError(e);
        }

    }

    @Override
    public void visit(MidiMetaKeySignature event) {
        try {
            writeByte(outputStream, MidiMetaEventName.KeySignature.value);
            writeVariableLength(outputStream, event.getLengthInBytes());

            writeByte(outputStream, event.keySigniture);
            writeByte(outputStream, event.minorKey);
        } catch (IOException e) {
            setError(e);
        }
    }

    @Override
    public void visit(MidiMetaSequencerSpecific event) {
        try {
            writeByte(outputStream, MidiMetaEventName.SequencerSpecific.value);
            writeVariableLength(outputStream, event.getLengthInBytes());

            writeString(outputStream, event.specific);
        } catch (IOException e) {
            setError(e);
        }
    }

    @Override
    public void visit(MidiMetaUnknown event) {
        try {
            writeByte(outputStream, event.type);
            writeVariableLength(outputStream, event.getLengthInBytes());
            outputStream.write(event.dataBlob);
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

    public void write(MidiMetaEvent event) throws IOException {
        outputStream.write(0xFF);

        visit(event);

        if (hasError()) {
            IOException error = getError();
            resetError();
            error.printStackTrace();
            throw error;
        }
    }

}
