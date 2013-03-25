package space;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import skeleton.*;
import space.objects.GameBackground;
import space.objects.Planet;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Yanis Biziuk
 */
public class MainController extends WindowController {
    public GestureDetector.GestureListener mGesturesListener = new GestureDetector.GestureListener() {
        @Override
        public boolean touchDown(float v, float v2, int i, int i2) {
            return false;
        }

        @Override
        public boolean tap(float x, float y, int i, int i2) {
            for(Planet planet: mPlanets){
                if (planet.dotInside(x, y)){
                    if (mPlayerHuman.mPlanets.contains(planet)){
                        mSelectedPlanet = planet;
                        return true;
                    }
                }
                /*if (screenToGameX(x) > planet.mPosX && screenToGameX(x) < planet.mPosX + planet.mRadius * 2){
                    if (screenToGameY(y) > planet.mPosY - planet.mRadius * 2 && screenToGameY(y) < planet.mPosY ){ // картинка рисуется вверх и вправо
                        if (mPlayerHuman.mPlanets.contains(planet)){
                            mSelectedPlanet = planet;
                            return true;
                        }
                    }
                }*/
            }

            //mSpriteBatch.draw(checker, gameToScreenX(planet.mPosX) - 40, gameToScreenY(planet.mPosY) + 48);
            //mSpriteBatch.draw(checker, gameToScreenX(planet.mPosX) - 40, gameToScreenY(planet.mPosY) - 20);
            //mSpriteBatch.draw(checker, gameToScreenX(planet.mPosX) + 65, gameToScreenY(planet.mPosY) + 48);

            /*if (mSelectedPlanet != null){
                int elemX = mSelectedPlanet.mPosX - 40;
                int elemY = mSelectedPlanet.mPosY - 48;
                if ( screenToGameX(x) > elemX && screenToGameX(x) < elemX + 23 ){
                    if ( screenToGameY(y) > elemY - 23 && screenToGameY(y) < elemY){
                        int test = 1;
                        return true;
                    }
                }
            }*/

            mSelectedPlanet = null;
            return false;
        }

        @Override
        public boolean longPress(float v, float v2) {
            return false;
        }

        @Override
        public boolean fling(float v, float v2, int i) {
            return false;
        }

        @Override
        public boolean pan(float fromX, float fromY, float deltaX, float deltaY) {
            //MainController.this.deltaX += deltaX;
            //MainController.this.deltaY += deltaY;

            /*mCurrentGamePosX -= deltaX;
            mCurrentGamePosY -= deltaY;

            if (mCurrentGamePosX < 0) mCurrentGamePosX = 0;
            if (mCurrentGamePosX > mGameWidth - mScreenWidth) mCurrentGamePosX = mGameWidth - mScreenWidth;

            if (mCurrentGamePosY < 0) mCurrentGamePosY = 0;
            if (mCurrentGamePosY > mGameHeight - mScreenHeight) mCurrentGamePosY = mGameHeight - mScreenHeight;*/

            return false;
        }

        @Override
        public boolean zoom(float v, float v2) {
            return false;
        }

        @Override
        public boolean pinch(Vector2 vector2, Vector2 vector22, Vector2 vector23, Vector2 vector24) {
            return false;
        }
    };

    private long mFeedbackTime;

    /* ###################### Game Variables ################## */
    int mCurrentGamePosX = 0;
    int mCurrentGamePosY = 0;

    int mGameWidth;
    int mGameHeight;

    GameBackground mBackground;

    ArrayList<Planet> mPlanets = new ArrayList<Planet>();
    Planet mSelectedPlanet;

    Player mPlayerHuman = new Player();
    Player mPlayerAI = new Player();
    /* ######################################################## */

    public MainController(Window window){
        super(window);

        mBackground = new GameBackground(this, null, 0, 0, new Texture(Gdx.files.internal("1680x1050.jpg")) );
        //mWindow.addObjectToStack(mBackground, null);

        mBackground.registerMoves("tap", TextureObject.ORDER.before, new TextureObject.callbackMove() {
            @Override
            public void event(Object object, String type, boolean result) {
                if (mSelectedPlanet != null){
                    mSelectedPlanet.hideLayer();
                    mSelectedPlanet = null;
                }
            }
        });

        mGameWidth = mBackground.getTexture().getWidth();
        mGameHeight = mBackground.getTexture().getHeight();

        Random random = new Random();
        for(int i=0;i<10;i++){
            Planet planet = new Planet(this, null, random.nextInt(mGameWidth - 100) + 50, random.nextInt(mGameHeight - 100) + 50, 25, new Texture(Gdx.files.internal("planet2.png")) );
            mPlanets.add(planet);
        }
        //getWindow().addObjectToStack(mPlanets, null);

        int nPlanet = random.nextInt(10);
        mPlayerHuman.addPlanet(mPlanets.get( nPlanet ));
        while (true){
            int newPlanet = random.nextInt(10);
            if (newPlanet != nPlanet){
                mPlayerAI.addPlanet(mPlanets.get( newPlanet ));
                break;
            }
        }

        mFeedbackTime = System.currentTimeMillis();


        mPlayerHuman.mPlanets.get(0).registerMoves("tap", TextureObject.ORDER.after, new TextureObject.callbackMove() {
            @Override
            public void event(Object object, String type, boolean result) {
                if (result){
                    if (mPlayerHuman.mPlanets.contains( (Planet)object )){
                        mSelectedPlanet = (Planet)object;
                        mSelectedPlanet.showLayer();
                    }
                }
            }
        });
    }

    private void feedback(){
        long tm = System.currentTimeMillis();
        int delta = (int) (tm / 1000 - mFeedbackTime / 1000);
        if (delta > 0){
            for(int pos=1;pos<=delta;pos++){ // throttling
                for(Planet planet: mPlayerHuman.mPlanets){
                    mPlayerHuman.mCrystals += planet.getGatherCrystals((int) ( mFeedbackTime / 1000 + pos ));
                    mPlayerHuman.mGas += planet.getGatherGas((int) ( mFeedbackTime / 1000 + pos ));
                }
                for(Planet planet: mPlayerAI.mPlanets){
                    mPlayerAI.mCrystals += planet.getGatherCrystals((int) ( mFeedbackTime / 1000 + pos ));
                    mPlayerAI.mGas += planet.getGatherGas((int) ( mFeedbackTime / 1000 + pos ));
                }
                mFeedbackTime = tm;
            }
        }
    }

    @Override
    public void tick(long delta_time) {
        mBackground.tick(delta_time);
        feedback();
    }

    @Override
    public int getScreenPosInGameX() {
        return mCurrentGamePosX;
    }

    @Override
    public int getScreenPosInGameY() {
        return mCurrentGamePosY;
    }

    @Override
    public void setScreenPosInGameX(int X) {
        mCurrentGamePosX = X;
    }

    @Override
    public void setScreenPosInGameY(int Y) {
        mCurrentGamePosY = Y;
    }

    @Override
    public int getGameWidth() {
        return mGameWidth;
    }

    @Override
    public int getGameHeight() {
        return mGameHeight;
    }

}
