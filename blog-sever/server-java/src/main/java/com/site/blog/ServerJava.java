package com.site.blog;

//import com.dtflys.forest.springboot.annotation.ForestScan;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 * 我的博客应用程序
 *
 * @author yanni
 * @date 2021/11/21
 */
@MapperScan("com.site.blog.mapper")
@SpringBootApplication

@Slf4j
public class ServerJava {
    public static void main(String[] args) throws UnknownHostException {
      var application=  SpringApplication.run(ServerJava.class, args);

        log.info("""
      ..######..##.....##..######...######..########..######...######.
      .##....##.##.....##.##....##.##....##.##.......##....##.##....##
      .##.......##.....##.##.......##.......##.......##.......##......
      ..######..##.....##.##.......##.......######....######...######.
      .......##.##.....##.##.......##.......##.............##.......##
      .##....##.##.....##.##....##.##....##.##.......##....##.##....##
      ..######...#######...######...######..########..######...######.""");
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String path = env.getProperty("server.servlet.context-path");
        if (StringUtils.isEmpty(path)) {
            path = "";
        }
        log.info("\n----------------------------------------------------------\n\t" +
                "Application  is running! Access URLs:\n\t" +
                "Local访问网址: \t\thttp://localhost:" + port + path + "\n\t" +
                "External访问网址: \thttp://" + ip + ":" + port + path + "\n\t" +
                "swagger访问网址: \thttp://localhost:"    + port + "/swagger-ui/index.html" + "\n\t" +
                "----------------------------------------------------------");
    }

}
