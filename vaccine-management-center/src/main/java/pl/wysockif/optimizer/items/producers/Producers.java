package pl.wysockif.optimizer.items.producers;

import pl.wysockif.optimizer.Optimizer;
import pl.wysockif.optimizer.io.ErrorsHandler;
import pl.wysockif.optimizer.items.Items;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.DataFormatException;

import static pl.wysockif.optimizer.io.ErrorsHandler.INPUT_FILE_EXCEED_LIMIT;

public class Producers implements Items {
    private final Map<Integer, Producer> producersByIndex;
    private final Map<Integer, Integer> indexById;

    private int counter;

    public Producers() {
        producersByIndex = new HashMap<>();
        indexById = new HashMap<>();
    }

    @Override
    public Object[] convertAttributes(String[] attributes) throws DataFormatException {
        checkIfArgumentIsNotNull(attributes);
        if (attributes.length != 3) {
            throw new DataFormatException("Niepoprawny format danych");
        }
        return parseAttributes(attributes);
    }

    @Override
    public void validateAttributes(Object[] attributes) throws DataFormatException {
        checkIfArgumentIsNotNull(attributes);
        int dailyProduction = (int) attributes[2];
        int id = (int) attributes[0];
        if (dailyProduction < 0) {
            String message = "Niepoprawny format danych. Ujemna wartość reprezentująca dzienną produkcję";
            throw new DataFormatException(message);
        }
        if (id < 0) {
            String message = "Niepoprawny format danych. Ujemna wartość reprezentująca id producenta";
            throw new DataFormatException(message);
        }
        if (contain(id)) {
            String message = "Nie można dodawać producentów o tym samym id";
            throw new DataFormatException(message);
        }
    }

    @Override
    public void addNewElement(Object[] attributes) {
        checkIfArgumentIsNotNull(attributes);
        int id = (int) attributes[0];
        String name = (String) attributes[1];
        int dailyProduction = (int) attributes[2];
        Producer producer = new Producer(id, name, dailyProduction);

        producersByIndex.put(counter, producer);
        indexById.put(id, counter);
        counter++;
        checkUpperLimit();
    }

    private void checkUpperLimit() {
        int upperLimit = 1000;
        if (Optimizer.isLimit() && producersByIndex.size() > upperLimit) {
            String message = "Przekroczono dozwolony limit ilości producentów wynoszący: " + upperLimit;
            String tip = "Aby znieść limit użyj polecenia \"-upper_limit=false\" " +
                    "na końcu komendy uruchamiającej program. \n           " +
                    "Czas działania włówczas może zostać włówczas znacząco wydłużony.";
            ErrorsHandler.handleError(INPUT_FILE_EXCEED_LIMIT, message, tip);
        }
    }

    private void checkIfArgumentIsNotNull(Object argument) {
        if(argument == null){
            throw new IllegalArgumentException("Niezainicjowany argument");
        }
    }

    public int getNumberOfProducers() {
        return producersByIndex.size();
    }

    public Producer getProducerByIndex(int index) {
        return producersByIndex.get(index);
    }

    public int getProducerIndexById(int producerId) {
        return indexById.get(producerId);
    }

    public Producer getProducerById(int producerId) {
        int index = indexById.get(producerId);
        return producersByIndex.get(index);
    }

    private Object[] parseAttributes(String[] attributes) throws DataFormatException {
        Object[] convertedAttributes = new Object[attributes.length];
        try {
            convertedAttributes[2] = Integer.parseInt(attributes[2]);
            convertedAttributes[1] = attributes[1];
            convertedAttributes[0] = Integer.parseInt(attributes[0]);
        } catch (NumberFormatException e) {
            String info = e.getMessage().replace("For input string: ", "");
            String message = "Nieudana konwersja danej: " + info;
            throw new DataFormatException(message);
        }
        return convertedAttributes;
    }

    public Collection<Producer> getProducersCollection() {
        return producersByIndex.values();
    }

    public boolean contain(int producerId) {
        return indexById.containsKey(producerId);
    }

}
