
package test.before;

import domain.UserService;
import domain.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BadTest3 {

    @Test
    void testEverything() {
        UserService service = new UserService();
        User user = service.register("john", "pass123");
        assertNotNull(user);
        assertEquals("john", user.getUsername());
        assertTrue(service.login("john", "pass123"));
    }
}
