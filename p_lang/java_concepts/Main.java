package p_lang.java_concepts;

import java.nio.ByteBuffer;

public class Main {
    
    public static void main(String[] args) {
        ByteBuffer buf = ByteBuffer.allocate(10);
        byte[] ba = new byte[0];
        buf.get(ba);
        System.out.println(buf.position());
    }
}
