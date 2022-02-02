package models.users;


public class RegularUser extends BaseUserImpl {
    public RegularUser(String username, String password, String role) {
        super(username, password, role);
    }
}
