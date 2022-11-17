package com.jiang.springbootniosocketserver.config;


import com.jiang.springbootniosocketserver.config.properties.SocketProperties;
import com.jiang.springbootniosocketserver.socket.server.NIOSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
@Slf4j
public class SocketServerConfig {

    private final SocketProperties properties;

    /**
     *
     * 借助Spring Boot的@Configuration配置在启动项目时启动SockerServer
     * @param properties
     */
    public SocketServerConfig(SocketProperties properties) {
        this.properties = properties;
        try {
            log.info("=========SocketServer启动中！=========");
            this.runServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void runServer() throws IOException {
        if (this.properties == null || properties.getPort() == null) {
            throw new IOException("SocketServer无法启动，配置为空！");
        } else {
            NIOSocketServer server = new NIOSocketServer(this.properties);
            server.run();
        }
    }

}
