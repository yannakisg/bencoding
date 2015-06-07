package me.gaspar.bencoding;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author Yannis Gasparis
 */
public class BEncoder {
    private final StringBuffer stringBuffer;
    
    public BEncoder() {
        stringBuffer = new StringBuffer();
    }
    
    public void bEncode(String s) {
        if (s == null) {
            throw new IllegalArgumentException("Null is not permitted");
        }
        stringBuffer.append(s.length()).append(":").append(s);
    }
    
    public void bEncode(Integer i) {
        if (i == null) {
            throw new IllegalArgumentException("Null is not permitted");
        }
        stringBuffer.append("i").append(i).append("e");
    }
    
    public void bEncode(List<Object> l) {
        if (l == null) {
            throw new IllegalArgumentException("Null is not permitted");
        }
        
        stringBuffer.append("l");
        
        for (Object o : l) {
            if (o instanceof List) {
                BEncoder.this.bEncode((List)o);
            } else if (o instanceof String) {
                BEncoder.this.bEncode((String)o);
            } else if (o instanceof Map) {
                bEncode((Map)o);
            } else if (o instanceof Integer) {
                BEncoder.this.bEncode((Integer)o);
            } else {
                throw new IllegalArgumentException(o + " is not accepted");
            }
        } 
        
        stringBuffer.append("e");
    }
    
    public void bEncode(Map<String, Object> m) {
        if (m == null) {
            throw new IllegalArgumentException("Null is not permitted");
        }
        
        stringBuffer.append("d");
        
        for (Entry<String, Object> e : m.entrySet()) {
            BEncoder.this.bEncode(e.getKey());
            Object  o = e.getValue();            
            if (o instanceof List) {
                BEncoder.this.bEncode((List)o);
            } else if (o instanceof String) {
                BEncoder.this.bEncode((String)o);
            } else if (o instanceof Map) {
                bEncode((Map)o);
            } else if (o instanceof Integer) {
                BEncoder.this.bEncode((Integer)o);
            } else {
                throw new IllegalArgumentException(o + " is not accepted");
            }
        }
        
        stringBuffer.append("e");
    }
    
    public void clear() {
        stringBuffer.setLength(0);
    }
    
    @Override
    public String toString() {
        return stringBuffer.toString();
    }
}
