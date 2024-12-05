package Backend;

public class Validation {
    public boolean emailValid(String email) {
        if (email == null || !email.contains("@")) {
            return false;
        }


        String[] splitter = email.split("@");
        if (splitter[0].isEmpty() || splitter.length != 2|| splitter[1].isEmpty()) {
            return false;
        }
        String domain= splitter[1];
        if (domain.contains(".")) {
            String[] splitter1 = domain.split("\\.");
            return domain.length() >= 2 && !splitter1[1].isEmpty() && !splitter1[0].isEmpty();

        }


        return false;
    }

    public boolean usernameValid(String username) {
        return username.length() >= 4 && username.length() <= 10;
    }

    public boolean passwordValid(String pass) {
        return pass.length() >= 6 && pass.length() <= 15;
    }
}
