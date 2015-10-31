package com.company.Simulation.Behaviours;

import com.company.Helper.CoordHelper.Coord;
import com.company.Simulation.Agents.Soldiers.Archer;
import com.company.Simulation.CommandType;

/**
 * Created by Szymon on 2015-10-31.
 */
public class ArcherBehaviour extends SoldierBehaviour {

    Archer soldier;

    public ArcherBehaviour(Archer archer){
        super(archer);
    }

    @Override
    protected void makeDecision() {
         listenCommand();
    }

    @Override
    public void action() {
        makeDecision();
    }

    @Override
    protected boolean listenCommand() {
        if (soldier.getCommand() != null) {
            if (soldier.getCommand().getCommType() == CommandType.ATTACK) {
                archeryAttack((int) soldier.getCommand().getPossition().getX(), (int) soldier.getCommand().getPossition().getY());
            } else {
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
            }
            return true;
        }
        return false;
    }

    protected void archeryAttack(int x, int y) {
        soldier.getSquad().arrowPhysic.setNewArrowAttack(x, y);
    }
}

