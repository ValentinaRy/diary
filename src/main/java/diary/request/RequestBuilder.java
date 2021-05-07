package diary.request;

import org.json.JSONObject;

public class RequestBuilder {
    private static final String TYPE = "type";
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";

    public static String signIn(String login, String password) {
        JSONObject request = new JSONObject();
        request.put(TYPE, RequestType.SIGN_IN.name());
        request.put(LOGIN, login);
        request.put(PASSWORD, password);
        return request.toString();
    }

    public static String signUp(String login, String password) {
        JSONObject request = new JSONObject();
        request.put(TYPE, RequestType.SIGN_UP.name());
        request.put(LOGIN, login);
        request.put(PASSWORD, password);
        return request.toString();
    }
}
