package com.site.blog.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Yangzhengqian
 * @description
 * @date:Created time 2021/8/13 14:13
 * @modified By:
 */
public class NewFeatureUtils {
    interface Predicte<String> {
        Boolean test(String s);
    }

    public static List<String> filter(List<String> fruit, Predicte<String> predicte) {
        List<String> f = new ArrayList<>();
        for (String s : fruit) {
            if (predicte.test(s)) {

                f.add(s);
            }
        }
        return f;
    }

    public static void main(String[] args) {
        List<String> fruit= Arrays.asList("香蕉","苹果","火龙果","落落莓","鸣草");
        List<String > newFruit=filter(fruit, new Predicte<String>() {
            @Override
            public Boolean test(String s) {
                return s.length()==2;
            }
        });
        //List<String> newFruit=filter(fruit,s->s.length()==2)
        System.out.println(newFruit);
    }
}
