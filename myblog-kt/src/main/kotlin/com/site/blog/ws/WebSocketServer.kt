package com.site.blog.ws

import cn.hutool.json.JSONUtil
import cn.hutool.log.LogFactory
import org.springframework.stereotype.Component
import java.io.IOException
import java.util.concurrent.CopyOnWriteArraySet
import javax.websocket.*
import javax.websocket.server.PathParam
import javax.websocket.server.ServerEndpoint

@ServerEndpoint("/socket/{sid}")
@Component
class WebSocketServer {
    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private var session: Session? = null

    /**
     * 接收sid
     */
    private var sid = ""

    /**
     * 连接建立成功调用的方法 */
    @OnOpen
    fun onOpen(session: Session?, @PathParam("sid") sid: String) {
        this.session = session
        //加入set中
        webSocketSet.add(this)
        //在线数加1
        addOnlineCount()
        log.info("有新窗口开始监听:" + sid + ",当前在线人数为" + onlineCount)
        this.sid = sid
        try {
            sendMessage("连接成功")
        } catch (e: IOException) {
            log.error("websocket IO异常")
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    fun onClose() {
        webSocketSet.remove(this)
        subOnlineCount()
        log.info("有一连接关闭！当前在线人数为" + onlineCount)
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    fun onMessage(message: String, session: Session?) {
        log.info("收到来自窗口" + sid + "的信息:" + message)
        for (item in webSocketSet) {
            try {
                item.sendMessage(message)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    /**
     *
     * @param session
     * @param error
     */
    @OnError
    fun onError(session: Session?, error: Throwable) {
        log.error("发生错误")
        error.printStackTrace()
    }

    /**
     * 实现服务器主动推送
     */
    @Throws(IOException::class)
    fun sendMessage(message: String?) {
        session!!.basicRemote.sendText(message)
    }

    /**
     * 实现服务器主动推送
     */
    @Throws(IOException::class)
    fun sendMessage(message: SocketMessage?) {
        session!!.basicRemote.sendText(JSONUtil.toJsonStr(message))
    }

    companion object {
        var log = LogFactory.get(WebSocketServer::class.java)

        /**
         * 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
         */
        @get:Synchronized
        var onlineCount = 0
            private set

        /**
         * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
         */
        private val webSocketSet = CopyOnWriteArraySet<WebSocketServer>()

        /**
         * 群发自定义消息
         */
        @Throws(IOException::class)
        fun sendInfo(message: SocketMessage, @PathParam("sid") sid: String?) {
            log.info("推送消息到窗口$sid，推送内容:$message")
            for (item in webSocketSet) {
                try {
                    if (sid == null) {
                        item.sendMessage(message)
                    } else if (item.sid == sid) {
                        item.sendMessage(message)
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

        @Synchronized
        fun addOnlineCount() {
            onlineCount++
        }

        @Synchronized
        fun subOnlineCount() {
            onlineCount--
        }
    }
}