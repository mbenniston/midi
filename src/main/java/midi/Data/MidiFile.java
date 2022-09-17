package midi.Data;

import java.util.ArrayList;

public class MidiFile {
    public MidiHeader header;
    public ArrayList<MidiTrack> tracks = new ArrayList<>();
}
