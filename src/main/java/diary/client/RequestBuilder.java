package diary.client;

import org.json.JSONObject;

public class RequestBuilder {
    private static final String TYPE = "type";
    private static final String SIGN_IN = "signIn";
    private static final String SIGN_UP = "signUp";
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";

    public static String signIn(String login, String password) {
        JSONObject request = new JSONObject();
        request.put(TYPE, SIGN_IN);
        request.put(LOGIN, login);
        request.put(PASSWORD, password);
        return request.toString();
    }

    public static String signUp(String login, String password) {
        JSONObject request = new JSONObject();
        request.put(TYPE, SIGN_UP);
        request.put(LOGIN, login);
        request.put(PASSWORD, password);
        return request.toString();
    }
}
