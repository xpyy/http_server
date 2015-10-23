package utils.argumentParser.validation;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

import java.io.File;

public class DirectoryValidation implements IParameterValidator {
    public void validate(String s, String directoryName) throws ParameterException {
        File dir = new File(directoryName);
        if (!dir.isDirectory()) {
            throw new ParameterException("'" + directoryName + "' is not a directory");
        }
    }
}
