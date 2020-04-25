package com.rendu.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rendu.model.OperationLog;

/**
 * @ClassName OperationLogService
 * @Description: TODO
 * @Author: li
 * @Date: 2020/4/1 11:46
 * @Version v1.0
 **/

public interface OperationLogService extends IService<OperationLog> {
    JSONObject getAllOperationLog(Integer pageIndex, Integer pageSize);
}
