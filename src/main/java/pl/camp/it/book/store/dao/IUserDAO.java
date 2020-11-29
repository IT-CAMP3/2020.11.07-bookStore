package pl.camp.it.book.store.dao;

import pl.camp.it.book.store.model.User;

public interface IUserDAO {
    User getUserByLogin(String login);
    void updateUser(User user);
    void persistUser(User user);
}
