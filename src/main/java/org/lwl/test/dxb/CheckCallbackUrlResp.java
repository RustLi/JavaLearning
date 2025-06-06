package org.lwl.test.dxb;

import lombok.Data;

/**
 * @title CheckCallbackUrlResp
 * @author yanmaoyuan
 * @date 2021年8月14日
 * @version 1.0
 */
@Data
public class CheckCallbackUrlResp {

    private Integer isUrlVerified;
    private String msg;
}
