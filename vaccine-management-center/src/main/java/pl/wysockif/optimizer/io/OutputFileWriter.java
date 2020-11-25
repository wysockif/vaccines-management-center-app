package pl.wysockif.optimizer.io;

import pl.wysockif.optimizer.algorithms.Deal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;

public class OutputFileWriter {
    public static String fileName;
    private PrintWriter printWriter;

    public OutputFileWriter(String path){
        File file = new File(path);
        fileName = file.getName();
        printWriter = createPrintWriterIfFileExists(file);
    }

    public void saveLineToFile( String line){
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
                    ErrorsHandler.handleTheError(ErrorsHandler.OUTPUT_FILE_NOT_FOUNT, message);
        }
        return printWriter;
    }


    public void saveDeals(List<Deal> deals) {
        int longestNameLength = 0;
        BigDecimal totalCost = new BigDecimal("0.0");
        if (!deals.isEmpty())
            longestNameLength = findLongestNameLength(deals);

        while(!deals.isEmpty()){
            Deal deal = deals.remove(0);
            BigDecimal price = BigDecimal.valueOf(deal.getPrice());
            BigDecimal amount = BigDecimal.valueOf(deal.getAmount());
            totalCost = totalCost.add(price.multiply(amount));
            String line = concatenateLine(longestNameLength, deal.getProducerName(), deal.getPharmacyName(), deal.getAmount(), deal.getPrice());
            saveLineToFile(line);
        }

        saveTheSummary(totalCost);
        printWriter.close();
    }

    private void saveTheSummary(BigDecimal totalCost) {
        String line = "Opłaty całkowite: " + totalCost;
        System.out.println(totalCost);
        saveLineToFile(line);
    }

    private int findLongestNameLength(List<Deal> deals) {
        int longestName = deals.get(0).getProducerName().length();

        for(Deal deal : deals){
            if(deal.getProducerName().length() > longestName){
                longestName = deal.getProducerName().length();
            }
        }
        return longestName;
    }
}
