package Calendar.Participant;

import java.io.Serializable;

/**
 * Created by ivann on 27.04.15.
 */
public interface Participant extends Serializable {

    String getFullName();
    String getEmail();
    String getPhone();

}
