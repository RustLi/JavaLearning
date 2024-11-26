package org.lwl.designpatterns.strategy.enums;


public class SyncVideo extends BaseSyncAttach {
    private SyncVideo() {}
    private static class SingletonInstance {
        private static final SyncVideo INSTANCE = new SyncVideo();
    }
    public static SyncVideo getInstance() {
        return SingletonInstance.INSTANCE;
    }

    @Override
    public void handleAttachFile(Object entity) {
    }
}
