package midi.Data;

import java.util.ArrayList;

import midi.Data.Message.MidiMessage;

public class MidiTrack {
    public MidiTrackHeader header;
    public ArrayList<MidiMessage> messages = new ArrayList<>();
}
