package com.damao.controller;

import com.damao.entity.TUser;
import com.damao.service.IUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huyongxing
 * @site www.skson.com
 * @date 2018/8/2 9:37
 */
@RestController
public class MainController {
    @Autowired
    private IUserDetailService userDetailService;

    @GetMapping("/hello")
    public Object main() {
        return "hello world";
    }

    @PostMapping("/user/register")
    public void register(@RequestBody TUser user) {
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        userDetailService.saveUser(user);
    }
}
