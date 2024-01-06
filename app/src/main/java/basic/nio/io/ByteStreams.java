package basic.nio.io;

import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.io.IOException; 
import java.io.DataInputStream;

public class ByteStreams {
    public static void main(String[] args) throws IOException {
        // byte[] b = {3, 6, 7, 8};
        InputStream bin = new ByteArrayInputStream(new byte[] {41, 42, 43, 44, 45});
        System.out.println("Available: " + bin.available());
        //byte[] target = bin.readAllBytes();
        ByteArrayOutputStream bout = new ByteArrayOutputStream(bin.available());
        bout.write(bin.readAllBytes());
        bout.writeTo(System.out);
        
        DataInputStream din = new DataInputStream(System.in);
        while(din.read() != -1) {
            System.out.println(din.read());
        }
        bin.close(); bout.close(); din.close();    
    }
}
