package com.school.wz_growth.model;

import lombok.Data;

/**
 * 通用返回结果
 */
@Data
public class Result<T> {
    private Integer code; // 状态码
    private String message; // 提示信息
    private T data; // 数据

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage("操作成功");
        result.setData(data);
        return result;
    }

    public static <T> Result<T> error(String message) {
        Result<T> result = new Result<>();
        result.setCode(500);
        result.setMessage(message);
        return result;
    }
} 