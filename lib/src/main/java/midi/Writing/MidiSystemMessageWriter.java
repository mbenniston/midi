package midi.Writing;

import java.io.IOException;
import java.io.OutputStream;

import midi.Data.MidiSystemEventName;
import midi.Data.Message.MidiSystemMessageVisitor;
import midi.Data.Message.MidiMessages.MidiSystemExclusive;
import midi.Data.Message.MidiMessages.MidiSystemMessages.*;
import static midi.Writing.WritingUtils.*;

public class MidiSystemMessageWriter extends MidiSystemMessageVisitor {
    private OutputStream outputStream;

    private IOException exception = null;

    public MidiSystemMessageWriter(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public void visit(MidiSystemSequence message) {
        try {
            writeByte(outputStream, 0xFF);
            writeByte(outputStream, MidiSystemEventName.MetaSequence.value);
            writeVariableLength(outputStream, message.getLengthInBytes());
            writeByte(outputStream, message.sequence1);
            writeByte(outputStream, message.sequence2);
        } catch (IOException e) {
            setError(e);
        }
    }

    @Override
    public void visit(MidiSystemText message) {
        try {
            writeByte(outputStream, 0xFF);
            writeByte(outputStream, MidiSystemEventName.MetaText.value);
            writeVariableLength(outputStream, message.getLengthInBytes());

            writeString(outputStream, message.text);
        } catch (IOException e) {
            setError(e);
        }
    }

    @Override
    public void visit(MidiSystemCopyright message) {
        try {
            writeByte(outputStream, 0xFF);
            writeByte(outputStream, MidiSystemEventName.MetaCopyright.value);
            writeVariableLength(outputStream, message.getLengthInBytes());

            writeString(outputStream, message.copyright);
        } catch (IOException e) {
            setError(e);
        }
    }

    @Override
    public void visit(MidiSystemTrackName message) {
        try {
            System.out.println(message.trackName);
            writeByte(outputStream, 0xFF);
            writeByte(outputStream, MidiSystemEventName.MetaTrackName.value);
            writeVariableLength(outputStream, message.getLengthInBytes());

            writeString(outputStream, message.trackName);
        } catch (IOException e) {
            setError(e);
        }
    }

    @Override
    public void visit(MidiSystemInstrumentName message) {
        try {
            writeByte(outputStream, 0xFF);
            writeByte(outputStream, MidiSystemEventName.MetaInstrumentName.value);
            writeVariableLength(outputStream, message.getLengthInBytes());

            writeString(outputStream, message.instrumentName);
        } catch (IOException e) {
            setError(e);
        }
    }

    @Override
    public void visit(MidiSystemLyrics message) {
        try {
            writeByte(outputStream, 0xFF);
            writeByte(outputStream, MidiSystemEventName.MetaLyrics.value);
            writeVariableLength(outputStream, message.getLengthInBytes());

            writeString(outputStream, message.lyrics);
        } catch (IOException e) {
            setError(e);
        }

    }

    @Override
    public void visit(MidiSystemMarker message) {
        try {
            writeByte(outputStream, 0xFF);
            writeByte(outputStream, MidiSystemEventName.MetaMarker.value);
            writeVariableLength(outputStream, message.getLengthInBytes());

            writeString(outputStream, message.marker);
        } catch (IOException e) {
            setError(e);
        }
    }

    @Override
    public void visit(MidiSystemCuePoint message) {
        try {
            writeByte(outputStream, 0xFF);
            writeByte(outputStream, MidiSystemEventName.MetaCuePoint.value);
            writeVariableLength(outputStream, message.getLengthInBytes());

            writeString(outputStream, message.cue);
        } catch (IOException e) {
            setError(e);
        }
    }

    @Override
    public void visit(MidiSystemChannelPrefix message) {
        try {
            writeByte(outputStream, 0xFF);
            writeByte(outputStream, MidiSystemEventName.MetaChannelPrefix.value);
            writeVariableLength(outputStream, message.getLengthInBytes());

            writeByte(outputStream, message.prefix);
        } catch (IOException e) {
            setError(e);
        }
    }

    @Override
    public void visit(MidiSystemEndOfTrack message) {
        try {
            writeByte(outputStream, 0xFF);
            writeByte(outputStream, MidiSystemEventName.MetaEndOfTrack.value);
            writeVariableLength(outputStream, message.getLengthInBytes());
        } catch (IOException e) {
            setError(e);
        }
    }

    @Override
    public void visit(MidiSystemSetTempo message) {
        try {
            writeByte(outputStream, 0xFF);
            writeByte(outputStream, MidiSystemEventName.MetaSetTempo.value);
            writeVariableLength(outputStream, message.getLengthInBytes());

            writeUnsignedIntTo3Bytes(outputStream, message.microsecondsPerQuarterNote);
        } catch (IOException e) {
            setError(e);
        }
    }

    @Override
    public void visit(MidiSystemSMPTEOffset message) {
        try {
            writeByte(outputStream, 0xFF);
            writeByte(outputStream, MidiSystemEventName.MetaSMPTEOffset.value);
            writeVariableLength(outputStream, message.getLengthInBytes());

            writeByte(outputStream, message.hours);
            writeByte(outputStream, message.minutes);
            writeByte(outputStream, message.seconds);
            writeByte(outputStream, message.FR);
            writeByte(outputStream, message.FF);
        } catch (IOException e) {
            setError(e);
        }
    }

    @Override
    public void visit(MidiSystemTimeSignature message) {
        try {
            writeByte(outputStream, 0xFF);
            writeByte(outputStream, MidiSystemEventName.MetaTimeSignature.value);
            writeVariableLength(outputStream, message.getLengthInBytes());

            writeByte(outputStream, message.timeSignitureUpper);
            writeByte(outputStream, message.timeSignitureLower);
            writeByte(outputStream, message.clocksPerTick);
            writeByte(outputStream, message.ticks32per24Clocks);
        } catch (IOException e) {
            setError(e);
        }

    }

    @Override
    public void visit(MidiSystemKeySignature message) {
        try {
            writeByte(outputStream, 0xFF);
            writeByte(outputStream, MidiSystemEventName.MetaKeySignature.value);
            writeVariableLength(outputStream, message.getLengthInBytes());

            writeByte(outputStream, message.keySigniture);
            writeByte(outputStream, message.minorKey);
        } catch (IOException e) {
            setError(e);
        }
    }

    @Override
    public void visit(MidiSystemSequencerSpecific message) {
        try {
            writeByte(outputStream, 0xFF);
            writeByte(outputStream, MidiSystemEventName.MetaSequencerSpecific.value);
            writeVariableLength(outputStream, message.getLengthInBytes());

            writeString(outputStream, message.specific);
        } catch (IOException e) {
            setError(e);
        }
    }

    @Override
    public void visit(MidiSystemManufacturerStart message) {
        try {
            writeByte(outputStream, 0xF0);
            writeVariableLength(outputStream, message.getLengthInBytes());
            outputStream.write(message.dataBlob);
            writeByte(outputStream, 0xF7);
        } catch (IOException e) {
            setError(e);
        }
    }

    @Override
    public void visit(MidiSystemManufacturerEnd message) {
        try {
            writeByte(outputStream, 0xF7);
            outputStream.write(message.dataBlob);
            writeByte(outputStream, 0xF7);
        } catch (IOException e) {
            setError(e);
        }
    }

    @Override
    public void visit(MidiSystemUnknown message) {
        try {
            writeByte(outputStream, 0xFF);
            writeByte(outputStream, message.type);
            writeVariableLength(outputStream, message.getLengthInBytes());
            outputStream.write(message.dataBlob);
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

    @Override
    public void visit(MidiSystemExclusive message) {
        super.visit(message);
    }

}
