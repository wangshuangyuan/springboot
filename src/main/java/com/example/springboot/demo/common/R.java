package com.example.springboot.demo.common;

import lombok.Data;

@Data
public class R<T> {
private int code;
private String msg;
private T data;

public R(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        }

public static <T> R<T> success() {
        return new R<>(200, "Success", null);
        }

public static <T> R<T> success(T data) {
        return new R<>(200, "Success", data);
        }

public static <T> R<T> success(String msg, T data) {
        return new R<>(200, msg, data);
        }

public static <T> R<T> failure() {
        return new R<>(500, "failure", null);
        }
public static <T> R<T> failure(int code, String msg) {
        return new R<>(code, msg, null);
        }

public static <T> R<T> failure(int code, String msg, T data) {
        return new R<>(code, msg, data);
        }
        }

