package utils.files;

public class File {
    public String type;
    public byte[] body;

    public File(String type, byte[] body) {
        this.type = type;
        this.body = body;
    }
}
