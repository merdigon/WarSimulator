package com.company.Simulation.Behaviours;

/**
 * Created by Szymon on 2015-10-31.
 */
import com.company.Helper.CoordHelper.Coord;
import com.company.Simulation.Agents.Soldiers.Soldier;
import com.company.Simulation.CommandType;
import jade.core.behaviours.CyclicBehaviour;

import java.util.ArrayList;

public class SoldierBehaviour extends CyclicBehaviour{

    //najwa¿niejsza klasa dla Soldier, która okreœla jego decyzjê, które podejmuje oraz symuluje jego psychikê
    //rozwa¿yæ dodanie sprawdzania czy umar³ itp jako inne, ale te¿ cykliczne behaviour
    Soldier soldier;
    ArrayList<Soldier> noticedEnemies = new ArrayList<Soldier>();
    ArrayList<Soldier> noticedFriends = new ArrayList<Soldier>();

    public SoldierBehaviour(Soldier soldier){
        this.soldier = soldier;
    }

    @Override
    public void action() {
        if(!checkIfAlive()){
            killSoldier();
            return;
        }
        thinking();
    }

    protected void thinking(){
        lookForAgents();
        makeDecision();
    }

    protected boolean checkIfAlive(){
        if(soldier.getHp()<0)
            return false;
        return true;
    }

    protected boolean move(int xRelative, int yRelative){
        Coord soldCoord = soldier.getCoord();
        if(!soldier.getSquad().terrainMap.moveSoldier((int)soldCoord.getX(), (int)soldCoord.getY(), (int)soldCoord.getX() + xRelative, (int)soldCoord.getY() + yRelative))
        {
            int newXRelative;
            int newYRelative;
            if(xRelative == 0)
            {
                newXRelative = 1;
                newYRelative = yRelative;
                if(!soldier.getSquad().terrainMap.moveSoldier((int)soldCoord.getX(), (int)soldCoord.getY(), (int)soldCoord.getX() + newXRelative, (int)soldCoord.getY() + newYRelative))
                {
                    newXRelative = -1;
                    if(!soldier.getSquad().terrainMap.moveSoldier((int)soldCoord.getX(), (int)soldCoord.getY(), (int)soldCoord.getX() + newXRelative, (int)soldCoord.getY() + newYRelative))
                    {
                        return false;
                    }
                }
            }
            else if(yRelative == 0)
            {
                newYRelative = 1;
                newXRelative = xRelative;
                if(!soldier.getSquad().terrainMap.moveSoldier((int)soldCoord.getX(), (int)soldCoord.getY(), (int)soldCoord.getX() + newXRelative, (int)soldCoord.getY() + newYRelative))
                {
                    newYRelative = -1;
                    if(!soldier.getSquad().terrainMap.moveSoldier((int)soldCoord.getX(), (int)soldCoord.getY(), (int)soldCoord.getX() + newXRelative, (int)soldCoord.getY() + newYRelative))
                    {
                        return false;
                    }
                }
            }
            else
            {
                newYRelative = 0;
                newXRelative = xRelative;
                if(!soldier.getSquad().terrainMap.moveSoldier((int)soldCoord.getX(), (int)soldCoord.getY(), (int)soldCoord.getX() + newXRelative, (int)soldCoord.getY() + newYRelative))
                {
                    newXRelative = 0;
                    newYRelative = yRelative;
                    if(!soldier.getSquad().terrainMap.moveSoldier((int)soldCoord.getX(), (int)soldCoord.getY(), (int)soldCoord.getX() + newXRelative, (int)soldCoord.getY() + newYRelative))
                    {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    protected void attack(Soldier soldToAttack){
        soldToAttack.addBehaviour(new AttackBehaviour(soldier, soldToAttack));
    }

    protected void lookForAgents(){
        Coord tmpCoord = soldier.getCoord();
        Soldier tmpsold;
        tmpCoord.setX(tmpCoord.getX()-3);
        tmpCoord.setY(tmpCoord.getY()-3);
        for(int i=0; i<7;i++)
        {
            for(int j=0; j<7; j++)
            {
                if(i!=3 && j!=3)
                {
                    if((tmpsold = soldier.getSquad().terrainMap.getSoldierOnPosition((int)tmpCoord.getX()*i,(int)tmpCoord.getY()+j))!=null){
                        if(tmpsold.getSquad().team == soldier.getSquad().team)
                            noticedFriends.add(tmpsold);
                        else
                            noticedEnemies.add(tmpsold);
                    }
                }
            }
        }
    }

    protected void makeDecision(){
        if(checkAndAttack())
            return;

        if(runToEnemy())
            return;

        if(listenCommand())
            return;
    }

    protected boolean listenCommand()  {
        if(soldier.getCommand() != null && soldier.getCommand().getCommType() == CommandType.MOVEMENT){
            Coord enemyCoord = soldier.getCommand().getPossition();
            if(Math.abs(soldier.getCoord().getX()-enemyCoord.getX()) <= 2){
                move(0, (int)(enemyCoord.getY() - soldier.getCoord().getY()/Math.abs(enemyCoord.getY() - soldier.getCoord().getY())));
                return true;
            }
            if(Math.abs(soldier.getCoord().getY()-enemyCoord.getY()) <= 2){
                move((int)(enemyCoord.getX() - soldier.getCoord().getX()/Math.abs(enemyCoord.getX() - soldier.getCoord().getX())), 0);
                return true;
            }
            move((int)(enemyCoord.getX() - soldier.getCoord().getX()/Math.abs(enemyCoord.getX() - soldier.getCoord().getX())),
                    (int)(enemyCoord.getY() - soldier.getCoord().getY()/Math.abs(enemyCoord.getY() - soldier.getCoord().getY())));
            return true;
        }
        return false;
    }

    protected boolean checkAndAttack(){
        for(Soldier soldEnemy : noticedEnemies){
            if(Math.abs(soldier.getCoord().getX() - soldEnemy.getCoord().getX()) <= 1 && Math.abs(soldier.getCoord().getY() - soldEnemy.getCoord().getY()) <= 1){
                attack(soldEnemy);
                return true;
            }
        }
        return false;
    }

    protected boolean runToEnemy(){
        if(!noticedEnemies.isEmpty()){
            Coord enemyCoord = noticedEnemies.get(0).getCoord();
            if(Math.abs(soldier.getCoord().getX()-enemyCoord.getX()) <= 2){
                move(0, (int)(enemyCoord.getY() - soldier.getCoord().getY()/Math.abs(enemyCoord.getY() - soldier.getCoord().getY())));
                return true;
            }
            if(Math.abs(soldier.getCoord().getY()-enemyCoord.getY()) <= 2){
                move((int)(enemyCoord.getX() - soldier.getCoord().getX()/Math.abs(enemyCoord.getX() - soldier.getCoord().getX())), 0);
                return true;
            }
            move((int)(enemyCoord.getX() - soldier.getCoord().getX()/Math.abs(enemyCoord.getX() - soldier.getCoord().getX())),
                    (int)(enemyCoord.getY() - soldier.getCoord().getY()/Math.abs(enemyCoord.getY() - soldier.getCoord().getY())));
            return true;
        }
        return false;
    }

    protected void killSoldier(){
        soldier.getSquad().terrainMap.clearPosition((int)soldier.getCoord().getX(), (int)soldier.getCoord().getY());
        soldier.getSquad().eliminateSoldier(soldier.getAID());
        soldier.doDelete();
    }
}
