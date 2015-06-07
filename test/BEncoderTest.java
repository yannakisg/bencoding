import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import me.gaspar.bencoding.BEncoder;
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
public class BEncoderTest {
    private BEncoder bEncoder;
    
    public BEncoderTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        bEncoder = new BEncoder();
    }
    
    @After
    public void tearDown() {
        bEncoder = null;
    }
    
    public void testEmptyList() {
        bEncoder.clear();
        bEncoder.bEncode(new ArrayList<>());
        String res = bEncoder.toString();
        assertEquals("le should represent the empty list", "le", res);
    }
    
    public void testEmptyDictionary() {
        bEncoder.clear();
        bEncoder.bEncode(new HashMap<String, Object>());
        String res = bEncoder.toString();
        assertEquals("de should represent the empty dictionary", "de", res);
    }
    
    public void testEmptyString() {
        bEncoder.clear();
        bEncoder.bEncode("");
        String res = bEncoder.toString();
        assertEquals("0: should represent the empty string \"\"", "0:", res);
    }
    
    public void testZeroInteger() {
        bEncoder.clear();
        bEncoder.bEncode(0);
        String res = bEncoder.toString();
        assertEquals("i0e should represent the integer 0", "i0e", res);
    }

    @Test
    public void testList() {
        List<Object> l = new ArrayList<>();
        l.add("spam");
        l.add("eggs");
        
        bEncoder.clear();
        bEncoder.bEncode(l);
        String res = bEncoder.toString();
        assertEquals("l4:spam4:eggse should represent the list of two strings: [ \"spam\", \"eggs\" ]", "l4:spam4:eggse", res);  
    }
    
    @Test
    public void testDictionary() {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("cow", "moo");
        m.put("spam", "eggs");
        
        bEncoder.clear();
        bEncoder.bEncode(m);
        String res = bEncoder.toString();
        assertEquals("d3:cow3:moo4:spam4:eggse should represent the  dictionary: { \"cow\" => \"moo\", \"spam\" => \"eggs\" }", "d3:cow3:moo4:spam4:eggse", res);  
    }
        
    @Test
    public void testInteger() {
        bEncoder.clear();
        bEncoder.bEncode(21);
        String res = bEncoder.toString();
        assertEquals("i21e should represent the integer 21", "i21e", res);
    }
    
    @Test
    public void testString() {
        bEncoder.clear();
        bEncoder.bEncode("spam");
        String res = bEncoder.toString();
        assertEquals("4:spam should represent the string \"spam\"", "4:spam", res);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testListForNullException() {
       bEncoder.bEncode((List<Object>) null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testIntegerForNullException() {
        bEncoder.bEncode((Integer) null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testStringForNullException() {
        bEncoder.bEncode((String) null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testMapForNullException() {
        bEncoder.bEncode((Map<String, Object>) null);
    }
    
}
