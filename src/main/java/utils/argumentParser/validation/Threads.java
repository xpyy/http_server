package utils.argumentParser.validation;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

public class Threads implements IParameterValidator {
    public void validate(String s, String s1) throws ParameterException {
        Integer threadNum;
        try {
            threadNum = Integer.parseInt(s1);
        } catch (Exception e) {
            throw new ParameterException(s + " should be integer.");
        }
        if (threadNum < 1) {
            throw new ParameterException(s + " should be over 0.");
        }
    }
}
