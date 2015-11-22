package com.company.Simulation.Agents;

import com.company.Enviroment.Map;
import com.company.Simulation.Agents.Squads.Squad;
import com.company.Simulation.Command;
import com.company.Simulation.Teams;

/**
 * Created by Szymon on 2015-10-16.
 */
public class Commander {
    Command comm;
    Teams commTeam;
    Map terrainMap;
    int currentSetSquadInInit = 0;

    public Commander(Teams team, Map battleMap){
        commTeam = team;
    }

    public void setCommand(Command comm){
        this.comm = comm;
    }

    public Command getCommand(){
        return comm;
    }
}
