package midi.Playback;

import midi.Data.MidiTrack;
import midi.Data.Event.MidiEvent;
import midi.Data.Event.MidiEventListener;
import midi.Data.Event.MidiEvents.MidiMetaEvents.MidiMetaSetTempo;

public class MidiTrackSequencer {
    private final MidiTrack track;
    private final MidiTiming timing;

    private int currentEventIndex = 0;
    private long waitTicks = 0;
    private MidiEvent stallEvent;

    private final MidiEventListener reciever;

    public MidiTrackSequencer(
            MidiTrack track,
            MidiEventListener reciever,
            MidiTiming timing) {
        this.track = track;
        this.reciever = reciever;
        this.timing = timing;
    }

    public void dispatchEvents() {
        while (!isStalled() && !isOutOfEvents()) {
            MidiEvent event = getCurrentEvent();

            if (event.timeDelta == 0 || event == stallEvent) {
                stallEvent = null;
                reciever.onRecieve(event);

                if (event instanceof MidiMetaSetTempo) {
                    MidiMetaSetTempo tempo = (MidiMetaSetTempo) event;
                    timing.setMicroSecondsPerBeat(tempo.microsecondsPerQuarterNote);
                }

                nextEvent();
            } else {
                stall(event);
            }
        }

        if (waitTicks > 0) {
            waitTicks -= timing.getDeltaTicks();
        }
    }

    private boolean isStalled() {
        return waitTicks > 0;
    }

    private boolean isOutOfEvents() {
        return currentEventIndex >= track.events.size();
    }

    private MidiEvent getCurrentEvent() {
        return track.events.get(currentEventIndex);
    }

    private void nextEvent() {
        currentEventIndex++;
    }

    private void stall(MidiEvent event) {
        stallEvent = event;
        waitTicks = event.timeDelta;
    }

    public boolean isFinished() {
        return isOutOfEvents();
    }
}
