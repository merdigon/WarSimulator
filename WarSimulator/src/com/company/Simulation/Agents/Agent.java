package com.company.Simulation.Agents;

import com.company.Simulation.Behaviours.BahavioursBasics.Behaviour;

import java.util.ArrayList;

/**
 * Created by Szymon on 2015-11-01.
 */
public class Agent {
    protected ArrayList<Behaviour> behaviours = new ArrayList<>();

    public void addBehaviour(Behaviour beh){
        behaviours.add(beh);
    }

    public void executeBehaviours(){
        for(int i=0;i<behaviours.size();i++){
            if(behaviours.get(i).executeBehaviour())
            {
                behaviours.remove(i);
            }
        }
    }
}
