package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.util.Vector2;

import java.util.Random;


public class FastOrSlowStrategy extends RemoveBrickStrategy{
    private BrickerGameManager manager;
    private ImageReader imageReader;
    private GameObjectCollection objects;
    private Random rand = new Random();

    /**
     * Constructor
     * @param object
     * @param manager
     * @param imageReader
     */
    public FastOrSlowStrategy(GameObjectCollection object, BrickerGameManager manager, ImageReader imageReader) {
        super(object);
        this.manager = manager;
        this.imageReader = imageReader;
    }

    /**
     * When a collision occurring do something
     * @param thisObj
     * @param otherObj
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        Vector2 vec= thisObj.getCenter();
        super.onCollision(thisObj, otherObj);
        manager.CreateFastOrSlow(imageReader, vec,rand.nextBoolean());
    }
}
