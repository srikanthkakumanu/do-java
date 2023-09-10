package serialization;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class PersistantTime implements Serializable {

    @Getter @Setter private Date time;
    public PersistantTime() { time = Calendar.getInstance().getTime(); }

}
