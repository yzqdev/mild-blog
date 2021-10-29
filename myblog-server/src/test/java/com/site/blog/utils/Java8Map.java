package com.site.blog.utils;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


public class Java8Map {
    @Test
    public void steam1 (){
        List<String> list = new ArrayList<>();
        list.add("java");
        list.add("php");
        list.add("python");

        //map function
        Stream<Integer> stream = list.stream().map(String::length);

        Integer[] lengthArr = stream.toArray(Integer[]::new);

        for (int a : lengthArr) {
            System.out.println(a);
        }
    }
}
