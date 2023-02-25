package com.mygdx.game.Extra;

import static com.mygdx.game.Extra.Utils.ATLAS_MAP;
import static com.mygdx.game.Extra.Utils.BACKGROUND_IMAGE;
import static com.mygdx.game.Extra.Utils.FONT_FNT;
import static com.mygdx.game.Extra.Utils.FONT_PNG;
import static com.mygdx.game.Extra.Utils.GAMEOVER;
import static com.mygdx.game.Extra.Utils.MUSIC_BG;
import static com.mygdx.game.Extra.Utils.ROCA;
import static com.mygdx.game.Extra.Utils.SONIC1;
import static com.mygdx.game.Extra.Utils.SONIC2;
import static com.mygdx.game.Extra.Utils.SONIC3;
import static com.mygdx.game.Extra.Utils.SONIC4;
import static com.mygdx.game.Extra.Utils.SOUND_DEAD;
import static com.mygdx.game.Extra.Utils.SOUND_JUMP;
import static com.mygdx.game.Extra.Utils.START;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
//Creacion de clase para la obtencion de los dieferentes elemenos del atlas.
public class AssetMan {
    //Creacion de atributos assetMangager y textureAtlas.
    private AssetManager assetManager;
    private TextureAtlas textureAtlas;

    public AssetMan(){
        //Creamos un AssetManger
        this.assetManager = new AssetManager();
        //Cargamos los diferentes elementos del atlas al assetManger
        assetManager.load(ATLAS_MAP, TextureAtlas.class);
        assetManager.load(SOUND_JUMP, Sound.class);
        assetManager.load(SOUND_DEAD,Sound.class);
        assetManager.load(MUSIC_BG, Music.class);
        assetManager.finishLoading();

        //Inicializamos el textureAtlas al assetManager.
        this.textureAtlas = assetManager.get(ATLAS_MAP);
    }

    //Metodo que se encarga de retornar la imagen de fondo de la pantalla de juego del atlas.
    public TextureRegion getBackground(){
        return this.textureAtlas.findRegion(BACKGROUND_IMAGE);
    }

    //Metodo que se encarga de la creacion de la animacion de Sonic y retornarla, a traves de diferentes imagenes.
    public Animation<TextureRegion> getSonicAnimation(){
        return new Animation<TextureRegion>(0.33f,//Division del tiempo de un frame comlpeto por cada imagen para la craacion de la animacion.
                textureAtlas.findRegion(SONIC1),
                textureAtlas.findRegion(SONIC2),
                textureAtlas.findRegion(SONIC3),
                textureAtlas.findRegion(SONIC4));
    }
    //Metodo que se encarga de retornar  el boton start del atlas.
    public TextureRegion getStart(){
        return this.textureAtlas.findRegion(START);
    }

    public TextureRegion getGameOver(){
        return this.textureAtlas.findRegion(GAMEOVER);

    }

    //TEXTTURA DE LA ROCA
    public TextureRegion getrocaTR(){
        return this.textureAtlas.findRegion(ROCA);
    }
    //Metodo que se encarga de sacar la
    public BitmapFont getFont(){
        return new BitmapFont(Gdx.files.internal(FONT_FNT),Gdx.files.internal(FONT_PNG),false);
    }
    //Metodos que se encarga de retornar los sonido y musica del juego.
   public Sound getsound(){
       return this.assetManager.get(SOUND_JUMP);
   }

   public Sound getDeadSound(){
       return this.assetManager.get(SOUND_DEAD);
   }

    public Music getmusic(){
        return this.assetManager.get(MUSIC_BG);
    }
}



