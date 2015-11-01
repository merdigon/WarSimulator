package com.company.Simulation.Behaviours.BasicBahaviours;

/**
 * Created by Szymon on 2015-11-01.
 */
public abstract class Behaviour {

    protected abstract void action();

    protected abstract boolean ifDone();

    public boolean executeBehaviour(){
        action();
        return ifDone();
    }
}
