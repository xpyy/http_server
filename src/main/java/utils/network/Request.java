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
}
