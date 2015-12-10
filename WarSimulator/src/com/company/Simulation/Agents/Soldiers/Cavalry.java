package com.company.Simulation.Agents.Soldiers;

import com.company.Simulation.Agents.Squads.CavalrySquad;
import com.company.Simulation.Behaviours.AgentsBehaviours.CavalryBehaviour;

/**
 * Created by Szymon on 2015-11-17.
 */
public class Cavalry extends Soldier {

    CavalrySquad squad;
    double velocity = 500;
    double maxVelocity = 125;
    double velocityStep = 18.7;
    long timeAfterLastThinking=0;

    public Cavalry(CavalrySquad squad){
        this.squad = squad;
    }

    @Override
    public CavalrySquad getSquad() {
        return squad;
    }

    @Override
    protected void setup() {
        addBehaviour(new CavalryBehaviour(this));
    }


    public double getVelocity(){
        return velocity;
    }

    public double getMaxVelocity(){
        return maxVelocity;
    }

    public double getVelocityStep(){
        return velocityStep;
    }

    public void setVelocity(double vel){
        velocity = vel;
    }

    public void setMaxVelocity(double max){
        maxVelocity = max;
    }

    public void setVelocityStep(double step){
        velocityStep = step;
    }

    public long getTimeAfterLastThinking(){
        return timeAfterLastThinking;
    }

    public void addTimeAfterLastThinking(long add){
        timeAfterLastThinking+=add;
    }

    public void setTimeAfterLastThinking(long time){
        timeAfterLastThinking=time;
    }
}
