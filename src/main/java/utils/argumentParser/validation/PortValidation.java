package utils.argumentParser.validation;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

public class PortValidation implements IParameterValidator {
    public void validate(String s, String s1) throws ParameterException {
        Integer portNum = null;
        try {
            portNum = Integer.parseInt(s1);
        } catch (Exception e) {
            throw new ParameterException(s + " should be integer.");
        }
        if (portNum < 1) {
            throw new ParameterException(s + " should be over 0.");
        }
    }
}
