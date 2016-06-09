package ua.com.juja.sqlcmd.utils;

/**
 * Created by ALEXANDER on 09.06.2016.
 */
public class InputUtils {
    private String command;

    public InputUtils(String command) {
        this.command = command;
    }

    public String[] getParameters() {
        return command.split("\\|");
    }

    public void pairValidation(String commandFormat) {
        if (command.split("\\|").length % 2 != 0) {
            throw new IllegalArgumentException("Invalid input, you must enter an even " +
                    "number of parameters in the following format:" + commandFormat);
        }
    }

    public void parametersValidation(String format) {
        int formatLength = format.split("\\|").length;
        if (formatLength != getLength()) {
            throw new IllegalArgumentException(String.format("Incorrect number of parameters " +
                    "separated by '|', expected %s but was: %s", formatLength, getLength()));
        }
    }

    @Override
    public String toString() {
        return command;
    }

    private int getLength() {
        return command.split("\\|").length;
    }
}
