import java.util.List;
import me.gaspar.bencoding.BDecoder;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Yannis Gasparis
 */
public class BDecoderTest {
    
    private BDecoder bDecoder;
    
    public BDecoderTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        bDecoder = new BDecoder();
    }
    
    @After
    public void tearDown() {
        bDecoder = null;
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testNullInput() {
        bDecoder.bDecode(null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testEmptyInput() {
        bDecoder.bDecode("");
    }
    
    @Test
    public void testInteger() {
        bDecoder.bDecode("i21e");
        List<String> l = bDecoder.getResult();
        assertEquals("i21e is the integer 21", "21", l.get(0));
    }
    
    @Test
    public void testString() {
        bDecoder.bDecode("4:spam");
        List<String> l = bDecoder.getResult();
        assertEquals("4:spam is the string \"spam\"", "spam", l.get(0));
    }
    
    @Test
    public void testList() {
        bDecoder.bDecode("l4:spam4:eggse");
        List<String> l = bDecoder.getResult();
        assertEquals("l4:spam4:eggse first element is \"spam\"", "spam", l.get(1));
        assertEquals("l4:spam4:eggse second element is \"eggs\"", "eggs", l.get(2));
    }
    
    @Test
    public void testDictionary() {
        bDecoder.bDecode("d3:cow3:moo4:spam4:eggse");
        List<String> l = bDecoder.getResult();
        assertEquals("d3:cow3:moo4:spam4:eggse  first key is \"cow\"", "cow", l.get(1));
        assertEquals("d3:cow3:moo4:spam4:eggse first value is \"moo\"", "moo", l.get(2));
        assertEquals("d3:cow3:moo4:spam4:eggse  second key is \"spam\"", "spam", l.get(3));
        assertEquals("d3:cow3:moo4:spam4:eggse second value is \"eggs\"", "eggs", l.get(4));
    }
    
    @Test
    public void testDictionaryWithList() {
        bDecoder.bDecode("d4:spaml1:a1:bee");
        List<String> l = bDecoder.getResult();
        assertEquals("d4:spaml1:a1:bee  first key is \"spam\"", "spam", l.get(1));
        assertEquals("d4:spaml1:a1:bee first value is \"moo\"", "a", l.get(3));
        assertEquals("d4:spaml1:a1:bee  second value is \"spam\"", "b", l.get(4));
    }
    
    @Test
    public void testEmptyList() {
        bDecoder.bDecode("le");
        List<String> l = bDecoder.getResult();
        assertEquals("le is the empty list", 2, l.size());
    }
    
    @Test
    public void testEmptyDictionary() {
        bDecoder.bDecode("de");
        List<String> l = bDecoder.getResult();
        assertEquals("de is the empty dictionary", 2, l.size());
    }
    
    @Test
    public void testEmptyString() {
        bDecoder.bDecode("0:");
        List<String> l = bDecoder.getResult();
        assertEquals("0: is the empty string", 0, l.size());
    }
}
