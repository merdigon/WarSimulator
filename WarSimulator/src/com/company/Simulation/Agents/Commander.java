package com.company.Simulation.Agents;

import com.company.Enviroment.Map;
import com.company.Simulation.Behaviours.AgentsBehaviours.CommanderBehaviour;
import com.company.Simulation.Command;
import com.company.Simulation.Teams;

/**
 * Created by Szymon on 2015-10-16.
 */
public class Commander extends Agent {
    Teams commTeam;
    Map terrainMap;

    public Commander(Teams team, Map battleMap){
        commTeam = team;
        setup();
    }

    protected void setup(){
        addBehaviour(new CommanderBehaviour(this));
    }
}
