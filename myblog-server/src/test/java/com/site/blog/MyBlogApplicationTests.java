package com.site.blog;

import com.site.blog.model.entity.AdminUser;
import com.site.blog.service.AdminUserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith({SpringExtension.class})

public class MyBlogApplicationTests {
@Resource
	AdminUserService adminUserService;
	@Test
	public void contextLoads() {
		AdminUser u=adminUserService.getAdminUserById(4);
		System.out.println(u);
	}

}
