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

import static java.util.Collections.replaceAll;
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

        String text = "Привет юзер!\n" +
                "Введи, пожалуйста имя базы данных, имя пользователя и пароль в формате: connect|database|userName|password\n" +
                "Существующие команды:\n" +
                "\t\tconnect|database|userName|password -- подключение к базе данных\n" +
                "\t\thelp -- список существующих команд\n" +
                "\t\texit -- Завершает работу с программой.\n" +
                "\t\tfind|tableName -- поиск данных в таблице\n" +
                "\t\tclear|tableName -- очистка таблицы\n" +
                "\t\tdatabases -- Выводит список баз данных.\n" +
                "\t\tcreateDatabase|databaseName -- Создает новую базу данных.\n" +
                "\t\tdropdatabase|databasename -- Удаляет базу данных.\n" +
                "\t\ttables -- Выводит список таблиц базы данных.\n" +
                "\t\tcreateTable|tableName(column1,column2,...,columnN) -- Создает таблицу в базе данных, в скобках необходимо ввести описание полей в формате SQL\n" +
                "Пример: user(id SERIAL NOT NULL PRIMARY KEY,username varchar(225) NOT NULL UNIQUE,password varchar(225))\n" +
                "\t\tdroptable|tablename -- Удаляет таблицу базы данных.\n" +
                "\t\tcreateEntry|tableName|column1|value1|column2|value2|...|columnN|valueN -- Создает запись в таблице.\n" +
                "\t\tupdate|tableName|ID -- Обновляет запись в таблице, используя ID\n" +
                "\t\tdisconnect -- Отключение от текущей базы данных.\n" +
                "Введи команду (или help для помощи):\n" +
                "Всего доброго!\n";
        assertEquals(text, getData());
    }

    public String getData() {
        try {
            String result = new String(out.toByteArray(), "UTF-8");
            out.reset();
            return result.replaceAll("\r\n","\n");
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
        assertEquals("Привет юзер!\n" +
                "Введи, пожалуйста имя базы данных, имя пользователя и пароль в формате: connect|database|userName|password\n" +
                "Всего доброго!\n", getData());
    }

    @Test
    public void testUnsupported() throws SQLException, ClassNotFoundException {
        // given
        in.add("unsupported");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        String text = "Привет юзер!\n" +
                "Введи, пожалуйста имя базы данных, имя пользователя и пароль в формате: connect|database|userName|password\n" +
                "Вы не можете использовать команду unsupported. Введи, пожалуйста имя базы данных, имя пользователя и пароль в формате: connect|database|userName|password\n" +
                "Введи команду (или help для помощи):\n" +
                "Всего доброго!\n";
        assertEquals(text, getData());
    }

    @Test
    public void testListWithOutConnect() throws SQLException, ClassNotFoundException {
        // given
        in.add("tables");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        String text = "Привет юзер!\n" +
                "Введи, пожалуйста имя базы данных, имя пользователя и пароль в формате: connect|database|userName|password\n" +
                "Вы не можете использовать команду tables. Введи, пожалуйста имя базы данных, имя пользователя и пароль в формате: connect|database|userName|password\n" +
                "Введи команду (или help для помощи):\n" +
                "Всего доброго!\n";
        assertEquals(text, getData());
    }

    @Test
    public void testListWithConnect() throws SQLException, ClassNotFoundException {
        // given
        in.add("connect|test|root|162399");
        in.add("tables");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        String text = "Привет юзер!\n" +
                "Введи, пожалуйста имя базы данных, имя пользователя и пароль в формате: connect|database|userName|password\n" +
                "Подключение к базе данных произошло успешно\n" +
                "Введи команду (или help для помощи):\n" +
                "+-----------+\n" +
                "|Имя таблицы|\n" +
                "+-----------+\n" +
                "|users      |\n" +
                "+-----------+\n" +
                "Введи команду (или help для помощи):\n" +
                "Всего доброго!\n";
        assertEquals(text, getData());
    }

    @Test
    public void testerror() throws SQLException, ClassNotFoundException {
        // given
        in.add("connect|test|root|162399");
        in.add("find|users");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        String text = "Привет юзер!\n" +
                "Введи, пожалуйста имя базы данных, имя пользователя и пароль в формате: connect|database|userName|password\n" +
                "Подключение к базе данных произошло успешно\n" +
                "Введи команду (или help для помощи):\n" +
                "+----+--+----+\n" +
                "|name|id|pass|\n" +
                "+----+--+----+\n" +
                "Введи команду (или help для помощи):\n" +
                "Всего доброго!\n";
        assertEquals(text, getData());
    }


    @Test
    public void testNewDB() throws SQLException, ClassNotFoundException {
        // given
        in.add("connect|test|root|162399");
        in.add("createdatabase|NewTest");
        in.add("dropdatabase|NewTest");
        in.add("exit");

        // when
        Main.main(new String[0]);

        // then
        String text = "Привет юзер!\n" +
                "Введи, пожалуйста имя базы данных, имя пользователя и пароль в формате: connect|database|userName|password\n" +
                "Подключение к базе данных произошло успешно\n" +
                "Введи команду (или help для помощи):\n" +
                "База данных 'NewTest' успешно  создана.\n" +
                "Введи команду (или help для помощи):\n" +
                "База данных 'NewTest' успешно  удалена.\n" +
                "Введи команду (или help для помощи):\n" +
                "Всего доброго!\n";
        assertEquals(text, getData());
    }




}
