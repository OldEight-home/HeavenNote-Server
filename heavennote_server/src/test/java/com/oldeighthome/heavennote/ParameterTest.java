package com.oldeighthome.heavennote;

import com.alibaba.fastjson.JSONObject;
import com.oldeighthome.heavennote.service.INoteService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Slf4j
@RunWith(Parameterized.class)
@SpringBootTest
public class ParameterTest {
    private String str;
    @Parameterized.Parameters
    public static Collection<Object> data() {
        Object[] data = new Object[] {
                "{\"name\":\"tom\",\"age\":\"12\"}",
                "{\"name\":\"tom\",\"age\":\"13\"}",
                "{\"name\":\"Jerry\",\"age\":\"12\"}"
        };
        return Arrays.asList(data);
    }
    public ParameterTest(String str){
        this.str=str;
    }

    @Test
    public void testJson(){
        JSONObject jb=JSONObject.parseObject(str);
        String s=jb.get("name").toString();
        log.info(s);
        Assert.assertEquals(s,"tom");
    }

}
