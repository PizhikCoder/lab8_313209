package fxproject.backend.tableHandlers;

import javafx.scene.control.TableColumn;

public class FixedNameTableColumn<S, T>  extends TableColumn {
    private ColumnNames fixedName;

    public FixedNameTableColumn(String columnName, ColumnNames columnFixedName){
        super(columnName);
        this.fixedName = columnFixedName;
    }

    public ColumnNames getFixedName(){
        return fixedName;
    }
}
