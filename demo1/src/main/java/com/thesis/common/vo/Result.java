package com.thesis.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.formula.functions.T;

//公共相应类，用于确定从后端向前端发送数据格式一致
/* @Data 是 Lombok 提供的一个注解，用于自动生成 Java 类的标准方法，
例如 toString、equals、hashCode、以及所有字段的 getter 和 setter 方法。
这可以减少编写样板代码的工作，使代码更加简洁。 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T>{
    private Integer code;
    private String message;
    //T是泛形
    private T data;

    public static <T> Result<T> success(){
        return new Result<>(20000,"success",null);

    }
    public static <T> Result<T> success(T data,String message){
        return new Result<>(20000,message,data);
    }
    public static <T> Result<T> success(T data){
        return new Result<>(20000,"success",data);

    }
    public static <T> Result<T> success(String message){
        return new Result<>(20000,message,null);

    }
    public static <T> Result<T> fail(){
        return new Result<>(20001,"fail",null);

    }
    public static <T> Result<T> fail(Integer code,String message){
        return new Result<>(code,message,null);
    }
    public static <T> Result<T> fail(Integer code){
        return new Result<>(code,"fail",null);

    }
    public static <T> Result<T> fail(String message){
        return new Result<>(20001,message,null);

    }
}
