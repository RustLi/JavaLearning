package org.lwl.test.dxb;

import kotlin.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;

@Slf4j
public class DxbApiTest {

    public static void main(String[] args) {
        String appId = "appatlo8r6a4l6zzuse";
        String token = "tAsMVnDJIfQb";
        String aesKey = "X3iW5r6tt2T02V537HvlzjXm9wbJu6DKP87FEf3aGpg";
        String callbackUrl = "http://crmtest.cloudgn.com:5555/callBack";
//
//
        CheckCallbackUrlReq req = new CheckCallbackUrlReq();
        req.setAppId(appId);
        req.setCallbackAesKey(aesKey);
        req.setCallbackUrl(callbackUrl);
        req.setCallbackToken(token);
        req.setIsCallbackEncrypt(1);
        CheckCallbackUrlResp resp = checkCallbackUrlTest(req);
        System.out.println(resp);


//        String signature="1a24bcfabdfaf2c4fd2754f26d34acb2c544979f";
//        Long timestamp=1745991876756L;
//        String nonce="lJnSbsDmYq7DIKEH";
//        String echostr = "vR6wJV8CrY8Ts8WZACQChT9PCFcyOCOlDfQBNpv%252BII4qLAJdw3TI7U1mxBLhwiMexe3Ol%252FXnFbSxrCbGaFfxSg%253D%253D";
//        testVerify(token, aesKey, appId, signature, timestamp,nonce, echostr);
    }


