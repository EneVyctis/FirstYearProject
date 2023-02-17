package com.mygdx.xcube.block;

import static com.mygdx.xcube.GameScreen.camera;
import static com.mygdx.xcube.GameScreen.players;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.xcube.End;
import com.mygdx.xcube.GameScreen;
import com.mygdx.xcube.PlayerManager;

import java.util.ArrayList;

public class Block {
    private Sprite sprite;
    Rectangle rectangle;
    final int x;
    final int y;
    int dx;
    int dy;
    public boolean isSquare;
    public boolean isFree;
    public boolean isBlue;
    public ArrayList<HollowBar> neighbors = new ArrayList<>();

    public Block(int x, int y) {
        this.x = x;
        this.y = y;
        isFree=true;
    }
    public void setSprite(String texture) {
        this.sprite = new Sprite(new Texture(Gdx.files.internal(texture)));
    }

    public void clickBlock(String texture, End end) {
        //rectangle.contains permet de savoir si le point que l'on indique appartient au rectangle
        //Gdx.input.get renvoie automatiquement la coordonnée X/Y sur laquelle on clique.

        Vector3 touchPos = new Vector3();                              //Création d'un vecteur à 3 coordonnées x,y,z
        touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);        // On récupère les coordonnées de touché
        camera.unproject(touchPos);                                    // On adapte les coordonnées à la camera
        if(this.rectangle.contains(touchPos.x, touchPos.y) && this.isClickable()){ // On test si l'endroit touché est un rectangle et s'il est libre
            isFree=false;
            if(players.getPlayer()) {
                isBlue=true;
            }
            else {
                isBlue=false;
            }
            this.sprite = new Sprite(new Texture(Gdx.files.internal(texture)));
            this.drawBlock();
            if(this.isSquare) { //vérifie si une condition de victoire est remplie
                end.checkAlign(GameScreen.players.getPlayer(), end);
            }
            PlayerManager.setCoup(GameScreen.players);
        }


    }
    /*public void clickSquareBlock(String texture, boolean player, End end) {
        //rectangle.contains permet de savoir si le point que l'on indique appartient au rectangle
        //Gdx.input.get renvoie automatiquement la coordonnée X/Y sur laquelle on clique.

        Vector3 touchPos = new Vector3();                              //Création d'un vecteur à 3 coordonnées x,y,z
        touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);        // On récupère les coordonnées de touché
        camera.unproject(touchPos);                                    // On adapte les coordonnées à la camera
        if (this.rectangle.contains(touchPos.x, touchPos.y) && this.isClickable()) { // On test si l'endroit touché est un rectangle et s'il est libre
            isFree = false;
            PlayerManager.setCoup(GameScreen.players);
            this.sprite = new Sprite(new Texture(Gdx.files.internal(texture)));
            if(player) {
                this.status = 0;
                this.drawBlock();
                end.winTest();
            }
            if(!player) {
                this.status = 1;
                this.drawBlock();
                end.winTest();
            }
        }
    }*/

    public void drawBlock() {
        camera.update();
        GameScreen.spriteBatch.begin();
        GameScreen.spriteBatch.draw(this.sprite,x,y,0,0,dx,dy,1,1,0);
        GameScreen.spriteBatch.end();
    }

    public boolean isClickable() {
        boolean clickable = this.isFree;
        if(this.isSquare) {
            for (HollowBar bar : this.neighbors
            ) {
                if(bar.isFree) {
                    clickable = false;
                }
            }
        }
        return clickable;
    }



}
