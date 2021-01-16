package pl.camp.it.book.store.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.camp.it.book.store.dao.IUserDAO;
import pl.camp.it.book.store.model.User;
import pl.camp.it.book.store.model.view.ChangePassData;
import pl.camp.it.book.store.model.view.UserRegistrationData;
import pl.camp.it.book.store.services.IUserService;
import pl.camp.it.book.store.session.SessionObject;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    IUserDAO userDAO;

    @Resource
    SessionObject sessionObject;

    @Override
    public User authenticate(User user) {
        User userFormDatabase = this.userDAO.getUserByLogin(user.getLogin());
        if(userFormDatabase != null && userFormDatabase.getPass().equals(user.getPass())) {
            return userFormDatabase;
        }
        return null;
    }

    @Override
    public User updateUserData(User user) {
        User currentUser = this.sessionObject.getUser();
        currentUser.setName(user.getName());
        currentUser.setSurname(user.getSurname());
        this.userDAO.updateUser(currentUser);
        return currentUser;
    }

    @Override
    public User updateUserPass(ChangePassData changePassData) {
        User user = new User();
        user.setLogin(this.sessionObject.getUser().getLogin());
        user.setPass(changePassData.getPass());
        User authenticatedUser = this.authenticate(user);
        if(authenticatedUser == null) {
            return null;
        }

        authenticatedUser.setPass(changePassData.getNewPass());

        this.userDAO.updateUser(authenticatedUser);

        return authenticatedUser;
    }

    @Override
    public boolean registerUser(UserRegistrationData userRegistrationData) {
        User userFromDatabase = this.userDAO.getUserByLogin(userRegistrationData.getLogin());
        if(userFromDatabase != null) {
            return false;
        }

        User user = new User();
        user.setName(userRegistrationData.getName());
        user.setSurname(userRegistrationData.getSurname());
        user.setLogin(userRegistrationData.getLogin());
        user.setPass(userRegistrationData.getPass());
        user.setRole(User.Role.USER);

        this.userDAO.persistUser(user);
        return true;
    }

    @Override
    public List<User> getAllUsers() {
        return this.userDAO.getAllUsers();
    }

    @Override
    public User getUserById(int id) {
        return this.userDAO.getUserById(id);
    }

    @Override
    public void persistUser(User user) {
        this.userDAO.persistUser(user);
    }

    @Override
    public void updateUser(User user) {
        this.userDAO.updateUser(user);
    }
}
