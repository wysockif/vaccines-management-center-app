package pl.wysockif.optimizer.items.pharmacies;

import org.junit.Before;
import org.junit.Test;

import java.util.zip.DataFormatException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

public class PharmaciesTest {
    private Pharmacies pharmacies;

    @Before
    public void setUp() {
        pharmacies = new Pharmacies();
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throwIllegalArgumentException_when_validatedAttributesAreNull() throws DataFormatException {
        // given
        Object[] attributes = null;

        // when
        pharmacies.validateAttributes(attributes);

        // then
        assert false;
    }

    @Test(expected = DataFormatException.class)
    public void should_throwDataFormatException_when_atLeastOneAttributeIsIncorrect() throws DataFormatException {
        // given
        Object[] attributes = {0, "Nazwa apteki", -44};

        // when
        pharmacies.validateAttributes(attributes);

        // then
        assert false;

    }

    @Test
    public void should_validateAttributes_when_theyAreCorrect() throws DataFormatException {
        // given
        Object[] attributes = {0, "Nazwa apteki", 600};

        // when
        pharmacies.validateAttributes(attributes);

        // then
        assert true;
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throwIllegalArgumentException_when_convertedAttributesAreNull() throws DataFormatException {
        // given
        String[] attributes = null;

        // when
        pharmacies.convertAttributes(attributes);

        // then
        assert false;
    }

    @Test(expected = DataFormatException.class)
    public void should_throwDataFormatException_when_numberOfAttributesIsIncorrect() throws DataFormatException {
        // given
        String[] attributes = {"tylko jeden argument"};

        // when
        pharmacies.convertAttributes(attributes);

        // then
        assert false;
    }

    @Test
    public void should_convertAttributes_when_theyAreCorrect() throws DataFormatException {
        // given
        String[] attributes = {"0", "Nazwa apteki", "600"};

        // when
        Object[] actualAttributes = pharmacies.convertAttributes(attributes);

        // then
        Object[] expectedAttributes = {0, "Nazwa apteki", 600};
        assertArrayEquals(expectedAttributes, actualAttributes);
    }

    @Test(expected = DataFormatException.class)
    public void should_throwDataFormatException_when_atLeastOneConvertingAttributeIsIncorrect() throws DataFormatException {
        // given
        String[] attributes = {"0", "Nazwa apteki", "sto"};

        // when
        pharmacies.convertAttributes(attributes);

        // then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throwIllegalArgumentException_when_addedAttributesAreNull() {
        // given
        Object[] attributes = null;

        // when
        pharmacies.addNewElement(attributes);

        // then
        assert false;
    }

    @Test
    public void should_addConnection_when_attributesAreCorrect() {
        // given
        int producerId = 0;
        Object[] attributes = {producerId, "Nazwa apteki", 90};

        // when
        pharmacies.addNewElement(attributes);
        boolean contain = pharmacies.contain(producerId);

        // then
        assertTrue(contain);
    }
}