package utils.network;

import utils.enums.Methods;

import java.util.HashMap;
import java.util.Map;

public class Request {
    public Methods method;
    public String path;
    public String version = "HTTP/1.1";
    public Map<String, String> params = new HashMap<String, String>();

    public Request() {
    }

    @Override
    public String toString() {
        StringBuilder temp = new StringBuilder();
        temp.append(method).append(" ").append(path).append(' ').append(version).append('\n');
        for (String key : params.keySet()) {
            temp.append(key).append(": ").append(params.get(key)).append("\n");
        }
        return temp.toString();
    }
}
