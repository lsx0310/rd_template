package com.rendu.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @ClassName OperationLog
 * @Description: TODO
 * @Author: li
 * @Date: 2020/4/1 10:28
 * @Version v1.0
 **/

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("operation_log")
public class OperationLog extends Model<OperationLog> {
    
    private static final long serialVersionUID = 1L;
    
    @TableId(value = "operation_id",type = IdType.AUTO)
    private Integer operationId;
    private String userName;
    private String userIp;
    private String requestDesc;
    
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @TableField("create_time")
    private String createTime;
    private String operationText;
    
    
    private static class operationInnerClass{
        private static OperationLog operationLog = new OperationLog();
    }
    
    public static OperationLog getInstance(){
        return operationInnerClass.operationLog;
    }
    
    
}
