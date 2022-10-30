package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class Bot extends GameObject {
    private static final float MOVEMENT_SPEED = 400;
    private static final float MIN_DISTANCE_FROM_SCREEN_EDGE = 1;
    private Vector2 windowDimensions;
    private BrickerGameManager manager;
    private boolean good;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     * @param manager       game manager object
     * @param good          a boolean if the bot is good or bad
     * @param windowDimensions the dimensions of the game window
     */
    public Bot(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, BrickerGameManager manager, boolean good, Vector2 windowDimensions) {
        super(topLeftCorner, dimensions, renderable);
        this.manager = manager;
        this.good = good;
        this.windowDimensions = windowDimensions;
    }

    /**
     * Updating the bot movement
     * @param deltaTime The time elapsed, in seconds, since the last frame. Can
     *                  be used to determine a new position/velocity by multiplying
     *                  this delta with the velocity/acceleration respectively
     *                  and adding to the position/velocity:
     *                  velocity += deltaTime*acceleration
     *                  pos += deltaTime*velocity
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 movementDir = Vector2.ZERO;
        if(good){
            if(manager.ball.getCenter().y()<this.getCenter().y()){
                if(manager.ball.getCenter().x()<this.getCenter().x()){
                    movementDir = movementDir.add(Vector2.LEFT);
                }
                if(manager.ball.getCenter().x()>this.getCenter().x()) {
                    movementDir = movementDir.add(Vector2.RIGHT);
                }
            }
            if(manager.ball.getCenter().y()>this.getCenter().y()) {
                if (manager.ball.getCenter().x() < this.getCenter().x()) {
                    movementDir = movementDir.add(Vector2.RIGHT);
                }
                if (manager.ball.getCenter().x() > this.getCenter().x()) {
                    movementDir = movementDir.add(Vector2.LEFT);
                }
            }
        }
        if(!good){
            if(manager.ball.getCenter().y()<this.getCenter().y()){
                if(manager.ball.getCenter().x()<this.getCenter().x()){
                    movementDir = movementDir.add(Vector2.RIGHT);
                }
                if(manager.ball.getCenter().x()>this.getCenter().x()) {
                    movementDir = movementDir.add(Vector2.LEFT);
                }
            }
            if(manager.ball.getCenter().y()>this.getCenter().y()) {
                if (manager.ball.getCenter().x() < this.getCenter().x()) {
                    movementDir = movementDir.add(Vector2.LEFT);
                }
                if (manager.ball.getCenter().x() > this.getCenter().x()) {
                    movementDir = movementDir.add(Vector2.RIGHT);
                }
            }
        }
        setVelocity(movementDir.mult(MOVEMENT_SPEED));
        if (getTopLeftCorner().x() < MIN_DISTANCE_FROM_SCREEN_EDGE) {
            transform().setTopLeftCornerX(MIN_DISTANCE_FROM_SCREEN_EDGE);
        }
        if (getTopLeftCorner().x() > windowDimensions.x() - MIN_DISTANCE_FROM_SCREEN_EDGE - getDimensions().x()) {
            transform().setTopLeftCornerX(windowDimensions.x() - MIN_DISTANCE_FROM_SCREEN_EDGE - getDimensions().x());
        }
    }

    /**
     * when a collision occurring do something
     * @param other The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     *                  A reasonable elastic behavior can be achieved with:
     *                  setVelocity(getVelocity().flipped(collision.getNormal()));
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (other instanceof Ball) {
            BrickerGameManager.BOT_COUNTER.increment();
            if (BrickerGameManager.BOT_COUNTER.value() >= 3) {
                manager.DeleteBot(this);
                BrickerGameManager.BOT = false;
                BrickerGameManager.BOT_COUNTER.reset();
            }
        }
    }


}

