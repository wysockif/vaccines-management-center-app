package pl.wysockif.optimizer.items.connections;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.wysockif.optimizer.items.pharmacies.Pharmacies;
import pl.wysockif.optimizer.items.producers.Producers;

import java.util.zip.DataFormatException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class ConnectionsTest {
    private Connections connections;

    @Mock
    private Producers producers;

    @Mock
    private Pharmacies pharmacies;

    @Before
    public void setUp() {
        connections = new Connections(producers, pharmacies);
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throwIllegalArgumentException_when_validatedAttributesAreNull() throws DataFormatException {
        // given
        Object[] attributes = null;

        // when
        connections.validateAttributes(attributes);

        // then
        assert false;
    }

    @Test(expected = DataFormatException.class)
    public void should_throwDataFormatException_when_atLeastOneAttributeIsIncorrect() throws DataFormatException {
        // given
        Object[] attributes = {0, 0, -44, 90.0};
        given(producers.contain(0)).willReturn(true);
        given(pharmacies.contain(0)).willReturn(true);

        // when
        connections.validateAttributes(attributes);

        // then
        assert false;

    }

    @Test
    public void should_validateAttributes_when_theyAreCorrect() throws DataFormatException {
        // given
        Object[] attributes = {0, 0, 600, 90.0};
        given(producers.contain(0)).willReturn(true);
        given(pharmacies.contain(0)).willReturn(true);

        // when
        connections.validateAttributes(attributes);

        // then
        assert true;
    }


    @Test(expected = IllegalArgumentException.class)
    public void should_throwIllegalArgumentException_when_convertedAttributesAreNull() throws DataFormatException {
        // given
        String[] attributes = null;

        // when
        connections.convertAttributes(attributes);

        // then
        assert false;
    }

    @Test(expected = DataFormatException.class)
    public void should_throwDataFormatException_when_numberOfAttributesIsIncorrect() throws DataFormatException {
        // given
        String[] attributes = {"tylko jeden argument"};

        // when
        connections.convertAttributes(attributes);

        // then
        assert false;
    }

    @Test
    public void should_convertAttributes_when_theyAreCorrect() throws DataFormatException {
        // given
        String[] attributes = {"0", "0", "23", "23.2"};

        // when
        Object[] actualAttributes = connections.convertAttributes(attributes);

        // then
        Object[] expectedAttributes = {0, 0, 23, 23.2};
        assertArrayEquals(expectedAttributes, actualAttributes);
    }

    @Test(expected = DataFormatException.class)
    public void should_throwDataFormatException_when_atLeastOneConvertingAttributeIsIncorrect() throws DataFormatException {
        // given
        String[] attributes = {"0", "zero", "23", "23.2"};

        // when
        connections.convertAttributes(attributes);

        // then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throwIllegalArgumentException_when_addedAttributesAreNull() {
        // given
        Object[] attributes = null;

        // when
        connections.addNewElement(attributes);

        // then
        assert false;
    }

    @Test
    public void should_addConnection_when_attributesAreCorrect() {
        // given
        int producerId = 0;
        int pharmacyId = 0;
        Object[] attributes = {producerId, pharmacyId, 800, 90};

        // when
        connections.addNewElement(attributes);
        boolean contain = connections.contain(producerId, pharmacyId);

        // then
        assertTrue(contain);
    }
}