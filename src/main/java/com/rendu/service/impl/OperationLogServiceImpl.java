package com.rendu.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rendu.mapper.OperationLogMapper;
import com.rendu.model.OperationLog;
import com.rendu.service.OperationLogService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName OperationLogServiceImpl
 * @Description: TODO
 * @Author: li
 * @Date: 2020/4/1 11:48
 * @Version v1.0
 **/
@Service
public class OperationLogServiceImpl extends ServiceImpl<OperationLogMapper, OperationLog> implements OperationLogService {
    
    @Override
    @Cacheable(cacheNames = "operation",key = "'operations'",sync = true)
    public JSONObject getAllOperationLog(Integer pageIndex, Integer pageSize){
        Page<OperationLog> page = new Page<>(pageIndex,pageSize);
        page.addOrder(OrderItem.desc("create_time"));
        IPage<OperationLog> iPage = page(page);
        List<OperationLog> operationLogs = iPage.getRecords();
        long total = iPage.getTotal();
        Map<String,Object> map = new HashMap<>();
        map.put("total",total);
        map.put("rows",operationLogs);
        return new JSONObject(map);
    }
    
    /*重载父类的save方法 方便清理缓存，清理脏数据*/
    @CacheEvict(cacheNames = "operation",key = "'operations'")
    @Override
    public boolean save(OperationLog entity) {
        return super.save(entity);
    }
}
