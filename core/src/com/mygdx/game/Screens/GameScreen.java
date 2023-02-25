package com.mygdx.game.Screens;

import static com.mygdx.game.Extra.Utils.SCREEN_HEIGHT;
import static com.mygdx.game.Extra.Utils.SCREEN_WIDTH;
import static com.mygdx.game.Extra.Utils.USER_ROCA;
import static com.mygdx.game.Extra.Utils.USER_SONIC;
import static com.mygdx.game.Extra.Utils.WORLD_HEIGHT;
import static com.mygdx.game.Extra.Utils.WORLD_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Actors.Obstaculos;
import com.mygdx.game.Actors.Sonic;
import com.mygdx.game.Actors.Stagee;
import com.mygdx.game.MainGame;

import javax.net.ssl.SSLPeerUnverifiedException;

public class GameScreen extends BaseScreen  {
    //Variable que se encarga de el tiempo de Spawn de la rocas.
    private static final float SPAWN = 2f;
    //Variable que se encarga de guaradar el tiempo de creacion de las rocas.
    private float tiempocreacion;

    private Stage stage;
    private Sonic sonic;
    private Image background;
    private World world;
    private Stagee stagee;
    Obstaculos roca;
    int nroca = 0;
    private Music music;

    private boolean muerto = false;
    public int scoreNumber;
    //Cracion de array donde meteremos las rocas que se van saliendo de la pantalla de juego.
    private Array<Obstaculos> arrayroca;

    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera worldcamera;

    //Creacion de las camaras
    private OrthographicCamera fontCamera;
    //BitmappFont para la manejar el texto.
    private BitmapFont scorelevel;

    //Constructor
    public GameScreen(MainGame mainGame) {
        super(mainGame);

        this.world = new World(new Vector2(0,-10),true);
        FitViewport fitViewport = new FitViewport(WORLD_WIDTH,WORLD_HEIGHT);
        this.stage = new Stage(fitViewport);

        this.arrayroca = new Array();
        this.tiempocreacion = 0f;

        this.music = this.mainGame.assetManager.getmusic();

        this.worldcamera = (OrthographicCamera) this.stage.getCamera();
        this.debugRenderer = new Box2DDebugRenderer();

        Score();

    }

    //Metodo encargado de añadir el fondo a nuestra pantalla de juego.
    public void addBackground(){
        //Guadamos dentro de la varible el fondo de la pantalla de jugo que nos devuelve el metodo.
        this.background = new Image(mainGame.assetManager.getBackground());
        //Indicamos en que posicion se va a encontrar.
        this.background.setPosition(0,0);
        //Indicamos el tamaño que va a ocupcar la imagen.
        this.background.setSize(WORLD_WIDTH,WORLD_HEIGHT);
        //Por ultimo añadimos el actor.
        this.stage.addActor(this.background);


    }

    public void addSonic(){
        Animation<TextureRegion> sonicSprite = mainGame.assetManager.getSonicAnimation();
        Sound sound = mainGame.assetManager.getsound();
        this.sonic = new Sonic(this.world,sonicSprite,sound , new Vector2(0.5f,1f));
        this.stage.addActor(this.sonic);
    }

    public void addroca(float delta){
        TextureRegion roca = mainGame.assetManager.getrocaTR();
        if(sonic.getState() == Sonic.STATE_NORMAL){
            //Sumamos el tiempo delta  a la variable hata que llegue al tiempo para la siguente roca.
            this.tiempocreacion+=delta;
            //Si es mayor a lo indicado se crea una roca.
            if (this.tiempocreacion >= SPAWN){
                //Y le restamos el tiempo que se ha pasdo para que no haya prolemas en el spawn.
                this.tiempocreacion -= SPAWN;
                //Indicamos donde se crea la roca que sera fuera de la pantlla de juego.
                this.roca = new Obstaculos(this.world,roca,new Vector2(5f,1f));
                arrayroca.add(this.roca);
                this.stage.addActor(this.roca);
            }
        }
    }

