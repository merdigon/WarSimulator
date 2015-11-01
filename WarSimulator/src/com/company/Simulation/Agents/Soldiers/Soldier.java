package com.company.Simulation.Agents.Soldiers;

/**
 * Created by Szymon on 2015-10-16.
 */
import com.company.Helper.CoordHelper.Coord;
import com.company.Simulation.Agents.Agent;
import com.company.Simulation.Agents.Squads.Squad;
import com.company.Simulation.Command;
import com.company.Simulation.Behaviours.SoldierBehaviour;

import java.util.ArrayList;

public class Soldier extends Agent {

    protected int hp;
    protected Command comm;
    protected Squad squad;
    protected Coord soldCoord;

    public Soldier(Squad _squad){
        this.squad = _squad;
        setup();
    }

    protected void setup()
    {
        addBehaviour(new SoldierBehaviour(this));
    }

    public void setCommand(Command comm)
    {
        this.comm = comm;
    }

    public Command getCommand()
    {
         return comm;
    }

    public Squad getSquad() { return squad;  }

    public Coord getCoord() { return soldCoord; }

    public void setCoord(Coord soldCoord) { this.soldCoord = soldCoord; }

    public void setHp(int a) { hp = a; }

    public void changeHp (int a) { hp = hp - a; }

    public int getHp() { return hp; }
}
