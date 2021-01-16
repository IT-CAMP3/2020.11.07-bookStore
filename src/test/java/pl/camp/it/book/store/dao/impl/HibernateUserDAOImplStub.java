package pl.camp.it.book.store.dao.impl;

import pl.camp.it.book.store.dao.IUserDAO;
import pl.camp.it.book.store.model.User;

import java.util.List;

public class HibernateUserDAOImplStub implements IUserDAO {
    @Override
    public User getUserByLogin(String login) {
        if(login.equals("admin")) {
            User user = new User();
            user.setId(1);
            user.setLogin("admin");
            user.setName("Admin");
            user.setPass("admin");
            user.setRole(User.Role.ADMIN);
            user.setSurname("Admin");

            return user;
        } else {
            return null;
        }
    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public void persistUser(User user) {

    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }
}
