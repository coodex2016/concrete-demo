package test.org.coodex.concrete.demo.test;

import org.coodex.concrete.accounts.AccountIDImpl;
import org.coodex.concrete.common.*;
import org.coodex.concrete.demo.api.DemoService;
import org.coodex.concrete.test.ConcreteTestCase;
import org.coodex.concrete.test.TestSubjoin;
import org.coodex.concrete.test.TestSubjoinItem;
import org.coodex.concrete.test.TokenID;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:demoService.xml")
public class DemoServiceTest extends ConcreteTestCase {
    @Inject
    private AccountFactory accountFactory;

    @Inject
    private DemoService demoService;

    @Inject
    private Token token;

    private void setUser(String userName, boolean credible) {
        token.setAccount(accountFactory.getAccountByID(new AccountIDImpl(99, userName)));
        token.setAccountCredible(credible);
    }

    private void check(String userName, boolean add, boolean sayHello, boolean randomPlate) {
        try {
            Assert.assertEquals(3, demoService.add(1, 2));
            Assert.assertTrue(add);
        } catch (ConcreteException t) {
            Assert.assertFalse(add);
        }

        try {
            Assert.assertEquals(
                    String.format("Hello %s!", userName),
                    demoService.sayHello(userName));
            Assert.assertTrue(sayHello);
        } catch (ConcreteException t) {
            Assert.assertFalse(sayHello);
        }

        try {
            Assert.assertNull(demoService.randomPlate());
            Assert.assertTrue(randomPlate);
        } catch (ConcreteException t) {
            Assert.assertFalse(randomPlate);
        }
    }

    private void testUser(String userName, boolean credible,
                          boolean add, boolean sayHello, boolean randomPlate) {
        setUser(userName, credible);
        check(userName, add, sayHello, randomPlate);
    }

    @Test
    @TokenID("1")
    @TestSubjoin(@TestSubjoinItem(key = "key", value = "ok"))
    public void testLele() {
        testUser("Lele",
                true, true, true, true);
        // 注意上下两段的第三个boolean的差别
        testUser("Lele",
                false, true, false, true);
    }

    @Test
    @TokenID("2")
    public void testFeifei() {
        testUser("Feifei", true, true, false, true);
        testUser("Feifei", false, true, false, true);
    }

    @Test
    @TokenID("3")
    public void testNana() {
        testUser("Nana", true, true, false, false);
        testUser("Nana", false, true, false, false);
    }

    @Test
    @TokenID("1")
    public void testUserLele() {
        Account account = token.currentAccount();
        if (account instanceof NamedAccount) {
            Assert.assertEquals(((NamedAccount) account).getName(), "Lele");
        } else {
            Assert.assertFalse(true);
        }
    }
}
