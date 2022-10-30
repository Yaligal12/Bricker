package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

public class Paddle extends GameObject {

    private static final float MOVEMENT_SPEED = 400;
    private static final float MIN_DISTANCE_FROM_SCREEN_EDGE = 1;
    private UserInputListener inputListener;
    private Vector2 windowDimensions;
    private BrickerGameManager manager;



    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner    Position of the object, in window coordinates (pixels).
     *                         Note that (0,0) is the top-left corner of the window.
     * @param dimensions       Width and height in window coordinates.
     * @param renderable       The renderable representing the object. Can be null, in which case
     *                         the GameObject will not be rendered.
     * @param inputListener
     * @param windowDimensions
     */
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, UserInputListener inputListener, Vector2 windowDimensions, BrickerGameManager manager){
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
        this.manager = manager;
    }

    /**
     * Moving the paddle
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
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {

                movementDir = movementDir.add(Vector2.LEFT);
            }
        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
                movementDir = movementDir.add(Vector2.RIGHT);
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
        if (this.getCenter().y() < windowDimensions.y() - 200 && other instanceof Ball) {
            BrickerGameManager.MORE_PADDLE_COUNTER.increment();
            if (BrickerGameManager.MORE_PADDLE_COUNTER.value() >= 3) {
                manager.DeletePaddle(this);
                BrickerGameManager.MORE_PADDLE = false;
                BrickerGameManager.MORE_PADDLE_COUNTER.reset();
            }
        }
    }
}