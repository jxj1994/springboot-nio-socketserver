package com.jiang.springbootniosocketserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiang.springbootniosocketserver.domain.entity.T_Clint_Connect_Log;

/**
 * @author jiang
 * @description 针对表【Clint_Connect_Log】的数据库操作Service
 * @createDate 2022-11-11 15:45:02
 */
public interface IClint_Connect_LogMapperService extends IService<T_Clint_Connect_Log> {

    /**
     * 更新设备状态
     *
     * @param param
     */
    void updateStatus(T_Clint_Connect_Log param);

    /**
     * 更新最后通信结束时间
     *
     * @param param
     */
    void updateLastConnectTime(T_Clint_Connect_Log param);

}
