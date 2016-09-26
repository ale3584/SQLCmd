package ua.com.juja.alexander.sqlcmd.utils;

import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;
import ua.com.juja.alexander.sqlcmd.model.DataSet;

import java.util.Map;

import static org.nocrala.tools.texttablefmt.CellStyle.*;

/**
 * Created by ALEXANDER on 21.09.2016.
 */
public class TextTable {

    Table table;
    CellStyle cs;

    public TextTable(int Colums) {
       this.table = new Table(Colums, BorderStyle.CLASSIC, ShownBorders.ALL, false, "");
        setCellStyle();
    }


    private void setCellStyle() {
        this.cs = new CellStyle(HorizontalAlign.left, AbbreviationStyle.crop,
                 NullStyle.emptyString);
    }

    public void addCells(Map<String,Object> content){
            for (Object value : content.keySet()) {
                table.addCell(value.toString(), this.cs);
            }
    }

    public void addCells(String[] content){
        for (String contentElement:content) {
            table.addCell(contentElement,this.cs);
        }
    }

    public void addCell(String content){
            table.addCell(content,this.cs);
    }

    public String getTable() {
        return this.table.render();
    }
}
