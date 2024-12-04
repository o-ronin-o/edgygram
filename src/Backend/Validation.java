package Backend;

public class Validation {
    public boolean emailValid(String email) {
        if (email.contains("@")) {
            String[] splitter = email.split("@");
            if (splitter[1] != null && splitter[0]!=null) {
                System.out.println(splitter[1]);
                if (splitter[1].contains("\\.")) {
                    String[] splitter1 = splitter[1].split("\\.");
                    System.out.println(splitter1[1]);
                    if (splitter1[1] != null) {
                        return true;
                    }
                }
            }

        }
        return false;
    }

    public boolean usernameValid(String username) {
        return username.length()>=4 && username.length()<=10;
    }

    public boolean passwordValid(String pass) {
        return pass.length()>=6 && pass.length()<=15;
    }
}
