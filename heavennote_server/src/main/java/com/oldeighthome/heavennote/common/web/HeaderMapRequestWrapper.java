package com.oldeighthome.heavennote.common.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.HashMap;
import java.util.Map;

public class HeaderMapRequestWrapper extends HttpServletRequestWrapper {
    private Map<String,String> headerMap=new HashMap<>();
    public HeaderMapRequestWrapper(HttpServletRequest request) {
        super(request);
    }
    public void addHeader(String name,String value){
        headerMap.put(name,value);
    }

    @Override
    public String getHeader(String name) {
        String headerValue=super.getHeader(name);
        if(headerMap.containsKey(name)){
            headerValue=headerMap.get(name);
        }
        return headerValue;
    }
}
