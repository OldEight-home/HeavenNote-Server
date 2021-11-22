package com.oldeighthome.heavennote.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oldeighthome.heavennote.common.api.ApiResult;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResponseUtil {
    public static PrintWriter getPrintWriter(HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;character=utf-8");
        return response.getWriter();
    }
    public static void output(PrintWriter printWriter, ApiResult result) throws JsonProcessingException {
        String json=new ObjectMapper().writeValueAsString(result);
        printWriter.write(json);
        printWriter.flush();
        printWriter.close();

    }
}
