package midiapp;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import midi.Data.MidiFile;
import midi.Reading.MidiFileReader;
import midi.Writing.MidiFileWriter;

public class App {

    public static void main(String[] args) throws IOException {
        // Create custom output stream that throws an error if the output is different
        // from the input

        InputStream fileInputStream = App.class.getClassLoader()
                .getResourceAsStream("midis/wii.mid");

        MidiFile file = MidiFileReader.load(fileInputStream);
        System.out.println("file loaded");

        final InputStream fileInputStream2 = App.class.getClassLoader()
                .getResourceAsStream("midis/wii.mid");

        OutputStream s = new OutputStream() {
            long filePosition = 0;

            @Override
            public void write(int arg0) throws IOException {
                int i = fileInputStream2.read();
                if (i != arg0) {
                    throw new IOException(
                            "expected: [" + Integer.toHexString(i) + "] got [" + Integer.toHexString(arg0)
                                    + "] at position " + filePosition);
                }

                filePosition++;
            }

        };

        MidiFileWriter writer = new MidiFileWriter(s);
        writer.writeFile(file);

        writer = new MidiFileWriter(new FileOutputStream("output.mid"));
        writer.writeFile(file);

        // MidiTiming timing = new MidiTiming();
        // MidiSequencer sequencer = new MidiSequencer(file, new MidiEventListener() {

        // @Override
        // public void onRecieve(MidiEvent event) {
        // System.out.println(event);
        // }

        // }, timing);

        // timing.setTicksPerBeat(file.header.divisions);

        // long startTime = System.currentTimeMillis();

        // while (!sequencer.isFinished()) {
        // double currentTime = (System.currentTimeMillis() - startTime) / 1000.0;
        // sequencer.dispatchEvents();
        // timing.update(currentTime);
        // }
    }
}
