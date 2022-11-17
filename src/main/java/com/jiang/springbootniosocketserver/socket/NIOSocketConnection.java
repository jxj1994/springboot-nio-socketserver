package com.jiang.springbootniosocketserver.socket;

import com.jiang.springbootniosocketserver.utils.Constant;
import com.jiang.springbootniosocketserver.utils.SpringUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * 封装NIO Socket连接，方便给其他连接使用
 */
@Slf4j
public class NIOSocketConnection {

    public String ipAddress;

    public SocketChannel channel;

    private ByteBuffer wBuffer = ByteBuffer.allocate(1024);

    public NIOSocketConnection(SocketChannel channel) {
        this.channel = channel;
        try {
            InetSocketAddress address = (InetSocketAddress) channel.getRemoteAddress();
            this.ipAddress = address.getAddress().getHostAddress();
        } catch (IOException e) {
            log.error("封装SocketChannel出错，无法获取远程ip地址");
            e.printStackTrace();
        }
    }

    public void putConnection() {
        NIOSocketConnectionMap map = SpringUtils.getBean(NIOSocketConnectionMap.class, "socketConnectionMap");
        map.put(ipAddress, this);
    }

    public void sendMessage(String message) throws IOException {
        if (this.channel.isConnected()) {
            log.info("=========开始发送消息至远端：{}========", this.ipAddress);
            wBuffer.put(message.getBytes(Constant.DefaultChart));
            wBuffer.flip();
            this.channel.write(wBuffer);
            wBuffer.clear();
            log.info("=========向远端发送消息完成，远端ip：{}========", this.ipAddress);
        }
    }
}
