package serialization;

import java.io.*;
import java.util.Calendar;

/**
 Basic Serialization Example. Please check SerializationTest.java for Serializable and Externalizable interface examples.
 */
public class Serialize {
    public static void main(String[] args) {
        //writeFlattenTime();
        readFlattenTime();
    }

    /**
     Writes serialized time object to a file.
     */
    private static void writeFlattenTime() {
        String file = "time.ser";

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
    }

    private static void readFlattenTime() {
        String file = "time.ser";
        PersistantTime time = null;
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
}
