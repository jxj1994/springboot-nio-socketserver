package com.jiang.springbootniosocketserver.socket.enums;

import lombok.Getter;

@Getter
public enum SocketEnum {

    SUCCESS(200, "发送成功"),
    FAULT(4000, "发送失败"),
    FAULT_CLIENT(4004, "客户端连接已断开");


    private final int status_code;

    private final String msg;

    SocketEnum(int code, String msg) {
        this.status_code = code;
        this.msg = msg;
    }
}
