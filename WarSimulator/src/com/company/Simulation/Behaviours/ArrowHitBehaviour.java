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
        //dodaæ kod, który okreœli co siê stanie, jak soldier oberwie strza³¹
        //!!!!! dodawaæ tylko te rzeczy, które nie zale¿¹ od jego decyzji
        //za decyzjê odpowiada behaviour SoldierBehaviour
        //tutaj dodaæ tylko utratê ¿ycia, ewentualnie œmieræ

        //W sumie ju¿ jest dodane, nie ma co rozszerzaæ
        Random gen = new Random();
        if(gen.nextDouble()*10>4)
            hitSoldier.changeHp(30);
    }
}
