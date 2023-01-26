package com.example.ProducerConsumer.ProducerConsumer;

import com.example.ProducerConsumer.ProducerConsumer.MementoDP.AddProductToRootRequest;

import javax.swing.text.AbstractDocument;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Manager
{
    private boolean SimulationGoing = false;
    private int NodeIDCounter = 0;
    private int ProductIDCounter = 0;
    private HashMap<Integer, Node> Map = new HashMap();
    private List<AddProductToRootRequest> Requests = new ArrayList<>();
    private Queuer Root;
    private Date StartingDate;

    public Manager()
    {
        this.StartingDate = new Date();
    }

    public void RestartSimulation()
    {
        this.ClearProgram();
        for (AddProductToRootRequest request : this.Requests)
            request.RunRequest();
    }

    private void ClearProgram()
    {
        this.StopAllMachineThread();
        this.ClearAllQueues();
    }

    private void StopAllMachineThread()
    {
        Map.forEach((i, node) -> {
            if (node instanceof Queuer) return;
            Machine machine = (Machine) node;
            machine.StopThread();
        });
    }

    private void ClearAllQueues()
    {

        Map.forEach((i, node) -> {
            if (node instanceof Machine) return;
            Queuer queuer = (Queuer) node;
            queuer.ClearQueue();
        });
    }

    public void AddProduct(Product product)
    {
        this.Root.HandleProduct(product);
        this.CreateRequestAndPutInRequestList(product);
    }

    private void CreateRequestAndPutInRequestList(Product product)
    {
        Date currentDate = new Date();
        long timeInMillisecond = currentDate.getTime() - this.StartingDate.getTime();

        AddProductToRootRequest request = new AddProductToRootRequest(product, timeInMillisecond, this);
        this.Requests.add(request);
    }

    public void AddProduct()
    {
        int ProductID = this.GetAndIncreamentProcutID();
        Product product = new Product(ProductID);
        this.AddProduct(product);
    }

    public void AddQueuer()
    {
        int id = this.GetAndIncreamentNodeID();
        Queuer queuer = new Queuer(id);
        this.AddNodeToMap(queuer);
    }



    public void AddMachine()
    {
        int id = this.GetAndIncreamentNodeID();
        Machine machine = new Machine(id);
        this.AddNodeToMap(machine);
    }

    public void AddEdge(int FirstNodeString, int SecondNodeString)
    {
        Node FirstNode = this.Map.get(FirstNodeString);
        Node SecondNode = this.Map.get(SecondNodeString);

        FirstNode.AddOutEdge(SecondNode);
        SecondNode.AddInEdge(FirstNode);
    }

    public void RemoveEdge(int FirstNodeString, int SecondNodeString)
    {
        Node FirstNode = this.Map.get(FirstNodeString);
        Node SecondNode = this.Map.get(SecondNodeString);

        FirstNode.RemoveOutEdge(SecondNode);
        SecondNode.RemoveInEdge(FirstNode);
    }

    public void AddNodeToMap(Node node)
    {
        int id = node.GetID();
        this.Map.put(id, node);
    }

    public void RemoveNodeFromMap(Node node)
    {
        int id = node.GetID();
        this.Map.remove(id);
    }

    private int GetAndIncreamentNodeID()
    {
        return this.NodeIDCounter++;
    }

    private int GetAndIncreamentProcutID()
    {
        return this.ProductIDCounter++;
    }

    public void StartSimulation()
    {
        this.SimulationGoing = true;
        this.Root.StartSimulation();
    }
}