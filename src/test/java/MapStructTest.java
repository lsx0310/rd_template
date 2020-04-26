import com.rendu.Application;
import com.rendu.entity.UserInfo;
import com.rendu.model.Convert.UserConvert;
import com.rendu.model.UserDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @ClassName MapStructTest
 * @Description: TODO
 * @Author: li
 * @Date: 2020/4/26 17:47
 * @Version v1.0
 **/

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class MapStructTest {

    @Autowired
    private UserConvert userConvert;
    
    @Test
    public void e2dTest(){
        UserInfo userInfo = UserInfo.builder()
                .userName("李四")
                .gender("男")
                .department("科技部")
                .institute("光电")
                .phone("12312312")
                .password("123123")
                .roleId("1")
                .userId(1)
                .build();
        
        UserDto userDto = userConvert.entityToDto(userInfo);
        System.out.println(userDto);
    }
}
