package space.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import skeleton.TextureObject;
import skeleton.WindowController;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Yanis Biziuk
 */
public class Planet extends TextureObject {
    public int mRadius;

    public String mName = "default";

    protected int mCrystals = 10000;
    protected int mGas = 2000;
    private int mCrystalsInterval = 1;
    private int mGasInterval = 3;

    public int mCrystalLevel = 1;
    public int mFactoryLevel = 0;
    public int mDefenseLevel = 0;

    public int mShips; // TODO
    public int mOwner; // TODO

    PlanetLayer mPlanetLayer;
    //private AtomicBoolean mPlanetLayerShowed = new AtomicBoolean(false);

    public Planet(WindowController controller, TextureObject before, int posX, int posY, int radius, Texture texture){
        super(controller, before, posX, posY, texture);
        mRadius = radius;

        //mPlanetLayer = new PlanetLayer(getController(), mGlobalX - 127, mGlobalY + 126, new Texture(Gdx.files.internal("layer.png")) ); // TODO: create if need
        //getController().getWindow().addObjectToStack(mPlanetLayer, null);
    }

    public void hideLayer(){
        if (mPlanetLayer.isVisible()){
            //getController().getWindow().removeObjectFromStack(mPlanetLayer);
            //mPlanetLayerShowed.set(false);
            mPlanetLayer.hide();
        }
    }

    public void showLayer(){
        if (mPlanetLayer == null){
            mPlanetLayer = new PlanetLayer(getController(), this, mGlobalX - 127, mGlobalY + 126, new Texture(Gdx.files.internal("layer.png")) );
        }
        if (!mPlanetLayer.isVisible()){
            //mPlanetLayerShowed.set(true);
            mPlanetLayer.show();
            //getController().getWindow().addObjectToStack(mPlanetLayer, null);
        }
    }

    public int getCrystals(){
        return mCrystals;
    }

    public int getGas(){
        return mGas;
    }

    public boolean dotInside(float x, float y){
        float convertedX = screenToGameX(x);
        float convertedY = screenToGameY(y);
        float centerX = mGlobalX + mRadius;
        float centerY = mGlobalY - mRadius;
        float distance = (float) Math.sqrt(Math.pow(centerX - convertedX, 2) + Math.pow(centerY - convertedY, 2));
        return distance <= mRadius;
    }

    public boolean _tap(float x, float y, int i, int i2){
        if (dotInside(x, y)){
            //getController().getWindow().addObjectToStack(mPlanetLayer, null);
            return true;
        }
        return false;
    }

    public int getGatherCrystals(int currentStep){
        int quantity =  getGatherQuantity(currentStep, mCrystalsInterval, mCrystalLevel);
        mCrystals -= quantity;
        return quantity;
    }

    public int getGatherGas(int currentStep){
        int quantity =  getGatherQuantity(currentStep, mGasInterval, mCrystalLevel);
        mGas -= quantity;
        return quantity;
    }

    private int getGatherQuantity(int currentStep, int timeInterval, int gatherQuantity){
        ArrayList<Integer> Cells = new ArrayList<Integer>(timeInterval);
        for(int i=0;i<timeInterval;i++){
            float count = (float)gatherQuantity / (timeInterval - i);
            int cell_value = Math.round(count);
            Cells.add(cell_value);
            gatherQuantity -= cell_value;
        }
        return Cells.get( currentStep % timeInterval );
    }

    @Override
    public void render(SpriteBatch mSpriteBatch) {
        mSpriteBatch.draw(getTexture(), getScreenX(), getScreenY(), mRadius * 2, mRadius * 2);
    }
}
