/**
 * kinyun.cn Inc.
 * Copyright (c) 2014-2021 All Rights Reserved.
 */
package org.lwl.test.dxb;

import lombok.Data;

/**
 * @title CheckCallbackUrlReq
 * @author yanmaoyuan
 * @date 2021年8月14日
 * @version 1.0
 */
@Data
public class CheckCallbackUrlReq {

    private String appId;

    private String callbackUrl;

    private String callbackToken;

    private String callbackAesKey;

    private Integer isCallbackEncrypt;

    private Integer isMod;// 是否要更新数据库
}