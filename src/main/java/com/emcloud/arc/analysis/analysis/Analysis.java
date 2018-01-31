package com.emcloud.arc.analysis.analysis;


import java.util.Map;

public interface Analysis<T> {
    T handle(Float data);
    T handle(Map<String,Float> data);
}
