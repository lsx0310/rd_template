package com.rendu.model.Convert;

import com.rendu.entity.UserInfo;
import com.rendu.model.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


/**
 * @ClassName UserConvert
 * @Description: TODO
 * @Author: li
 * @Date: 2020/4/26 17:42
 * @Version v1.0
 **/
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserConvert extends BaseConvert<UserInfo, UserDto> {
    
    @Override
    UserInfo DtoToEntity(UserDto Dto);
    
    @Override
    UserDto entityToDto(UserInfo entity);
    
    
}
