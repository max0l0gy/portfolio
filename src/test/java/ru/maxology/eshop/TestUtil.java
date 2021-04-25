package ru.maxology.eshop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import lombok.SneakyThrows;


public class TestUtil {
    public static ObjectMapper MAPPER = new ObjectMapper();
    @SneakyThrows
    public static String getBody(String path){
        return Resources.toString(Resources.getResource(path), Charsets.UTF_8);
    }
}
