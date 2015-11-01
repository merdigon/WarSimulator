package com.company.Simulation.Behaviours.BahavioursBasics;

/**
 * Created by Szymon on 2015-11-01.
 */
public abstract class OneShotBehaviour extends Behaviour {

    @Override
    protected abstract void action();

    @Override
    protected boolean ifDone() {
        return true;
    }
}
