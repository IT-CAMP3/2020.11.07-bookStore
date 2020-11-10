package pl.camp.it.book.store.database.impl;

import org.springframework.stereotype.Component;
import pl.camp.it.book.store.database.IUserRepository;
import pl.camp.it.book.store.model.User;

import java.util.ArrayList;
import java.util.List;

@Component
public class ListUserRepositoryImpl implements IUserRepository {

    private final List<User> userList = new ArrayList<>();

    public ListUserRepositoryImpl() {
        this.userList.add(new User("admin", "admin"));
    }

    @Override
    public boolean authenticate(User user) {
        for(User userFromDatabase : this.userList) {
            if(userFromDatabase.getLogin().equals(user.getLogin())) {
                if(userFromDatabase.getPass().equals(user.getPass())) {
                    return true;
                } else {
                    return false;
                }
            }
        }

        return false;
    }
}
