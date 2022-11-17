package com.jiang.springbootniosocketserver.socket.server;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * BIO socket服务
 */
@Slf4j
public class BIOSocketServer {

    private int port;

    public Socket socket;
    private ServerSocket serverSocket;

    public BIOSocketServer(int port) {
        this.port = port;
    }

    public void start() {
        log.info("=========socket服务开启中,端口{}==========", port);
        try {
            //SocketChannel channel =SocketChannel.open();
            serverSocket = new ServerSocket(port);
            log.info("=========socket服务已开启,端口{}==========", port);
            while (true) {
                //开始接受消息
                //server.accept();
                socket = serverSocket.accept();
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true)) {
                    String message;
                    while ((message = reader.readLine()) != null) {
                        // 接受消息
                        log.info("Received:" + message);
                        // 返回消息，存在问题，只有当接受后才能主动发送消息
                        writer.println("xxxxxx");
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true)) {
            writer.println("xxxxxx");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 关闭本地socket服务
     */
    public void close() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("SocketServer.close failed.exception:{}", e);
        }
    }

    /**
     * 程序停止时的销毁操作
     */
    public void destroy() {
        log.info("=========开始销毁SocketServer======");
        this.close();
        log.info("=========SocketServer已关闭======");
    }
}
