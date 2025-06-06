package org.lwl.test;

import com.google.common.net.InetAddresses;
import lombok.extern.slf4j.Slf4j;
import org.lwl.utils.MobileUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
public class CheckTest {
    public static void main(String[] args) {
//        String content = "192.188.99.00";
//        boolean isValidIp = InetAddresses.isInetAddress(content);
//        String mobile = "15677880099";
//        boolean isMobile = MobileUtils.isPhone(mobile);
//        System.out.println("isValidIp = " + isValidIp);
//        System.out.println("isMobile = " + isMobile);

        test();
    }


    public static void test(){
//        Long originPrice = 50010000L;
//        Long activityPrice = 100L;
//        Long levelPercent = 100L;
//        Long shippingPrice = 20000L;
//        Long payPrice = originPrice;
//
//        long activityDiscount = 0L;
//        long levelDiscount = 0L;
//        long couponDiscount = 0L;
//        // 拼团价
//        if (activityPrice != null) {
//            activityDiscount = Math.max(0, originPrice - activityPrice);
//            payPrice = Math.max(0, payPrice - activityDiscount);
//        }
//        // 会员折扣
//        if (levelPercent != null) {
//            long afterLevelPrice = originPrice * levelPercent / 100;
//            levelDiscount = Math.max(0, originPrice - afterLevelPrice);
//            payPrice = Math.max(0, payPrice - levelDiscount);
//        }
////        // 优惠券
////        if (couponSelectResp != null) {
////            couponDiscount = couponSelectResp.getDiscountedPrice();
////            payPrice = Math.max(0, payPrice - couponDiscount);
////        }
//
//        if (shippingPrice == null || shippingPrice < 0L) {
//            shippingPrice = 0L;
//        }
//        if (payPrice <= 0) {
//            payPrice = 0L;
//        }
//
//        System.out.println("111 payPrice = " + payPrice);
//        //运费
//        payPrice += shippingPrice;
//
//        System.out.println("222 payPrice = " + payPrice);
//
//
//        //此处价格保留两位小数，并向下取整
//        payPrice = priceScale(payPrice);
//
//        System.out.println("333 payPrice = " + payPrice);
//
//
//        System.out.println("originPrice = " + originPrice);
//        System.out.println("activityPrice = " + activityPrice);
//        System.out.println("levelPercent = " + levelPercent);
//        System.out.println("shippingPrice = " + shippingPrice);
//        System.out.println("payPrice = " + payPrice);

        long price1 = priceScale2(4740L);
        System.out.println("price1 = " + price1);
        long price2 = priceScale2(14763L);
        System.out.println("price2 = " + price2);
        long price3 = priceScale2(1L);
        System.out.println("price3 = " + price3);
        long price5 = priceScale2(101L);
        System.out.println("price5 = " + price5);
        long price4 = priceScale2(20100L);
        System.out.println("price4 = " + price4);
        long price6 = priceScale2(100020390L);
        System.out.println("price6 = " + price6);

//        double aa = 2.03;
//        double bb = aa * 100;
//        System.out.println("bb = " + bb);
    }

    public static long priceScale(Long price) {
        if (price == null || price <= 0L){
            return 0L;
        }
        double originalValue = (double) price / 10000;
        System.out.println("originalValue 11 = " + originalValue);
        System.out.println("originalValue 22 = " + originalValue * 100);
        System.out.println("originalValue 22 = " + Math.floor(originalValue * 100));
        double result = Math.floor(originalValue * 100) * 100;
        return (long) result;
    }


    public static long priceScale2(Long price) {
        if (price == null || price <= 0L){
            return 0L;
        }
        return price - price % 100;
//        BigDecimal bd = new BigDecimal(price).divide(new BigDecimal(10000), 2, RoundingMode.FLOOR);
//        return bd.multiply(new BigDecimal(10000)).longValue();
    }
}
