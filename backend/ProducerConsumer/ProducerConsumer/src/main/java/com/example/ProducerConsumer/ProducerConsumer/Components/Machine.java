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
        mythread = new Thread(this);
    }

    public synchronized void SetProduct(Product product)
    {
        this.myProduct = product;
    }

    public synchronized void HandleRequest(Product product)
    {
        System.out.println(product);
        this.SetProduct(product);
        this.GiveSameColorAsProduct();

        this.mythread = new Thread(this);
        Thread thread = this.mythread;
        System.out.println(this.myProduct);
        thread.run();
        thread.interrupt();
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

        System.out.println(this.myProduct);

        this.PrintFinishingObjectMessage();
        this.AfterOperationgOnProduct();
    }

    public synchronized void StopThread()
    {
        this.mythread.interrupt();
    }

    private synchronized void AfterOperationgOnProduct()
    {
        this.SetDefaultColor();
        this.SendObjectToNextQueuer();
        this.ClearProductAndGetReady();
        this.NotifyObservers();
    }

    private synchronized void SendObjectToNextQueuer()
    {
        Queuer queuer = (Queuer) this.NextNodes.get(0);
        queuer.HandleProduct(this.myProduct);
    }

    @Override
    public synchronized void NotifyObservers()
    {
        for (Node node : this.PreviousNodes)
        {
            Queuer queuer = (Queuer) node;
            if (queuer.HasProducts())
            {
                this.HandleRequest(queuer.GetProductFromQueue());
                break;
            }
        }
    }

    public synchronized boolean IsCurrentlyHandlingProduct()
    {
        return this.myProduct != null;
    }

    public synchronized void PrintStartingObjectMessage()
    {
        System.out.printf("Machine %s Starting to Handle Product %s time%n", this.toString(), this.myProduct.toString());
    }

    public synchronized void PrintFinishingObjectMessage()
    {
        System.out.printf("Machine %s Finished Handling Product %s time %s%n", this.toString(), this.myProduct.toString(), this.MachineTimeInMilliseconds);
    }

    public synchronized void ClearProductAndGetReady()
    {
        this.myProduct = null;
    }

    private synchronized void GiveSameColorAsProduct()
    {
        String color = this.myProduct.GetColor();
        this.SetColor(color);
    }

    @Override
    protected void SetDefaultColor(){
        this.SetColor(MyColor.GetDefaultColorForM());
    }

    public synchronized String toString()
    {
        return "M" + this.GetID();
    }
}
