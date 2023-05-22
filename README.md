# Midi library

A Java library for handling basic midis.

Used in [Synthesizer visualizer](https://github.com/mbenniston/synthesizer-visualizer) for midi playback.

Features:

- Reading and writing midis
- Playback of midis

Missing features:

- Support for sequential and asynchronous track modes

Key classes:

- [MidiSequencer](/src/main/java/midi/Playback/MidiSequencer.java), execute midi events at their specified times.
- [MidiFileReader](/src/main/java/midi/Reading/MidiFileReader.java), reads a midi file from an input stream.
- [MidiFileWriter](/src/main/java/midi/Writing/MidiFileWriter.java), writes midi data to an output stream.

## Supported midi events

### Channel

- Voice after touch
- Voice channel pressure
- Voice control change
- Voice note off
- Voice note on
- Voice pitch blend
- Voice program change

### Meta

- Channel prefix
- Copyright
- Cue point
- End of track
- Instrument name
- Key signature
- Lyrics
- Marker
- Sequence
- Sequencer specific
- Set tempo
- SMPTE offset
- Text
- Time signature
- Track name
- Unknown

### System

- Manufacturer message