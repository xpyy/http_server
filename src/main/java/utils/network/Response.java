package utils.network;

public class Response {
    public final String server = "http server by I.Linnik";
    public String date;
    public String type;
    public int contentLength = 0;
    public String connection = "closed";
    public byte[] body;

    public Response() {
    }
}
