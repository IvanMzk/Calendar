package Calendar.Participant;

/**
 * Created by ivann on 27.04.15.
 */
public class Person implements Participant {

    private final String firstName;
    private final String secondName;
    private final String email;
    private final String phone;

    public Person(PersonBuilder builder) {
        firstName = builder.firstName;
        secondName = builder.secondName;
        email = builder.email;
        phone = builder.phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public String getFullName(){
        return firstName + secondName;
    }

    public static class PersonBuilder{

        private final String firstName;
        private final String secondName;
        private String email;
        private String phone;

        public PersonBuilder(String firstName, String secondName){
            this.firstName = firstName;
            this.secondName = secondName;
        }

        public PersonBuilder email(String email){
            this.email = email;
            return this;
        }

        public PersonBuilder phone(String phone){
            this.phone = phone;
            return this;
        }

        public Person build(){
            return new Person(this);
        }
    }




}
