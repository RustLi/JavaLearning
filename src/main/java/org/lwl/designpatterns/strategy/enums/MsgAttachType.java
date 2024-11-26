package org.lwl.designpatterns.strategy.enums;

/**
 * Created by ljc on 2022/1/4
 * Line 原始定义，区分图片、视频、语音、文件...
 * 配合{@link MsgType}使用
 */
public enum MsgAttachType {
    NONE(0),
    IMAGE(1),
    VIDEO(2),
    AUDIO(3),
    HTML(4),
    PDF(5),
    CALL(6),        //通话
    STICKER(7),
    PRESENCE(8),
    GIFT(9),
    GROUPBOARD(10),
    APPLINK(11),
    LINK(12),
    CONTACT(13),
    FILE(14),
    LOCATION(15),
    POSTNOTIFICATION(16),
    RICH(17),
    CHATEVENT(18),
    MUSIC(19),
    PAYMENT(20),
    EXTIMAGE(21),
    FLEX(22);

    private final int value;

    MsgAttachType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
