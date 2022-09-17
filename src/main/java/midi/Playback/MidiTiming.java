package midi.Playback;

public class MidiTiming {
    private double currentTime = 0.0;
    private long ticks = 0;
    private long deltaTicks = 0;
    private double lastTickTime = 0;

    private long ticksPerBeat = 60;
    private long microSecondsPerBeat = 500000;
    private double microSecondsPerTick = microSecondsPerBeat / (double) ticksPerBeat;

    public final MidiTimingView view;

    public MidiTiming() {
        view = new MidiTimingView();
    }

    public void update(double time) {
        currentTime = time;

        double timeSinceLastTick = currentTime - lastTickTime;
        double timeSinceLastTickMicroSeconds = secondsToMicroSeconds(timeSinceLastTick);

        deltaTicks = 0;

        if (timeSinceLastTickMicroSeconds > microSecondsPerTick) {
            deltaTicks = (long) (timeSinceLastTickMicroSeconds / microSecondsPerTick);

            double timeLeftOver = timeSinceLastTickMicroSeconds - (deltaTicks * microSecondsPerTick);
            lastTickTime = currentTime - microSecondsToSeconds(timeLeftOver);
        }

        ticks += deltaTicks;
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

    public long getTicks() {
        return ticks;
    }

    public long getDeltaTicks() {
        return deltaTicks;
    }

    public double getCurrentTime() {
        return currentTime;
    }

    private static double secondsToMicroSeconds(double seconds) {
        return seconds * 1000000.0;
    }

    private static double microSecondsToSeconds(double microSeconds) {
        return microSeconds / 1000000.0;
    }

    public class MidiTimingView {
        public double getCurrentTime() {
            return MidiTiming.this.getCurrentTime();
        }

        public long getTicks() {
            return MidiTiming.this.getTicks();
        }

        public long getDeltaTicks() {
            return MidiTiming.this.getDeltaTicks();
        }
    }
}
