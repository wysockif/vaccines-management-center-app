package pl.wysockif.optimizer.io;

import pl.wysockif.optimizer.algorithms.Deal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;

import static java.lang.Long.MAX_VALUE;
import static pl.wysockif.optimizer.io.ErrorsHandler.OUTPUT_FILE_NOT_FOUND;
import static pl.wysockif.optimizer.io.ErrorsHandler.TOTAL_COST_OUT_OF_LIMIT;

public class OutputFileWriter {
    public static String fileName;
    private final PrintWriter printWriter;

    public OutputFileWriter(String path) {
        File file = new File(path);

        fileName = file.getName();
        printWriter = createPrintWriterIfFileExists(file);
    }

    private void saveLineToFile(String line) {
        printWriter.println(line);
    }

    private String concatenateLine(int length, String producerName, String pharmacyName, int amount, double price) {
        producerName = rightPad(producerName, length);
        double totalCost = (double) amount * price / 100;

        return producerName + " -> " + pharmacyName +
                " [Koszt = " + amount + " * " + price / 100 + " = " + totalCost + " zł]";
    }

    private String rightPad(String text, int length) {
        return String.format("%-" + length + "." + length + "s", text);
    }

    private PrintWriter createPrintWriterIfFileExists(File outputFile) {
        PrintWriter printWriter = null;

        try {
            printWriter = new PrintWriter(outputFile);
        } catch (FileNotFoundException e) {
            String message = "[Plik wyjściowy: " + fileName + "]. Błąd tworzenia pliku.";
            ErrorsHandler.handleError(OUTPUT_FILE_NOT_FOUND, message);
        }
        return printWriter;
    }

    public double saveDeals(List<Deal> deals) {
        int longestNameLength = 0;
        long totalCost = 0;

        if (!deals.isEmpty()) {
            longestNameLength = findLongestNameLength(deals);
        }
        while (!deals.isEmpty()) {
            totalCost = saveSingleDeal(deals, longestNameLength, totalCost);
        }

        double price = saveTheSummary(totalCost);
        printWriter.close();
        return price;
    }

    private long saveSingleDeal(List<Deal> deals, int longestNameLength, long totalCost) {
        Deal deal = deals.remove(0);
        int price = deal.getPrice();
        int amount = deal.getAmount();

        checkTotalCostLimit(totalCost, price, amount + 1);
        totalCost += price * amount;
        String line = concatenateLine(longestNameLength, deal.getProducerName(), deal.getPharmacyName(), deal.getAmount(), deal.getPrice());
        saveLineToFile(line);

        return totalCost;
    }

    private void checkTotalCostLimit(long totalCost, int price, int amount) {
        if (totalCost >= MAX_VALUE - price * amount) {
            ErrorsHandler.handleError(TOTAL_COST_OUT_OF_LIMIT, "");
        }
    }


    private double saveTheSummary(long totalCost) {
        double convertedTotalCost =  (double)totalCost / 100.0;
        BigDecimal totalCostDecimal = BigDecimal.valueOf(convertedTotalCost);
        String line = "Opłaty całkowite: " + totalCostDecimal.toPlainString() + " zł";

        saveLineToFile(line);
        return convertedTotalCost;
    }

    private int findLongestNameLength(List<Deal> deals) {
        int longestName = deals.get(0).getProducerName().length();

        for (Deal deal : deals) {
            if (deal.getProducerName().length() > longestName) {
                longestName = deal.getProducerName().length();
            }
        }
        return longestName;
    }
}
