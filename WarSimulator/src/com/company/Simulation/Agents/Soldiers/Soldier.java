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
    protected boolean isAlive = true;
    protected boolean wasAttacked = false;
    protected int moral = 100;
    protected int brave;
    protected boolean ifRunAway = false;

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

    public boolean getStatus() { return isAlive; }

    public void setStatus(boolean bool) { isAlive = bool; }

    public boolean getWasAttacked() { return wasAttacked; }

    public void setWasAttacked() { wasAttacked = true; }

    public void resetWasAttacked() { wasAttacked = false; }

    public void setMoral(int moral){
        this.moral = moral;
    }

    public int getMoral(){
        return moral;
    }

    public void setBrave(int brave){
        this.brave = brave;
    }

    public int getBrave(){
        return brave;
    }

    public boolean getIfRunAway(){
        return ifRunAway;
    }

    public void setIfRunAway(){
        ifRunAway = true;
    }
}
