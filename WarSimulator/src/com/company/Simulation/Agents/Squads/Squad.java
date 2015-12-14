package com.company.Simulation.Agents.Squads;

import com.company.Battle;
import com.company.Enviroment.Map;
import com.company.Simulation.Agents.Agent;
import com.company.Simulation.Agents.Commander;
import com.company.Simulation.Agents.Soldiers.Soldier;
import com.company.Simulation.Command;
import com.company.Simulation.Teams;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Szymon on 2015-10-27.
 */
public abstract class Squad extends Agent{
    protected Command comm;
    protected ArrayList<Soldier> squadSoldiers;
    protected Battle battle;
    protected Commander commander;
    public Teams team;
    public Map terrainMap;
    public SquadType squadType;

    public Squad(Teams team, Map map, Battle battle, Commander commander){
        terrainMap = map;
        squadSoldiers = new ArrayList<>();
        this.team = team;
        this.battle = battle;
        this.commander = commander;
        setup();
    }

    protected abstract void setup();

    public abstract void executePhysic();

    public Command getCommand(){
        return comm;
    }

    public void setCommand(Command comm){
        this.comm = comm;
    }

    public abstract Squad setSquad(int startCoordX, int startCoordY, int howManyInX, int howManyInY);/*{
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

    public Teams getTeam() {
        return this.team;
    }

    public boolean checkIfAlive() {
        for (Soldier sold : getSoldiers()) {
            if (sold.getStatus())
                return true;
        }
        return false;
    }
}