package com.company.Simulation.Agents.Squads;

import com.company.Battle;
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
    protected Battle battle;
    public Teams team;
    public Map terrainMap;

    public Squad(Teams team, Map map, Battle battle){
        terrainMap = map;
        squadSoldiers = new ArrayList<>();
        this.team = team;
        this.battle = battle;
    }

    public abstract void executePhysic();

    public void setCommand(Command comm){
        this.comm = comm;
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
                if(squadSoldiers.get(i).getStatus())
                    squadSoldiers.get(i).setCommand(comm);
            }
        }
    }

    public synchronized void eliminateSoldier(Soldier sold){
        for(int i=0;i<squadSoldiers.size();i++){
            if(squadSoldiers.get(i) == sold) {
                squadSoldiers.get(i).setStatus(false);
                return;
            }
        }
    }

    public ArrayList<Soldier> getSoldiers(){ return squadSoldiers; }
}