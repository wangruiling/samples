package org.wrl.websocket.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * 聊天服务器类
 * @author: wangrl
 * @Date: 2015-12-10 10:51
 */
@ServerEndpoint("/websocket")
public class ChatServer {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");	// 日期格式化
    @OnOpen
    public void open(Session session) {
        // 添加初始化操作
        System.out.println("初始化操作");
    }

    /**
     * 接受客户端的消息，并把消息发送给所有连接的会话
     * @param message 客户端发来的消息
     * @param session 客户端的会话
     */
    @OnMessage
    public void getMessage(String message, Session session) {
        // 把客户端的消息解析为JSON对象
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ObjectNode rootNode = (ObjectNode) objectMapper.readTree(message);
            // 在消息中添加发送日期
            rootNode.put("date", DATE_FORMAT.format(new Date()));

            // 把消息发送给所有连接的会话
            for (Session openSession : session.getOpenSessions()) {
                // 添加本条消息是否为当前会话本身发的标志
                rootNode.put("isSelf", openSession.equals(session));
                // 发送JSON格式的消息
                openSession.getAsyncRemote().sendText(rootNode.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void close() {
        // 添加关闭会话时的操作
        System.out.println("关闭会话");
    }
    @OnError
    public void error(Throwable t) {
        // 添加处理错误的操作
        System.out.println("处理错误");
    }
}
