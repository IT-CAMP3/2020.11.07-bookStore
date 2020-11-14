package pl.camp.it.book.store.database;

import pl.camp.it.book.store.model.User;

public interface IUserRepository {
    User authenticate(User user);
    User updateUserData(User user);
    User updateUserPass(User user);
}
