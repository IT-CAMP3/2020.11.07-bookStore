package pl.camp.it.book.store;

import pl.camp.it.book.store.rest.api.model.AddOrderRequest;

public class Main2 {
    public static void main(String[] args) {
        OuterClass outerClass = new OuterClass();

        OuterClass.InnerClass innerClass = new OuterClass.InnerClass();

        AddOrderRequest.AddOrderPositionModel positionModel = new AddOrderRequest.AddOrderPositionModel();
    }
}
