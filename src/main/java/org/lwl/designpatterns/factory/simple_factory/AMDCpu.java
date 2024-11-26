package org.lwl.designpatterns.factory.simple_factory;

/**
 * @program: javaProjects
 * @description: ADM Cpu
 * @author: RustLi
 * @create: 2018-11-13 10:51
 **/
public class AMDCpu implements Cpu {
    private int cpuType;
    AMDCpu(int type){
        cpuType = type;
    }

    @Override
    public void installCpu() {
        System.out.println(cpuType);
    }
}
