package com.rendu.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @ClassName UserDto
 * @Description: TODO
 * @Author: li
 * @Date: 2020/4/26 17:42
 * @Version v1.0
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    
    private String userName;
    
    private String gender;
    
    private String phone;
    
    private String institute;
}
