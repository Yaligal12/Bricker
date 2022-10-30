package bricker.brick_strategies;

import bricker.gameobjects.Brick;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.util.Vector2;

public class MorePaddleStrategy extends RemoveBrickStrategy{
    private BrickerGameManager manager;
    private ImageReader imageReader;
    private UserInputListener inputListener;

    private boolean CREATED;

    /**
     * Constructor
     * @param object
     * @param manager
     * @param imageReader
     * @param inputListener
     */
    public MorePaddleStrategy(GameObjectCollection object, BrickerGameManager manager, ImageReader imageReader , UserInputListener inputListener){
        super(object);
        this.manager = manager;
        this.imageReader = imageReader;
        this.inputListener = inputListener;
        BrickerGameManager.MORE_PADDLE = true;
        CREATED = false;
    }

    /**
     * When a collision occurring do something
     * @param thisObj
     * @param otherObj
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        super.onCollision(thisObj, otherObj);
        if(!CREATED) {
            manager.CreatePaddle(imageReader, inputListener, manager.paddle);
            CREATED = true;
        }
    }
}
