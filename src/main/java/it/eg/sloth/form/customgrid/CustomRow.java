package it.eg.sloth.form.customgrid;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
public class CustomRow {

    List<CustomCell> cellList;

    @Getter
    @Setter
    private boolean header;

    public CustomRow(boolean header) {
        this.header = header;
        this.cellList = new ArrayList<>();
    }

    public CustomCell addCell(String html, String css) {
        CustomCell customCell = new CustomCell(html, css);
        cellList.add(customCell);

        return customCell;
    }

}
