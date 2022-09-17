package midi.Data.Event;

import midi.Data.Event.MidiEvents.*;

public abstract class MidiEventVisitor implements MidiEventListener {
    public abstract void visit(MidiVoiceNoteOff event);

    public abstract void visit(MidiVoiceNoteOn event);

    public abstract void visit(MidiVoiceAfterTouch event);

    public abstract void visit(MidiVoiceControlChange event);

    public abstract void visit(MidiVoiceProgramChange event);

    public abstract void visit(MidiVoiceChannelPressure event);

    public abstract void visit(MidiVoicePitchBend event);

    public abstract void visit(MidiMetaEvent event);

    public void visit(MidiEvent event) {
        event.acceptVisitor(this);
    }

    @Override
    public void onRecieve(MidiEvent event) {
        visit(event);
    }

    public static class DefaultMessgeVisitor extends MidiEventVisitor {

        @Override
        public void visit(MidiVoiceNoteOff event) {

        }

        @Override
        public void visit(MidiVoiceNoteOn event) {
        }

        @Override
        public void visit(MidiVoiceAfterTouch event) {
        }

        @Override
        public void visit(MidiVoiceControlChange event) {
        }

        @Override
        public void visit(MidiVoiceProgramChange event) {
        }

        @Override
        public void visit(MidiVoiceChannelPressure event) {
        }

        @Override
        public void visit(MidiVoicePitchBend event) {
        }

        @Override
        public void visit(MidiMetaEvent event) {
        }
    }

}
