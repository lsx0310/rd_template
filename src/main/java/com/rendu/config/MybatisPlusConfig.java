package com.rendu.config;


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName MybatisPlusConfig
 * @Description: TODO
 * @Author: li
 * @Date: 2020/2/14 13:34
 * @Version v1.0
 **/
@Configuration
@MapperScan("com.rendu.mapper")
@ConditionalOnClass(value = {PaginationInterceptor.class})
public class MybatisPlusConfig {

    /*
     * 分页查询
     * */
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor().setDbType(DbType.MYSQL).setCountSqlParser(
                new JsqlParserCountOptimize());
    }
}

