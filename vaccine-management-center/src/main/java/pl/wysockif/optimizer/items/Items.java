package pl.wysockif.optimizer.items;

import java.util.zip.DataFormatException;

public interface Items {
    Object[] convertAttributes(String[] attributes) throws DataFormatException;

    void validateAttributes(Object[] attributes) throws DataFormatException;

    void addNewElement(Object[] attributes);

    default Object[] parseAttributes(String[] attributes) throws DataFormatException {
        Object[] convertedAttributes = new Object[attributes.length];
        try {
            convertedAttributes[0] = Integer.parseInt(attributes[0]);
            convertedAttributes[1] = attributes[1];
            convertedAttributes[2] = Integer.parseInt(attributes[2]);
        } catch (NumberFormatException e) {
            String info = e.getMessage().replaceAll("For input string: ", "");
            String message = "Nieudana konwersja danej: " + info;
            throw new DataFormatException(message);
        }
        return convertedAttributes;
    }
}