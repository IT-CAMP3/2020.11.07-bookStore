package pl.camp.it.book.store.services;

import pl.camp.it.book.store.model.User;
import pl.camp.it.book.store.model.view.ChangePassData;
import pl.camp.it.book.store.model.view.UserRegistrationData;
import java.util.List;

public interface IUserService {
    User authenticate(User user);
    User updateUserData(User user);
    User updateUserPass(ChangePassData changePassData);
    boolean registerUser(UserRegistrationData userRegistrationData);
    List<User> getAllUsers();
}
