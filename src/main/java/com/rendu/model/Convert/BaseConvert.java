package com.rendu.model.Convert;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.MapperConfig;

import java.util.List;
import java.util.stream.Stream;

/**
 * @ClassName BaseConvert
 * @Description: TODO
 * @Author: li
 * @Date: 2020/4/26 11:27
 * @Version v1.0
 **/
@MapperConfig
public interface BaseConvert<S,D> {

    /**
     * @Description:  映射同名属性
     * @Author: Li
     * @param entity: 源实体对象
     * @return: D
     **/
    D entityToDto(S entity);
    
    /**
     * @Description:  反向，映射同名属性
     * @Author: Li
     * @param Dto: Dto对象
     * @return: S
     **/
    @InheritInverseConfiguration(name = "source2Target")
    S DtoToEntity(D Dto);
    
    /**
     * @Description:
     * @Author: Li
     * @param var1:  源实体的列表
     * @return: java.util.List<D>
     **/
    @InheritConfiguration(name = "entityToDto")
    List<D> entityToDto(List<S> var1);
    
    
    /**
     * @Description:
     * @Author: Li
     * @param var1: Dto对象的列表
     * @return: java.util.List<S>
     **/
    @InheritConfiguration(name = "DtoTOEntity")
    List<S> DtoToEntity(List<D> var1);
    
    /**
     * @Description:
     * @Author: Li
     * @param var1: 映射同名属性，集合流形式
     * @return: java.util.List<D>
     **/
    List<D> entityToDto(Stream<S> var1);
    
    
    /**
     * @Description:
     * @Author: Li
     * @param var1: 反向，映射同名属性，集合流形式
     * @return: java.util.List<S>
     **/
    List<S> DtoToEntity(Stream<D> var1);
    
    
    
}
