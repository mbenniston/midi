package midi.Data.Message;

import midi.Data.Message.MidiMessages.MidiSystemExclusive;

public interface MidiSystemMessageListener {
    void onRecieve(MidiSystemExclusive message);
}
