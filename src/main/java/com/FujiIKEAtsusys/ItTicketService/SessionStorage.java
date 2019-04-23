package com.FujiIKEAtsusys.ItTicketService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class SessionStorage {


    @Autowired
    private RedisTemplate<String, String> userTemplate;

    public void saveUser(String key, String user){
        userTemplate.opsForValue().set(key,user);
    }

    public String findById(final String userId) {
        return userTemplate.opsForValue().get(userId);
    }
}
