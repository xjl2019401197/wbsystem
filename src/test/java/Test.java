
import com.example.wbsystem_ssm.dao.UserDao;
import com.example.wbsystem_ssm.entity.User;
import org.junit.runner.RunWith;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
public class Test {


@org.junit.Test
    public void test(){
    System.out.println("password:"+new BCryptPasswordEncoder().encode("cxy"));
}
}
