package org.lwl.test.exam;

import lombok.Data;

@Data
public class ScoreResultDto {
    /**
     * 查看详细考试详情的链接，有30分钟时间限制，超过30分钟后得重新获取链接
     **/
    private String viewResult;

    /**
     * 考试得分
     **/
    private Float score;

    /**
     * 是否通过 0：未通过 1：通过
     **/
    private Integer passed;

    /**
     * 考生开始作答的时间戳，int类型，单位s(秒)
     **/
    private Integer startTime;

    /**
     * 考生交卷的时间戳，int类型，单位s(秒)
     **/
    private Integer endTime;

    /**
     * status 表示答卷状态，1表示正常交卷, 0正在考试中，2表示超时自动交卷，3表示被管理员强制交卷，4表示系统执行交卷
     **/
    private Integer status;

    /**
     * status 表示答卷状态，1表示正常交卷, 0正在考试中，2表示超时自动交卷，3表示被管理员强制交卷，4表示系统执行交卷
     **/
    private String statusDesc;

    /**
     * 试卷Id
     **/
    private String eid;

    private String tid;
}
