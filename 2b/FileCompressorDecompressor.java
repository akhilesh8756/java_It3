import java.io.*;

class InvalidCompressionFormatException extends Exception {
    public InvalidCompressionFormatException(String message) {
        super(message);
    }
}

public class FileCompressorDecompressor {

    public static void compressFile(File inputFile, File outputFile) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            int currentChar = reader.read();
            if (currentChar == -1) {
                return; // Empty file
            }

            int count = 1;
            int nextChar;
            while ((nextChar = reader.read()) != -1) {
                if (nextChar == currentChar) {
                    count++;
                } else {
                    writer.write((char) currentChar);
                    writer.write(String.valueOf(count));
                    currentChar = nextChar;
                    count = 1;
                }
            }

            // Write the last character and its count
            writer.write((char) currentChar);
            writer.write(String.valueOf(count));
        } catch (IOException e) {
            throw new IOException("Error compressing file: " + e.getMessage());
        }
    }

    public static void decompressFile(File inputFile, File outputFile) throws IOException, InvalidCompressionFormatException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            int currentChar;
            while ((currentChar = reader.read()) != -1) {
                StringBuilder countBuilder = new StringBuilder();
                int nextChar;

                while ((nextChar = reader.read()) != -1 && Character.isDigit((char) nextChar)) {
                    countBuilder.append((char) nextChar);
                }

                if (countBuilder.length() == 0) {
                    throw new InvalidCompressionFormatException("Missing count after character: " + (char) currentChar);
                }

                int count = Integer.parseInt(countBuilder.toString());
                for (int i = 0; i < count; i++) {
                    writer.write((char) currentChar);
                }

                if (nextChar == -1) {
                    break;
                }
                currentChar = nextChar;
            }
        } catch (IOException e) {
            throw new IOException("Error decompressing file: " + e.getMessage());
        } catch (NumberFormatException e) {
            throw new InvalidCompressionFormatException("Invalid compression format: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        File inputFile = new File("input.txt"); // Replace with your input file
        File compressedFile = new File("compressed.txt");
        File decompressedFile = new File("decompressed.txt");

        try {
            compressFile(inputFile, compressedFile);
            System.out.println("Compression successful.");
            
            decompressFile(compressedFile, decompressedFile);
            System.out.println("Decompression successful.");
        } catch (IOException | InvalidCompressionFormatException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
