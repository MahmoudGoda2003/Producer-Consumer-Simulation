package com.example.ProducerConsumer.ProducerConsumer.MementoDP;

import java.util.List;

public class AddProcess
{
    private List<AddRequest> RequestList;

    public AddProcess(List<AddRequest> list)
    {
        this.RequestList = list;
    }

    public void RunAllAddRequests()
    {
        RequestList.forEach((request)->{
            request.RunRequest();
        });
    }
}
