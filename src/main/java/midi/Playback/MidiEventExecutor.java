package midi.Playback;

import midi.Data.Event.MidiEvent;

public interface MidiEventExecutor {
    void onExecute(MidiEvent event, double executeTime);
}
