package com.example.ProducerConsumer.ProducerConsumer.MementoDP;

import com.example.ProducerConsumer.ProducerConsumer.Components.Manager;

import java.util.List;

public class AddingProductsInRestart extends AddingProducts implements Runnable
{
    private final List<AddProductToRootRequest> Requests;

    public AddingProductsInRestart(Manager m, int numberOfProducts, List<AddProductToRootRequest> reuqests)
    {
        super(m, numberOfProducts);
        this.Requests = reuqests;
    }

    @Override
    public void run()
    {
        for (AddProductToRootRequest request : this.Requests)
            request.RunRequest();
    }
}
