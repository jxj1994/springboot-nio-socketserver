package com.jiang.springbootniosocketserver.domain.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class VO_SendMsg {

    private String ipAddress;
    private String message;

}
