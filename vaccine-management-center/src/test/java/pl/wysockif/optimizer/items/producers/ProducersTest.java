package pl.wysockif.optimizer.items.producers;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.zip.DataFormatException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;


public class ProducersTest {

    private Producers producers;

    @Before
    public void setUp()  {
        producers = new Producers();
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throwIllegalArgumentException_when_validatedAttributesAreNull() throws DataFormatException {
        // given
        Object[] attributes = null;

        // when
        producers.validateAttributes(attributes);

        // then
        assert false;
    }

    @Test(expected = DataFormatException.class)
    public void should_throwDataFormatException_when_atLeastOneAttributeIsIncorrect() throws DataFormatException {
        // given
        Object[] attributes = {0, "Nazwa producenta", -44};

        // when
        producers.validateAttributes(attributes);

        // then
        assert false;

    }

    @Test
    public void should_validateAttributes_when_theyAreCorrect() throws DataFormatException {
        // given
        Object[] attributes = {0, "Nazwa producenta", 600};

        // when
        producers.validateAttributes(attributes);

        // then
        assert true;
    }


    @Test(expected = IllegalArgumentException.class)
    public void should_throwIllegalArgumentException_when_convertedAttributesAreNull() throws DataFormatException {
        // given
        String[] attributes = null;

        // when
        producers.convertAttributes(attributes);

        // then
        assert false;
    }

    @Test(expected = DataFormatException.class)
    public void should_throwDataFormatException_when_numberOfAttributesIsIncorrect() throws DataFormatException {
        // given
        String[] attributes = {"tylko jeden argument"};

        // when
        producers.convertAttributes(attributes);

        // then
        assert false;
    }

    @Test
    public void should_convertAttributes_when_theyAreCorrect() throws DataFormatException {
        // given
        String[] attributes = {"0", "Nazwa producenta", "600"};

        // when
        Object[] actualAttributes = producers.convertAttributes(attributes);

        // then
        Object[] expectedAttributes = {0, "Nazwa producenta", 600};
        assertArrayEquals(expectedAttributes,actualAttributes);
    }

    @Test(expected = DataFormatException.class)
    public void should_throwDataFormatException_when_atLeastOneConvertingAttributeIsIncorrect() throws DataFormatException {
        // given
        String[] attributes = {"0", "Nazwa producenta", "sto"};

        // when
        producers.convertAttributes(attributes);

        // then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throwIllegalArgumentException_when_addedAttributesAreNull() {
        // given
        Object[] attributes = null;

        // when
        producers.addNewElement(attributes);

        // then
        assert false;
    }

    @Test
    public void should_addConnection_when_attributesAreCorrect() {
        // given
        int producerId = 0;
        Object[] attributes = {producerId, "Nazwa producenta", 90};

        // when
        producers.addNewElement(attributes);
        boolean contain = producers.contain(producerId);

        // then
        assertTrue(contain);
    }
}