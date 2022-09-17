package midi.Data.Event.Callbacks;

import midi.Data.Event.MidiEvent;
import midi.Data.Event.MidiEvents.MidiChannelEvent;
import midi.Data.Event.MidiEvents.MidiMetaEvent;
import midi.Data.Event.MidiEvents.MidiSystemExclusiveEvent;

public abstract class MidiEventVisitor implements MidiEventListener {

    public abstract void visit(MidiChannelEvent event);

    public abstract void visit(MidiSystemExclusiveEvent event);

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
        public void visit(MidiMetaEvent event) {
        }

        @Override
        public void visit(MidiChannelEvent event) {
        }

        @Override
        public void visit(MidiSystemExclusiveEvent event) {
        }
    }
}
