import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import skeleton.Planet;
import skeleton.WindowController;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Yanis Biziuk
 */
public class MainController extends WindowController{
    public GestureDetector.GestureListener mGesturesListener = new GestureDetector.GestureListener() {
        @Override
        public boolean touchDown(float v, float v2, int i, int i2) {
            return false;
        }

        @Override
        public boolean tap(float x, float y, int i, int i2) {
            for(Planet planet: mPlanets){
                if (screenToGameX(x) > planet.mPosX && screenToGameX(x) < planet.mPosX + planet.mRadius * 2){
                    if (screenToGameY(y) > planet.mPosY - planet.mRadius * 2 && screenToGameY(y) < planet.mPosY ){ // картинка рисуется вверх и вправо
                        int test = 1;
                    }
                }
            }
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
            MainController.this.deltaX += deltaX;
            MainController.this.deltaY += deltaY;
            /*mCurrentScreenPosX -= deltaX;
            mCurrentScreenPosY -= deltaY;

            if (mCurrentScreenPosX < 0) mCurrentScreenPosX = 0;
            if (mCurrentScreenPosX > mGameWidth - mScreenWidth) mCurrentScreenPosX = mGameWidth - mScreenWidth;

            if (mCurrentScreenPosY < 0) mCurrentScreenPosY = 0;
            if (mCurrentScreenPosY > mGameHeight - mScreenHeight) mCurrentScreenPosY = mGameHeight - mScreenHeight;*/

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

    private int deltaX;
    private int deltaY;

    public int mScreenWidth;
    public int mScreenHeight;
    public int mGameWidth;
    public int mGameHeight;

    public Texture mBackground;
    public int mCurrentScreenPosX = 0;
    public int mCurrentScreenPosY = 0;

    public int mPlayerCrystals = 0;
    public int mPlayerGas = 0;

    public ArrayList<Planet> mPlanets = new ArrayList<Planet>();

    public MainController(){
        mBackground = new Texture(Gdx.files.internal("1680x1050.jpg"));

        mScreenWidth = Gdx.graphics.getWidth();
        mScreenHeight = Gdx.graphics.getHeight();
        mGameWidth = mBackground.getWidth();
        mGameHeight = mBackground.getHeight();

        Random random = new Random();
        for(int i=0;i<10;i++){
            mPlanets.add(
                    new Planet(random.nextInt(mGameWidth - 100) + 50, random.nextInt(mGameHeight - 100) + 50, 25, new Texture(Gdx.files.internal("planet.png")) )
            );
        }
    }

    private void dragScreen(){
        if (deltaX != 0){
            int delta = (int) (Math.abs(deltaX) * 0.4);
            if (deltaX > 0){
                mCurrentScreenPosX -= delta;
                deltaX -= delta;
            } else {
                mCurrentScreenPosX -= -delta;
                deltaX += delta;
            }
            if (mCurrentScreenPosX < 0) mCurrentScreenPosX = 0;
            if (mCurrentScreenPosX > mGameWidth - mScreenWidth) mCurrentScreenPosX = mGameWidth - mScreenWidth;
        }
        if (deltaY != 0){
            int delta = (int) (Math.abs(deltaY) * 0.4);
            if (deltaY > 0){
                mCurrentScreenPosY -= delta;
                deltaY -= delta;
            } else {
                mCurrentScreenPosY -= -delta;
                deltaY += delta;
            }
            if (mCurrentScreenPosY < 0) mCurrentScreenPosY = 0;
            if (mCurrentScreenPosY > mGameHeight - mScreenHeight) mCurrentScreenPosY = mGameHeight - mScreenHeight;
        }
    }

    @Override
    public void tick(long delta_time) {
        dragScreen();
    }

    public float gameToScreenX(float X){
        return X - mCurrentScreenPosX;
    }

    public float gameToScreenY(float Y){
        // с преобразованием к нижнему левому углу
        return (mGameHeight - Y) - (mGameHeight - mCurrentScreenPosY - mScreenHeight);
    }

    public float screenToGameX(float X){
        return X + mCurrentScreenPosX;
    }

    public float screenToGameY(float Y){
        return Y + mCurrentScreenPosY;
    }
}
