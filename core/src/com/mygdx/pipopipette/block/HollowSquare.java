package com.mygdx.pipopipette.block;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class HollowSquare extends Block{

    public HollowBar[] neighbors;
    public HollowSquare(float x, float y) {
        super(x,y);
        dx=400;
        dy=400;
        rectangle = new Rectangle(x, y, dx, dy);
        isFree=false;

    }

    //public HollowBar[] FindNeighbors(Array<HollowBar> bar, int i) {
        //this.neighbors.add();
    //}

}