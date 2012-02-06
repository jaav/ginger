import org.junit.Test;
import play.test.UnitTest;

/**
 * Created by IntelliJ IDEA.
 * User: jefw
 * Date: 1/13/12
 * Time: 9:02 AM
 * To change this template use File | Settings | File Templates.
 */
public class AsciiTest  extends org.junit.Assert {

    @Test
    public void aVeryImportantThingToTest() {
      System.out.println("kopi\u00EBren");
        assertEquals(2, 1 + 1);
    }

}