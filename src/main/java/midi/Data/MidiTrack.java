package midi.Data;

import java.util.ArrayList;

import midi.Data.Event.MidiEvent;

public class MidiTrack {
    public MidiTrackHeader header;
    public ArrayList<MidiEvent> events = new ArrayList<>();
}
