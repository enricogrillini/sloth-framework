package it.eg.sloth;

import it.eg.sloth.db.datasource.row.Row;
import it.eg.sloth.db.datasource.table.Table;
import it.eg.sloth.db.decodemap.map.StringDecodeMap;
import it.eg.sloth.form.fields.field.impl.ComboBox;
import it.eg.sloth.form.fields.field.impl.Text;
import it.eg.sloth.form.fields.field.impl.TextTotalizer;
import it.eg.sloth.form.grid.Grid;
import it.eg.sloth.framework.common.casting.DataTypes;
import it.eg.sloth.framework.security.Menu;
import it.eg.sloth.framework.security.VoiceType;
import it.eg.sloth.webdesktop.search.SearchManager;
import it.eg.sloth.webdesktop.search.SearchRelevance;
import it.eg.sloth.webdesktop.search.impl.DataTableSearcher;
import it.eg.sloth.webdesktop.search.impl.MenuSearcher;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestFactory {

    public static final String OUTPUT_DIR = "target/test-output";

    public static void createOutputDir() throws IOException {
        Files.createDirectories(Paths.get(OUTPUT_DIR));
    }

    public static Grid getGrid() {

        Table table = new Table();
        Row row = table.add();
        row.setString("campo1", "valore1");
        row.setString("campo2", "A");
        row.setBigDecimal("campo3", BigDecimal.valueOf(3.333));
        row.setString("campo4", "U");

        row = table.add();
        row.setString("campo1", "valore2");
        row.setString("campo2", "B");
        row.setBigDecimal("campo3", BigDecimal.valueOf(-3.444));
        row.setString("campo4", "B");

        row = table.add();
        row.setString("campo1", "valore2");
        row.setString("campo2", "B");
        row.setBigDecimal("campo3", BigDecimal.valueOf(-1));
        row.setString("campo4", "A");

        Grid grid = new Grid<>("provaGrid");
        grid.setTitle("Prova Grid");
        grid.setDescription("Prova sottotitolo");
        grid.addChild(new Text<String>("campo1", "campo 1", DataTypes.STRING));
        grid.addChild(new Text<String>("campo2", "campo 2", DataTypes.STRING));
        grid.addChild(new TextTotalizer("campo3", "campo 3", DataTypes.CURRENCY));
        grid.addChild(new ComboBox<String>("campo4", "campo 4", DataTypes.STRING));

        ComboBox<String> comboBox = (ComboBox<String>) grid.getElement("campo4");
        comboBox.setDecodeMap(new StringDecodeMap("A,Ancora;B,Basta;U,Un po'"));

        grid.setDataSource(table);

        return grid;
    }

    public static SearchManager getSearchManager() {
        SearchManager searchManager = new SearchManager();

        // MenuSearcher
        Menu menu = new Menu()
                .addChild("CLI", VoiceType.VOICE, "Clienti", "Gestione clienti", "immagine", "#")
                .addChild("ORD", VoiceType.VOICE, "Ordini", "Gestione ordini", "immagine", "#")
                .addChild("OFF", VoiceType.VOICE, "Offerte", "Gestione offerte", "immagine", "#")
                .addChild("DEC", VoiceType.VOICE, "Decodifiche", "Decodifiche", null, null);

        menu.getChilds().get("DEC").addChild("TIP", VoiceType.VOICE, "Tipo Clienti", "Tipo Clienti", null, "#");

        searchManager.addSearcher(new MenuSearcher(menu), SearchRelevance.VERY_HIGH);

        // DataTableSearcher 1
        Table table1 = new Table();
        Row row1 = table1.add();
        row1.setBigDecimal("Id", BigDecimal.valueOf(1));
        row1.setString("value", "Bob");
        row1.setString("subValue", "Bob the Builder - Tipo costrutture");

        row1 = table1.add();
        row1.setBigDecimal("Id", BigDecimal.valueOf(2));
        row1.setString("value", "Alice");
        row1.setString("subValue", "Alice in wonderland - Tipo manager");

        DataTableSearcher<Row> dataTableSearcher1 = new DataTableSearcher<>("Clienti", "id", "value", "subValue", "prova.html?id=");
        dataTableSearcher1.setKeyWords("Clienti", "Cliente", "Cli");
        dataTableSearcher1.addData(table1);

        searchManager.addSearcher(dataTableSearcher1, SearchRelevance.MEDIUM);

        // DataTableSearcher 2
        Table table2 = new Table();
        Row row2 = table2.add();
        row2.setBigDecimal("Id", BigDecimal.valueOf(1));
        row2.setString("value", "Ordine cliente Bob");
        row2.setString("subValue", "5 prodotti - 1.000€");

        row2 = table2.add();
        row2.setBigDecimal("Id", BigDecimal.valueOf(2));
        row2.setString("value", "Ordine cliente Alice");
        row2.setString("subValue", "4 prodotti - 500€");

        DataTableSearcher<Row> dataTableSearcher2 = new DataTableSearcher<>("Ordini", "id", "value", "subValue", "prova.html?id=");
        dataTableSearcher2.setKeyWords("Ordini", "Ordine", "Ord");
        dataTableSearcher2.addData(table2);

        searchManager.addSearcher(dataTableSearcher2, SearchRelevance.MEDIUM);

        return searchManager;
    }

}
