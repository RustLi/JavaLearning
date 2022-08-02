package java_common.guava;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TableTest {
    public static void main(String[] args) {
        test();
    }

    private static void test(){
        //常规方法
        Map<String, Map<String,Integer>> map=new HashMap<>();
        Map<String,Integer> workMap=new HashMap<>();
        workMap.put("Jan",20);
        workMap.put("Feb",21);
        map.put("A",workMap);
        Integer dayCount = map.get("A").get("Jan");
        System.out.println("dayCount = " + dayCount);

        //HashBasedTable
        Table<String,String,Integer> table= HashBasedTable.create();
        table.put("A", "Jan", 20);
        table.put("A", "Feb", 22);
        table.put("B", "Jan", 22);
        table.put("B", "Feb", 15);
        Integer dayCount2 = table.get("A", "Jan");
        System.out.println("dayCount2 = " + dayCount2);

        Map<String,Integer> nameMap = table.row("A");
        System.out.println("nameMap = " + nameMap);
        Map<String,Integer> monthMap = table.column("Feb");
        System.out.println("monthMap = " + monthMap);


        //rowKey或columnKey的集合
        Set<String> rowKeys = table.rowKeySet();
        Set<String> columnKeys = table.columnKeySet();
        //value集合
        Collection<Integer> values = table.values();
        System.out.println("rowKeys = " + rowKeys + ", columnKeys = " + columnKeys + ", values = " + values);


        //统计每个人工作天数之和
        for (String key : table.rowKeySet()) {
            Set<Map.Entry<String, Integer>> rows = table.row(key).entrySet();
            int total = 0;
            for (Map.Entry<String, Integer> row : rows) {
                total += row.getValue();
            }
            System.out.println(key + ": " + total);
        }

        //统计每月工作天数之和
        for (String key : table.columnKeySet()) {
            Set<Map.Entry<String, Integer>> rows = table.column(key).entrySet();
            int total = 0;
            for (Map.Entry<String, Integer> row : rows) {
                total += row.getValue();
            }
            System.out.println(key + ": " + total);
        }

        //遍历和转换遍历
        Set<Table.Cell<String, String, Integer>> cells = table.cellSet();
        cells.forEach(cell->
                System.out.println(cell.getRowKey()+","+cell.getColumnKey()+":"+cell.getValue())
        );

        Table<String, String, Integer> table2 = Tables.transpose(table);
        Set<Table.Cell<String, String, Integer>> cells2 = table2.cellSet();
        cells2.forEach(cell->
                System.out.println(cell.getRowKey()+","+cell.getColumnKey()+":"+cell.getValue())
        );

        //转map
        Map<String, Map<String, Integer>> rowMap = table.rowMap();
        Map<String, Map<String, Integer>> columnMap = table.columnMap();
        System.out.println("rowMap = " + rowMap + ", columnMap = " + columnMap);

    }
}
