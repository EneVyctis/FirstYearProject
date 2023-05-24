package com.mygdx.xcube.block;

import static com.mygdx.xcube.GameScreen.camera;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.xcube.GameScreen;
import com.mygdx.xcube.Multiplayer;

import java.util.ArrayList;

public class TerrainBlock extends Block {
    public boolean isSquare;
    public boolean isFree;
    public boolean isBlue;

    public TerrainBlock(int x, int y) {
        this.x = x;
        this.y = y;
        isFree=true;

    }
    public void setSprite(String texture) {
        this.sprite = new Sprite(new Texture(Gdx.files.internal(texture)));
    }
    public Sprite getSprite() {
        return this.sprite;
    }

    public int[] getSize() {

        return new int[]{dx,dy};
    }

    public void clickBlock(String texture, GameScreen gameScreen) {
        //rectangle.contains permet de savoir si le point que l'on indique appartient au rectangle
        //Gdx.input.get renvoie automatiquement la coordonnée X/Y sur laquelle on clique.
        Vector3 touchPos = new Vector3();                              //Création d'un vecteur à 3 coordonnées x,y,z
        touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);        // On récupère les coordonnées de touché
        camera.unproject(touchPos);                                    // On adapte les coordonnées à la camera
        if(gameScreen.getMode() == 1){                                 //En multi, on envoie les coordonnées à l'autre joueur
           Multiplayer.send(touchPos);
        }
        if(this.rectangle.contains(touchPos.x, touchPos.y) && this.isClickable()){ // On test si l'endroit touché est un rectangle et s'il est libre
            changeBlock(texture,gameScreen);
        }

    }

    public void clickBlock(String texture, GameScreen gameScreen, Vector3 touchPos) { //Pareil mais en multi
        if(this.rectangle.contains(touchPos.x, touchPos.y) && this.isClickable()){
            changeBlock(texture,gameScreen);
        }
    }

    public void iaClickBlock(String texture, GameScreen gameScreen){ //pareil mais pour l'IA
       changeBlock(texture,gameScreen);
    }

    private void changeBlock(String texture, GameScreen gameScreen) {
        isFree=false;                                                          //Le rectangle ne peut plus être cliqué
        isBlue=gameScreen.players.getPlayer();                                 //Il prend la couleur du joueur dont c'est le tour
        this.setSprite(texture);                                               //Il change de texture
        if(this.isSquare && gameScreen.getMode()!=3 && gameScreen.checkEveryAlign(gameScreen.players.getPlayer())) {                                                   //vérifie si une condition de victoire est remplie
            gameScreen.setVictoryScreen(gameScreen.players.getPlayer());
        }
        gameScreen.terrain.getLastPlay().add(this);                           //Est enregistré comme le dernier coup joué pour apparaitre d'une couleur différente de la normal
        gameScreen.terrain.addPlay(this);
        gameScreen.players.setCoup(gameScreen.terrain);                       //Vérifie combien de coup il reste au joueur actuel pour jouer, et change la couleur des blocks récemment joués

    }


    public void drawBlock(SpriteBatch batch) {
        batch.draw(this.sprite,x,y,0,0,dx,dy,1,1,0);
    }

    public boolean isClickable() { //Vérifie si le rectangle cliqué est cliquable
        if(!isFree) {
            return false;
        }
        if(this.isSquare) {
            HollowSquare square = (HollowSquare) this;
            return square.FillNeighbors();
        }
        return true;
    }
    public int[] getCoords() {

        return new int[]{x,y};
    } //retourne les coordonnées d'un carré
    public void unPlay(GameScreen gameScreen) {
        isFree=true;                                                          //Le rectangle ne peut plus être cliqué
        if(isSquare) {
            setSprite("square_holder.png");
        }
        else {
            setSprite("greybarv2.png");
        }
        gameScreen.players.backCoup(gameScreen.terrain);                       //Vérifie combien de coup il reste au joueur actuel pour jouer, et change la couleur des blocks récemment joués

    }
    public void rePlay(GameScreen gameScreen) {
        isFree=false;                                                          //Le rectangle ne peut plus être cliqué
        isBlue=gameScreen.players.getPlayer();                                 //Il prend la couleur du joueur dont c'est le tour
        if(isSquare) {
            if(isBlue) {
                setSprite("V2/bluecross2.png");
            }
            else {
                setSprite("V2/redcross2.png");
            }
        }
        else {
            if(isBlue) {
                setSprite("V2/bluebar2.png");
            }
            else {
                setSprite("V2/redbar2.png");
            }
        }
        gameScreen.players.setCoup(gameScreen.terrain);                       //Vérifie combien de coup il reste au joueur actuel pour jouer, et change la couleur des blocks récemment joués

    }

}
