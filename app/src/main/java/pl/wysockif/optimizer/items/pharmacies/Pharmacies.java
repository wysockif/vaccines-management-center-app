package pl.wysockif.optimizer.items.pharmacies;

import pl.wysockif.optimizer.Optimizer;
import pl.wysockif.optimizer.io.ErrorsHandler;
import pl.wysockif.optimizer.items.Items;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.DataFormatException;

import static pl.wysockif.optimizer.io.ErrorsHandler.INPUT_FILE_EXCEED_LIMIT;

public class Pharmacies implements Items {
    private int counter;
    private final Map<Integer, Pharmacy> pharmacyByIndex;
    private final Map<Integer, Integer> indexById;

    public Pharmacies() {
        pharmacyByIndex = new HashMap<>();
        indexById = new HashMap<>();
    }

    public int getNumberOfPharmacies() {
        return pharmacyByIndex.size();
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
    public void addNewElement(Object[] attributes) {
        checkIfArgumentIsNotNull(attributes);
        int id = (int) attributes[0];
        String name = (String) attributes[1];
        int dailyRequirement = (int) attributes[2];
        Pharmacy pharmacy = new Pharmacy(id, name, dailyRequirement);

        pharmacyByIndex.put(counter, pharmacy);
        indexById.put(id, counter);
        counter++;
        if (Optimizer.isLimit()) {
            checkUpperLimit();
        }
    }

    @Override
    public void validateAttributes(Object[] attributes) throws DataFormatException {
        checkIfArgumentIsNotNull(attributes);
        int dailyRequirement = (int) attributes[2];
        int id = (int) attributes[0];

        if (dailyRequirement < 0) {
            String message = "Niepoprawny format danych. Ujemna wartość reprezentująca dzienne zapotrzebowanie";
            throw new DataFormatException(message);
        }
        if (id < 0) {
            String message = "Niepoprawny format danych. Ujemna wartość reprezentująca id producenta";
            throw new DataFormatException(message);
        }
        if (contain(id)) {
            throw new DataFormatException("Nie można dodawać aptek o tym samym id");
        }
    }

    private void checkIfArgumentIsNotNull(Object argument) {
        if (argument == null) {
            throw new IllegalArgumentException("Niezainicjowany argument");
        }
    }

    private void checkUpperLimit() {
        int upperLimit = 1000;

        if (pharmacyByIndex.size() > upperLimit) {
            String message = "Przekroczono dozwolony limit ilości aptek wynoszący: " + upperLimit;
            String tip = "Aby znieść limit użyj polecenia \"-upper_limit=false\" " +
                    "na końcu komendy uruchamiającej program. \n           " +
                    "Czas działania włówczas może zostać włówczas znacząco wydłużony.";
            ErrorsHandler.handleError(INPUT_FILE_EXCEED_LIMIT, message, tip);
        }
    }

    private Object[] parseAttributes(String[] attributes) throws DataFormatException {
        Object[] convertedAttributes = new Object[attributes.length];
        try {
            convertedAttributes[0] = Integer.parseInt(attributes[0]);
            convertedAttributes[1] = attributes[1];
            convertedAttributes[2] = Integer.parseInt(attributes[2]);
        } catch (NumberFormatException e) {
            String info = e.getMessage().replace("For input string: ", "");
            String message = "Nieudana konwersja danej: " + info;
            throw new DataFormatException(message);
        }
        return convertedAttributes;
    }

    public Pharmacy getPharmacyByIndex(int index) {
        return pharmacyByIndex.get(index);
    }

    public boolean contain(int pharmacyId) {
        return indexById.containsKey(pharmacyId);
    }

    public int getPharmacyIndexById(int pharmacyId) {
        return indexById.get(pharmacyId);
    }

    public Pharmacy getPharmacyById(int pharmacyId) {
        int index = indexById.get(pharmacyId);
        return pharmacyByIndex.get(index);
    }

    public Collection<Pharmacy> getPharmaciesCollection() {
        return pharmacyByIndex.values();
    }
}
