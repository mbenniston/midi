package midi.Data;

import java.util.ArrayList;

import midi.MidiMessage.MidiMessage;

public class MidiTrack {
    public MidiTrackHeader header;
    public ArrayList<MidiMessage> messages = new ArrayList<>();
}
