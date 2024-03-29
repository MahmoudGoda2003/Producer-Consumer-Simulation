package com.example.ProducerConsumer.ProducerConsumer.Components;

public class Machine extends Node implements Runnable, SubjectOfObserver
{
    private Thread mythread;
    private Product myProduct;
    private final long MachineTimeInMilliseconds;

    public Machine(String id, long machineTime)
    {
        super(id);
        this.MachineTimeInMilliseconds = machineTime;

        mythread = new Thread(this, "Thread " + this.toString());
    }

    public Machine(String id)
    {
        super(id);
        this.MachineTimeInMilliseconds = Time.GetRandomTimeInMilliseconds();
        //this.MachineTimeInMilliseconds = 5000;
        mythread = new Thread(this);
    }

    public void SetProduct(Product product)
    {
        this.myProduct = product;
    }

    public void HandleRequest(Product product)
    {
        this.SetProduct(product);
        this.GiveSameColorAsProduct();

        mythread.start();
    }

    @Override
    public int GetProductsNo() {
        return -1;
    }

    @Override
    public void run()
    {
        this.PrintStartingObjectMessage();
        try
        {
            Thread.sleep(this.MachineTimeInMilliseconds);
        }
        catch(Exception e)
        {}

        this.PrintFinishingObjectMessage();
        this.AfterOperationgOnProduct();
    }

    private void AfterOperationgOnProduct()
    {
        this.SetDefaultColor();
        this.SendObjectToNextQueuer();
        this.ClearProductAndGetReady();
        this.NotifyObservers();
    }

    private void SendObjectToNextQueuer()
    {
        Queuer queuer = (Queuer) this.NextNodes.get(0);
        queuer.HandleProduct(this.myProduct);
    }

    @Override
    public void NotifyObservers()
    {
        for (Node node : this.PreviousNodes)
        {
            Queuer queuer = (Queuer) node;
            Product product = queuer.CheckQueueNotEmptyAndGetProduct();
            // product may be null if queue is empty
            if (product != null)
            {
                this.HandleRequest(product);
                return;
            }
        }
    }

    public boolean IsCurrentlyHandlingProduct()
    {
        return this.myProduct != null;
    }

    public void ClearProductAndGetReady()
    {
        this.myProduct = null;
    }

    private void GiveSameColorAsProduct()
    {
        String color = this.myProduct.GetColor();
        this.SetColor(color);
    }

    public void StopThread()
    {
        this.mythread.interrupt();
    }

    public void PrintStartingObjectMessage()
    {
        System.out.printf("Machine %s Starting to Handle Product %s time%n", this.toString(), this.myProduct.toString());
    }

    public void PrintFinishingObjectMessage()
    {
        System.out.printf("Machine %s Finished Handling Product %s time %s%n", this.toString(), this.myProduct.toString(), this.MachineTimeInMilliseconds);
    }

    @Override
    protected void SetDefaultColor(){
        this.SetColor(MyColor.GetDefaultColorForM());
    }

    public String toString()
    {
        return "M" + this.GetID();
    }
}
