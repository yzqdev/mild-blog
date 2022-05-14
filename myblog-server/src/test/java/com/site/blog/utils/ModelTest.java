package com.site.blog.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;

/**
 * @author yanni
 * @date time 2022/5/14 22:27
 * @modified By:
 */
@Data
@AllArgsConstructor
class Person{
    String name;
    String age;

}
public class ModelTest {
    @Test
    void get(){
        Person p=new Person("hhh","bbb");
        System.out.println(p.toString());
    }
}
