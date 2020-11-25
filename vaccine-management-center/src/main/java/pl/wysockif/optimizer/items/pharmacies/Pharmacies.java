package pl.wysockif.optimizer.items.pharmacies;

import org.jetbrains.annotations.NotNull;
import pl.wysockif.optimizer.Optimizer;
import pl.wysockif.optimizer.io.ErrorsHandler;
import pl.wysockif.optimizer.items.Items;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.DataFormatException;

import static pl.wysockif.optimizer.io.ErrorsHandler.OUTPUT_FILE_EXCEED_LIMIT;

public class Pharmacies implements Items, Iterable<Pharmacy> {
    private final List<Pharmacy> pharmacies;

    public Pharmacies() {
        pharmacies = new ArrayList<>();
    }

    public int getNumberOfPharmacies() {
        return pharmacies.size();
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
        pharmacies.add(pharmacy);
        if (Optimizer.isLimit) {
            checkUpperLimit();
        }
    }

    private void checkUpperLimit() {
        int upperLimit = 300;
        if (pharmacies.size() >= upperLimit) {
            String message = "Przekroczono dozwolony limit ilości aptek wynoszący: " + upperLimit;
            String tip = "Aby znieść limit użyj polecenia \"-upper_limit=false\" " +
                    "na końcu komendy uruchamiającej program. \n           " +
                    "Czas działania włówczas może zostać włówczas znacząco wydłużony.";
            ErrorsHandler.handleTheError(OUTPUT_FILE_EXCEED_LIMIT, message, tip);
        }
    }

    @NotNull
    @Override
    public Iterator iterator() {
        return pharmacies.iterator();
    }

    public Pharmacy getPharmacyByIndex(int index) {
        return pharmacies.get(index);
    }

    public boolean alreadyContains(int pharmacyId) {
        return pharmacies.contains(new Pharmacy(pharmacyId, "", 0));
    }

    public int getPharmacyIndexById(int pharmacyId) {
        return pharmacies.indexOf(new Pharmacy(pharmacyId, "", 0));
    }

    public Pharmacy getPharmacyById(int pharmacyId) {
        return pharmacies.get(getPharmacyIndexById(pharmacyId));
    }
}
