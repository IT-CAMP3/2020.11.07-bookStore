package pl.camp.it.book.store.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.camp.it.book.store.configuration.TestConfiguration;
import pl.camp.it.book.store.dao.IBookDAO;
import pl.camp.it.book.store.dao.IOrderDAO;
import pl.camp.it.book.store.dao.IUserDAO;
import pl.camp.it.book.store.model.User;
import pl.camp.it.book.store.session.SessionObject;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfiguration.class})
@WebMvcTest
public class CommonControllerTest {

    @MockBean
    IUserDAO userDAO;

    @MockBean
    IBookDAO bookDAO;

    @MockBean
    IOrderDAO orderDAO;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    SessionObject sessionObject;

    @Test
    public void mainRedirectTest() throws Exception {
        this.mockMvc.perform(get("/")).andExpect(status().is3xxRedirection());
    }

    @Test
    public void mainTest() throws Exception {
        Mockito.when(this.sessionObject.isLogged()).thenReturn(true);
        Mockito.when(this.sessionObject.getUser()).thenReturn(generateFakeAdmin());
        Mockito.when(this.sessionObject.getFilter()).thenReturn("asdf");
        Mockito.when(this.sessionObject.getBasket()).thenReturn(new ArrayList<>());

        this.mockMvc.perform(get("/main"))
                .andExpect(status().isOk())
                .andExpect(view().name("main"))
                .andExpect(model().attribute("filter", "asdf"));
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
