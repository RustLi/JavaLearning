package org.lwl.designpatterns.factory.simple_factory;

/**
 * @program: javaProjects
 * @description: intel cpu
 * @author: RustLi
 * @create: 2018-11-13 10:50
 **/
public class IntelCpu implements Cpu {

    private int cpuType;
    IntelCpu(int type){
        cpuType = type;
    }

    @Override
    public void installCpu() {
        System.out.println(cpuType);
    }
}
