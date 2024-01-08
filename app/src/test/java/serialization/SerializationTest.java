package serialization;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.*;
import java.util.Calendar;

public class SerializationTest {

    @Test
    public void writeAndReadFlattenTime_With_Serializable() {
        String file = "time.ser";

        // Writing to a file
        PersistantTime time = new PersistantTime();
        try {
            FileOutputStream fout = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fout);
            out.writeObject(time);
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        // Reading from a File
        try {
            FileInputStream fin = new FileInputStream(file);
            ObjectInputStream oin = new ObjectInputStream(fin);
            time = (PersistantTime) oin.readObject();
            oin.close();
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        System.out.printf("Flattened Time: " + time.getTime());
        System.out.printf("\nCurrent Time: " + Calendar.getInstance().getTime());

    }

    @Test
    public void whenSerializing_thenUseExternalizable()
            throws IOException, ClassNotFoundException {

        final String OUTPUT_FILE = "Country.txt";
        ExtnPersistantCountry c = new ExtnPersistantCountry();
        c.setCode(374);
        c.setName("Armenia");

        FileOutputStream fileOutputStream
                = new FileOutputStream(OUTPUT_FILE);
        ObjectOutputStream objectOutputStream
                = new ObjectOutputStream(fileOutputStream);
        c.writeExternal(objectOutputStream);

        objectOutputStream.flush();
        objectOutputStream.close();
        fileOutputStream.close();

        FileInputStream fileInputStream
                = new FileInputStream(OUTPUT_FILE);
        ObjectInputStream objectInputStream
                = new ObjectInputStream(fileInputStream);

        ExtnPersistantCountry c2 = new ExtnPersistantCountry();
        c2.readExternal(objectInputStream);

        objectInputStream.close();
        fileInputStream.close();

        assertEquals(c2.getCode(), c.getCode());
        assertEquals(c2.getName(), c.getName());
    }
}
