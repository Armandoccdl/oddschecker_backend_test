package test;

import com.oddschecker.Utils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UtilsTest {

    @Test
    public void basicTest1() {
        String result = Utils.reassemble("O draconia;conian devil! Oh la;h lame sa;saint!");
        assertEquals("O draconian devil! Oh lame saint!", result);
    }

    @Test
    public void basicTest2() {
        String result = Utils.reassemble("m quaerat voluptatem.;pora incidunt ut labore et d;, consectetur, " +
                "adipisci velit;olore magnam aliqua;idunt ut labore et dolore magn;uptatem.;i dolorem ipsum qu;" +
                "iquam quaerat vol;psum quia dolor sit amet, consectetur, a;ia dolor sit amet, conse;squam est, " +
                "qui do;Neque porro quisquam est, qu;aerat voluptatem.;m eius modi tem;Neque porro qui;, " +
                "sed quia non numquam ei;lorem ipsum quia dolor sit amet;ctetur, adipisci velit, sed quia " +
                "non numq;unt ut labore et dolore magnam aliquam qu;dipisci velit, sed quia non numqua;us " +
                "modi tempora incid;Neque porro quisquam est, qui dolorem i;uam eius modi tem;pora inc;am al");
        assertEquals("Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet" +
                ", consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt " +
                "ut labore et dolore magnam aliquam quaerat voluptatem.", result);
    }

    @Test
    public void checkinCaseSensitive() {
        String result = Utils.reassemble("O draconia;conian devil! Oh la;h lame Sa;saint!");
        assertEquals("O draconian devil! Oh lame Sa", result);
    }

    @Test
    public void checkingEmptyValue() {
        String result = Utils.reassemble("");
        assertEquals("", result);
    }

    @Test
    public void checkingJustSemiColons() {
        String result = Utils.reassemble(";;;;");
        assertEquals("", result);
    }

    @Test
    public void checkingSentenceEndingInSemiColon() {
        String result = Utils.reassemble("End;ding with semicolon;");
        assertEquals("Ending with semicolon", result);
    }

    @Test
    public void checkingCompleteSentenceWithOverlapping() {
        String result = Utils.reassemble("Hello this is a complete sentence;plete sen;He;is a");
        assertEquals("Hello this is a complete sentence", result);
    }

    @Test
    public void checkingSentenceNoOverlapping() {
        String result = Utils.reassemble("No overlapping;I love ducks");
        assertEquals("No overlapping", result);
    }

}
