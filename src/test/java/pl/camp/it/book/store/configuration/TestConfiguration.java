package pl.camp.it.book.store.configuration;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import pl.camp.it.book.store.dao.IBookDAO;
import pl.camp.it.book.store.dao.IOrderDAO;
import pl.camp.it.book.store.dao.IUserDAO;
import pl.camp.it.book.store.dao.impl.HibernateBookDAOImplStub;
import pl.camp.it.book.store.dao.impl.HibernateOrderDAOImplStub;
import pl.camp.it.book.store.dao.impl.HibernateUserDAOImplStub;

@Configuration
@ComponentScan(basePackages = {
        "pl.camp.it.book.store.controllers",
        "pl.camp.it.book.store.services",
        "pl.camp.it.book.store.session"
})
public class TestConfiguration {
    /*@Bean
    public IUserDAO userDAO() {
        return Mockito.mock(IUserDAO.class);
    }

    @Bean
    public IBookDAO bookDAO() {
        return Mockito.mock(IBookDAO.class);
    }

    @Bean
    public IOrderDAO orderDAO() {
        return Mockito.mock(IOrderDAO.class);
    }*/
}
