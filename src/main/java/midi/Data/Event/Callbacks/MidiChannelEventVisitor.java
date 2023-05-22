package midi.Data.Event.Callbacks;

import midi.Data.Event.MidiEvents.MidiChannelEvent;
import midi.Data.Event.MidiEvents.MidiChannelEvents.*;

public abstract class MidiChannelEventVisitor implements MidiChannelEventListener {
    public abstract void visit(MidiVoiceNoteOff event);

    public abstract void visit(MidiVoiceNoteOn event);

    public abstract void visit(MidiVoiceAfterTouch event);

    public abstract void visit(MidiVoiceControlChange event);

    public abstract void visit(MidiVoiceProgramChange event);

    public abstract void visit(MidiVoiceChannelPressure event);

    public abstract void visit(MidiVoicePitchBend event);

    public void visit(MidiChannelEvent event) {
        event.acceptVisitor(this);
    }

    @Override
    public void onRecieve(MidiChannelEvent event) {
        visit(event);
    }

    public static class DefaultMessageVisitor extends MidiChannelEventVisitor {

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
    }

}
