package com.company.Simulation.Agents;

import com.company.Enviroment.Map;
import com.company.Simulation.Agents.Soldiers.Soldier;
import com.company.Simulation.Behaviours.ArrowHitBehaviour;
import com.company.Simulation.Behaviours.BasicBahaviours.WakerBehaviour;

/**
 * Created by Szymon on 2015-10-31.
 */
public class ArrowPhysic extends Agent {

    //squad ³uczniczy albo sami ³ucznicy wywo³uj¹ setNextArrowAttack (wystrzelenie strza³y w powietrze)

    public ArrowPhysic(Map map){
        simMap = map;
    }

    Map simMap;
    public void setNewArrowAttack(final int x, final int y){

        //dodaæ obliczanie czasu lotu strza³y
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

    //dodaæ metody obliczaj¹ce lot strza³
}
