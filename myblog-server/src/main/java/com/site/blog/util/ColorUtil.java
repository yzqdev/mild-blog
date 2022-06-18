package com.site.blog.util;


public class ColorUtil {
    /**
     * @param color   颜色代号：背景颜色代号(41-46)；前景色代号(31-36)
     * @param type    样式代号：0无；1加粗；3斜体；4下划线
     * @param content 要打印的内容
     */
    public  static void colorLog(String content, int color, int type) {
        boolean hasType = type != 1 && type != 3 && type != 4;
        if (hasType) {
            System.out.printf("\u001B[%dm%s\u001B[0m%n", color, content);
        } else {
            System.out.printf("\u001B[%d;%dm%s\u001B[0m%n", color, type, content);
        }
    }

    public  static void red(String content) {
        colorLog(content, 31, 0);
    }

    public  static void green(String content) {
        colorLog(content, 32, 0);
    }
    public  static void yellow(String content) {
        colorLog(content, 33, 0);
    }
    public  static void blue(String content) {
        colorLog(content, 34, 0);
    }
    public  static void purple(String content) {
        colorLog(content, 35, 0);
    }
    public  static void cyan(String content) {
        colorLog(content, 36, 0);
    }

    public  static void redBold(String content) {
        colorLog(content, 31, 1);
    }

    public  static void greenBold(String content) {
        colorLog(content, 32, 1);
    }
    public  static void yellowBold(String content) {
        colorLog(content, 33, 1);
    }
    public  static void blueBold(String content) {
        colorLog(content, 34, 1);
    }
    public  static void purpleBold(String content) {
        colorLog(content, 35, 1);
    }
    public  static void cyanBold(String content) {
        colorLog(content, 36, 1);
    }

    public  static void redItalic(String content) {
        colorLog(content, 31, 3);
    }

    public  static void greenItalic(String content) {
        colorLog(content, 32, 3);
    }
    public  static void yellowItalic(String content) {
        colorLog(content, 33, 3);
    }
    public  static void blueItalic(String content) {
        colorLog(content, 34, 3);
    }
    public  static void purpleItalic(String content) {
        colorLog(content, 35, 3);
    }
    public  static void cyanItalic(String content) {
        colorLog(content, 36, 3);
    }

    public  static void redUnderline(String content) {
        colorLog(content, 31, 4);
    }

    public  static void greenUnderline(String content) {
        colorLog(content, 32, 4);
    }
    public  static void yellowUnderline(String content) {
        colorLog(content, 33, 4);
    }
    public  static void blueUnderline(String content) {
        colorLog(content, 34, 4);
    }
    public  static void purpleUnderline(String content) {
        colorLog(content, 35, 4);
    }
    public  static void cyanUnderline(String content) {
        colorLog(content, 36, 4);
    }
 

     
    public void showColor() {
        System.out.println("控制台颜色测试：");
        //System.out.println(colorLog("[ 红色 ]", 31, 0));
        //System.out.println(colorLog("[ 黄色 ]", 32, 0));
        //System.out.println(colorLog("[ 橙色 ]", 33, 0));
        //System.out.println(colorLog("[ 蓝色 ]", 34, 0));
        //System.out.println(colorLog("[ 紫色 ]", 35, 0));
        //System.out.println(colorLog("[ 绿色 ]", 36, 0));
        //System.out.println(colorLog("[ 绿色 ]", 41, 0));
    }
}