package utils.files;

import utils.exceptions.BadRequest;
import utils.exceptions.Forbidden;
import utils.exceptions.NotFound;
import utils.exceptions.UnsupportedMediaType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileReader {
    private static final String DEFAULT_FILE = "index.html";
    private static Path path;
    private static String filesDir;

    public static File readFile(String url) throws BadRequest, Forbidden, NotFound, UnsupportedMediaType {
        url = filesDir + url;
        if (url.charAt(url.length() - 1) == '/') {
            url += DEFAULT_FILE;
        }
        url = url.split("#|\\?")[0];
        path = Paths.get(url);
        if (!path.normalize().startsWith(Paths.get(filesDir).normalize())) {
            throw new BadRequest("Wrong way");
        }
        byte[] body;
        try {
            body = Files.readAllBytes(path);
        } catch (IOException e) {
            if (path.endsWith(DEFAULT_FILE)) {
                throw new Forbidden("No index here");
            } else {
                throw new NotFound("No such file");
            }
        }

        return new File(getType(path.toString()), body);
    }

    public static void setFilesDir(String dir) {
        filesDir = dir;
    }

    private static String getType(String path) throws UnsupportedMediaType {
        return Type.httpType.get(path.substring(path.lastIndexOf('.'), path.length()).toLowerCase());
    }
}
