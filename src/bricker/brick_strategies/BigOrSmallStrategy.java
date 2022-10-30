package bricker.brick_strategies;

import bricker.gameobjects.BigOrSmall;
import bricker.gameobjects.Brick;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.util.Vector2;

import java.util.Random;

public class BigOrSmallStrategy extends RemoveBrickStrategy{


    private GameObjectCollection object;
    private BrickerGameManager manager;
    private ImageReader imageReader;
    private Random rand = new Random();

    /**
     * Constructor
     * @param object
     * @param manager
     * @param imageReader
     */
    public BigOrSmallStrategy(GameObjectCollection object, BrickerGameManager manager, ImageReader imageReader) {
        super(object);
        this.manager = manager;
        this.imageReader = imageReader;
    }

    /**
     * On collision do something
     * @param thisObj
     * @param otherObj
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        Vector2 vec= thisObj.getCenter();
        super.onCollision(thisObj, otherObj);
        manager.CreateBigOrSmall(imageReader, vec, rand.nextBoolean());
    }
}
