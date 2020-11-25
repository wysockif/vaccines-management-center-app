package pl.wysockif.optimizer.items.producers;

import pl.wysockif.optimizer.Optimizer;
import pl.wysockif.optimizer.io.ErrorsHandler;
import pl.wysockif.optimizer.items.Items;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.DataFormatException;

import static pl.wysockif.optimizer.io.ErrorsHandler.OUTPUT_FILE_EXCEED_LIMIT;

public class Producers implements Items, Iterable<Producer> {
    private final List<Producer> producers;

    public Producers() {
        producers = new ArrayList<>();
    }

    public boolean alreadyContains(int id) {
        Producer producer = new Producer(id, "", 0);
        return producers.contains(producer);
    }

    public int getNumberOfProducers() {
        return producers.size();
    }


    @Override
    public Object[] convertAttributes(String[] attributes) throws DataFormatException {
        if (attributes.length != 3) {
            throw new DataFormatException("Niepoprawny format danych");
        }
        return parseAttributes(attributes);
    }

    @Override
    public void validateAttributes(Object[] attributes) throws DataFormatException {
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
        if (alreadyContains(id)) {
            String message = "Nie można dodawać producentów o tym samym id";
            throw new DataFormatException(message);
        }
    }

    @Override
    public void addNewElement(Object[] attributes) {
        int id = (int) attributes[0];
        String name = (String) attributes[1];
        int dailyProduction = (int) attributes[2];
        Producer producer = new Producer(id, name, dailyProduction);
        producers.add(producer);
        checkUpperLimit();
    }

    private void checkUpperLimit() {
        int upperLimit = 300;
        if (Optimizer.isLimit && producers.size() >= upperLimit) {
            String message = "Przekroczono dozwolony limit ilości producentów wynoszący: " + upperLimit;
            String tip = "Aby znieść limit użyj polecenia \"-upper_limit=false\" " +
                    "na końcu komendy uruchamiającej program. \n           " +
                    "Czas działania włówczas może zostać włówczas znacząco wydłużony.";
            ErrorsHandler.handleTheError(OUTPUT_FILE_EXCEED_LIMIT, message, tip);
        }
    }

    @Override
    public Iterator<Producer> iterator() {
        return producers.iterator();
    }

    public Producer getProducerByIndex(int index) {
        return producers.get(index);
    }

    public int getProducerIndexById(int producerId) {
        return producers.indexOf(new Producer(producerId, "", 0));
    }

    public Producer getProducerById(int producerId) {
        return producers.get(getProducerIndexById(producerId));
    }
}
