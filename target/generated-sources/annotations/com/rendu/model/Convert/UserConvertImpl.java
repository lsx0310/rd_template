package com.rendu.model.Convert;

import com.rendu.entity.UserInfo;
import com.rendu.model.UserDto;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-04-26T18:16:03+0800",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_101 (Oracle Corporation)"
)
@Component
public class UserConvertImpl implements UserConvert {

    @Override
    public List<UserDto> entityToDto(List<UserInfo> var1) {
        if ( var1 == null ) {
            return null;
        }

        List<UserDto> list = new ArrayList<UserDto>( var1.size() );
        for ( UserInfo userInfo : var1 ) {
            list.add( entityToDto( userInfo ) );
        }

        return list;
    }

    @Override
    public List<UserInfo> DtoToEntity(List<UserDto> var1) {
        if ( var1 == null ) {
            return null;
        }

        List<UserInfo> list = new ArrayList<UserInfo>( var1.size() );
        for ( UserDto userDto : var1 ) {
            list.add( DtoToEntity( userDto ) );
        }

        return list;
    }

    @Override
    public List<UserDto> entityToDto(Stream<UserInfo> var1) {
        if ( var1 == null ) {
            return null;
        }

        return var1.map( userInfo -> entityToDto( userInfo ) )
        .collect( Collectors.toCollection( ArrayList<UserDto>::new ) );
    }

    @Override
    public List<UserInfo> DtoToEntity(Stream<UserDto> var1) {
        if ( var1 == null ) {
            return null;
        }

        return var1.map( userDto -> DtoToEntity( userDto ) )
        .collect( Collectors.toCollection( ArrayList<UserInfo>::new ) );
    }

    @Override
    public UserInfo DtoToEntity(UserDto Dto) {
        if ( Dto == null ) {
            return null;
        }

        UserInfo userInfo = new UserInfo();

        userInfo.setUserName( Dto.getUserName() );
        userInfo.setGender( Dto.getGender() );
        userInfo.setPhone( Dto.getPhone() );
        userInfo.setInstitute( Dto.getInstitute() );

        return userInfo;
    }

    @Override
    public UserDto entityToDto(UserInfo entity) {
        if ( entity == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setUserName( entity.getUserName() );
        userDto.setGender( entity.getGender() );
        userDto.setPhone( entity.getPhone() );
        userDto.setInstitute( entity.getInstitute() );

        return userDto;
    }
}
