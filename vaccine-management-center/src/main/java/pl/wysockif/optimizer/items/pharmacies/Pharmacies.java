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

    private final Map<Integer, Pharmacy> pharmacyByIndex;
    private final Map<Integer, Integer> indexById;
    private int counter;

    public Pharmacies() {
        pharmacyByIndex = new HashMap<>();
        indexById = new HashMap<>();
    }

    public int getNumberOfPharmacies() {
        return pharmacyByIndex.size();
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
        if (alreadyContains(id)) {
            throw new DataFormatException("Nie można dodawać aptek o tym samym id");
        }
    }

    @Override
    public void addNewElement(Object[] attributes) {
        int id = (int) attributes[0];
        String name = (String) attributes[1];
        int dailyRequirement = (int) attributes[2];
        Pharmacy pharmacy = new Pharmacy(id, name, dailyRequirement);
        pharmacyByIndex.put(counter, pharmacy);
        indexById.put(id, counter);
        counter++;
        if (Optimizer.isLimit) {
            checkUpperLimit();
        }
    }

    private void checkUpperLimit() {
        int upperLimit = 300;
        if (pharmacyByIndex.size() >= upperLimit) {
            String message = "Przekroczono dozwolony limit ilości aptek wynoszący: " + upperLimit;
            String tip = "Aby znieść limit użyj polecenia \"-upper_limit=false\" " +
                    "na końcu komendy uruchamiającej program. \n           " +
                    "Czas działania włówczas może zostać włówczas znacząco wydłużony.";
            ErrorsHandler.handleError(INPUT_FILE_EXCEED_LIMIT, message, tip);
        }
    }

    public Pharmacy getPharmacyByIndex(int index) {
        return pharmacyByIndex.get(index);
    }

    public boolean alreadyContains(int pharmacyId) {
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
