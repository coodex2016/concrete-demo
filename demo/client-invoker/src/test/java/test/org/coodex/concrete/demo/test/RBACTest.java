package test.org.coodex.concrete.demo.test;

import org.coodex.concrete.Client;
import org.coodex.concrete.accounts.simple.api.Login;
import org.coodex.concrete.common.ConcreteException;
import org.coodex.concrete.demo.api.DemoService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RBACTest {

    private Login login;
    private DemoService demoService;

    @Before
    public synchronized void init(){
        if(login == null){
            login = Client.getInstance(Login.class, "demo");
            demoService = Client.getInstance(DemoService.class, "demo");
        }
    }

    /**
     * @param user
     * @param add add方法是否可用
     * @param sayHello sayHello方法是否可用
     * @param randomPlate randomPlate方法是否可用
     */
    private synchronized void check(String user, boolean add, boolean sayHello, boolean randomPlate){
        login.login(user,"123456",null);
        try {
            try {
                demoService.add(1, 2);
                Assert.assertTrue(add);
            } catch (ConcreteException e) {
                Assert.assertTrue(!add);
            }

            try {
                demoService.sayHello(user);
                Assert.assertTrue(sayHello);
            } catch (ConcreteException e) {
                Assert.assertTrue(!sayHello);
            }

            try {
                demoService.randomPlate();
                Assert.assertTrue(randomPlate);
            } catch (ConcreteException e) {
                Assert.assertTrue(!randomPlate);
            }
        }finally {
            login.logout();
        }
    }

    @Test
    public void testLele(){
        check("Lele", true, true, true );
    }

    @Test
    public void testFeifei(){
        check("Feifei", true, false, true );
    }

    @Test
    public void testNama(){
        check("Nana", true, false, false );
    }
}


