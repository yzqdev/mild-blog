package com.site.blog.constants

enum class HttpStatusEnum(val status: Int, val content: String) {
    /**
     * 200提示
     */
    OK(200, "成功"), BAD_REQUEST(400, "请求参数有误"), UNAUTHORIZED(401, "登录失败"), INTERNAL_SERVER_ERROR(
        500,
        "服务器遇到了一个未曾预料的状况"
    ),
    BAD_GATEWAY(502, "从上游服务器接收到无效的响应");

}