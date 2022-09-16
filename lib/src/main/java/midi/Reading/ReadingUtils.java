package midi.Reading;

import java.io.IOException;
import java.io.InputStream;

public class ReadingUtils {
    public static final int MAX_READABLE_BYTES_FOR_VARIABLE_PRECISION = (Long.BYTES * 8 - 1) / 7;
    public static final int MAX_BITS_SUPPORTED_FOR_VARIABLE_PRECISION = MAX_READABLE_BYTES_FOR_VARIABLE_PRECISION * 8;

    public static long readVariableLength(InputStream stream) throws IOException {
        long output = 0;
        long currentByte;
        int bytesRead = 0;
        do {
            currentByte = byteToLong(readByte(stream));
            bytesRead++;

            if (bytesRead > MAX_READABLE_BYTES_FOR_VARIABLE_PRECISION) {
                throw new IOException();
            }

            output = (output << 7) | (currentByte & 0x7F);
        } while ((currentByte & 0x80) != 0);

        return output;
    }

    public static String readString(InputStream stream) throws IOException {
        long length = readUnsignedInt(stream);

        return readString(stream, length);
    }

    public static String readString(InputStream stream, long length) throws IOException {
        StringBuffer buffer = new StringBuffer();

        for (long i = 0; i < length; i++) {
            buffer.append((char) readByte(stream));
        }

        return buffer.toString();
    }

    public static int readUnsignedShort(InputStream stream) throws IOException {
        byte[] shortBytes = new byte[2];
        int bytesRead = stream.read(shortBytes);
        if (bytesRead != 2) {
            throw new IOException();
        }

        return ((int) (byteToInt(shortBytes[1]) |
                (byteToInt(shortBytes[0]) << 8))) & 0xFFFF;
    }

    public static byte readByte(InputStream stream) throws IOException {
        int b = stream.read();

        if (b == -1) {
            throw new IOException("Expected byte, got end of file");
        }

        return (byte) (b & 0xFF);
    }

    public static long readUnsignedInt(InputStream stream) throws IOException {
        byte[] intBytes = new byte[4];
        int bytesRead = stream.read(intBytes);

        if (bytesRead != 4) {
            throw new IOException();
        }

        return (byteToLong(intBytes[0]) << 24) |
                (byteToLong(intBytes[1]) << 16) |
                (byteToLong(intBytes[2]) << 8) |
                byteToLong(intBytes[3]);
    }

    public static long readUnsignedIntFrom3Bytes(InputStream stream) throws IOException {
        byte[] intBytes = new byte[3];
        int bytesRead = stream.read(intBytes);
        if (bytesRead != 3) {
            throw new IOException();
        }

        return (byteToLong(intBytes[0]) << 16) |
                (byteToLong(intBytes[1]) << 8) |
                byteToLong(intBytes[2]);
    }

    public static int byteToInt(byte b) {
        return ((int) b) & 0xFF;
    }

    public static long byteToLong(byte b) {
        return ((long) b) & 0xFF;
    }
}
