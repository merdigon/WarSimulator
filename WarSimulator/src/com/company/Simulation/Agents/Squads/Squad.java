package com.company.Simulation.Agents.Squads;

import com.company.Enviroment.Map;
import com.company.Simulation.Agents.Soldiers.Soldier;
import com.company.Simulation.Command;
import com.company.Simulation.Teams;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Szymon on 2015-10-27.
 */
public abstract class Squad {
    protected Command comm;
    protected ArrayList<Soldier> squadSoldiers;
    public Teams team;
    public Map terrainMap;

    public Squad(Teams team, Map map){
        terrainMap = map;
        squadSoldiers = new ArrayList<Soldier>();
        this.team = team;
    }

    public abstract void setSquad(int startCoordX, int startCoordY, int howManyInX, int howManyInY);/*{
        Soldier sld;
        for(int i=0;i<howManyInX;i++){
            for(int j=0;j<howManyInY;j++) {
                sld = new Soldier();
                terrainMap.putSoldierOnPosition(sld, startCoordX+i, startCoordY+j);
                squadSoldiers.add(sld);
            }
        }
    }*/

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

    public synchronized void eliminateSoldier(Soldier sold){
        for(int i=0;i<squadSoldiers.size();i++){
            if(squadSoldiers.get(i) == sold) {
                squadSoldiers.remove(i);
                return;
            }
        }
    }

    public ArrayList<Soldier> getSoldiers(){ return squadSoldiers; }
}