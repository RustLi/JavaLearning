package design_patterns.strategy.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public enum Type2Obj {
    IMAGE(MsgAttachType.IMAGE.getValue(), "图片同步", SyncImage.getInstance()),
    VIDEO(MsgAttachType.VIDEO.getValue(), "视频同步", SyncVideo.getInstance()),
    AUDIO(MsgAttachType.AUDIO.getValue(), "语音同步", SyncAudio.getInstance()),
    FILE(MsgAttachType.FILE.getValue(), "文件同步", SyncFile.getInstance())
    ;

    private int value;
    private String desc;
    private Object obj;
    Type2Obj(int value, String desc, Object obj) {
        this.value = value;
        this.desc = desc;
        this.obj = obj;
    }
    public int getValue() {
        return value;
    }

    public Object getObj() {
        return obj;
    }

    private static final Map<Integer, Object> type2ObjMap;
    static {
        type2ObjMap = Arrays.stream(Type2Obj.values()).filter(a -> Objects.nonNull(a.getObj()))
                .collect(Collectors.toMap(Type2Obj::getValue, Type2Obj::getObj));
    }

    public static Object getObjByType(Integer value) {
        return type2ObjMap.get(value);
    }
}
