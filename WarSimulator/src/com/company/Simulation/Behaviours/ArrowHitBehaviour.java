package com.company.Simulation.Behaviours;

import com.company.Simulation.Agents.Soldiers.Soldier;
import com.company.Simulation.Behaviours.BasicBahaviours.OneShotBehaviour;

import java.util.Random;

/**
 * Created by Szymon on 2015-10-31.
 */
public class ArrowHitBehaviour extends OneShotBehaviour {

    Soldier hitSoldier;

    public ArrowHitBehaviour(Soldier _hitSoldier)
    {
        super();
        hitSoldier = _hitSoldier;
    }

    @Override
    public void action() {
        //doda� kod, kt�ry okre�li co si� stanie, jak soldier oberwie strza��
        //!!!!! dodawa� tylko te rzeczy, kt�re nie zale�� od jego decyzji
        //za decyzj� odpowiada behaviour SoldierBehaviour
        //tutaj doda� tylko utrat� �ycia, ewentualnie �mier�

        //W sumie ju� jest dodane, nie ma co rozszerza�
        Random gen = new Random();
        if(gen.nextDouble()*10>4)
            hitSoldier.changeHp(30);
    }
}
