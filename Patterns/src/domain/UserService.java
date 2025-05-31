
package domain;

public class UserService {
    public User register(String username, String password) {
        return new User(username);
    }

    public boolean login(String username, String password) {
        return "john".equals(username) && "pass123".equals(password);
    }
}
