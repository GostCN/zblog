
package com.cchcz.blog.config.websocket;

import com.cchcz.blog.event.EventBus;
import com.cchcz.blog.event.WebSocketVisitEventConsumer;
import com.cchcz.blog.util.WebSocketUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@ServerEndpoint(value = "/websocket")
@Component
public class WebSocket {

    /**
     * 初始在线人数
     */
    private static AtomicInteger onlineCount = new AtomicInteger(0);
    /**
     * 线程安全的socket集合
     */
    private static CopyOnWriteArraySet<Session> webSocketSet = new CopyOnWriteArraySet<>();
    /**
     *访问人经纬度
     */
    private static WebSocketVisitEventConsumer webSocketVisitEventConsumer = new WebSocketVisitEventConsumer(webSocketSet);

    //static {
    //    EventBus.getInstance().register(webSocketVisitEventConsumer);
    //}

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        webSocketSet.add(session);
        onlineCount.incrementAndGet();
        log.info("有链接加入，当前在线人数为: {}", getOnlineCount());
        WebSocketUtil.broadcast(getOnlineCount(), webSocketSet);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        onlineCount.decrementAndGet();
        log.info("有链接关闭,当前在线人数为: {}", getOnlineCount());
        WebSocketUtil.broadcast(getOnlineCount(), webSocketSet);
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("{}来自客户端的消息:{}", session.getId(), message);
        WebSocketUtil.sendMessage(message, session);
    }

    @OnError
    public void onError(Session session, Throwable thr) {

    }

    private String getOnlineCount() {
        return Integer.toString(onlineCount.get());
    }
}
