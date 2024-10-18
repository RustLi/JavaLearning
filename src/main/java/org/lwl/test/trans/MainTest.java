package org.lwl.test.trans;


import java.io.IOException;

public class MainTest {

    public static void main(String[] args) {
        MainTest main = new MainTest();
        String json = main.getMainJson();
        System.out.println("序列化： json = " + json);

        try {
            MainDto dto = JacksonUtil.str2Obj(json, MainDto.class);
            System.out.println("name = " + dto.getName());
            System.out.println("subName = " + ((SubDto)dto).getSubName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private MainDto getMainDto() {
        SubDto dto = new SubDto();
        dto.setName("aaa");
        dto.setSubName("bbb");
        return dto;
    }

    private String getMainJson(){
        return JacksonUtil.obj2Str(getMainDto());
    }

}
