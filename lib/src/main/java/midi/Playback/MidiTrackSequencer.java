package midi.Playback;

import midi.Data.MidiTrack;
import midi.Data.Message.MidiMessage;
import midi.Data.Message.MidiMessageListener;
import midi.Data.Message.MidiMessages.MidiSystemMessages.MidiSystemSetTempo;

public class MidiTrackSequencer {
    private final MidiTrack track;
    private final MidiTiming timing;

    private int currentMessageIndex = 0;
    private long waitTicks = 0;
    private MidiMessage stallMessage;

    private final MidiMessageListener reciever;

    public MidiTrackSequencer(
            MidiTrack track,
            MidiMessageListener reciever,
            MidiTiming timing) {
        this.track = track;
        this.reciever = reciever;
        this.timing = timing;
    }

    public void dispatchEvents() {
        while (!isStalled() && !isOutOfMessages()) {
            MidiMessage message = getCurrentMessage();

            if (message.timeDelta == 0 || message == stallMessage) {
                stallMessage = null;
                reciever.onRecieve(message);

                if (message instanceof MidiSystemSetTempo) {
                    MidiSystemSetTempo tempo = (MidiSystemSetTempo) message;
                    timing.setMicroSecondsPerBeat(tempo.microsecondsPerQuarterNote);
                }

                nextMessage();
            } else {
                stall(message);
            }
        }

        if (waitTicks > 0) {
            waitTicks -= timing.getDeltaTicks();
        }
    }

    private boolean isStalled() {
        return waitTicks > 0;
    }

    private boolean isOutOfMessages() {
        return currentMessageIndex >= track.messages.size();
    }

    private MidiMessage getCurrentMessage() {
        return track.messages.get(currentMessageIndex);
    }

    private void nextMessage() {
        currentMessageIndex++;
    }

    private void stall(MidiMessage message) {
        stallMessage = message;
        waitTicks = message.timeDelta;
    }

    public boolean isFinished() {
        return isOutOfMessages();
    }
}
