package com.jiang.springbootniosocketserver.socket;

import com.jiang.springbootniosocketserver.utils.Constant;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Set;

/**
 * @author jiang
 * 使用单独线程执行NIO中的selector
 */
@Slf4j
public class SelectorThread implements Runnable {

    private Selector selector;

    private ServerSocketChannel socketChannel;

    public SelectorThread(Selector selector, ServerSocketChannel socketChannel) {
        this.selector = selector;
        this.socketChannel = socketChannel;
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (selector.select() > 0) {
                    // 获取选择器中所有注册通道的就绪事件
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    for (SelectionKey key : selectionKeys) {
                        //handler(key);
                        // 判断事件类型
                        if (key.isAcceptable()) {
                            // 每有客户端连接，即注册通信信道为可读
                            // 获取当前客户端通道
                            log.info("=========客户端连接进入=========");
                            SocketChannel channel = socketChannel.accept();
                            channel.configureBlocking(false);
                            channel.register(selector, SelectionKey.OP_READ);
                            NIOSocketConnection connection = new NIOSocketConnection(channel);
                            connection.putConnection();
                        } else if (key.isReadable()) {
                            handler((SocketChannel) key.channel());
                        } else if (key.isConnectable()) {
                            log.info("=========其他事件=======");
                        }
                    }
                    selectionKeys.clear();
                } else {
                    log.info("=========当前无连接==========");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 处理可读事件
     *
     * @param socketChannel
     */
    private void handler(SocketChannel socketChannel) throws IOException {
        ByteBuffer rBuffer = ByteBuffer.allocate(1024);
        Charset cs = Constant.DefaultChart;
        String requestMsg;
        rBuffer.clear();
        int count = socketChannel.read(rBuffer);
        // 读取数据
        if (count > 0) {
            rBuffer.flip();
            requestMsg = String.valueOf(cs.decode(rBuffer).array());
            log.info("客户端消息：{}", requestMsg);
        }
        rBuffer.clear();
    }
}
