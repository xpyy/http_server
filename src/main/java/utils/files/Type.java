package utils.files;

import java.util.HashMap;
import java.util.Map;

public class Type {
    public static Map<String, String> httpType = new HashMap<String, String>();

    public static void setType() {
        httpType.put(".html", "text/html");
        httpType.put(".css", "text/css");
        httpType.put(".js", "application/x-javascript");
        httpType.put(".jpeg", "image/jpeg");
        httpType.put(".jpg", "image/jpeg");
        httpType.put(".png", "image/png");
        httpType.put(".gif", "image/gif");
        httpType.put(".swf", "application/x-shockwave-flash");
    }
}
