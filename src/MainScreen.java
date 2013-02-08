import skeleton.*;

/**
 * @author Yanis Biziuk
 */
public class MainScreen extends Screen {
    public MainScreen(skeleton.Game parent){
        super(parent);
        MainWindow mw = new MainWindow(this);
        addWindow(mw);
    }

    public void init(){
        super.init();
    }
}
