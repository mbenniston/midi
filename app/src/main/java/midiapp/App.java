package midiapp;

import java.io.InputStream;
import java.time.Clock;
import java.util.Timer;

import midi.Data.MidiFile;
import midi.Data.Message.MidiMessage;
import midi.Data.Message.MidiMessageListener;
import midi.Playback.MidiSequencer;
import midi.Playback.MidiTiming;
import midi.Reading.MidiFileReader;

public class App {

    public static void main(String[] args) {
        InputStream fileInputStream = App.class.getClassLoader()
                .getResourceAsStream("midis/scale.mid");

        MidiFile file = MidiFileReader.load(fileInputStream);
        System.out.println("file loaded");

        MidiTiming timing = new MidiTiming();
        MidiSequencer sequencer = new MidiSequencer(file, new MidiMessageListener() {

            @Override
            public void onRecieve(MidiMessage message) {
                System.out.println(message);
            }

        }, timing);

        timing.setTicksPerBeat(file.header.divisions);

        long startTime = System.currentTimeMillis();

        while (!sequencer.isFinished()) {
            double currentTime = (System.currentTimeMillis() - startTime) / 1000.0;
            sequencer.dispatchEvents();
            timing.update(currentTime);
        }
    }
}
