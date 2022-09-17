package midi.Writing;

import java.io.IOException;
import java.io.OutputStream;

import midi.Data.MidiSystemEventName;
import midi.Data.Event.MidiSystemEventVisitor;
import midi.Data.Event.MidiEvents.MidiSystemExclusive;
import midi.Data.Event.MidiEvents.MidiSystemEvents.*;

import static midi.Writing.WritingUtils.*;

public class MidiSystemEventWriter extends MidiSystemEventVisitor {
    private OutputStream outputStream;

    private IOException exception = null;

    public MidiSystemEventWriter(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public void visit(MidiSystemSequence event) {
        try {
            writeByte(outputStream, 0xFF);
            writeByte(outputStream, MidiSystemEventName.MetaSequence.value);
            writeVariableLength(outputStream, event.getLengthInBytes());
            writeByte(outputStream, event.sequence1);
            writeByte(outputStream, event.sequence2);
        } catch (IOException e) {
            setError(e);
        }
    }

    @Override
    public void visit(MidiSystemText event) {
        try {
            writeByte(outputStream, 0xFF);
            writeByte(outputStream, MidiSystemEventName.MetaText.value);
            writeVariableLength(outputStream, event.getLengthInBytes());

            writeString(outputStream, event.text);
        } catch (IOException e) {
            setError(e);
        }
    }

    @Override
    public void visit(MidiSystemCopyright event) {
        try {
            writeByte(outputStream, 0xFF);
            writeByte(outputStream, MidiSystemEventName.MetaCopyright.value);
            writeVariableLength(outputStream, event.getLengthInBytes());

            writeString(outputStream, event.copyright);
        } catch (IOException e) {
            setError(e);
        }
    }

    @Override
    public void visit(MidiSystemTrackName event) {
        try {
            System.out.println(event.trackName);
            writeByte(outputStream, 0xFF);
            writeByte(outputStream, MidiSystemEventName.MetaTrackName.value);
            writeVariableLength(outputStream, event.getLengthInBytes());

            writeString(outputStream, event.trackName);
        } catch (IOException e) {
            setError(e);
        }
    }

    @Override
    public void visit(MidiSystemInstrumentName event) {
        try {
            writeByte(outputStream, 0xFF);
            writeByte(outputStream, MidiSystemEventName.MetaInstrumentName.value);
            writeVariableLength(outputStream, event.getLengthInBytes());

            writeString(outputStream, event.instrumentName);
        } catch (IOException e) {
            setError(e);
        }
    }

    @Override
    public void visit(MidiSystemLyrics event) {
        try {
            writeByte(outputStream, 0xFF);
            writeByte(outputStream, MidiSystemEventName.MetaLyrics.value);
            writeVariableLength(outputStream, event.getLengthInBytes());

            writeString(outputStream, event.lyrics);
        } catch (IOException e) {
            setError(e);
        }

    }

    @Override
    public void visit(MidiSystemMarker event) {
        try {
            writeByte(outputStream, 0xFF);
            writeByte(outputStream, MidiSystemEventName.MetaMarker.value);
            writeVariableLength(outputStream, event.getLengthInBytes());

            writeString(outputStream, event.marker);
        } catch (IOException e) {
            setError(e);
        }
    }

    @Override
    public void visit(MidiSystemCuePoint event) {
        try {
            writeByte(outputStream, 0xFF);
            writeByte(outputStream, MidiSystemEventName.MetaCuePoint.value);
            writeVariableLength(outputStream, event.getLengthInBytes());

            writeString(outputStream, event.cue);
        } catch (IOException e) {
            setError(e);
        }
    }

    @Override
    public void visit(MidiSystemChannelPrefix event) {
        try {
            writeByte(outputStream, 0xFF);
            writeByte(outputStream, MidiSystemEventName.MetaChannelPrefix.value);
            writeVariableLength(outputStream, event.getLengthInBytes());

            writeByte(outputStream, event.prefix);
        } catch (IOException e) {
            setError(e);
        }
    }

    @Override
    public void visit(MidiSystemEndOfTrack event) {
        try {
            writeByte(outputStream, 0xFF);
            writeByte(outputStream, MidiSystemEventName.MetaEndOfTrack.value);
            writeVariableLength(outputStream, event.getLengthInBytes());
        } catch (IOException e) {
            setError(e);
        }
    }

    @Override
    public void visit(MidiSystemSetTempo event) {
        try {
            writeByte(outputStream, 0xFF);
            writeByte(outputStream, MidiSystemEventName.MetaSetTempo.value);
            writeVariableLength(outputStream, event.getLengthInBytes());

            writeUnsignedIntTo3Bytes(outputStream, event.microsecondsPerQuarterNote);
        } catch (IOException e) {
            setError(e);
        }
    }

    @Override
    public void visit(MidiSystemSMPTEOffset event) {
        try {
            writeByte(outputStream, 0xFF);
            writeByte(outputStream, MidiSystemEventName.MetaSMPTEOffset.value);
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
    public void visit(MidiSystemTimeSignature event) {
        try {
            writeByte(outputStream, 0xFF);
            writeByte(outputStream, MidiSystemEventName.MetaTimeSignature.value);
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
    public void visit(MidiSystemKeySignature event) {
        try {
            writeByte(outputStream, 0xFF);
            writeByte(outputStream, MidiSystemEventName.MetaKeySignature.value);
            writeVariableLength(outputStream, event.getLengthInBytes());

            writeByte(outputStream, event.keySigniture);
            writeByte(outputStream, event.minorKey);
        } catch (IOException e) {
            setError(e);
        }
    }

    @Override
    public void visit(MidiSystemSequencerSpecific event) {
        try {
            writeByte(outputStream, 0xFF);
            writeByte(outputStream, MidiSystemEventName.MetaSequencerSpecific.value);
            writeVariableLength(outputStream, event.getLengthInBytes());

            writeString(outputStream, event.specific);
        } catch (IOException e) {
            setError(e);
        }
    }

    @Override
    public void visit(MidiSystemManufacturerStart event) {
        try {
            writeByte(outputStream, 0xF0);
            writeVariableLength(outputStream, event.getLengthInBytes());
            outputStream.write(event.dataBlob);
            writeByte(outputStream, 0xF7);
        } catch (IOException e) {
            setError(e);
        }
    }

    @Override
    public void visit(MidiSystemManufacturerEnd event) {
        try {
            writeByte(outputStream, 0xF7);
            outputStream.write(event.dataBlob);
            writeByte(outputStream, 0xF7);
        } catch (IOException e) {
            setError(e);
        }
    }

    @Override
    public void visit(MidiSystemUnknown event) {
        try {
            writeByte(outputStream, 0xFF);
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

    @Override
    public void visit(MidiSystemExclusive event) {
        super.visit(event);
    }

}
