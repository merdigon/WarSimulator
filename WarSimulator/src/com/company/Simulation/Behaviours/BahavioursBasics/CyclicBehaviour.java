package com.company.Simulation.Behaviours.BahavioursBasics;

/**
 * Created by Szymon on 2015-11-01.
 */
public abstract class CyclicBehaviour extends Behaviour {

    protected abstract void action();

    protected boolean ifDone(){ return false; }
}
