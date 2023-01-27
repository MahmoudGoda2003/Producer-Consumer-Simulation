package com.example.ProducerConsumer.ProducerConsumer;

import com.example.ProducerConsumer.ProducerConsumer.Components.BuildCircuit;
import com.example.ProducerConsumer.ProducerConsumer.Components.Manager;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/Produce")
public class Controller {
    private Manager manager;
    
    @PostMapping("/Simulate")
    @ResponseBody
    public void Simulate(@RequestBody String data){
        BuildCircuit builder = new BuildCircuit();
        this.manager = builder.build(data);
        this.manager.StartSimulation();
    }



}
