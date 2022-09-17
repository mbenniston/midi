package midi.Writing;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;

import midi.Reading.ReadingUtils;

public class WritingUtils {

    public static void convertLongToVariableLength(long l, ArrayList<Byte> destBytes) {
        ArrayList<Byte> longInBytes = new ArrayList<>();

        long numSevenBits = ReadingUtils.MAX_READABLE_BYTES_FOR_VARIABLE_PRECISION;

        // read long left to right in groups of 7 bits
        for (int i = 0; i < numSevenBits; i++) {
            long b = (l >>> (i * 7)) & 0x7FL;
            longInBytes.add((byte) b);
        }

        // reverse to maintain big endian order
        Collections.reverse(longInBytes);

        // remove empty bytes at the end of the number
        byte byteToRemove;
        do {
            byteToRemove = longInBytes.get(0);
            if ((byteToRemove & 0x7F) == 0 && longInBytes.size() > 1) {
                longInBytes.remove(0);
            }
        } while ((byteToRemove & 0x7F) == 0 && longInBytes.size() > 1);

        for (int i = 0; i < longInBytes.size(); i++) {
            final int value = longInBytes.get(i) & 0x7F;
            final boolean isLastByte = i == (longInBytes.size() - 1);

            final int encodedValue = value | (isLastByte ? 0 : 1 << 7);

            destBytes.add((byte) encodedValue);
        }
    }

    public static void writeVariableLength(OutputStream stream, long l) throws IOException {
        ArrayList<Byte> bytes = new ArrayList<>();
        convertLongToVariableLength(l, bytes);
        for (Byte b : bytes) {
            stream.write(b & 0xFF);
        }
    }

    public static void writeString(OutputStream stream, String string) throws IOException {
        for (int i = 0; i < string.length(); i++) {
            writeByte(stream, (byte) string.charAt(i));
        }
    }

    public static void writeUnsignedIntTo3Bytes(OutputStream stream, long unsignedInt) throws IOException {
        stream.write((int) ((unsignedInt & 0xFF0000L) >>> 16) & 0xFF);
        stream.write((int) ((unsignedInt & 0xFF00L) >>> 8) & 0xFF);
        stream.write((int) ((unsignedInt & 0xFFL)) & 0xFF);
    }

    public static void writeByte(OutputStream stream, byte b) throws IOException {
        stream.write(b & 0xFF);
    }

    public static void writeByte(OutputStream stream, int b) throws IOException {
        stream.write(b & 0xFF);
    }

    public static void writeUnsignedInt(OutputStream stream, long unsignedInt) throws IOException {
        stream.write((int) ((unsignedInt & 0xFF000000L) >>> 24) & 0xFF);
        stream.write((int) ((unsignedInt & 0xFF0000L) >>> 16) & 0xFF);
        stream.write((int) ((unsignedInt & 0xFF00L) >>> 8) & 0xFF);
        stream.write((int) ((unsignedInt & 0xFFL)) & 0xFF);
    }

    public static void writeUnsignedShort(OutputStream stream, int unsignedShort) throws IOException {
        stream.write((unsignedShort & 0xFF00) >>> 8);
        stream.write(unsignedShort & 0xFF);
    }
}
