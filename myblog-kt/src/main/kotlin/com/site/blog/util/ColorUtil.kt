package com.site.blog.util

class ColorUtil {
    fun showColor() {
        println("控制台颜色测试：")
        //System.out.println(colorLog("[ 红色 ]", 31, 0));
        //System.out.println(colorLog("[ 黄色 ]", 32, 0));
        //System.out.println(colorLog("[ 橙色 ]", 33, 0));
        //System.out.println(colorLog("[ 蓝色 ]", 34, 0));
        //System.out.println(colorLog("[ 紫色 ]", 35, 0));
        //System.out.println(colorLog("[ 绿色 ]", 36, 0));
        //System.out.println(colorLog("[ 绿色 ]", 41, 0));
    }

    companion object {
        /**
         * @param color   颜色代号：背景颜色代号(41-46)；前景色代号(31-36)
         * @param type    样式代号：0无；1加粗；3斜体；4下划线
         * @param content 要打印的内容
         */
        fun colorLog(content: String?, color: Int, type: Int) {
            val hasType = type != 1 && type != 3 && type != 4
            if (hasType) {
                System.out.printf("\u001B[%dm%s\u001B[0m%n", color, content)
            } else {
                System.out.printf("\u001B[%d;%dm%s\u001B[0m%n", color, type, content)
            }
        }

        fun red(content: String?) {
            colorLog(content, 31, 0)
        }

        @JvmStatic
        fun green(content: String?) {
            colorLog(content, 32, 0)
        }

        fun yellow(content: String?) {
            colorLog(content, 33, 0)
        }

        fun blue(content: String?) {
            colorLog(content, 34, 0)
        }

        fun purple(content: String?) {
            colorLog(content, 35, 0)
        }

        fun cyan(content: String?) {
            colorLog(content, 36, 0)
        }

        fun redBold(content: String?) {
            colorLog(content, 31, 1)
        }

        fun greenBold(content: String?) {
            colorLog(content, 32, 1)
        }

        fun yellowBold(content: String?) {
            colorLog(content, 33, 1)
        }

        fun blueBold(content: String?) {
            colorLog(content, 34, 1)
        }

        fun purpleBold(content: String?) {
            colorLog(content, 35, 1)
        }

        fun cyanBold(content: String?) {
            colorLog(content, 36, 1)
        }

        fun redItalic(content: String?) {
            colorLog(content, 31, 3)
        }

        fun greenItalic(content: String?) {
            colorLog(content, 32, 3)
        }

        fun yellowItalic(content: String?) {
            colorLog(content, 33, 3)
        }

        fun blueItalic(content: String?) {
            colorLog(content, 34, 3)
        }

        fun purpleItalic(content: String?) {
            colorLog(content, 35, 3)
        }

        fun cyanItalic(content: String?) {
            colorLog(content, 36, 3)
        }

        fun redUnderline(content: String?) {
            colorLog(content, 31, 4)
        }

        fun greenUnderline(content: String?) {
            colorLog(content, 32, 4)
        }

        fun yellowUnderline(content: String?) {
            colorLog(content, 33, 4)
        }

        fun blueUnderline(content: String?) {
            colorLog(content, 34, 4)
        }

        fun purpleUnderline(content: String?) {
            colorLog(content, 35, 4)
        }

        fun cyanUnderline(content: String?) {
            colorLog(content, 36, 4)
        }
    }
}