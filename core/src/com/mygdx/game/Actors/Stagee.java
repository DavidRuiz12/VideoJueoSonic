package com.mygdx.game.Actors;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Extra.Utils;

public class Stagee extends Actor {

    private static final float STAGE_WIDTH = 4.8f;
    private static final float STAGE_HEIGHT = 8f;


    private TextureRegion stageTR;
    private Body body;
    private Body bodycounter;
    private Fixture fixtureCounter;
    private World world;

    public Stagee(World world, TextureRegion stageTR, Vector2 position){
        this.world = world;
        this.stageTR = stageTR;
        createBodyStage(position);


    }
    private void createBodyStage(Vector2 position) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position);
        bodyDef.type = BodyDef.BodyType.StaticBody;

        EdgeShape shape = new EdgeShape();
        shape.set(0,0,STAGE_WIDTH,0);
        FixtureDef fixDef = new FixtureDef();
        fixDef.shape = shape;

        body = world.createBody(bodyDef);
        body.createFixture(fixDef);
        shape.dispose();
    }


}
