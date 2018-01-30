package com.emcloud.arc.analysis;


import java.util.Map;

public interface Analysis<T> {
    T handle(String data);
    T handle(Map<String,Object> data);
}
