package midi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import midi.Reading.ReadingUtils;

public class TestUtils {

    public static String createRandomString(int length) {
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            builder.append(random.nextInt(0xFF + 1));
        }
        return builder.toString();
    }

    public static byte[] createRandomBytes(int length) {
        byte[] bytes = new byte[length];
        new Random().nextBytes(bytes);
        return bytes;
    }

    public static int[] createRandomShorts(int length) {
        int[] shorts = new int[length];
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            shorts[i] = random.nextInt(0, 0xFFFF + 1);
        }
        return shorts;
    }

    public static long[] createRandomUnsignedInts(int length) {
        return createRandomUnsignedInts(length, 0xFFFFFFFFL);
    }

    public static long[] createRandomUnsignedInts(int length, long maxInt) {
        long[] ints = new long[length];
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            ints[i] = random.nextLong(0L, maxInt + 1L);
        }
        return ints;
    }

    public static long[] createRandomVariableLength(int length) {
        long[] ints = new long[length];
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            ints[i] = random.nextLong(0L, (1L << (ReadingUtils.MAX_BITS_SUPPORTED_FOR_VARIABLE_PRECISION + 1))
                    - (1L << (ReadingUtils.MAX_BITS_SUPPORTED_FOR_VARIABLE_PRECISION)) + 1L);
        }
        return ints;
    }

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

    public static byte[] convertLongsToVariableLength(long[] longs) {
        ArrayList<Byte> bytes = new ArrayList<>();

        for (long l : longs) {
            convertLongToVariableLength(l, bytes);
        }

        byte[] bytes2 = new byte[bytes.size()];
        for (int i = 0; i < bytes.size(); i++) {
            bytes2[i] = bytes.get(i);
        }
        return bytes2;
    }
}
