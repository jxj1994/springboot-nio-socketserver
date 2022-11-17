package com.jiang.springbootniosocketserver.socket.server;

import com.jiang.springbootniosocketserver.socket.NIOSocketConnectionMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author
 * 连接的心跳检测
 */
@Component
@Slf4j
public class HeartBeatTest {

    private NIOSocketConnectionMap map;

    public HeartBeatTest(NIOSocketConnectionMap map) {
        this.map= map;
    }

    /**
     * 定时心跳检查,每30秒检查一次连接心跳
     */
    @Scheduled(cron = "*/60 * * * * ?")
    public void heartBeatTest(){
        log.info("=========开始检查Socket连接心跳==============");
        map.forEach((key,value) -> {
            try {
                value.sendMessage("connection test...");
            } catch (IOException e) {
                log.error("客户端{}的连接已失效，将从连接Map中去除！",key);
                map.remove(key);
                // TODO 调用服务记录状态和最后通信时间
                e.printStackTrace();
            }
        });
    }
}
