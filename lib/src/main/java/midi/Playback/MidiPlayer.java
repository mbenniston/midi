// package midi.Playback;

// import java.util.ArrayList;

// import midi.Data.MidiFile;
// import midi.MidiEvent.MidiMessageVisitor;
// import synth.WaveForms.Instrument;
// import synth.WaveForms.StandardVoices;
// import synth.WaveForms.Voice;

// public class MidiPlayer {
// private static final int CHANNELS = 16;

// private MidiSequencer sequencer;
// private MidiChannel[] channels;

// private MidiChannelEventRouter channelRouter;
// private Timing timing;
// private ArrayList<MidiMessageVisitor> listener = new ArrayList<>();

// public MidiPlayer(MidiFile file) throws CloneNotSupportedException {
// channels = new MidiChannel[CHANNELS];

// channelRouter = new MidiChannelEventRouter();
// channelRouter.adapters = new MidiChannelEventAdapter[CHANNELS];

// timing = new Timing();
// timing.setTicksPerBeat(file.header.divisions);

// sequencer = new MidiSequencer(file, channelRouter, timing);

// Voice defaultVoice = StandardVoices.createDefaultVoice2();

// for (int i = 0; i < CHANNELS; i++) {
// MidiChannel channel = new MidiChannel();
// channel.instrument = new Instrument(defaultVoice);
// channel.timingView = timing.view;

// channels[i] = channel;
// channelRouter.adapters[i] = new MidiChannelEventAdapter(channel);
// }
// }

// public void update(double currentTime) {
// timing.update(currentTime);

// if (timing.getDeltaTicks() != 0)
// sequencer.dispatchEvents();

// for (MidiChannel channel : channels) {
// channel.instrument.update(currentTime);
// }
// }

// public double sample(double currentTime) {
// double a = 0.0;
// for (MidiChannel channel : channels) {
// a += channel.instrument.sample(currentTime);
// }
// return a;
// }

// public boolean isPlaying() {
// return !sequencer.isFinished();
// }

// public void addListener(MidiMessageVisitor visitor) {
// if (!listener.contains(visitor)) {
// listener.add(visitor);
// }
// }

// public void removeListener(MidiMessageVisitor visitor) {
// listener.remove(visitor);
// }
// }
