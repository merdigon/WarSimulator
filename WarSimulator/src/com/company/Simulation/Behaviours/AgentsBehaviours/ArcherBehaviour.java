package com.company.Simulation.Behaviours.AgentsBehaviours;

import com.company.Helper.CoordHelper.Coord;
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
    protected void makeDecision() {
         listenCommand();
    }

    @Override
    public void action() {
        if(!checkIfAlive()){
            killSoldier();
            return;
        }
        makeDecision();
    }

    @Override
    protected boolean listenCommand() {
        if (soldier.getCommand() != null) {
            if (soldier.getCommand().getCommType() == CommandType.MOVEMENT) {
                archeryAttack((int) soldier.getCommand().getPossition().getX(), (int) soldier.getCommand().getPossition().getY());
            } else {/*
                Coord enemyCoord = soldier.getCommand().getPossition();
                if (Math.abs(soldier.getCoord().getX() - enemyCoord.getX()) <= 2) {
                    move(0, (int) (enemyCoord.getY() - soldier.getCoord().getY() / Math.abs(enemyCoord.getY() - soldier.getCoord().getY())));
                    return true;
                }
                if (Math.abs(soldier.getCoord().getY() - enemyCoord.getY()) <= 2) {
                    move((int) (enemyCoord.getX() - soldier.getCoord().getX() / Math.abs(enemyCoord.getX() - soldier.getCoord().getX())), 0);
                    return true;
                }
                move((int) (enemyCoord.getX() - soldier.getCoord().getX() / Math.abs(enemyCoord.getX() - soldier.getCoord().getX())),
                        (int) (enemyCoord.getY() - soldier.getCoord().getY() / Math.abs(enemyCoord.getY() - soldier.getCoord().getY())));
                */
                Random gen = new Random();
                int attackX = (int)soldier.getCommand().getPossition().getX() + ((int)(gen.nextDouble()*4)) * (gen.nextBoolean()?1:(-1));
                int attackY = (int)soldier.getCommand().getPossition().getY() + ((int)(gen.nextDouble()*4)) * (gen.nextBoolean()?1:(-1));
                if(attackX>0 && attackY>0)
                    getSoldier().getSquad().arrowPhysic.setNewArrowAttack((int)getSoldier().getCoord().getX(), (int)getSoldier().getCoord().getY(), attackX, attackY);
            }
            return true;
        }
        return false;
    }

    protected void archeryAttack(int x, int y) {
        soldier.getSquad().arrowPhysic.setNewArrowAttack((int)soldier.getCoord().getX(), (int)soldier.getCoord().getY(), x, y);
    }
}

