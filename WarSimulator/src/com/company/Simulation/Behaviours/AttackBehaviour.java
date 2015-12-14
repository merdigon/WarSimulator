package com.company.Simulation.Behaviours;

import com.company.Simulation.Agents.Soldiers.Soldier;
import com.company.Simulation.Behaviours.BasicBahaviours.OneShotBehaviour;

import java.util.Random;

/**
 * Created by Szymon on 2015-10-31.
 */
public class AttackBehaviour extends OneShotBehaviour {
    Soldier attacker;
    Soldier defender;
    Random generator = new Random();

    public AttackBehaviour(Soldier attacker, Soldier defender)
    {
        this.attacker = attacker;
        this.defender = defender;
    }

    @Override
    public void action() {
        //co jezeli zolnierz zostanie trafiony
        //obliczanie obra¿eñ na podstawie ró¿nicy wysokoœci
        double attackHeight = attacker.getSquad().terrainMap.Terrain[(int)attacker.getCoord().getX()][(int)attacker.getCoord().getY()].getHeight();
        double defenderHeight = defender.getSquad().terrainMap.Terrain[(int)defender.getCoord().getX()][(int)defender.getCoord().getY()].getHeight();
        double percentHeight = (attackHeight-defenderHeight)/10;

        int addPoint=0;
        if(percentHeight>0 && percentHeight<0.2)
            addPoint=1;
        else if (percentHeight>0.2 && percentHeight<0.4)
            addPoint=2;
        else if (percentHeight>0.4)
            addPoint=2;
        else if (percentHeight<0 && percentHeight>-0.2)
            addPoint=-1;
        else if (percentHeight<0.2 && percentHeight>-0.4)
            addPoint=-2;
        else if (percentHeight<-0.4)
            addPoint=-3;

        //super trudny algorytm obliczaj¹cy szanse na trafienie na podstawie wysokoœci, ranienie go oraz ustawianie tego, ¿e zosta³ zaatakowany
        if((generator.nextDouble()*10)>(4-addPoint)) {
            defender.changeHp(40);
            defender.setWasAttacked();
        }
    }
}
