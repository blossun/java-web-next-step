package util;


import model.User;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class SolarHttpRequestUtilsTest {
    @Test
    void parseQueryString() {
        String queryString = "userId=solar";
        Map<String, String> params = HttpRequestUtils.parseQueryString(queryString);
        assertEquals("solar", params.get("userId"));
        assertNull(params.get("password"));

        queryString = "userId=solar&password=password2";
        params = HttpRequestUtils.parseQueryString(queryString);
        assertEquals("solar", params.get("userId"));
        assertEquals("password2", params.get("password"));
    }

    @Test
    void parseQueryString_null() {
        Map<String, String> params = HttpRequestUtils.parseQueryString(null);
        assertTrue(params.isEmpty()); //바로 Map이 empty(not null)인지 확인하는 assert가 있나?

        params = HttpRequestUtils.parseQueryString("");
        assertTrue(params.isEmpty());

        params = HttpRequestUtils.parseQueryString("   ");
        assertTrue(params.isEmpty());
    }

    @Test
    void createUserWithParameters() {
        String queryString = "userId=solar&password=password2&name=솔라&email=solar@test.com";
        Map<String, String> params = HttpRequestUtils.parseQueryString(queryString);
        User newUser = new User(params.get("userId"), params.get("password"), params.get("name"), params.get("email"));
        System.out.println("newUser : " + newUser.toString());
    }

    @Test
    void map() {
        Map<String, String> maps = new HashMap<>();
        maps.put("Content-Length", null);
        assertNull(maps.get("Cookie"));

        maps.put("logined", "true");
        assertEquals("true", maps.get("logined"));
        maps.put("logined", "false");
        assertEquals("false", maps.get("logined"));

    }

}
