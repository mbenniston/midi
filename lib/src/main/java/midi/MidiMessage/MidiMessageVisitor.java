package midi.MidiMessage;

import midi.MidiMessage.MidiMessages.*;

public abstract class MidiMessageVisitor {
    public abstract void visit(MidiVoiceNoteOff message);

    public abstract void visit(MidiVoiceNoteOn message);

    public abstract void visit(MidiVoiceAfterTouch message);

    public abstract void visit(MidiVoiceControlChange message);

    public abstract void visit(MidiVoiceProgramChange message);

    public abstract void visit(MidiVoiceChannelPressure message);

    public abstract void visit(MidiVoicePitchBend message);

    public abstract void visit(MidiSystemExclusive message);

    public void visit(MidiMessage message) {
        message.acceptVisitor(this);
    }

    public static class DefaultMessgeVisitor extends MidiMessageVisitor {

        @Override
        public void visit(MidiVoiceNoteOff message) {

        }

        @Override
        public void visit(MidiVoiceNoteOn message) {
        }

        @Override
        public void visit(MidiVoiceAfterTouch message) {
        }

        @Override
        public void visit(MidiVoiceControlChange message) {
        }

        @Override
        public void visit(MidiVoiceProgramChange message) {
        }

        @Override
        public void visit(MidiVoiceChannelPressure message) {
        }

        @Override
        public void visit(MidiVoicePitchBend message) {
        }

        @Override
        public void visit(MidiSystemExclusive message) {
        }
    }
}
