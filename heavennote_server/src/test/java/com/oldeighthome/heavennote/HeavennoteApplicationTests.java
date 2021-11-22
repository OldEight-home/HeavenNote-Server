package com.oldeighthome.heavennote;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oldeighthome.heavennote.entity.User;
import com.oldeighthome.heavennote.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
@Slf4j
class HeavennoteApplicationTests extends ServiceImpl<UserMapper, User> {
    @Autowired
    private
    @Test
    void contextLoads() {
        log.info("xx");
    }
    @Test
    void testPlus(){
        User user = getOne(new QueryWrapper<User>().eq("user_id", 1));

        log.info(user.getUsername());
    }
    @Test
    void testJson(){
        String str="{\"name\":\"tom\",\"age\":\"12\"}";
        JSONObject jb=JSONObject.parseObject(str);
        String s=jb.get("name").toString();
        log.info(s);
    }

}
