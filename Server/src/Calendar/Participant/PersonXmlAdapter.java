package Calendar.Participant;

import javax.xml.bind.annotation.XmlType;

/**
 * Created by ivann on 13.05.15.
 */
@XmlType(name = "person")
public class PersonXmlAdapter extends ParticipantXmlAdapter {

    private String firstName;
    private String secondName;
    private String email;
    private String phone;

    public PersonXmlAdapter(){}

    public PersonXmlAdapter(Person person){

        firstName = person.getFirstName();
        secondName = person.getSecondName();
        email = person.getEmail();
        phone = person.getPhone();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonXmlAdapter)) return false;

        PersonXmlAdapter that = (PersonXmlAdapter) o;

        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        if (secondName != null ? !secondName.equals(that.secondName) : that.secondName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (secondName != null ? secondName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        return result;
    }
}
