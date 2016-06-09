package ua.com.juja.sqlcmd.model;

/**
 * Created by ALEXANDER on 09.06.2016.
 */
public class DatabaseManagerException extends RuntimeException{
    public DatabaseManagerException(String message, Throwable cause) {
        super(message, cause);
    }
}
