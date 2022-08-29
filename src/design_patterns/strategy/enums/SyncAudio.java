package design_patterns.strategy.enums;


public class SyncAudio extends BaseSyncAttach {
    private SyncAudio() {}
    private static class SingletonInstance {
        private static final SyncAudio INSTANCE = new SyncAudio();
    }
    public static SyncAudio getInstance() {
        return SingletonInstance.INSTANCE;
    }
    @Override
    public void handleAttachFile(Object entity) {
    }

}
