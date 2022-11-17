package com.jiang.springbootniosocketserver.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @TableName T_Clint_link_log
 * 记录客户端连接记录
 */
@TableName(value = "Clint_Connect_Log")
@Data
public class T_Clint_Connect_Log implements Serializable {
    /**
     * 台体标识
     */
    @TableId
    private String clientAddress;

    private String status;

    private LocalDateTime lastConnectTime;
}