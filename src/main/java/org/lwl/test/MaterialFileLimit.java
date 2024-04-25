package org.lwl.test;

import java.util.HashSet;
import java.util.Set;

public enum MaterialFileLimit {
    /**
     * 公众号 图片
     */
    offical_account_image("bmp,png,jpg,jpeg,gif", 2 * 1024, -1),

    /**
     * 公众号 声音
     */
    offical_account_voice("mp3,wma,wav,amr", 2 * 1024, 60),

    /**
     * 公众号 视频
     */
    offical_account_video("mp4", 10 * 1024, -1),

    /**
     * 公众号 缩略图
     */
    offical_account_thumb("jpg,jpeg", 2 * 1024, -1),

    /**
     * 公众号 图文内图片
     */
    offical_account_news("jpg,jpeg,png", 2 * 1024, -1),

    /**
     * 微信 图片
     */
    weixin_image("bmp,png,jpg,jpeg,gif", 10 * 1024, -1),

    /**
     * 微信 声音
     */
    weixin_voice("amr", 10 * 1024, 60),

    /**
     * 微信 视频
     */
    weixin_video("mp4", 50 * 1024, -1),

    /**
     * 微信 文件
     */
    weixin_file("ppt,pptx,xls,xlsx,doc,docx,pdf,zip,rar,7z,mp3,wma,wav", 50 * 1024 * 1024, -1),

    /**
     * 朋友圈 图片
     */
    wechat_moments_image("bmp,png,jpg,jpeg,gif", 10 * 1024, -1),

    /**
     * 朋友圈 视频
     */
    wechat_moments_video("mp4", 7 * 1024 + 512, 15),

    ;

    private static Set<String> formatSet = new HashSet<>();

    static {
        for (MaterialFileLimit it : values()) {
            formatSet.addAll(it.formats);
        }
    }

    /**
     * 校验格式
     *
     * @param format
     * @return
     */
    public static boolean contains(String format) {
        String lowerFormat = format.toLowerCase();
        return formatSet.contains(lowerFormat);
    }

    /**
     * @param format
     * @param size
     */
    private MaterialFileLimit(String format, int size) {
        this(format, size, -1);
    }

    /**
     * @param format
     * @param size
     * @param duration
     */
    private MaterialFileLimit(String format, int size, int duration) {
        this.format = format;
        this.size = size;
        this.duration = duration;

//        formats = Sets.newHashSet();
        formats = new HashSet<>();
        String[] array = format.split(",");
        for (String it : array) {
            formats.add(it);
        }
    }

    /**
     * 大小限制
     */
    private int size;

    /**
     * 时长限制
     */
    private long duration;

    /**
     * 格式限制
     */
    private String format;

    private Set<String> formats;

    public String getFormat() {
        return format;
    }

    public int getSize() {
        return size;
    }

    public long getDuration() {
        return duration;
    }

    /**
     * 校验格式
     *
     * @param format
     * @return
     */
    public boolean checkFormat(String format) {
        if (format != null && format.length() > 0) {
            String lowerFormat = format.toLowerCase();
            return this.formats.contains(lowerFormat);
        }
        return false;
    }

    /**
     * 校验大小
     *
     * @param size
     * @return
     */
    public boolean checkSize(int size) {
        return size <= this.size;
    }

    /**
     * 校验时长
     *
     * @param duration
     * @return
     */
    public boolean checkDuration(long duration) {
        if (this.duration <= 0) {
            return true;
        }
        return duration <= this.duration;
    }

}
