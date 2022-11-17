package com.jiang.springbootniosocketserver.socket.server;


import com.jiang.springbootniosocketserver.config.properties.SocketProperties;
import com.jiang.springbootniosocketserver.socket.SelectorThread;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;

/**
 * @author jiang
 * NIO Socket实现
 */
@Slf4j
public class NIOSocketServer {

    public SocketProperties properties;

    public Selector selector;

    public NIOSocketServer(SocketProperties properties) {
        this.properties = properties;
    }

    public void run() throws IOException {
        if (null == this.properties || null == this.properties.getPort()) {
            throw new IOException("Socket配置为空，无法启动示例");
        }
        log.info("=========Socket非堵塞BIO实例准备启动，监听端口{}==========", this.properties.getPort());
        // 获取socket通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(this.properties.getPort()));
        //设置为非堵塞
        serverSocketChannel.configureBlocking(false);
        //打开nio模式通信监听，并使用selector通信通道
        selector = Selector.open();
        // 将通道注册到选择器上，并且指定监听接收事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        log.info("=========Socket非堵塞BIO实例已启动，通道已注册==========");
        // 持续监听,轮询获取事件,这部分应该放入单独线程
        SelectorThread selectorThread = new SelectorThread(selector, serverSocketChannel);
        Thread thread = new Thread(selectorThread);
        thread.start();
        log.info("=========SelectorThread开始启动==========");
    }


}
