package midi.Data;

public class MidiHeader {
    public long magicNumber;
    public long headerLength;
    public int format;
    public int numberOfTracks;
    public int divisions;
}