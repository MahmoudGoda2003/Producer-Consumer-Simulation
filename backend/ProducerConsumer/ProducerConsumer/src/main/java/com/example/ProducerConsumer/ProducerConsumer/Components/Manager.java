package com.example.ProducerConsumer.ProducerConsumer.Components;

import com.example.ProducerConsumer.ProducerConsumer.MementoDP.AddProcess;
import com.example.ProducerConsumer.ProducerConsumer.MementoDP.AddRequest;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Manager
{
    private static Manager myManager;

    private HashMap<String, Node> Map = new HashMap();
    private Queuer Root;
    private Date StartingDate;
    List<AddRequest> RequestList;

    private Manager(int numberOfProducts)
    {
        this.StartingDate = new Date();
        this.RequestList = AddRequest.GetListOfRandomRequests(numberOfProducts);
    }

    public static Manager SetManager(int numberOfProducts)
    {
        //Manager.ClearProgramThreadsAndQueuesAndGetRequest();
        Manager.myManager = new Manager(numberOfProducts);
        return Manager.myManager;
    }

    public static Manager getManger()
    {
        return Manager.myManager;
    }

    public void StartSimulation()
    {
        AddProcess addProcess = new AddProcess(this.RequestList);
        addProcess.RunAllAddRequests();
    }

    public JSONObject GetState(){
        JSONObject State = new JSONObject();
        List<String> machines = new ArrayList<>();
        List<String> machineStates = new ArrayList<>();
        List<String> queues = new ArrayList<>();
        List<Integer> queueProducts = new ArrayList<>();
        this.Map.forEach((Id, Node) ->
        {
            if(!Node.GetColor().equals("yellow")){
                machines.add(Id);
                machineStates.add(Node.GetColor());
            }else{
                queues.add(Id);
                queueProducts.add(Node.GetProductsNo());
            }
        });
        return State.put("state","running").put("machines",machines).put("machineStates",machineStates).put("queues",queues).put("queueProducts",queueProducts);
    }

    public void RestartSimulation()
    {
        this.StartSimulation();
    }

//    public void PauseAddingProducts()
//    {
//        AddingProducts.GetAddingProductsInstance().PauseAddingProducts();
//    }
//
//    public void ResumeAddingProducts()
//    {
//        AddingProducts.GetAddingProductsInstance().Resume();
//    }

    private void ClearProgramThreadsAndQueuesForRestart()
    {
        this.StopAllMachineThread();
        this.ClearAllQueues();
        this.StopAddProcess();
    }

    public void StopAddProcess()
    {
        this.RequestList.forEach((request)->{
            request.StopRequest();
        });
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
    }

    public Queuer AddQueuer(String id)
    {
        Queuer queuer = new Queuer(id);
        this.AddNodeToMap(queuer);
        return queuer;
    }

    public void AddMachine(String id)
    {
        Machine machine = new Machine(id);
        this.AddNodeToMap(machine);
    }

    public void AddEdge(String FirstNodeString, String SecondNodeString)
    {
        Node FirstNode = this.Map.get(FirstNodeString);
        Node SecondNode = this.Map.get(SecondNodeString);

        FirstNode.AddOutEdge(SecondNode);
        SecondNode.AddInEdge(FirstNode);
    }

    public void AddNodeToMap(Node node)
    {
        String id = node.GetID();
        this.Map.put(id, node);
    }

    public void SetRoot(Queuer queuer)
    {
        this.Root = queuer;
    }
}