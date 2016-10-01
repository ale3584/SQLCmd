package ua.com.juja.alexander.sqlcmd.Integration;

import org.junit.Before;
import org.junit.Test;
import ua.com.juja.alexander.sqlcmd.Main;
import ua.com.juja.alexander.sqlcmd.model.DatabaseManager;
import ua.com.juja.alexander.sqlcmd.model.MySQLDatabaseManager;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

/**
 * Created by ALEXANDER on 30.09.2016.
 */
public class IntegrationTest  {

    DatabaseManager databaseManager;
    private ConfigurableInputStream in;
    private ByteArrayOutputStream out;

    @Before
    public void setup() {
        databaseManager = new MySQLDatabaseManager();
        out = new ByteArrayOutputStream();
        in = new ConfigurableInputStream();

        System.setIn(in);
        System.setOut(new PrintStream(out));
    }

    @Test
    public void testHelp() throws SQLException, ClassNotFoundException {
        // given
        in.add("help");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then

        String text = "Привет юзер!\r\n" +
                "Введи, пожалуйста имя базы данных, имя пользователя и пароль в формате: connect|database|userName|password\r\n" +
                "Существующие команды:\r\n" +
                "\t\tconnect|database|userName|password -- подключение к базе данных\r\n" +
                "\t\thelp -- список существующих команд\r\n" +
                "\t\texit -- Завершает работу с программой.\r\n" +
                "\t\tfind|tableName -- поиск данных в таблице\r\n" +
                "\t\tclear|tableName -- очистка таблицы\r\n" +
                "\t\tdatabases -- Выводит список баз данных.\r\n" +
                "\t\tcreateDatabase|databaseName -- Создает новую базу данных.\r\n" +
                "\t\tdropdatabase|databasename -- Удаляет базу данных.\r\n" +
                "\t\ttables -- Выводит список таблиц базы данных.\r\n" +
                "\t\tcreateTable|tableName(column1,column2,...,columnN) -- Создает таблицу в базе данных, в скобках необходимо ввести описание полей в формате SQL\n" +
                "Пример: user(id SERIAL NOT NULL PRIMARY KEY,username varchar(225) NOT NULL UNIQUE,password varchar(225))\r\n" +
                "\t\tdroptable|tablename -- Удаляет таблицу базы данных.\r\n" +
                "\t\tcreateEntry|tableName|column1|value1|column2|value2|...|columnN|valueN -- Создает запись в таблице.\r\n" +
                "\t\tupdate|tableName|ID -- Обновляет запись в таблице, используя ID\r\n" +
                "\t\tdisconnect -- Отключение от текущей базы данных.\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                "Всего доброго!\r\n";
        assertEquals(text, getData());
    }

    public String getData() {
        try {
            String result = new String(out.toByteArray(), "UTF-8");
            out.reset();
            return result;
        } catch (UnsupportedEncodingException e) {
            return e.getMessage();
        }
    }

    @Test
    public void testExit() throws SQLException, ClassNotFoundException {
        // given
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Привет юзер!\r\n" +
                "Введи, пожалуйста имя базы данных, имя пользователя и пароль в формате: connect|database|userName|password\r\n" +
                "Всего доброго!\r\n", getData());
    }

    @Test
    public void testUnsupported() throws SQLException, ClassNotFoundException {
        // given
        in.add("unsupported");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        assertEquals("Привет юзер!\r\n" +
                "Введи, пожалуйста имя базы данных, имя пользователя и пароль в формате: connect|database|userName|password\r\n" +
                "Вы не подключены к базе данных, пожалуйста подключитесь.\r\n" +
                "Введи команду (или help для помощи):\r\n" +
                "Всего доброго!\r\n", getData());
    }

}
