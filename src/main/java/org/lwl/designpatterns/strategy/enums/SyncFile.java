package org.lwl.designpatterns.strategy.enums;

public class SyncFile extends BaseSyncAttach {
    private SyncFile() {}
    private static class SingletonInstance {
        private static final SyncFile INSTANCE = new SyncFile();
    }
    public static SyncFile getInstance() {
        return SingletonInstance.INSTANCE;
    }
    @Override
    public void handleAttachFile(Object entity) {
    }

}
