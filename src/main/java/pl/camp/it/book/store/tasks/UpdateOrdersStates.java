package pl.camp.it.book.store.tasks;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class UpdateOrdersStates {

    @Scheduled(fixedRate = 1000 * 5)
    public void updateOrders() {
        //wyciaganie z bazy orderow
        //zmiana stanow orderow
        //zapis zaktualizowanych orderow na bazie
        System.out.println("Jakies zadanie !!");
    }
}
