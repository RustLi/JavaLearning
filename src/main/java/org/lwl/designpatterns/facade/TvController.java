package org.lwl.designpatterns.facade;

/**
 * @program: javaProjects
 * @description: 外观类，电视遥控器
 * @author: RustLi
 * @create: 2018-11-14 13:45
 **/
public class TvController {
    private PowerSystem mPowerSystem = new PowerSystem();
    private VoiceSystem mVoiceSystem = new VoiceSystem();
    private ChannelSystem mChannelSystem = new ChannelSystem();

    public void powerOn(){
        mPowerSystem.powerOn();
    }

    public void powerOff(){
        mPowerSystem.powerOff();
    }

    public void turnUp(){
        mVoiceSystem.turnUp();
    }

    public void turnDown(){
        mVoiceSystem.turnDown();
    }

    public void nextChannel(){
        mChannelSystem.nextChannel();
    }

    public void previousChannel(){
        mChannelSystem.previousChannel();
    }
}
