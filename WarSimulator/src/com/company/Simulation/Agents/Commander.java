package com.company.Simulation.Agents;

import com.company.Battle;
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
    Battle battle;

    public Commander(Teams team, Map battleMap, Battle battle){
        commTeam = team;
        terrainMap = battleMap;
        this.battle = battle;
        setup();
    }

    public Battle getBattle(){
        return this.battle;
    }

    public Teams getTeam(){
        return this.commTeam;
    }

    public Map getMap(){
        return this.terrainMap;
    }

    protected void setup(){
        addBehaviour(new CommanderBehaviour(this));
    }
}
