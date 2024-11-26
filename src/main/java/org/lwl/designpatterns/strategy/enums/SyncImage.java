package org.lwl.designpatterns.strategy.enums;


/**
 * @date: 2020/8/18
 * @author: lwl
 * @description: 图片同步
 */
public class SyncImage extends BaseSyncAttach {
    private SyncImage() {}
    private static class SingletonInstance {
        private static final SyncImage INSTANCE = new SyncImage();
    }
    public static SyncImage getInstance() {
        return SingletonInstance.INSTANCE;
    }

    @Override
    public void handleAttachFile(Object entity) {
        System.out.println("IMAGE...");
    }
}
