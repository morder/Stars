package skeleton;

/**
 * @author Yanis Biziuk
 */
public abstract class WindowController {
    protected final Window mWindow;
    public WindowController(Window window){
        mWindow = window;
    }
    public Window getWindow(){
        return mWindow;
    }
    public abstract void tick(long delta_time);
    public abstract int getScreenPosInGameX();
    public abstract int getScreenPosInGameY();
    public abstract void setScreenPosInGameX(int X);
    public abstract void setScreenPosInGameY(int Y);

    public abstract int getGameWidth();
    public abstract int getGameHeight();
}
