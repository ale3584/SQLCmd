package ua.com.juja.alexander.sqlcmd.view;

import java.util.Scanner;

/**
 * Created by ALEXANDER on 09.06.2016.
 */
public class Console implements View {

    @Override
    public void write(String message) {
        System.out.println(message);
    }

    @Override
    public String read() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
