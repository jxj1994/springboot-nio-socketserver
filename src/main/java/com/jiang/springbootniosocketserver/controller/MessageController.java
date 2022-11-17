package com.jiang.springbootniosocketserver.controller;

import com.jiang.springbootniosocketserver.domain.entity.T_Clint_Connect_Log;
import com.jiang.springbootniosocketserver.domain.vo.ApiResult;
import com.jiang.springbootniosocketserver.domain.vo.VO_SendMsg;
import com.jiang.springbootniosocketserver.service.IClint_Connect_LogMapperService;
import com.jiang.springbootniosocketserver.socket.NIOSocketConnectionMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 消息控制器
 *
 * @author jiang
 */
@RestController
public class MessageController {

    private final NIOSocketConnectionMap connectionMap;

    private final IClint_Connect_LogMapperService service;

    public MessageController(NIOSocketConnectionMap connectionMap, IClint_Connect_LogMapperService service) {
        this.connectionMap = connectionMap;
        this.service = service;
    }


    @GetMapping("/sendMessageAll")
    public ApiResult sendMessage(@RequestParam("msg") String msg) {
        String res = connectionMap.sendAll(msg);
        return ApiResult.ok(res);
    }

    @PostMapping("/sendMessageByIP")
    public ApiResult sendMessageByIP(@RequestParam("param") VO_SendMsg param) {
        String res = connectionMap.sendMessage(param.getIpAddress(), param.getMessage());
        return ApiResult.ok(res);
    }

    /**
     * 查询日志
     * @param ip
     * @return
     */
    @GetMapping("/query")
    public ApiResult<T_Clint_Connect_Log> query(@RequestParam("ip") String ip) {
        T_Clint_Connect_Log log = service.getById(ip);
        return ApiResult.ok(log);
    }


}
