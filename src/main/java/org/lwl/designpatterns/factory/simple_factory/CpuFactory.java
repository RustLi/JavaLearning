package org.lwl.designpatterns.factory.simple_factory;

/**
 * @program: javaProjects
 * @description: cpu factory
 * @author: RustLi
 * @create: 2018-11-13 10:56
 **/
public class CpuFactory {
    public static Cpu createCpu(int type){
        Cpu cpu = null;
        if (type == 1){
            cpu = new IntelCpu(333);
        }else if (type == 2){
            cpu = new AMDCpu(245);
        }
        return cpu;
    }
}
