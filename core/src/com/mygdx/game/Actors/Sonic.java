package com.mygdx.game.Actors;

import static com.mygdx.game.Extra.Utils.USER_SONIC;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.awt.geom.RectangularShape;

public class Sonic extends Actor  {

    //Creacion de los estados en lo que se puede encontrar el personaje.
    public static final int STATE_NORMAL = 0;
    public static final float JUMP_SPEED = 6F;

    private int state;
    private Animation<TextureRegion> sonicanimation;
    private Vector2 position;
    private float stateTime;
    private World world;
    private Body body;
    private Fixture fixture;
    private Sound saltomusic;

    //Constructor
    public Sonic(World world, Animation<TextureRegion> animation,Sound sound ,Vector2 position) {
        this.sonicanimation = animation;
        this.position = position;
        this.world = world;
        this.stateTime = 0f;
        this.state = STATE_NORMAL;
        this.saltomusic = sound;

        createBody();
        createFixture();

    }


    public int getState(){
        return this.state;
    }


    public boolean isOutOfScreenSonic(){
        return this.body.getPosition().x <= -1f;

    }


    private void createBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(this.position);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        this.body = this.world.createBody(bodyDef);
    }

    private void createFixture() {
        CircleShape circle = new CircleShape();
        circle.setRadius(0.30f);

        this.fixture = this.body.createFixture(circle,8);
        this.fixture.setUserData(USER_SONIC);

        circle.dispose();
    }

    //Metodo que se encarga de la creacion de una actividad, en este caso el salto de Sonic.
    @Override
    public void act(float delta){
        //variable boleana que indica si tocamos la pantalla o no.
        boolean jump = Gdx.input.justTouched();

        //Si el estado de Sonic es normal.
       if(jump && this.state == STATE_NORMAL){
           //Reproducira el sonido del salto
            this.saltomusic.play();
            //Creacion del impilso al cuerpo.
        this.body.setLinearVelocity(0,JUMP_SPEED);

        }

    }

    @Override
    public void draw (Batch batch, float parentAlpha){
        setPosition(body.getPosition().x-0.4f, body.getPosition().y-0.25f);
        batch.draw(this.sonicanimation.getKeyFrame(stateTime,true),getX(), getY(),1f,1f);
        stateTime += Gdx.graphics.getDeltaTime();
    }

    //Metodo encargado de destruir la textura y cuerpo de sonic.
    public void detach(){
        this.body.destroyFixture(this.fixture);
        this.world.destroyBody(this.body);
    }



}
