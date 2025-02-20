package org.lwl.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DataProcessor {

    public static Map<Integer, Long> countIdWithCountGreaterThanZero(List<Data> dataList) {
        return dataList.stream()
                .filter(data -> data.getCount() > 0)
                .collect(Collectors.groupingBy(Data::getId, Collectors.counting()));
    }

    public static void main(String[] args) {
        List<Data> dataList = new ArrayList<>();

        dataList.add(new Data(1, 1));
        dataList.add(new Data(1, 2));
        dataList.add(new Data(2, 1));
        dataList.add(new Data(2, 0));

        Map<Integer, Long> result = countIdWithCountGreaterThanZero(dataList);
        result.forEach((id, count) -> 
            System.out.println("ID: " + id + ", Count of count>0: " + count)
        );
    }


}
