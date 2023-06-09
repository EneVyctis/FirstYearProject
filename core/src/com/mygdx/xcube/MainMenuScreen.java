package com.mygdx.xcube;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.xcube.block.Button;
import com.mygdx.xcube.block.Items;

public class MainMenuScreen implements Screen {
    final public XCube game;
    private final Button local;
    private final Button multiplayer;
    private final Items logo;
    private final Button IA;
    private final Button DLC;
    private final Button Chrono;
    private float startTime = 150;
    private boolean dlc = false;
    Viewport viewport = new ExtendViewport(800, 480);
    float inputTime = 0;
    private final int width_screen = 540;
    private final int height_screen = 1200;
    OrthographicCamera camera;
    private boolean touchOff;

    public MainMenuScreen(XCube game, boolean dlc, float timer){
        this.game=game;
        //Valeurs modifiable selon le menu désiré
        local = new Button(100,300,"V2/bluebar1.png","Local",1);
        multiplayer = new Button(100,200,"V2/bluebar1.png","Multijoueur",1);
        IA = new Button(100,100,"V2/bluebar1.png","Intelligence Artificielle",1);
        this.dlc=dlc;
        if(dlc) {
            DLC = new Button(100, 400, "V2/bluebar1.png", "DLC Activés", 1);
        }
        else {
            DLC = new Button(100, 400, "V2/redbar1.png", "DLC Désactivés", 1);
        }
        this.startTime=timer;
        if(timer==90) {
            Chrono = new Button(100, 500, "V2/redbar1.png", "Temps : Pro (90 sec)", 1);
        }
        else if(timer==150) {
            Chrono = new Button(100, 500, "V2/bluebar1.png", "Temps : Medium (150 sec)", 1);
        }
        else {
            Chrono = new Button(100, 500, "V2/bluebar2.png", "Temps : Découverte (300 sec)", 1);
        }
        logo = new Items(width_screen/4,3*height_screen/4,"V2/title.png");
        camera = new OrthographicCamera();
        camera.setToOrtho(false,width_screen,height_screen);
    }

    public void render(float delta){            // Boucle infinie d'exécution
        inputTime += delta;
        ScreenUtils.clear(1,1,1,1);  // Supprime l'ancien background et en place un nouveau de la couleur rgb voulu
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();     // Début des éléments à afficher
        local.drawButton(game, 90);
        multiplayer.drawButton(game,90);
        IA.drawButton(game,90);
        DLC.drawButton(game,90);
        Chrono.drawButton(game,90);
        logo.drawItems(game,(float)(0.5));
        game.batch.end();       // Fin des éléments à afficher

        if (Gdx.input.isTouched() && touchOff){
            touchOff=false;
            Vector3 touchPos = new Vector3();                              //Création d'un vecteur à 3 coordonnées x,y,z
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);        // On récupère les coordonnées de touché
            camera.unproject(touchPos);                                    // On adapte les coordonnées à la camera

            if(this.multiplayer.contains(touchPos.x,touchPos.y)){
                game.setScreen(new Multiplayer(game, 150, false));   // Si l'écran est touché, l'écran passe à GameScreen
                dispose();                              // Supprime les élements définie dans dispose ( ici aucun)
            }
            else if(this.local.contains(touchPos.x,touchPos.y)){
                game.setScreen(new GameScreen(game,0, startTime, dlc));   // Si l'écran est touché, l'écran passe à GameScreen
                dispose();                              // Supprime les élements définie dans dispose ( ici aucun)
            }
            else if(this.IA.contains(touchPos.x,touchPos.y)){
                game.setScreen(new GameScreen(game,2, startTime, dlc));   // Si l'écran est touché, l'écran passe à GameScreen
                dispose();                              // Supprime les élements définie dans dispose ( ici aucun)
            }
            else if(this.DLC.contains(touchPos.x,touchPos.y)){
                dlc=!dlc;
                if(dlc) {
                    DLC.setSprite("V2/bluebar1.png");
                    DLC.setText("DLC Activés");
                }
                else {
                    DLC.setSprite("V2/redbar1.png");
                    DLC.setText("DLC Désactivés");

                }
            }
            else if(this.Chrono.contains(touchPos.x,touchPos.y)){
                if(startTime==90) {
                    startTime=150;
                    Chrono.setSprite("V2/bluebar1.png");
                    Chrono.setText("Temps : Medium (150 sec)");
                }
                else if(startTime==150){
                    startTime=300;
                    Chrono.setSprite("V2/bluebar2.png");
                    Chrono.setText("Temps : Découverte (300 sec)");
                }
                else if(startTime==300){
                    startTime=90;
                    Chrono.setSprite("V2/redbar1.png");
                    Chrono.setText("Temps : Pro (90 sec)");
                }
            }
        }
        if (!Gdx.input.isTouched()) {
            touchOff = true;
        }
    }

    // Fonctions non utilisées recquises par l'implementation de screen
    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public void show(){

    }
}
