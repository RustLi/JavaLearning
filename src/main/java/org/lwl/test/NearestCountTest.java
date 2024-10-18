package org.lwl.test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class NearestCountTest {
    public static void main(String[] args) {
        Map<Long, LiveTimeGapCountItem> map = new HashMap<>();
        LiveTimeGapCountItem item = new LiveTimeGapCountItem();
        item.setCount(0L);
        item.setDate(100L);
        map.put(100L, item);

        LiveTimeGapCountItem item1 = new LiveTimeGapCountItem();
        item1.setCount(0L);
        item1.setDate(105L);
        map.put(105L, item1);

        LiveTimeGapCountItem nearestItem = getNearestCountItem(102L, map);
        System.out.println("nearestItem = " + nearestItem);
    }

    public static LiveTimeGapCountItem getNearestCountItem(Long time, Map<Long, LiveTimeGapCountItem> map) {
        long nearestTime = 0;
        for (Map.Entry<Long, LiveTimeGapCountItem> entry : map.entrySet()) {
            Long curTime = entry.getKey();
            if (curTime <= time && curTime > nearestTime) {
                nearestTime = curTime;
            }
        }
        if (nearestTime != 0L) {
            return map.get(nearestTime);
        }
        LiveTimeGapCountItem item = new LiveTimeGapCountItem();
        item.setCount(0L);
        item.setDate(0L);
        return item;
    }

    public static class LiveTimeGapCountItem{
        private Long count;
        private Long date;

        public Long getCount() {
            return count;
        }
        public void setCount(Long count) {
            this.count = count;
        }
        public Long getDate() {
            return date;
        }
        public void setDate(Long date) {
            this.date = date;
        }

        @Override
        public String toString() {
            return "LiveTimeGapCountItem{" +
                    "count=" + count +
                    ", date=" + date +
                    '}';
        }
    }
}
