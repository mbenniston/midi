
- [X] convert bytes to ints in data classes
- [X] seperate status into channel and type
    - maybe implement getLength function to return the amount of bytes required for an event
- [X] Implement getLength for rest of system events
- [ ] Seperate meta and sysex events
- [ ] header: divisions timing
- [ ] track formats (sequentials, asyncrhonus)


MidiEvent (has delta time)
    MidiChannelEvent (has channel)
    SysExEvent (has custom data)
    MetaEvent (has type and length)