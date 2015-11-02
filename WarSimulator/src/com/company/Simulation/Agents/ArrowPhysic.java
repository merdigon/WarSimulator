package com.company.Simulation.Agents;

import com.company.Enviroment.Map;
import com.company.Simulation.Agents.Soldiers.Soldier;
import com.company.Simulation.Behaviours.ArrowHitBehaviour;
import com.company.Simulation.Behaviours.BasicBahaviours.WakerBehaviour;

/**
 * Created by Szymon on 2015-10-31.
 */
public class ArrowPhysic extends Agent {

    //squad �uczniczy albo sami �ucznicy wywo�uj� setNextArrowAttack (wystrzelenie strza�y w powietrze)

    public ArrowPhysic(Map map){
        simMap = map;
    }

    Map simMap;
    public void setNewArrowAttack(final int x, final int y){

        //doda� obliczanie czasu lotu strza�y
        int arrowTimeout = 1000;

        addBehaviour(
                new WakerBehaviour(arrowTimeout) {
                    public void action(){
                        Soldier sld;
                        if((sld = simMap.Terrain[x][y].getSoldier())!=null)
                        {
                            sld.addBehaviour(new ArrowHitBehaviour(sld));
                        }
                    }
                }
        );
    }

    //doda� metody obliczaj�ce lot strza�
}
