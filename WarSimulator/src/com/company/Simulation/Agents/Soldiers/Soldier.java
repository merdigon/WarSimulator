package com.company.Simulation.Agents.Soldiers;

/**
 * Created by Szymon on 2015-10-16.
 */
import com.company.Helper.CoordHelper.Coord;
import com.company.Simulation.Agents.Agent;
import com.company.Simulation.Agents.Squads.Squad;
import com.company.Simulation.Command;

public abstract class Soldier extends Agent {

    protected int hp=100;
    protected Command comm;
    protected Coord soldCoord;

    public Soldier(){
        setup();
    }

    public abstract Squad getSquad();

    protected abstract void setup();

    public void setCommand(Command comm)
    {
        this.comm = comm;
    }

    public Command getCommand()
    {
         return comm;
    }

    public Coord getCoord() { return soldCoord; }

    public void setCoord(Coord soldCoord) {
        this.soldCoord = soldCoord;
    }

    public void setHp(int a) { hp = a; }

    public void changeHp (int a) { hp = hp - a; }

    public int getHp() { return hp; }
}
