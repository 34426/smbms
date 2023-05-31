import com.igeek.mapper.BillMapper;
import com.igeek.mapper.RoleMapper;
import com.igeek.mapper.UserMapper;
import com.igeek.pojo.Bill;
import com.igeek.pojo.Role;
import com.igeek.pojo.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

;import java.util.Date;
import java.util.List;

public class MyTest {

    @Test
    public void test1() {
        ApplicationContext context = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        UserMapper bean = context.getBean(UserMapper.class);
        User byId = bean.findById(1);
        System.out.println(byId);
    }

    @Test
    public void test2() {
        ApplicationContext context = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        BillMapper bean = context.getBean(BillMapper.class);
        Bill byId = bean.findById(1);
        System.out.println(byId);
    }

    @Test
    public void test3() {
        ApplicationContext context = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        UserMapper bean = context.getBean(UserMapper.class);
        User byId = bean.findById(2);
        System.out.println(byId);
    }

    @Test
    public void test4() {
        ApplicationContext context = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        RoleMapper bean = context.getBean(RoleMapper.class);
        Role byId = bean.findById(1);
        System.out.println(byId);
    }

    @Test
    public void test5() {
        ApplicationContext context = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        UserMapper bean = context.getBean(UserMapper.class);
        int userByRoleIdAndUsername = bean.findUserCountByRoleIdAndUsername(0, "李");
        System.out.println(userByRoleIdAndUsername);
    }

    @Test
    public void test6() {
        ApplicationContext context = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        UserMapper bean = context.getBean(UserMapper.class);
        List<User> userByRoleIdAndUsernameAndPageLimit = bean.findUserByRoleIdAndUsernameAndPageLimit(0, null, 0, 5);
        for (User user : userByRoleIdAndUsernameAndPageLimit) {
            System.out.println(user);
        }
    }

    @Test
    public void test7() {
        ApplicationContext context = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        UserMapper bean = context.getBean(UserMapper.class);
        User user = new User();
        user.setUserCode("gaojiaqian");
        user.setUserName("高佳倩");
        user.setUserPassword("11111111");
        user.setGender(1);
        user.setBirthday(new Date());
        user.setPhone("11111111");
        user.setAddress("江苏");
        user.setUserRole(2L);
        user.setCreatedBy(1L);
        user.setCreationDate(new Date());
        System.out.println("======================>"+user);
        bean.insertUser(user);
    }

    @Test
    public void test8(){
        java.sql.Date date = new java.sql.Date(new Date().getTime());
        System.out.println(date);
    }
}
