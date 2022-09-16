package midi.Data.Message;

import midi.Data.Message.MidiMessages.MidiSystemExclusive;
import midi.Data.Message.MidiMessages.MidiSystemMessages.*;

public abstract class MidiSystemMessageVisitor implements MidiSystemMessageListener {

    public abstract void visit(MidiSystemSequence message);

    public abstract void visit(MidiSystemText message);

    public abstract void visit(MidiSystemCopyright message);

    public abstract void visit(MidiSystemTrackName message);

    public abstract void visit(MidiSystemInstrumentName message);

    public abstract void visit(MidiSystemLyrics message);

    public abstract void visit(MidiSystemMarker message);

    public abstract void visit(MidiSystemCuePoint message);

    public abstract void visit(MidiSystemChannelPrefix message);

    public abstract void visit(MidiSystemEndOfTrack message);

    public abstract void visit(MidiSystemSetTempo message);

    public abstract void visit(MidiSystemSMPTEOffset message);

    public abstract void visit(MidiSystemTimeSignature message);

    public abstract void visit(MidiSystemKeySignature message);

    public abstract void visit(MidiSystemSequencerSpecific message);

    public void visit(MidiSystemExclusive message) {
        message.acceptVisitor(this);
    }

    @Override
    public void onRecieve(MidiSystemExclusive message) {
        visit(message);
    }

    public static class DefaultMessageVisitor extends MidiSystemMessageVisitor {

        @Override
        public void visit(MidiSystemSequence message) {
        }

        @Override
        public void visit(MidiSystemText message) {
        }

        @Override
        public void visit(MidiSystemCopyright message) {
        }

        @Override
        public void visit(MidiSystemTrackName message) {
        }

        @Override
        public void visit(MidiSystemInstrumentName message) {
        }

        @Override
        public void visit(MidiSystemLyrics message) {
        }

        @Override
        public void visit(MidiSystemMarker message) {
        }

        @Override
        public void visit(MidiSystemCuePoint message) {
        }

        @Override
        public void visit(MidiSystemChannelPrefix message) {
        }

        @Override
        public void visit(MidiSystemEndOfTrack message) {
        }

        @Override
        public void visit(MidiSystemSetTempo message) {
        }

        @Override
        public void visit(MidiSystemSMPTEOffset message) {
        }

        @Override
        public void visit(MidiSystemTimeSignature message) {
        }

        @Override
        public void visit(MidiSystemKeySignature message) {
        }

        @Override
        public void visit(MidiSystemSequencerSpecific message) {
        }
    }
}