    /**
     * 校验回调URL
     **/
    public static CheckCallbackUrlResp checkCallbackUrlTest(CheckCallbackUrlReq params) {
        log.info("check callback url, params={}", params);
        System.out.println("check callback url, params={}");
        CheckCallbackUrlResp result = new CheckCallbackUrlResp();
        result.setIsUrlVerified(0);

        String appId = params.getAppId();
        String token = params.getCallbackToken();
        String aesKey = params.getCallbackAesKey();
        Integer isCallbackEncrypt = params.getIsCallbackEncrypt();

        URI baseUri;
        try {
            baseUri = new URI(params.getCallbackUrl());
        } catch (URISyntaxException e) {
            result.setMsg("参数错误: 回调URL格式不正确");
            return result;
        }

        // 回文消息内容
        String echoStr = "ECHO_" + BizMsgCrypt.getNonce();
        String queryString;

        if (isCallbackEncrypt != null && isCallbackEncrypt != 0) {
            /*
             * 消息加密
             */
            if (StringUtils.isAnyBlank(token, aesKey)) {
                log.info("第三方未设置加密参数，执行回调。appId:{}", appId);
                result.setMsg("参数错误: 未设置Token或EncodingAESKey");
                return result;
            }

            BizMsgCrypt crypt;
            try {
                crypt = new BizMsgCrypt(token, aesKey, appId);
            } catch (AesException e) {
                log.error("第三方加密参数错误, appId={}, aesKey={}", appId, aesKey, e);
                result.setMsg("请求失败: EncodingAESKey错误");
                return result;
            }

            Long timestamp = System.currentTimeMillis();
            String nonce = BizMsgCrypt.getNonce();

            EncryptMsg encrypt;
            try {
                encrypt = crypt.encryptMsg(echoStr, timestamp, nonce);
            } catch (AesException e) {
                log.error("第三方消息加密失败, appId={}, aesKey={}", appId, aesKey, e);
                result.setMsg("请求失败: 消息加密失败");
                return result;
            }

            /*
             * 将加密签名补充在回调uri上
             */
            String encryptMsg;
            try {
                encryptMsg = URLEncoder.encode(encrypt.getEncrypt(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                encryptMsg = encrypt.getEncrypt();
                // should not happen
            }

            queryString = "signature=" + encrypt.getSignature() + "&timestamp=" + timestamp + "&nonce=" + nonce + "&echostr=" + encryptMsg;
        } else {
            queryString = "echostr=" + echoStr;
        }

        URI uri;
        try {
            uri = CallbackClient.appendUri(baseUri, queryString);
        } catch (URISyntaxException e) {
            log.error("给URI追加queryString失败, baseUri:{}, queryString:{}", baseUri, queryString, e);
            result.setMsg("回调URL格式不正确");
            return result;
        }

        // 发送请求
        CallbackClient client = new CallbackClient(1000, 0);
        HttpResp resp = client.get(uri.toString());

        log.info("check callback url, uri={}, resp.code={}, resp.content={}", uri, resp.code, resp.content);

        System.out.println("check callback url, uri={}" + uri + ", code = " + resp.code + ", content" + resp.content);
        if (resp.code == 200) {
            if (echoStr.equals(resp.content)) {
                result.setIsUrlVerified(1);
                result.setMsg("OK");
            } else {
                log.info("校验失败: 回调URL没有返回正确的结果, appId={}, resp.content={}, echoStr:{}", appId, resp.content, echoStr);
                result.setMsg("校验失败: 没有返回正确的结果");
            }
        } else if (resp.code == 408) {
            result.setMsg("校验失败: 回调URL响应超时");
        } else {
            result.setMsg("校验失败: 回调URL响应" + resp.code);
        }
        return result;
    }


    /**
     * 接收到回调以后校验
     **/
    private static void testVerify(String token, String aesKey, String appId, String signature, Long timestamp,String nonce, String echostr){
        BizMsgCrypt crypt;
        try {
            crypt = new BizMsgCrypt(token, aesKey, appId);
        } catch (AesException e) {
            log.error("第三方加密参数错误, appId={}, aesKey={}", appId, aesKey, e);
            return;
        }
        try {
            //两次解码，代码里面进入Url encode两次，所以需要解两次
            String echostr1 = URLDecoder.decode(echostr, "UTF-8");
            System.out.println("verifyurl echostr1: " + echostr1);
            String echostr2 = URLDecoder.decode(echostr1, "UTF-8");
            System.out.println("verifyurl echostr2: " + echostr2);
            String  sEchoStr = crypt.verifyURL(signature, timestamp, nonce, echostr2);
            System.out.println("verifyurl echostr: " + sEchoStr);
            // 验证URL成功，将sEchoStr返回
            // HttpUtils.SetResponse(sEchoStr);
        } catch (Exception e) {
            //验证URL失败，错误原因请查看异常
            e.printStackTrace();
        }
    }

    /**
     * 回调业务数据 直接postman请求下，可能直接无法请求
     **/
//    public <T> void callback(Long bizId, CallbackDto<T> request, boolean returnDataDirectly) {
//
//        bizId = bizService.getMainOrSelfBizId(bizId).getData();
//
//        BizApiConfig config = configMapper.getByBizId(bizId);
//        if (config == null) {
//            log.warn("该商户未开通API, bizId={}", bizId);
//            return;
//        }
//
//        String appId = config.getAppId();
//
//        URI baseUri = getCallbackUri(config);
//        if (baseUri == null) {
//            // 未能获取合法的uri
//            log.info("回调url不合法，无法推送消息。bizId={}, appId: {}", bizId, appId);
//            return;
//        }
//
//        if (config.getIsUrlVerified() == 0) {
//            log.info("第三方回调URL未进行校验，不执行回调。bizId={}, appId={}", bizId, appId);
//            return;
//        }
//
//        Object postData;
//        Integer isCallbackEncrypt = config.getIsCallbackEncrypt();
//
//        if (isCallbackEncrypt != null && isCallbackEncrypt != 0) {
//            /*
//             * 消息加密
//             */
//            String token = config.getCallbackToken();
//            String aesKey = config.getCallbackAesKey();
//            if (StringUtils.isAnyBlank(token, aesKey)) {
//                log.info("第三方未设置加密参数，不执行回调。bizId={}, appId:{}", bizId, appId);
//                return;
//            }
//
//            BizMsgCrypt crypt;
//            try {
//                crypt = new BizMsgCrypt(token, aesKey, appId);
//            } catch (AesException e) {
//                log.error("第三方加密参数错误, bizId={}, appId={}, aesKey={}", bizId, appId, aesKey, e);
//                return;
//            }
//
//            String msg;
//            if (returnDataDirectly){
//                msg = JacksonUtil.obj2Str(request.getData());
//            } else {
//                msg = JacksonUtil.obj2Str(request);
//            }
//            EncryptMsg encrypt;
//            try {
//                encrypt = crypt.encryptMsg(msg, request.getTimestamp(), BizMsgCrypt.getNonce());
//            } catch (AesException e) {
//                log.error("第三方消息加密失败, bizId={}, appId={}, aesKey={}", bizId, appId, aesKey, e);
//                return;
//            }
//            postData = encrypt;
//        } else {
//            // 消息不加密
//            if (returnDataDirectly){
//                postData = request.getData();
//            } else {
//                postData = request;
//            }
//        }
//        log.info("callback postData:{}", postData);
//
//        int retry = 0;
//        if (config.getCallbackRetry() != 0) {
//            retry = 1;
//        }
//
//        /*
//         * 发送回调消息
//         */
//        int error = 0;
//        String errorMsg = "";
//        try {
//            CallbackClient client = new CallbackClient(5000, retry);
//            HttpResp resp = client.postJson(baseUri.toString(), postData);
//
//            // 记录错误信息
//            if (resp.code != 200 && resp.code != 201 && resp.code != 202) {
//                error = resp.code;
//                errorMsg = resp.content;
//            }
//        } catch (Exception e) {
//            error = 1;
//            errorMsg = "回调推送失败, ex=" + e.getMessage();
//            log.error("回调推送失败", e);
//        }
//
//        /*
//         * 记录回调消息
//         */
//        saveCallbackMsg(bizId, appId, request, error, StringUtils.abbreviate(errorMsg, 200));
//    }
}


