package com.company.Simulation.Agents.Squads;

import com.company.Enviroment.Map;
import com.company.Simulation.Agents.Soldiers.Soldier;
import com.company.Simulation.Command;
import com.company.Simulation.Teams;
import jade.core.AID;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Szymon on 2015-10-27.
 */
public class Squad {
    protected Command comm;
    protected ArrayList<Soldier> squadSoldiers;
    public Teams team;
    public Map terrainMap;

    public Squad(Teams team){
        squadSoldiers = new ArrayList<Soldier>();
        this.team = team;
    }

    public void setSquad(int a){
        for(int i=0;i<a;i++)
            squadSoldiers.add(new Soldier(this));
    }

    public void giveCommand()
    {
        if(comm!=null)
        {
            for(int i=0;i<squadSoldiers.size();i++)
            {
                squadSoldiers.get(i).setCommand(comm);
            }
        }
    }

    public synchronized void eliminateSoldier(AID aid){
        for(int i=0;i<squadSoldiers.size();i++){
            if(squadSoldiers.get(i).getAID() == aid) {
                squadSoldiers.remove(i);
                return;
            }
        }
    }
}