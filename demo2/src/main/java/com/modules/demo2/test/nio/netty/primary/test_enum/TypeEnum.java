package com.modules.demo2.test.nio.netty.primary.test_enum;

public enum TypeEnum {
    SMALL("1", "small"), LARGE("2", "large");

    private String code;
    private String name;
    private TypeEnum(String code, String name){
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return this.name + "-" + this.code;
    }

    public static void main(String[] args){
        System.out.println(TypeEnum.SMALL.toString());
    }
}
