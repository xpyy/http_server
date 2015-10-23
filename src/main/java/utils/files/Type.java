package utils.files;

import java.util.HashMap;
import java.util.Map;

public class Type {
    public static Map<Types, String> httpType = new HashMap<Types, String>();

    {
        httpType.put(Types.html, "text/html");
        httpType.put(Types.css, "text/css");
        httpType.put(Types.js, "application/x-javascript");
        httpType.put(Types.jpeg, "image/jpeg");
        httpType.put(Types.jpg, "image/jpg");
        httpType.put(Types.png, "image/png");
        httpType.put(Types.gif, "image/gif");
        httpType.put(Types.swf, "application/x-shockwave-flash");
    }

    public enum Types {
        html, css, js, jpg, jpeg, png, gif, swf
    }

}
