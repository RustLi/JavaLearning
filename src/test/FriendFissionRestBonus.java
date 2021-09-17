package test;

import java.util.HashMap;
import java.util.Map;

public enum FriendFissionRestBonus{

        //剩余奖品数量一阶梯
        REST_BONUS_NUM_ONE(1,"${remainReward-1}"),
        //剩余奖品数量二阶梯
        REST_BONUS_NUM_TWO(2,"${remainReward-2}"),
        //剩余奖品数量三阶梯
        REST_BONUS_NUM_THREE(3,"${remainReward-3}");

        private int value;
        private String desc;

        FriendFissionRestBonus(int value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        private static final Map<Integer, String> cache;

        static {
            cache = new HashMap<>(3);
            for (FriendFissionRestBonus item : FriendFissionRestBonus.values()) {
                cache.put(item.value, item.desc);
            }
        }

        public static Map<Integer, String> getCache(){
            return cache;
        }

    }