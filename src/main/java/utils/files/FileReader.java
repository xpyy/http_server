package utils.files;

import utils.exceptions.BadRequest;
import utils.exceptions.Forbidden;
import utils.exceptions.NotFound;
import utils.exceptions.UnsupportedMediaType;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileReader {
    private static final String DEFAULT_FILE = "index.html";
    private static Path path;
    private static String filesDir;

    public static File readFile(String url) throws BadRequest, Forbidden, NotFound, UnsupportedMediaType {

        if (url.charAt(url.length() - 1) == '/') {
            url += DEFAULT_FILE;
        }
        try {
            path = Paths.get(new URL(url).toURI());
        } catch (MalformedURLException e) {
            throw new BadRequest("Malformed URL");
        } catch (URISyntaxException e) {
            throw new BadRequest("Something wrong in URL");
        }
        if (!path.normalize().startsWith(filesDir)) {
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
        try {
            return Type.httpType.get(Enum.valueOf(Type.Types.class, path.substring(path.lastIndexOf('.') + 1, path.length()).toLowerCase()));
        } catch (IllegalArgumentException e) {
            throw new UnsupportedMediaType("Bad file type");
        }
    }
}
