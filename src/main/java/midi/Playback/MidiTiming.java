package midi.Playback;

public class MidiTiming {
    private double currentTime = 0.0;

    private long ticksPerBeat = 60;
    private long microSecondsPerBeat = 500000;
    private double microSecondsPerTick = microSecondsPerBeat / (double) ticksPerBeat;

    public final MidiTimingView view;

    public void reset() {
        currentTime = 0;
        ticksPerBeat = 60;
        microSecondsPerBeat = 500000;
        microSecondsPerTick = microSecondsPerBeat / (double) ticksPerBeat;
    }

    public MidiTiming() {
        view = new MidiTimingView();
    }

    public void update(double time) {
        currentTime = time;
    }

    public void setTicksPerBeat(long ticksPerBeat) {
        this.ticksPerBeat = ticksPerBeat;
        microSecondsPerTick = microSecondsPerBeat / (double) ticksPerBeat;
    }

    public void setMicroSecondsPerBeat(long microSecondsPerBeat) {
        this.microSecondsPerBeat = microSecondsPerBeat;

        microSecondsPerTick = microSecondsPerBeat / (double) ticksPerBeat;
    }

    public double getBeatsPerMinute() {
        return secondsToMicroSeconds(60.0) / microSecondsPerBeat;
    }

    public double getCurrentTime() {
        return currentTime;
    }

    public double getMicroSecondsPerTick() {
        return microSecondsPerTick;
    }

    public double ticksToTime(double ticks) {
        return MidiTiming.ticksToTime(ticks, microSecondsPerTick);
    }

    public double timeToTicks(double seconds) {
        return MidiTiming.timeToTicks(seconds, microSecondsPerTick);
    }

    public static double secondsToMicroSeconds(double seconds) {
        return seconds * 1000000.0;
    }

    public static double microSecondsToSeconds(double microSeconds) {
        return microSeconds / 1000000.0;
    }

    public static double ticksToTime(double ticks, double microSecondsPerTick) {
        return microSecondsToSeconds(ticks * microSecondsPerTick);
    }

    public static double timeToTicks(double seconds, double microSecondsPerTick) {
        return secondsToMicroSeconds(seconds) / microSecondsPerTick;
    }

    public class MidiTimingView {
        public double getCurrentTime() {
            return MidiTiming.this.getCurrentTime();
        }
    }
}
