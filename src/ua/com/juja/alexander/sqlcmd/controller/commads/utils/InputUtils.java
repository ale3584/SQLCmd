package ua.com.juja.alexander.sqlcmd.controller.commads.utils;

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
            throw new IllegalArgumentException("Не верный ввод, должно быть введено четное " +
                    "количество параметров следующего формата:" + commandFormat);
        }
    }

    public void parametersValidation(String format) {
        int formatLength = format.split("\\|").length;
        if (formatLength != getLength()) {
            throw new IllegalArgumentException(String.format("Не верное количество параметров " +
                    "введенных через '|', ожидаеться %s было введено: %s", formatLength, getLength()));
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
