package com.site.blog;

import com.site.blog.model.entity.AdminUser;
import com.site.blog.service.AdminUserService;
import com.site.blog.util.UserUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import jakarta.annotation.Resource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith({SpringExtension.class})

public class ServerJavaTests {
    @Resource
    AdminUserService adminUserService;

    @Test
    public void contextLoads() {
        AdminUser u = adminUserService.getAdminUserById("myid");
        System.out.println(u);
    }

    @Test
    public String getUserById() {
        AdminUser u = adminUserService.getAdminUserById("4");
        System.out.println(u);
        return "aaa";
    }
@Test
    void getUserb(){
        AdminUser u= UserUtil.getUserByUserCode("myid");
    System.out.println(u.toString());
}

}
