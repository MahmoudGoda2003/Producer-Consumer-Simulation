package com.example.ProducerConsumer.ProducerConsumer.Components;


import com.google.gson.Gson;
import org.json.JSONObject;

import java.util.List;

public class BuildCircuit {

    private Manager manager;

    public Manager build(String data)
    {
        JSONObject Data = new JSONObject(data);
        int productsNo = Data.getInt("productsNo");
        this.manager = new Manager(productsNo);
        List<String> Machines = new Gson().fromJson(Data.getJSONArray("machines").toString(), List.class);
        List<String> Queues = new Gson().fromJson(Data.getJSONArray("queues").toString(), List.class);
        List<String> ToConnectors = new Gson().fromJson(Data.getJSONArray("toConnectors").toString(), List.class);
        List<String> FromConnectors = new Gson().fromJson(Data.getJSONArray("FromConnectors").toString(), List.class);
        AddMachines(Machines);
        AddQueues(Queues);
        AddEdge(FromConnectors, ToConnectors);
        return manager;
    }

    private void AddMachines(List<String> Machines)
    {
        Machines.forEach((machine)->{
            this.manager.AddMachine(machine);
        });
    }

    private void AddQueues(List<String> Queues)
    {
        Queues.forEach((queue)->{
            this.manager.AddQueuer(queue);
        });
    }

    private void AddEdge(List<String> FromConnectors, List<String> ToConnectors)
    {
        for (int index=0; index<FromConnectors.size(); index++ ){
            this.manager.AddEdge(FromConnectors.get(index), ToConnectors.get(index));
        }
    }
}
