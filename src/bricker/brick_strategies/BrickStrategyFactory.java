package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;

import java.util.Random;

public class BrickStrategyFactory {

    private ImageReader imageReader;
    private SoundReader soundReader;
    private CollisionStrategy strategy;
    private UserInputListener inputListener;
    private BrickerGameManager manager;
    private GameObjectCollection object;

    /**
     * Creating randomly brick strategy
     * @param object
     * @param manager
     * @param imageReader
     * @param soundReader
     * @param inputListener
     */
    public BrickStrategyFactory(GameObjectCollection object, BrickerGameManager manager, ImageReader imageReader , SoundReader soundReader, UserInputListener inputListener){
        Random rand = new Random();
        int choice = rand.nextInt(12);
        switch (choice){
            case 1:case 2:case 3:case 4: case 5:
                this.strategy = new RemoveBrickStrategy(object);
                break;
            case 6:
                this.strategy = new ThreeBallsStrategy(object,manager,imageReader,soundReader);
                break;
            case 7:
                this.strategy = new BigOrSmallStrategy(object,manager,imageReader);
                break;
            case 8:
                this.strategy = new FastOrSlowStrategy(object,manager,imageReader);
                break;
            case 9:
                if(BrickerGameManager.BOT){
                    this.strategy = new RemoveBrickStrategy(object);
                    break;
                }
                this.strategy = new BotStrategy(object,manager,imageReader);
                break;
            case 10:
                this.strategy = new GravityStategy(object,manager,imageReader);
                break;
            default:
                if(BrickerGameManager.MORE_PADDLE){
                    this.strategy = new RemoveBrickStrategy(object);
                    break;
                }
                this.strategy = new MorePaddleStrategy(object,manager,imageReader,inputListener);
                break;
        }
    }

    public CollisionStrategy getStrategy() {
        return strategy;
    }
}
