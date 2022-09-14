package test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class UrlDecodeTest {

    public static final String aaa = "{\"msg\":\"%7B%22update_time%22%3A%222022-09-01+15%3A52%3A36%22%2C%22refund_type%22%3A%22REFUND_ONLY%22%2C%22refund_reason_desc%22%3A%22%E5%85%B6%E4%BB%96%22%2C%22refunded_fee%22%3A%220.01%22%2C%22refund_reason%22%3A%22RETURNSNOT_OTHER%22%2C%22refund_id%22%3A%22202209011552353952310720%22%2C%22version%22%3A1662018756482%2C%22oids%22%3A%222893166363399291031%22%2C%22tid%22%3A%22E20220901102943072006201%22%7D\",\"kdt_name\":\"云测试店铺6dpTS\",\"test\":false,\"sign\":\"fde291372a0b7abfbddc5da0fa67ff6f\",\"type\":\"trade_refund_BuyerCreated\",\"sendCount\":1,\"version\":1662018756,\"client_id\":\"a32028d7f0dd846ec5\",\"mode\":1,\"kdt_id\":111301484,\"id\":\"E20220901102943072006201\",\"msg_id\":\"59db1a32-7bfb-4a6c-a34f-76edc894faab\",\"root_kdt_id\":111301484}";

    public static final String bbb = "{\"clientId\":\"a32028d7f0dd846ec5\",\"eventType\":\"trade_refund_BuyerCreated\",\"msg\":\"{\\\"msg\\\":\\\"{\\\\\\\"update_time\\\\\\\":\\\\\\\"2022-09-01 17:47:29\\\\\\\",\\\\\\\"refund_type\\\\\\\":\\\\\\\"REFUND_ONLY\\\\\\\",\\\\\\\"refund_reason_desc\\\\\\\":\\\\\\\"其他\\\\\\\",\\\\\\\"refunded_fee\\\\\\\":\\\\\\\"0.01\\\\\\\",\\\\\\\"refund_reason\\\\\\\":\\\\\\\"RETURNSNOT_OTHER\\\\\\\",\\\\\\\"refund_id\\\\\\\":\\\\\\\"202209011747283952460720\\\\\\\",\\\\\\\"version\\\\\\\":1662025649319,\\\\\\\"oids\\\\\\\":\\\\\\\"2893222679010476072\\\\\\\",\\\\\\\"tid\\\\\\\":\\\\\\\"E20220901174644072006147\\\\\\\"}\\\",\\\"kdt_name\\\":\\\"云测试店铺6dpTS\\\",\\\"test\\\":false,\\\"sign\\\":\\\"2b4a2fd144cf0cbb011a45f7882474a3\\\",\\\"type\\\":\\\"trade_refund_BuyerCreated\\\",\\\"sendCount\\\":1,\\\"version\\\":1662025649,\\\"client_id\\\":\\\"a32028d7f0dd846ec5\\\",\\\"mode\\\":1,\\\"kdt_id\\\":111301484,\\\"id\\\":\\\"E20220901174644072006147\\\",\\\"msg_id\\\":\\\"9b512f77-f528-4d74-ac87-0886d29e3983\\\",\\\"root_kdt_id\\\":111301484}\",\"platform\":1}";

    public static void main(String[] args) {
//        String msg = "{\"msg\":\"%7B%22delivery_order%22%3A%5B%5D%2C%22order_promotion%22%3A%7B%22item%22%3A%5B%5D%2C%22adjust_fee%22%3A%220.00%22%2C%22order%22%3A%5B%5D%7D%2C%22refund_order%22%3A%5B%5D%2C%22full_order_info%22%3A%7B%22address_info%22%3A%7B%22self_fetch_info%22%3A%22%22%2C%22delivery_address%22%3A%22%24VRG5Ul0gMlT0SklUlZIKIIRrZDOXcHNgEgabs2PaKx0%3D%241%24%22%2C%22delivery_postal_code%22%3A%22430070%22%2C%22receiver_name%22%3A%22%24nGMqIF1XBijI7B%2Bm9ImE7Q%3D%3D%241%24%22%2C%22delivery_province%22%3A%22%E6%B9%96%E5%8C%97%E7%9C%81%22%2C%22delivery_city%22%3A%22%E6%AD%A6%E6%B1%89%E5%B8%82%22%2C%22address_extra%22%3A%22%7B%5C%22areaCode%5C%22%3A%5C%22420111%5C%22%2C%5C%22lon%5C%22%3A0.0%2C%5C%22lat%5C%22%3A0.0%7D%22%2C%22delivery_district%22%3A%22%E6%B4%AA%E5%B1%B1%E5%8C%BA%22%2C%22receiver_tel%22%3A%22%24yN99BuJK4NQFxFwXXiOY5A%3D%3D%241%24%22%7D%2C%22remark_info%22%3A%7B%22buyer_message%22%3A%22%22%7D%2C%22pay_info%22%3A%7B%22outer_transactions%22%3A%5B%5D%2C%22deduction_real_pay%22%3A1%2C%22real_payment%22%3A%220.00%22%2C%22post_fee%22%3A%220.00%22%2C%22deduction_pay%22%3A0%2C%22deduct_value_card_pay%22%3A0%2C%22deduct_gift_card_pay%22%3A0%2C%22phase_payments%22%3A%5B%5D%2C%22total_fee%22%3A%220.01%22%2C%22payment%22%3A%220.01%22%2C%22transaction%22%3A%5B%5D%7D%2C%22buyer_info%22%3A%7B%22outer_user_id%22%3A%22o55Ub5ilDjtXtxXMsVhOlj8wLukE%22%2C%22buyer_phone%22%3A%22%24Xyb9B3HbGaOjdYTHHwP0PA%3D%3D%241%24%22%2C%22yz_open_id%22%3A%228HrlTtrt641344948230217728%22%2C%22fans_type%22%3A1%2C%22buyer_id%22%3A10193843920%2C%22fans_nickname%22%3A%22%24nGMqIF1XBijI7B%2Bm9ImE7Q%3D%3D%241%24%22%2C%22fans_id%22%3A17417711384%7D%2C%22orders%22%3A%5B%7B%22is_cross_border%22%3A%22%22%2C%22outer_item_id%22%3A%22%22%2C%22discount_price%22%3A%220.01%22%2C%22item_type%22%3A0%2C%22num%22%3A1%2C%22oid%22%3A%222893201869893533901%22%2C%22title%22%3A%22%E5%AE%9E%E7%89%A9%E5%95%86%E5%93%81-01%22%2C%22fenxiao_payment%22%3A%220.00%22%2C%22item_message%22%3A%22%22%2C%22item_no%22%3A%22%22%2C%22buyer_messages%22%3A%22%22%2C%22cross_border_trade_mode%22%3A%22%22%2C%22is_present%22%3Afalse%2C%22sub_order_no%22%3A%22%22%2C%22price%22%3A%220.01%22%2C%22fenxiao_price%22%3A%220.00%22%2C%22total_fee%22%3A%220.01%22%2C%22alias%22%3A%222oo98nnpvnzgcup%22%2C%22payment%22%3A%220.01%22%2C%22item_barcode%22%3A%22%22%2C%22outer_sku_id%22%3A%22%22%2C%22goods_url%22%3A%22https%3A%2F%2Fh5.youzan.com%2Fv2%2Fshowcase%2Fgoods%3Falias%3D2oo98nnpvnzgcup%22%2C%22customs_code%22%3A%22%22%2C%22item_id%22%3A2019949394%2C%22sku_properties_name%22%3A%22%5B%5D%22%2C%22sku_id%22%3A0%2C%22pic_path%22%3A%22https%3A%2F%2Fimg01.yzcdn.cn%2Fupload_files%2F2022%2F08%2F22%2FFuv22P5GC_asA_889tFHzwp1Sq3F.png%22%2C%22sku_no%22%3A%22%22%2C%22points_price%22%3A%220%22%2C%22sku_barcode%22%3A%22%22%7D%5D%2C%22source_info%22%3A%7B%22is_offline_order%22%3Afalse%2C%22book_key%22%3A%228f71f767-9af1-4ab6-9ca4-7ccc535ee554%22%2C%22biz_source%22%3A%22%22%2C%22source%22%3A%7B%22platform%22%3A%22wx%22%2C%22wx_entrance%22%3A%22direct_buy%22%7D%7D%2C%22order_info%22%3A%7B%22consign_time%22%3A%22%22%2C%22order_extra%22%3A%7B%22is_from_cart%22%3A%22false%22%2C%22is_points_order%22%3A%220%22%7D%2C%22created%22%3A%222022-09-01+15%3A05%3A11%22%2C%22status_str%22%3A%22%E5%BE%85%E6%94%AF%E4%BB%98%22%2C%22expired_time%22%3A%222022-09-01+16%3A05%3A11%22%2C%22success_time%22%3A%22%22%2C%22type%22%3A0%2C%22confirm_time%22%3A%22%22%2C%22tid%22%3A%22E20220901150511072000049%22%2C%22pay_time%22%3A%22%22%2C%22update_time%22%3A%222022-09-01+15%3A05%3A12%22%2C%22is_retail_order%22%3Afalse%2C%22team_type%22%3A0%2C%22pay_type%22%3A0%2C%22refund_state%22%3A0%2C%22close_type%22%3A0%2C%22order_tags%22%3A%7B%22is_member%22%3Atrue%2C%22is_secured_transactions%22%3Atrue%7D%2C%22express_type%22%3A0%2C%22status%22%3A%22WAIT_BUYER_PAY%22%7D%7D%7D\",\"kdt_name\":\"云测试店铺6dpTS\",\"test\":false,\"sign\":\"fc6755a8ee6c1b451433a5ac02286cf1\",\"sendCount\":1,\"type\":\"trade_TradeCreate\",\"version\":1662015912,\"client_id\":\"a32028d7f0dd846ec5\",\"mode\":1,\"kdt_id\":111301484,\"id\":\"E20220901150511072000049\",\"msg_id\":\"c394d4d5-09f9-46bc-9b80-e50f861ddb47\",\"root_kdt_id\":111301484,\"status\":\"WAIT_BUYER_PAY\"}";
//        DtoTest dtoTest = new DtoTest();
//        dtoTest.msg = aaa;
//        dtoTest.name = "lwl";
//
//        String decodeStr = decode(dtoTest.msg);
//        System.out.println(decodeStr);
//
//
//        System.out.println(111);
//        dtoTest.msg = decodeStr;
//        System.out.println(dtoTest);

    }

    private static String decode(String msg){
        try {
            msg = URLDecoder.decode(msg, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            System.out.println("失败:" + e);
        }
        return msg;
    }

    private static class DtoTest{
        public String msg;
        public String name;

        @Override
        public String toString() {
            return "DtoTest{" +
                    "msg='" + msg + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
}
