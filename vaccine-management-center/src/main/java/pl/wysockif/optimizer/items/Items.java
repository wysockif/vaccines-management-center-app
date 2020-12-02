package pl.wysockif.optimizer.items;

import java.util.zip.DataFormatException;

public interface Items {
    Object[] convertAttributes(String[] attributes) throws DataFormatException;

    void validateAttributes(Object[] attributes) throws DataFormatException;

    void addNewElement(Object[] attributes);
}