    //Metodo que se ancarga de eliminar las rocas.
    public void removeroca() {
        //Recorremos el array
        for (Obstaculos roca : this.arrayroca) {
            //Si el mundo no esta bloqueado
            if (!world.isLocked()) {
                //Si la roca esta fuera de la pantalla
                if (roca.fuerapantalla()) {
                    //Elimanamos los recursos de la roca
                    roca.detach();
                    roca.remove();
                    //Añadimos 1 a la variable nroca
                    nroca = nroca + 1;
                    //Cada vez que se elimine una roca se sumara uno al contador ya que indicara que la hemos saltado.
                    scoreNumber++;
                    //Eliminamos la roca del array
                    arrayroca.removeValue(roca, false);
                    //Si la variable nroca llega  a 3 se dublica la velocidad de las rocas.
                    if (nroca == 3) {
                        roca.SPEED = roca.SPEED - 2f;
                        //Se vuelve a inicializar la variable a 0.
                        nroca = 0;

                    }
                }
            }
        }
    }
    //Metodo que se encarrga de indicar que si el personaje sonic esta fuera de ka pantalla se para la musica la roca.
    public void sonicfuera(){
        if (sonic.isOutOfScreenSonic()){
            this.music.stop();
            for (Obstaculos obstaculos: arrayroca){
                obstaculos.pararrocas();
            }
            //Indicamos con una variable booleana que el persnajes esta fuera por lo tanto ha muerto. Y en el render indicamos que si es true, se pasa a la pantalla de GameOver.
            muerto = true;
        }
    }

//Metodo que se encarga de la
    private void Score(){

        //Cargamos la fuente y le indicamos el tamaño.
        this.scorelevel = this.mainGame.assetManager.getFont();
        this.scorelevel.getData().scale(1f);

        //Creamos la camara, le indicamos el tamaño y luego se actualiza
        this.fontCamera = new OrthographicCamera();
        this.fontCamera.setToOrtho(false,SCREEN_WIDTH,SCREEN_HEIGHT);
        this.fontCamera.update();
    }

//Metodo que se encarga de renderizar el videojuego.
    @Override
    public void render(float delta){
        //Si la variable es true se cambia de pantalla.
        if (muerto)
            mainGame.setScreen(new GameOverScreen(mainGame));

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //Añadimos la roca a patir del tiempo delta pasapor por pantalla.
        addroca(delta);
        //Le pasamos  al batch, los datos de la cámara del mundo
        this.stage.getBatch().setProjectionMatrix(worldcamera.combined);
        this.stage.act();
        this.world.step(delta,6,2);
        this.stage.draw();

        this.debugRenderer.render(this.world, this.worldcamera.combined);
        //Eliminamos la roca
        removeroca();

        //A quie cargamos la matriz de proyección con los datos de la cámara de la fuente para que proyecte el texto con las dimensiones en píxeles
        this.stage.getBatch().setProjectionMatrix(this.fontCamera.combined);
        this.stage.getBatch().begin();
        this.scorelevel.draw(this.stage.getBatch(), ""+this.scoreNumber,SCREEN_WIDTH/2, 725);
        this.stage.getBatch().end();
        //Llamamos al metodo
        sonicfuera();
    }

    //Metodo encargado de mostrar los elemtos en pantalla.
    @Override
    public void show(){
        //Añadimos los metodos.
        addBackground();
        addSonic();

        //Aqui lo que hacemos es indicar donde se van a poner las coliones tanto del suelo como del techo.
        TextureRegion fondo = mainGame.assetManager.getBackground();
        this.stagee = new Stagee(this.world,fondo,new Vector2(0,1));
        this.stagee = new Stagee(this.world,fondo,new Vector2(0,8));

        //Indicamos que la musica se ejecuta en bucle.
        this.music.setLooping(true);
        //Se inicia la musica.
        this.music.play();

    }

    //Metodo encargado de eliminar recursos.
    @Override
    public void hide(){
        this.sonic.detach();
        this.sonic.remove();

        this.roca.detach();
        this.roca.remove();

        this.music.stop();
        this.stage.dispose();

        //Volvemos a iniciarlizar la velocidad de la roca a la original.
        roca.SPEED = -1.5f;

    }

    @Override
    public void dispose(){
        this.stage.dispose();
        this.world.dispose();
    }


}
