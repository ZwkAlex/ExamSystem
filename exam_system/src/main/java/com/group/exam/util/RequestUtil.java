package com.group.exam.util;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestUtil {
    public static String charReader(HttpServletRequest request) throws IOException {
        BufferedReader br = request.getReader();
        String str;
        StringBuilder wholeStr = new StringBuilder();
        while ((str = br.readLine()) != null) {
            wholeStr.append(str);
        }
        return wholeStr.toString();
    }

    public static Map<String,String>  parseParam(String str){
        Map<String,String> map = new HashMap<>();
        String[] params = str.split("&");
        for (String p : params){
            String[] temp = p.split("=");
            map.put(temp[0], temp[1]);
        }
        return map;
    }
}
