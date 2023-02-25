package com.mygdx.game.Actors;

import static com.mygdx.game.Extra.Utils.USER_ROCA;
import static com.mygdx.game.Extra.Utils.USER_SONIC;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Extra.Utils;

//Clase obstaculos que se encarga de la creacion de las rocas extiende de Actor.
public class Obstaculos extends Actor {
    //Atributos de medidas y velocidad de la roca.
    private static final float ROCA_WIDTH = 1.3f;
    private static final float ROCA_HEIGHT = 1.3f;
    public static  float SPEED = -1.5f;

    //Atributos para la creacion del cuerpo textura de la roca.
    private TextureRegion rocaTR;
    private Body body;
    private Fixture fixture;
    private World world;

    //Constructor
    public Obstaculos(World world, TextureRegion rocaTR, Vector2 position){
        this.world = world;
        this.rocaTR = rocaTR;
        //Inicializmaos los metodos
        createBodyRoca(position);
        createFixture();



    }
    //El metodo createBodyRoca se encagara de la creacion del Body de la roca.Le pasamos por parametro un Vector de posicion.
    private void createBodyRoca(Vector2 position) {
        //Creamos el BodyDef
        BodyDef bodyDef = new BodyDef();
        //Le pasamos la posicion donde quereos que este el cuerpo de la roca.
        bodyDef.position.set(position);
        //Le indicamos el tipo de Body que va a tener en este caso KynematicBody, al cual no le afectan ni las colisiones ni la gravedad pero tienen una animacion.
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        body = world.createBody(bodyDef);
        body.setUserData(Utils.USER_ROCA);
        //Indicamos la velocidad que va a tener la animacion de la roca.
        body.setLinearVelocity(SPEED,0);
    }

    //Metodo que se encarga de la creacion de la textura de la roca.
    private void createFixture() {
        //Creamos una textura que sea de forma circular y que tenga un diametro de 0.6f.
        CircleShape circle = new CircleShape();
        circle.setRadius(0.6f);

        //Inicialiamos la variable al metodo y le pasamos una posicion por parametro.
        this.fixture = this.body.createFixture(circle,8);
        //Le pasamos el usuario por parametro.
        this.fixture.setUserData(USER_ROCA);

        circle.dispose();
    }

    //Metodo que se encarga de retonar si el cuerpo ha salido de la pantalla de juego.
    public boolean fuerapantalla(){
        return this.body.getPosition().x <= -1f;
    }

    //Metodo que se emcarga de deterner las rocas.
    public void pararrocas(){

        body.setLinearVelocity(0,0);
    }



    @Override
    public void act(float delta) {
        super.act(delta);
    }

    //Metodo enccargado de dibujar en el mundo tanto el cuerpo como la roca.
    @Override
    public void draw (Batch batch, float parentAlpha){
        setPosition(body.getPosition().x-0.5f, body.getPosition().y-0.6f);
       batch.draw(this.rocaTR,getX(),getY(),ROCA_WIDTH,ROCA_HEIGHT);
    }

    //Metodo encargado de la liberacion de recursos.
    public void detach(){
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }




}
