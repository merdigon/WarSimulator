package com.company.Simulation.Behaviours.AgentsBehaviours;

/**
 * Created by Szymon on 2015-10-31.
 */
import com.company.Helper.CoordHelper.Coord;
import com.company.Simulation.Agents.Soldiers.Soldier;
import com.company.Simulation.Behaviours.AttackBehaviour;
import com.company.Simulation.Behaviours.BasicBahaviours.CyclicBehaviour;
import com.company.Simulation.CommandType;

import java.util.ArrayList;

public abstract class SoldierBehaviour extends CyclicBehaviour {

    //najwa�niejsza klasa dla Soldier, kt�ra okre�la jego decyzj�, kt�re podejmuje oraz symuluje jego psychik�
    //rozwa�y� dodanie sprawdzania czy umar� itp jako inne, ale te� cykliczne behaviour
    ArrayList<Soldier> noticedEnemies = new ArrayList<Soldier>();
    ArrayList<Soldier> noticedFriends = new ArrayList<Soldier>();

    public SoldierBehaviour(){
    }

    public abstract Soldier getSoldier();

    @Override
    public void action() {
        if(!checkIfAlive()){
            killSoldier();
            return;
        }
        thinking();
        noticedEnemies.clear();
        noticedFriends.clear();
    }

    protected void thinking(){
        lookForAgents();
        makeDecision();
    }

    protected boolean checkIfAlive(){
        if(getSoldier().getHp()<0)
            return false;
        return true;
    }

