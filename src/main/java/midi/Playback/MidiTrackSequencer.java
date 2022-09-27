package midi.Playback;

import midi.Data.MidiTrack;
import midi.Data.Event.MidiEvent;
import midi.Data.Event.MidiEvents.MidiMetaEvents.MidiMetaSetTempo;

public class MidiTrackSequencer {
    private final MidiTrack track;
    private final MidiTiming timing;

    private double currentTime = 0;
    private double previousMicroSecondsPerTick;
    private int currentEventIndex = 0;

    private MidiEvent stallEvent = null;
    private double waitStart = 0;
    private double waitTicks = 0;

    private final MidiEventExecutor receiver;

    public MidiTrackSequencer(
            MidiTrack track,
            MidiEventExecutor receiver,
            MidiTiming timing) {
        this.track = track;
        this.receiver = receiver;
        this.timing = timing;

        previousMicroSecondsPerTick = timing.getMicroSecondsPerTick();
    }

    public void executeUntilStall() {
        while (!isWithinStallTime() && !isOutOfEvents()) {
            MidiEvent event = getCurrentEvent();

            if (event.timeDelta == 0 || event == stallEvent) {
                stallEvent = null;

                receiver.onExecute(event, getStallUntil());

                if (event instanceof MidiMetaSetTempo) {
                    MidiMetaSetTempo tempo = (MidiMetaSetTempo) event;
                    timing.setMicroSecondsPerBeat(tempo.microsecondsPerQuarterNote);
                    previousMicroSecondsPerTick = timing.getMicroSecondsPerTick();
                }
                nextEvent();
            } else {
                stall(event);
            }
        }
    }

    public void updateCurrentTime(double time) {
        consumeStallTime(time);
        currentTime = time;
    }

    private void consumeStallTime(double timeStepEndTime) {
        double deltaTime = timeStepEndTime - currentTime;

        double ticksElapsed = MidiTiming.timeToTicks(deltaTime, previousMicroSecondsPerTick);
        waitStart = timeStepEndTime;
        waitTicks -= ticksElapsed;
        previousMicroSecondsPerTick = timing.getMicroSecondsPerTick();
    }

    public double getStallUntil() {
        return waitStart + MidiTiming.ticksToTime(waitTicks, previousMicroSecondsPerTick);
    }

    public boolean isStalled() {
        return stallEvent != null;
    }

    public MidiEvent getStallEvent() {
        return stallEvent;
    }

    private void stall(MidiEvent event) {
        stallEvent = event;
        waitStart = currentTime;
        waitTicks = event.timeDelta;
    }

    private boolean isWithinStallTime() {
        return currentTime < getStallUntil();
    }

    public boolean isFinished() {
        return isOutOfEvents();
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

    public void reset() {
        previousMicroSecondsPerTick = timing.getMicroSecondsPerTick();
        currentTime = 0;
        currentEventIndex = 0;
        stallEvent = null;
        waitStart = 0;
        waitTicks = 0;
    }
}
