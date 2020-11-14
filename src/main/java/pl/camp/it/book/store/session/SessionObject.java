package pl.camp.it.book.store.session;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import pl.camp.it.book.store.model.Book;
import pl.camp.it.book.store.model.User;

import java.util.List;

@Component
@SessionScope
public class SessionObject {
    private User user = null;

    public boolean isLogged() {
        return !(this.user == null);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