    protected boolean move(int xRelative, int yRelative){
        if(xRelative>1 || xRelative<-1 || yRelative<-1 || yRelative>1)
            xRelative++;

        Coord soldCoord = getSoldier().getCoord();
        if(!getSoldier().getSquad().terrainMap.moveSoldier((int)soldCoord.getX(), (int)soldCoord.getY(), (int)soldCoord.getX() + xRelative, (int)soldCoord.getY() + yRelative))
        {
            int newXRelative;
            int newYRelative;
            if(xRelative == 0)
            {
                newXRelative = 1;
                newYRelative = yRelative;
                if(!getSoldier().getSquad().terrainMap.moveSoldier((int)soldCoord.getX(), (int)soldCoord.getY(), (int)soldCoord.getX() + newXRelative, (int)soldCoord.getY() + newYRelative))
                {
                    newXRelative = -1;
                    if(!getSoldier().getSquad().terrainMap.moveSoldier((int)soldCoord.getX(), (int)soldCoord.getY(), (int)soldCoord.getX() + newXRelative, (int)soldCoord.getY() + newYRelative))
                    {
                        return false;
                    }
                }
            }
            else if(yRelative == 0)
            {
                newYRelative = 1;
                newXRelative = xRelative;
                if(!getSoldier().getSquad().terrainMap.moveSoldier((int)soldCoord.getX(), (int)soldCoord.getY(), (int)soldCoord.getX() + newXRelative, (int)soldCoord.getY() + newYRelative))
                {
                    newYRelative = -1;
                    if(!getSoldier().getSquad().terrainMap.moveSoldier((int)soldCoord.getX(), (int)soldCoord.getY(), (int)soldCoord.getX() + newXRelative, (int)soldCoord.getY() + newYRelative))
                    {
                        return false;
                    }
                }
            }
            else
            {
                newYRelative = 0;
                newXRelative = xRelative;
                if(!getSoldier().getSquad().terrainMap.moveSoldier((int)soldCoord.getX(), (int)soldCoord.getY(), (int)soldCoord.getX() + newXRelative, (int)soldCoord.getY() + newYRelative))
                {
                    newXRelative = 0;
                    newYRelative = yRelative;
                    if(!getSoldier().getSquad().terrainMap.moveSoldier((int)soldCoord.getX(), (int)soldCoord.getY(), (int)soldCoord.getX() + newXRelative, (int)soldCoord.getY() + newYRelative))
                    {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    protected void attack(Soldier soldToAttack){
        soldToAttack.addBehaviour(new AttackBehaviour(getSoldier(), soldToAttack));
    }

    protected void lookForAgents(){
        Coord tmpCoord = getSoldier().getCoord().clone();
        Soldier tmpsold;
        tmpCoord.setX(tmpCoord.getX()-3);
        tmpCoord.setY(tmpCoord.getY()-3);
        for(int i=0; i<7;i++)
        {
            for(int j=0; j<7; j++)
            {
                if(i!=3 || j!=3)
                {
                    if((int)tmpCoord.getX()+i < getSoldier().getSquad().terrainMap.X && (int)tmpCoord.getY()+j < getSoldier().getSquad().terrainMap.Y) {
                        if ((tmpsold = getSoldier().getSquad().terrainMap.getSoldierOnPosition((int) tmpCoord.getX() + i, (int) tmpCoord.getY() + j)) != null) {
                            if (tmpsold.getSquad().team == getSoldier().getSquad().team)
                                noticedFriends.add(tmpsold);
                            else
                                noticedEnemies.add(tmpsold);
                        }
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
        if(getSoldier().getCommand() != null && getSoldier().getCommand().getCommType() == CommandType.MOVEMENT){
            Coord enemyCoord = getSoldier().getCommand().getPossition();
            if(Math.abs(getSoldier().getCoord().getX() - enemyCoord.getX()) <= 2){
                move(0, (int)((enemyCoord.getY() - getSoldier().getCoord().getY())/Math.abs(enemyCoord.getY() - getSoldier().getCoord().getY())));
                return true;
            }
            if(Math.abs(getSoldier().getCoord().getY()-enemyCoord.getY()) <= 2){
                move((int)((enemyCoord.getX() - getSoldier().getCoord().getX())/Math.abs(enemyCoord.getX() - getSoldier().getCoord().getX())), 0);
                return true;
            }
            move((int)((enemyCoord.getX() - getSoldier().getCoord().getX())/Math.abs(enemyCoord.getX() - getSoldier().getCoord().getX())),
                    (int)((enemyCoord.getY() - getSoldier().getCoord().getY())/Math.abs(enemyCoord.getY() - getSoldier().getCoord().getY())));
            return true;
        }
        return false;
    }

    protected boolean checkAndAttack(){
        for(Soldier soldEnemy : noticedEnemies){
            if(Math.abs(getSoldier().getCoord().getX() - soldEnemy.getCoord().getX()) <= 1 && Math.abs(getSoldier().getCoord().getY() - soldEnemy.getCoord().getY()) <= 1){
                attack(soldEnemy);
                return true;
            }
        }
        return false;
    }

    protected boolean runToEnemy(){
        if(!noticedEnemies.isEmpty()){
            Coord enemyCoord = noticedEnemies.get(0).getCoord();
            if(Math.abs(getSoldier().getCoord().getX()-enemyCoord.getX()) <= 2){
                move(0, (int)((enemyCoord.getY() - getSoldier().getCoord().getY())/Math.abs(enemyCoord.getY() - getSoldier().getCoord().getY())));
                return true;
            }
            if(Math.abs(getSoldier().getCoord().getY()-enemyCoord.getY()) <= 2){
                move((int)((enemyCoord.getX() - getSoldier().getCoord().getX())/Math.abs(enemyCoord.getX() - getSoldier().getCoord().getX())), 0);
                return true;
            }
            move((int)((enemyCoord.getX() - getSoldier().getCoord().getX())/Math.abs(enemyCoord.getX() - getSoldier().getCoord().getX())),
                    (int)((enemyCoord.getY() - getSoldier().getCoord().getY())/Math.abs(enemyCoord.getY() - getSoldier().getCoord().getY())));
            return true;
        }
        return false;
    }

    protected void killSoldier(){
        getSoldier().getSquad().terrainMap.clearPosition((int) getSoldier().getCoord().getX(), (int) getSoldier().getCoord().getY());
        getSoldier().getSquad().eliminateSoldier(getSoldier());
    }
}
