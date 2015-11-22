package com.company.Simulation.Agents;

import com.company.Simulation.Command;

/**
 * Created by Szymon on 2015-10-16.
 */
public class Commander {
    Command comm;

    public void setCommand(Command comm){
        this.comm = comm;
    }

    public Command getCommand(){
        return comm;
    }
}
