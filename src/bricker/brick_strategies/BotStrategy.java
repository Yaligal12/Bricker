package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;

import java.util.Random;

public class BotStrategy extends RemoveBrickStrategy{
    private BrickerGameManager manager;
    private ImageReader imageReader;
    private Random rand = new Random();
    private boolean CREATED;

    /**
     * Constructor
     * @param object
     * @param manager
     * @param imageReader
     */
    public BotStrategy(GameObjectCollection object, BrickerGameManager manager, ImageReader imageReader) {
        super(object);
        this.manager = manager;
        this.imageReader = imageReader;
        BrickerGameManager.BOT = true;
        CREATED = false;
    }

    /**
     * On collision do something
     * @param thisObj
     * @param otherObj
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        super.onCollision(thisObj, otherObj);
        if(!CREATED) {
            manager.CreateBot(imageReader, rand.nextBoolean(), manager.paddle);
            CREATED = true;
        }
    }
}
