//package pl.wysockif.optimizer.io;
//
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//
//import static org.junit.Assert.assertEquals;
//
//
//@RunWith(MockitoJUnitRunner.class)
//public class OutputFileWriterTest {
//
//   private OutputFileWriter outputFileWriter = new OutputFileWriter("test.txt");
//
//
//    @Test
//    public void should_concatenateLineCorrectly_when_rightPaddingIsZero() {
////        public String concatenateLine(int length, String producerName, String pharmacyName, int amount, double price) {
//        // given
////        int padding = 1;
//        String producerName = "Producent";
//        String pharmacyName = "Apteka";
//        int amount = 10;
//        double price = 19.99;
//
//        // when
//        String actualLine = outputFileWriter.concatenateLine(0, producerName, pharmacyName, amount, price);
//
//        //then
//        String expectedLine = "Producent -> Apteka [Koszt = 10 * 19.99 = 199.9 zł]";
//        assertEquals(expectedLine, actualLine);
//        }
//}