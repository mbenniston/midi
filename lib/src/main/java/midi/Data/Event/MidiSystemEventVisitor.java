package midi.Data.Event;

import midi.Data.Event.MidiEvents.MidiSystemExclusive;
import midi.Data.Event.MidiEvents.MidiSystemEvents.*;

public abstract class MidiSystemEventVisitor implements MidiSystemEventListener {

    public abstract void visit(MidiSystemSequence event);

    public abstract void visit(MidiSystemText event);

    public abstract void visit(MidiSystemCopyright event);

    public abstract void visit(MidiSystemTrackName event);

    public abstract void visit(MidiSystemInstrumentName event);

    public abstract void visit(MidiSystemLyrics event);

    public abstract void visit(MidiSystemMarker event);

    public abstract void visit(MidiSystemCuePoint event);

    public abstract void visit(MidiSystemChannelPrefix event);

    public abstract void visit(MidiSystemEndOfTrack event);

    public abstract void visit(MidiSystemSetTempo event);

    public abstract void visit(MidiSystemSMPTEOffset event);

    public abstract void visit(MidiSystemTimeSignature event);

    public abstract void visit(MidiSystemKeySignature event);

    public abstract void visit(MidiSystemSequencerSpecific event);

    public abstract void visit(MidiSystemManufacturerStart event);

    public abstract void visit(MidiSystemManufacturerEnd event);

    public abstract void visit(MidiSystemUnknown event);

    public void visit(MidiSystemExclusive event) {
        event.acceptVisitor(this);
    }

    @Override
    public void onRecieve(MidiSystemExclusive event) {
        visit(event);
    }

    public static class DefaultEventVisitor extends MidiSystemEventVisitor {

        @Override
        public void visit(MidiSystemSequence event) {
        }

        @Override
        public void visit(MidiSystemText event) {
        }

        @Override
        public void visit(MidiSystemCopyright event) {
        }

        @Override
        public void visit(MidiSystemTrackName event) {
        }

        @Override
        public void visit(MidiSystemInstrumentName event) {
        }

        @Override
        public void visit(MidiSystemLyrics event) {
        }

        @Override
        public void visit(MidiSystemMarker event) {
        }

        @Override
        public void visit(MidiSystemCuePoint event) {
        }

        @Override
        public void visit(MidiSystemChannelPrefix event) {
        }

        @Override
        public void visit(MidiSystemEndOfTrack event) {
        }

        @Override
        public void visit(MidiSystemSetTempo event) {
        }

        @Override
        public void visit(MidiSystemSMPTEOffset event) {
        }

        @Override
        public void visit(MidiSystemTimeSignature event) {
        }

        @Override
        public void visit(MidiSystemKeySignature event) {
        }

        @Override
        public void visit(MidiSystemSequencerSpecific event) {
        }

        @Override
        public void visit(MidiSystemManufacturerStart event) {

        }

        @Override
        public void visit(MidiSystemManufacturerEnd event) {

        }

        @Override
        public void visit(MidiSystemUnknown event) {
            // TODO Auto-generated method stub

        }
    }
}
