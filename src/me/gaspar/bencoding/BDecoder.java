package me.gaspar.bencoding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Yannis Gasparis
 */
public class BDecoder {
    private List<String> decoded;
    
    public BDecoder() {
        decoded = null;
    }

    public void bDecode(String bEncStr) {
        if (bEncStr == null || bEncStr.isEmpty()) {
            throw new IllegalArgumentException("Null or Empty Strings are not permitted");
        }
        AtomicInteger index = new AtomicInteger(0);
        decoded = bDecode(bEncStr, index);
    }
    
    public List<String> getResult() {
        return decoded;
    }

    private List<String> bDecode(String bEncStr, AtomicInteger index) {
        char ch = bEncStr.charAt(index.get());
        switch (ch) {
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                return bDecodeString(bEncStr, index);
            case 'i':
                return bDecodeInteger(bEncStr, index);
            case 'd':
                return bDecodeDictionary(bEncStr, index);
            case 'l':
                return bDecodeList(bEncStr, index);
            default:
                throw new IllegalArgumentException("The input is not a Bencoded String");
        }
    }

    private List<String> bDecodeString(String bEncStr, AtomicInteger index) {
        int startIndex = index.get();
        int colonIndex = bEncStr.indexOf(":", startIndex);

        if (colonIndex == -1) {
            throw new IllegalArgumentException("The input is not a Bencoded String");
        }

        int length = Integer.parseInt(bEncStr.substring(startIndex, colonIndex));
        index.set(index.get() + colonIndex - startIndex + length + 1);

        List<String> l = new ArrayList<>();
        if (length > 0) {
            l.add(bEncStr.substring(colonIndex + 1, colonIndex + length + 1));
        }
        return l;
    }

    private List<String> bDecodeInteger(String bEncStr, AtomicInteger index) {
        int startIndex = index.get();
        int lastIndex = bEncStr.indexOf("e", startIndex);

        if (lastIndex == -1) {
            throw new IllegalArgumentException("The input is not a Bencoded Integer");
        }
        index.set(index.get() + lastIndex - startIndex + 1);

        List<String> l = new ArrayList<>();
        l.add(bEncStr.substring(startIndex + 1, lastIndex));
        return l;
    }

    private List<String> bDecodeList(String bEncStr, AtomicInteger index) {
        List<String> res = new ArrayList<>();
        int startIndex = index.get();
        
        int lastIndex;
        if (startIndex == 0) {
            lastIndex = bEncStr.lastIndexOf("e");
        } else {
            lastIndex = bEncStr.indexOf("e", startIndex);
        }
        if (lastIndex == -1) {
            throw new IllegalArgumentException("The input is not a Bencoded List");
        }

        index.set(startIndex + 1);
        res.add("[");
        while (index.get() < lastIndex) {
            res.addAll(bDecode(bEncStr, index));
        }
        res.add("]");
        index.set(index.get() + 1);
        return res;
    }

    private List<String> bDecodeDictionary(String bEncStr, AtomicInteger index) {
        List<String> res = new ArrayList<>();
        int startIndex = index.get();
        
        int lastIndex;
        if (startIndex == 0) {
            lastIndex = bEncStr.lastIndexOf("e");
        } else {
            lastIndex = bEncStr.indexOf("e", startIndex);
        }
        
        if (lastIndex == -1) {
            throw new IllegalArgumentException("The input is not a Bencoded Dictionary");
        }

        index.set(startIndex + 1);
        res.add("{");
        while (index.get() < lastIndex) {
            res.addAll(bDecode(bEncStr, index));
        }
        res.add("}");
        index.set(index.get() + 1);
        return res;
    }
}
