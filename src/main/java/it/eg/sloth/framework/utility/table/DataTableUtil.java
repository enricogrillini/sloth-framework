package it.eg.sloth.framework.utility.table;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import it.eg.sloth.db.datasource.DataRow;
import it.eg.sloth.db.datasource.DataTable;
import it.eg.sloth.framework.common.base.BigDecimalUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataTableUtil {

    private DataTableUtil() {
        // NOP
    }

    public static <T extends DataRow> void saveDataToJsonFile(DataTable<T> dataTable, File file) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (DataRow dataRow : dataTable) {
            list.add(dataRow.entries());
        }

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
        writer.writeValue(file, list);
    }

    public static <T extends DataRow> void saveDataToJsonFile(DataTable<T> dataTable, String fileName) throws IOException {
        saveDataToJsonFile(dataTable, new File(fileName));
    }

    public static <T extends DataRow> void loadDataJsonFile(DataTable<T> dataTable, File file) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, Object>> list = mapper.readValue(file, List.class);
        for (Map<String, Object> map : list) {
            DataRow dataRow = dataTable.add();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if (entry.getValue() instanceof Number) {
                    dataRow.setBigDecimal(entry.getKey(), BigDecimalUtil.toBigDecimal((Number) entry.getValue()));
                } else {
                    dataRow.setObject(entry.getKey(), entry.getValue());
                }
            }
        }
    }

    public static <T extends DataRow> void loadDataJsonFile(DataTable<T> dataTable, String fileName) throws IOException {
        loadDataJsonFile(dataTable, new File(fileName));
    }

}
