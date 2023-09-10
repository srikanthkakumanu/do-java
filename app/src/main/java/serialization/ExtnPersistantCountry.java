package serialization;

import lombok.*;

import java.io.*;
import java.util.Calendar;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ExtnPersistantCountry implements Externalizable {

    private static final Long serialVersionUID = 1L;

    @Getter @Setter private String name;
    @Getter @Setter private int code;


    @Override
    public void writeExternal(ObjectOutput objectOutput) throws IOException {
        objectOutput.writeUTF(name);
        objectOutput.writeInt(code);
    }

    @Override
    public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
        this.name = objectInput.readUTF();
        this.code = objectInput.readInt();
    }
}
