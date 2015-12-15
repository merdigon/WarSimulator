package com.company.Simulation.Behaviours.AgentsBehaviours;

import com.company.Helper.CoordHelper.Coord;
import com.company.Helper.SquadHelper;
import com.company.Simulation.Agents.Soldiers.Archer;
import com.company.Simulation.Behaviours.AgentsBehaviours.SoldierBehaviour;
import com.company.Simulation.CommandType;

import java.util.Random;

/**
 * Created by Szymon on 2015-10-31.
 */
public class ArcherBehaviour extends SoldierBehaviour {

    public Archer soldier;

    public ArcherBehaviour(Archer archer){
        super();
        soldier = archer;
    }

    @Override
    public Archer getSoldier(){
        return soldier;
    }

    @Override
    protected void attackFromCommand(){
        archeryAttack();
    }

    protected void archeryAttack() {
        Random gen = new Random();
        int attackX = -1;
        int attackY = -1;
        if (soldier.getCommand() != null && soldier.getCommand().getPossition() != null) {
            attackX = (int)soldier.getCommand().getPossition().getX() + ((int)(gen.nextDouble()*4)) * (gen.nextBoolean()?1:(-1));
            attackY = (int)soldier.getCommand().getPossition().getY() + ((int)(gen.nextDouble()*4)) * (gen.nextBoolean()?1:(-1));
        }else
        if (soldier.getCommand() != null && soldier.getCommand().getSquad() != null){
            attackX = (int)SquadHelper.getMiddlePointOfSquad(soldier.getCommand().getSquad()).getX() + ((int)(gen.nextDouble()*4)) * (gen.nextBoolean()?1:(-1));
            attackY = (int)SquadHelper.getMiddlePointOfSquad(soldier.getCommand().getSquad()).getY() + ((int)(gen.nextDouble()*4)) * (gen.nextBoolean()?1:(-1));
        }

        if(attackX>0 && attackY>0)
            soldier.getSquad().arrowPhysic.setNewArrowAttack((int)soldier.getCoord().getX(), (int)soldier.getCoord().getY(), attackX, attackY);
    }
}

