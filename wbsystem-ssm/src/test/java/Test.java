
import com.example.wbsystem_ssm.dao.UserDao;
import com.example.wbsystem_ssm.entity.User;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description:
 *
 * @Author: kk(专业bug开发)
 * DateTime: 2022-02-11 17:38
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:SpringApplicationContext.xml"})
public class Test {
    @Resource
    private UserDao userDao;



    //测试MP更新数据
    @org.junit.Test
    public void testUpdate(){
        User user = userDao.selectById(1);   //MP内置的selectById方法
        System.out.println(user);
    }


}
