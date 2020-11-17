package pl.wysockif.optimizer.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class OutputFileWriter {
    public static String fileName;
    public int longestLength;

    public OutputFileWriter(String path){
        File file = new File(path);
        fileName = file.getName();
        longestLength = findLongestLength();
        PrintWriter printWriter = createPrintWriterIfFileExists(file);
        String line = concatenateLine(longestLength, "Eko Polska 2020", "CentMedEko 24h", 100, 50.5 );
        saveLineToFile(printWriter, line);

        line = concatenateLine(longestLength, "Post-Covid Sp. z o.o.", "CentMedEko Nowogrodzka", 500, 10.5 );
        saveLineToFile(printWriter, line);


        printWriter.close();
    }

    private int findLongestLength() {
        return 25;
    }

    public void saveLineToFile(PrintWriter printWriter, String line){
        printWriter.println(line);
    }

    public String concatenateLine(int length, String producerName, String pharmacyName, int amount, double price){
        producerName = rightPad(producerName, length);
        String line = producerName + " -> " + pharmacyName +
                " [Koszt = " + amount + " * " + price + " = " + amount*price + " zł]";
        return line;
    }

    private String rightPad(String text, int length) {
        return String.format("%-" + length + "." + length + "s", text);
    }

    private PrintWriter createPrintWriterIfFileExists(File outputFile) {
        PrintWriter printWriter = null;
        try{
            printWriter = new PrintWriter(outputFile);
        }catch (FileNotFoundException e){
            String message = "[Plik wyjściowy: " + fileName + "]. Plik nie został znaleziony.";
                    Errors.handleTheError(Errors.OUTPUT_FILE_NOT_FOUNT, message);
        }
        return printWriter;
    }


}
