package com.jiang.springbootniosocketserver.socket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author jiang
 * 将所有连接都归集到一个hashmap中，并且是由spring管理，方便其他地方调用
 */
@Slf4j
@Component(value = "socketConnectionMap")
public class NIOSocketConnectionMap extends HashMap<String, NIOSocketConnection> {

//    public void newConnection(String ipaddress, NIOSocketConnection newConnection){
//        this.forEach((key,value) -> {
//            if (key.equals(ipaddress)){
//                this.replace(ipaddress,newConnection);
//            }
//        });
//    }

    /**
     * 对所有连接发送消息
     *
     * @param msg
     * @return
     */
    public String sendAll(String msg) {
        StringBuffer result = new StringBuffer();
        StringBuffer faultIp = new StringBuffer();
        AtomicInteger i = new AtomicInteger();
        result.append("向").append(this.size()).append("个客户端发送消息，");
        this.forEach((key, value) -> {
            try {
                // 不可连接时去掉连接
                if (!value.channel.isConnected()) {
                    this.remove(key);
                    faultIp.append(value.ipAddress).append(";");
                }
                value.sendMessage(msg);
            } catch (IOException e) {
                faultIp.append(value.ipAddress).append(";");
                i.addAndGet(1);
                log.error("=========向所有客户端发送消息时出现失败情况，未能向{}发送消息=======", value.ipAddress);
            }
        });
        result.append("成功").append(this.size() - i.get()).append("个,失败ip为").append(faultIp);
        return result.toString();
    }

    public String sendMessage(String addr, String msg) {
        StringBuffer result = new StringBuffer();
        this.forEach((key, value) -> {
            if (key.equals(addr)) {
                // 不可连接时去掉连接
                if (!value.channel.isConnected()) {
                    this.remove(key);
                    result.append("无法连接客户端，客户端可能已掉线！");
                }
                try {
                    value.sendMessage(msg);
                    result.append("成功发送至：" + value.ipAddress);
                } catch (IOException e) {
                    result.append("向：" + value.ipAddress + "发送失败");
                    e.printStackTrace();
                }
            }
        });
        if (!(result.length() > 0)) {
            result.append("未找到连接，客户端可能已掉线！");
        }
        return result.toString();
    }
}
