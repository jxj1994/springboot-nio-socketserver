package com.jiang.springbootniosocketserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiang.springbootniosocketserver.domain.entity.T_Clint_Connect_Log;
import com.jiang.springbootniosocketserver.mapper.Clint_Connect_LogMapper;
import com.jiang.springbootniosocketserver.service.IClint_Connect_LogMapperService;
import org.springframework.stereotype.Service;

/**
 * @author jiang
 * @description 针对表【Clint_Connect_Log】的数据库操作Service实现
 * @createDate 2022-11-11 15:45:02
 */
@Service
public class Clint_Connect_LogMapperServiceImpl extends ServiceImpl<Clint_Connect_LogMapper, T_Clint_Connect_Log>
        implements IClint_Connect_LogMapperService {

    /**
     * 更新设备状态
     *
     * @param param
     */
    @Override
    public void updateStatus(T_Clint_Connect_Log param) {
        UpdateWrapper<T_Clint_Connect_Log> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("status", param.getStatus()).eq("client_address",param.getClientAddress());
        this.update(updateWrapper);
    }

    /**
     * 更新设备最后通信时间
     *
     * @param param
     */
    @Override
    public void updateLastConnectTime(T_Clint_Connect_Log param) {
        UpdateWrapper<T_Clint_Connect_Log> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("last_connect_time", param.getLastConnectTime()).eq("client_address",param.getClientAddress());
        this.update(updateWrapper);
    }
}




