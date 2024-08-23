package it.eg.sloth.webdesktop.history;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HistoryEntry {

    private String code;
    private String description;
    private String detail;
    private String icon;
    private String href;

}
