package com.cornelius.eduservice.controller;


import com.cornelius.commonutils.R;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eduservice/user")
//解决跨域问题
@CrossOrigin
public class EduloginController {


    //登录
    @PostMapping("login")
    public R login() {

        return R.ok().data("token","admin");
    }

    //获取用户信息
    @GetMapping("info")
    public R info() {

        return R.ok().data("roles","[admin]").data("name","admin")
                .data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
