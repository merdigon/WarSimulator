package com.company.Simulation.Behaviours.BasicBahaviours;

/**
 * Created by Szymon on 2015-11-02.
 */
public abstract class WakerBehaviour extends Behaviour {

    long startTime;
    int timeout;
    public WakerBehaviour(int timeout){
        startTime = System.nanoTime();
        this.timeout = timeout;
    }

    @Override
    protected boolean ifDone() {
        if((System.nanoTime()-startTime) < timeout)
            return false;
        return true;
    }

    @Override
    public boolean executeBehaviour(){
        if(ifDone()){
            action();
            return true;
        }
        return false;
    }
}
