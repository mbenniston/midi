package midi.Data.Event.Callbacks;

import midi.Data.Event.MidiEvents.MidiMetaEvent;
import midi.Data.Event.MidiEvents.MidiMetaEvents.*;

public abstract class MidiMetaEventVisitor implements MidiMetaEventListener {

    public abstract void visit(MidiMetaSequence event);

    public abstract void visit(MidiMetaText event);

    public abstract void visit(MidiMetaCopyright event);

    public abstract void visit(MidiMetaTrackName event);

    public abstract void visit(MidiMetaInstrumentName event);

    public abstract void visit(MidiMetaLyrics event);

    public abstract void visit(MidiMetaMarker event);

    public abstract void visit(MidiMetaCuePoint event);

    public abstract void visit(MidiMetaChannelPrefix event);

    public abstract void visit(MidiMetaEndOfTrack event);

    public abstract void visit(MidiMetaSetTempo event);

    public abstract void visit(MidiMetaSMPTEOffset event);

    public abstract void visit(MidiMetaTimeSignature event);

    public abstract void visit(MidiMetaKeySignature event);

    public abstract void visit(MidiMetaSequencerSpecific event);

    public abstract void visit(MidiMetaUnknown event);

    public void visit(MidiMetaEvent event) {
        event.acceptVisitor(this);
    }

    @Override
    public void onRecieve(MidiMetaEvent event) {
        visit(event);
    }

    public static class DefaultEventVisitor extends MidiMetaEventVisitor {

        @Override
        public void visit(MidiMetaSequence event) {
        }

        @Override
        public void visit(MidiMetaText event) {
        }

        @Override
        public void visit(MidiMetaCopyright event) {
        }

        @Override
        public void visit(MidiMetaTrackName event) {
        }

        @Override
        public void visit(MidiMetaInstrumentName event) {
        }

        @Override
        public void visit(MidiMetaLyrics event) {
        }

        @Override
        public void visit(MidiMetaMarker event) {
        }

        @Override
        public void visit(MidiMetaCuePoint event) {
        }

        @Override
        public void visit(MidiMetaChannelPrefix event) {
        }

        @Override
        public void visit(MidiMetaEndOfTrack event) {
        }

        @Override
        public void visit(MidiMetaSetTempo event) {
        }

        @Override
        public void visit(MidiMetaSMPTEOffset event) {
        }

        @Override
        public void visit(MidiMetaTimeSignature event) {
        }

        @Override
        public void visit(MidiMetaKeySignature event) {
        }

        @Override
        public void visit(MidiMetaSequencerSpecific event) {
        }

        @Override
        public void visit(MidiMetaUnknown event) {
        }
    }
}
