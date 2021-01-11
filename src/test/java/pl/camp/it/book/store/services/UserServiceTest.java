package pl.camp.it.book.store.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import pl.camp.it.book.store.configuration.TestConfiguration;
import pl.camp.it.book.store.dao.IBookDAO;
import pl.camp.it.book.store.dao.IOrderDAO;
import pl.camp.it.book.store.dao.IUserDAO;
import pl.camp.it.book.store.model.User;
import pl.camp.it.book.store.model.view.ChangePassData;
import pl.camp.it.book.store.model.view.UserRegistrationData;
import pl.camp.it.book.store.session.SessionObject;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfiguration.class})
@WebAppConfiguration
public class UserServiceTest {

    @Autowired
    IUserService userService;

    @MockBean
    IUserDAO userDAO;

    @MockBean
    IOrderDAO orderDAO;

    @MockBean
    IBookDAO bookDAO;

    @Resource
    SessionObject sessionObject;

    @Before
    public void setUpMocks() {
        Mockito.when(this.userDAO.getUserByLogin(Mockito.anyString())).thenReturn(null);
        Mockito.when(this.userDAO.getUserByLogin("admin")).thenReturn(generateFakeAdmin());
    }

    @Test
    public void authenticateCorrectLoginAndPassTest() {
        User user = new User();
        user.setLogin("admin");
        user.setPass("admin");

        User expectedUser = new User();
        expectedUser.setId(1);
        expectedUser.setLogin("admin");
        expectedUser.setName("Admin");
        expectedUser.setPass("admin");
        expectedUser.setRole(User.Role.ADMIN);
        expectedUser.setSurname("Admin");

        User resultUser = this.userService.authenticate(user);

        Assert.assertEquals(expectedUser, resultUser);
    }

    @Test
    public void authenticateIncorrectLoginTest() {
        User user = new User();
        user.setLogin("lakshjgdflk");
        user.setPass("ksauydhgfka");

        User resultUser = this.userService.authenticate(user);

        Assert.assertNull(resultUser);
    }

    @Test
    public void authenticateIncorrectPasswordTest() {
        User user = new User();
        user.setLogin("admin");
        user.setPass("ksauydhgfka");

        User resultUser = this.userService.authenticate(user);

        Assert.assertNull(resultUser);
    }

    @Test
    public void updateUserDataTest() {
        User user = new User();
        user.setName("mateusz");
        user.setSurname("bereda");
        User currentUser = generateFakeAdmin();
        this.sessionObject.setUser(currentUser);

        User updateResult = this.userService.updateUserData(user);

        Assert.assertEquals(user.getName(), updateResult.getName());
        Assert.assertEquals(user.getSurname(), updateResult.getSurname());
        Assert.assertEquals(currentUser.getId(), updateResult.getId());
        Assert.assertEquals(currentUser.getLogin(), updateResult.getLogin());
        Assert.assertEquals(currentUser.getPass(), updateResult.getPass());
        Assert.assertEquals(currentUser.getRole(), updateResult.getRole());
        Mockito.verify(this.userDAO, Mockito.times(1)).updateUser(Mockito.any());
    }

    @Test
    public void updateUserPassWithCorrectAuthentication() {
        ChangePassData changePassData = new ChangePassData();
        changePassData.setPass("admin");
        changePassData.setNewPass("admin2");
        changePassData.setRepeatedNewPass("admin2");
        User currentUser = generateFakeAdmin();
        this.sessionObject.setUser(currentUser);

        User updateResult = this.userService.updateUserPass(changePassData);

        Assert.assertEquals(currentUser.getId(), updateResult.getId());
        Assert.assertEquals(currentUser.getName(), updateResult.getName());
        Assert.assertEquals(currentUser.getSurname(), updateResult.getSurname());
        Assert.assertEquals(currentUser.getLogin(), updateResult.getLogin());
        Assert.assertEquals(changePassData.getNewPass(), updateResult.getPass());
        Assert.assertEquals(currentUser.getRole(), updateResult.getRole());
        Mockito.verify(this.userDAO, Mockito.times(1)).updateUser(Mockito.any());
    }

    @Test
    public void updateUserPassWithIncorrectAuthentication() {
        ChangePassData changePassData = new ChangePassData();
        changePassData.setPass("incorrectPassword");
        changePassData.setNewPass("asdfasdf");
        changePassData.setRepeatedNewPass("asdfasdf");
        User currentUser = generateFakeAdmin();
        this.sessionObject.setUser(currentUser);

        User updateResult = this.userService.updateUserPass(changePassData);

        Assert.assertNull(updateResult);
        Mockito.verify(this.userDAO, Mockito.never()).updateUser(Mockito.any());
    }

    @Test
    public void registerUsedLoginUser() {
        UserRegistrationData userRegistrationData = new UserRegistrationData();
        userRegistrationData.setLogin("admin");
        userRegistrationData.setPass("laksjdhfhas");
        userRegistrationData.setRepeatedPass("laksjdhfhas");
        userRegistrationData.setSurname("lksajdhgfkkas");
        userRegistrationData.setName("aslkhjdgfklas");

        boolean registrationResult = this.userService.registerUser(userRegistrationData);

        Assert.assertFalse(registrationResult);
    }

    @Test
    public void registerUser() {
        UserRegistrationData userRegistrationData = new UserRegistrationData();
        userRegistrationData.setLogin("test");
        userRegistrationData.setPass("test");
        userRegistrationData.setRepeatedPass("test");
        userRegistrationData.setSurname("Testowy");
        userRegistrationData.setName("Testowy");

        boolean registrationResult = this.userService.registerUser(userRegistrationData);

        Assert.assertTrue(registrationResult);
        Mockito.verify(this.userDAO, Mockito.times(1)).persistUser(Mockito.any());
    }

    private User generateFakeAdmin() {
        User user = new User();
        user.setId(1);
        user.setLogin("admin");
        user.setName("Admin");
        user.setPass("admin");
        user.setRole(User.Role.ADMIN);
        user.setSurname("Admin");

        return user;
    }
}
