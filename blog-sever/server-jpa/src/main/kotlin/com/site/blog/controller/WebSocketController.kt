package com.site.blog.controller

import com.site.blog.ws.SocketMessage
import com.site.blog.ws.WebSocketServer
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.servlet.ModelAndView
import java.io.IOException
import java.time.LocalDate

@Controller
@RequestMapping("/socket")
class WebSocketController {
    /**
     * 页面请求
     * @param cid
     * @return
     */
    @GetMapping("/{cid}")
    fun socket(@PathVariable cid: String?): ModelAndView {
        val mav = ModelAndView("/socket")
        mav.addObject("cid", cid)
        return mav
    }

    /**
     * 推送数据接口
     * @param cid
     * @param message
     * @return
     */
    @ResponseBody
    @RequestMapping("/send")
    fun pushToWeb(cid: String, message: String?): String {
        try {
            val newMessage = SocketMessage(message, LocalDate.now())
            WebSocketServer.sendInfo(newMessage, cid)
        } catch (e: IOException) {
            e.printStackTrace()
            return cid + "#" + e.message
        }
        return cid
    }
}