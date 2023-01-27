package com.example.ProducerConsumer.ProducerConsumer;

import com.example.ProducerConsumer.ProducerConsumer.Components.BuildCircuit;
import com.example.ProducerConsumer.ProducerConsumer.Components.Manager;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/Produce")
public class Controller
{
    
    @PostMapping("/Simulate")
    @ResponseBody
    public void Simulate(@RequestBody String data)
    {
        System.out.printf(data);
        BuildCircuit builder = new BuildCircuit();
        builder.build(data);
        Manager.getManger().StartSimulation();
    }

    @GetMapping("/Replay")
    @ResponseBody
    public void Replay()
    {
        Manager.getManger().RestartSimulation();
    }

    @GetMapping("/Poll")
    @ResponseBody
    public String Pulling()
    {
       return Manager.getManger().GetState().toString();
    }

    @GetMapping("/Stop")
    @ResponseBody
    public void Stop()
    {
        Manager.getManger().PauseAddingProducts();
    }

}
