package bricker.main;

import bricker.brick_strategies.BrickStrategyFactory;
import bricker.gameobjects.*;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.util.Random;

public class BrickerGameManager extends GameManager{

    public static final float BALL_SPEED = 350;
    private int BORDER_WIDTH = 5;
    //HOME:
    //X = 1400
    //Y = 1000
    //LEPTOP:
    //X=1000
    //Y=600
    public static int X_BOARD = 1000;
    public static int Y_BOARD = 600;
    public Ball ball;
    public GameObject paddle;

    public GameObject heart1;
    public GameObject heart2;
    public GameObject heart3;

    public static Boolean MORE_PADDLE;

    public static Boolean BOT;

    public Vector2 windowDimensions;
    public WindowController windowController;

    public static Counter BRICK_COUNTER;

    public static Counter BIGORSMALL_COUNTER;
    public static Counter FASTORSLOW_COUNTER;

    public static Counter OPPOSITE_COUNTER;

    public static Counter MORE_PADDLE_COUNTER;
    public static Counter BOT_COUNTER;

    public static Counter HEART_COUNTER;
    public Sound collisionSound;
    public Ball ball1;

    /**
     * Bricker Game constructor
     * @param windowTitle
     * @param windowDimensions
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions){
        super(windowTitle,windowDimensions);
    }

    /**
     * Initialize the game
     * @param imageReader Contains a single method: readImage, which reads an image from disk.
     *                 See its documentation for help.
     * @param soundReader Contains a single method: readSound, which reads a wav file from
     *                    disk. See its documentation for help.
     * @param inputListener Contains a single method: isKeyPressed, which returns whether
     *                      a given key is currently pressed by the user or not. See its
     *                      documentation.
     * @param windowController Contains an array of helpful, self explanatory methods
     *                         concerning the window.
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController ) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.windowController = windowController;
        //initializing new Brick Counter
        InitializeVars();
        //creating a ball and placing it
        CreateBall(imageReader,soundReader);
        //creating a paddle
        paddle = CreatePaddle(imageReader,inputListener);
        //Crating the walls
        CreateWalls(windowDimensions);
        //Creating all the bricks
        CreateBricks(imageReader,8,5,soundReader , inputListener);
        //Creating game background
        CreateBackround(imageReader);
        //Creating the hearts
        heart1 = CreateHeart(imageReader,new Vector2(X_BOARD/28,windowDimensions.y()-50));
        heart2 = CreateHeart(imageReader,new Vector2(X_BOARD/28+70,windowDimensions.y()-50));
        heart3 = CreateHeart(imageReader,new Vector2(X_BOARD/28+140,windowDimensions.y()-50));
    }


    /**
     * Initializing the static valuables
     */
    private void InitializeVars(){
        BRICK_COUNTER =new Counter(0);
        BIGORSMALL_COUNTER =new Counter(0);
        FASTORSLOW_COUNTER =new Counter(0);
        MORE_PADDLE_COUNTER =new Counter(0);
        MORE_PADDLE = false;
        BOT_COUNTER =new Counter(0);
        BOT = false;
        OPPOSITE_COUNTER = new Counter(0);
        HEART_COUNTER = new Counter(3);
    }

    /**
     * Update the game
     * @param deltaTime The time, in seconds, that passed since the last invocation
     *                  of this method (i.e., since the last frame). This is useful
     *                  for either accumulating the total time that passed since some
     *                  event, or for physics integration (i.e., multiply this by
     *                  the acceleration to get an estimate of the added velocity or
     *                  by the velocity to get an estimate of the difference in position).
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        CheckForGameEnd();
    }

    /**
     * Creating the main ball
     * @param imageReader
     * @param soundReader
     */
    private void CreateBall(ImageReader imageReader, SoundReader soundReader){
        Renderable ballImage = imageReader.readImage("assets/ball.png",true);
        collisionSound = soundReader.readSound("assets/blop_cut_silenced.wav");
        ball = new Ball(Vector2.ZERO,new Vector2(20,20),ballImage,collisionSound,this);
        StartLocation(ball);
        windowDimensions = windowController.getWindowDimensions();
        ball.setCenter(windowDimensions.mult(0.5f));
        this.gameObjects().addGameObject(ball);
    }

    /**
     * Creating mock ball
     * @param imageReader
     * @param soundReader
     * @param place
     */
    public void CreateMockBall(ImageReader imageReader, SoundReader soundReader,Vector2 place){
        Renderable ballImage = imageReader.readImage("assets/mockBall.png",true);
        collisionSound = soundReader.readSound("assets/blop_cut_silenced.wav");
        ball1 = new Ball(Vector2.ZERO,new Vector2(20,20),ballImage,collisionSound,this);
        StartLocation(ball1);
        ball1.setCenter(place);
        this.gameObjects().addGameObject(ball1);
    }

    /**
     * Deleting mock ball
     * @param mockball
     */
    public void DeleteMockBall(GameObject mockball){
        this.gameObjects().removeGameObject(mockball);
    }


    /**
     * Creating heart
     * @param imageReader
     * @param place
     * @return GameObject
     */
    public GameObject CreateHeart(ImageReader imageReader,Vector2 place) {
        Renderable heartimage = imageReader.readImage("assets/heart.png",true);
        GameObject heart = new Heart(place,new Vector2(50,30),heartimage);
        this.gameObjects().addGameObject(heart, Layer.BACKGROUND);
        return heart;
    }

    /**
     * Deleting heart
     * @param heart
     */
    public void DeleteHeart(GameObject heart) {
        this.gameObjects().removeGameObject(heart, Layer.BACKGROUND);
    }


    /**
     * Creating the main paddle
     * @param imageReader
     * @param inputListener
     * @return GameObject
     */
    private GameObject CreatePaddle(ImageReader imageReader, UserInputListener inputListener){
        Renderable paddleImage = imageReader.readImage("assets/paddle.png",true);
        GameObject paddle = new Paddle(Vector2.ZERO,new Vector2(100,15),paddleImage, inputListener , windowDimensions,this);
        paddle.setCenter(new Vector2(windowDimensions.x()/2, (windowDimensions.y()-20)));
        this.gameObjects().addGameObject(paddle);
        return paddle;
    }

    /**
     * Creating another paddle
     * @param imageReader
     * @param inputListener
     * @param paddle
     */
    public void CreatePaddle(ImageReader imageReader, UserInputListener inputListener, GameObject paddle){
        Renderable paddleImage = imageReader.readImage("assets/paddle.png",true);
        GameObject paddle2 = new Paddle(Vector2.ZERO,new Vector2(100,15),paddleImage, inputListener , windowDimensions,this);
        paddle2.setCenter(new Vector2(paddle.getCenter().x(),paddle.getCenter().y()-200));
        this.gameObjects().addGameObject(paddle2);
    }

    /**
     * Delete paddle
     * @param paddle
     */
    public void DeletePaddle(GameObject paddle){
        this.gameObjects().removeGameObject(paddle);
    }

    /**
     * Creating game background
     * @param imageReader
     */
    private void CreateBackround(ImageReader imageReader){
        GameObject background = new GameObject(Vector2.ZERO, windowController.getWindowDimensions(), imageReader.readImage("assets/DARK_BG2_small.jpeg",false));
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        this.gameObjects().addGameObject(background, Layer.BACKGROUND);
    }

    /**
     * Creating a brick
     * @param imageReader
     * @param x
     * @param y
     * @param cols
     * @param soundReader
     * @param inputListener
     */
    private void CreateBrick(ImageReader imageReader, float x, float y,int cols, SoundReader soundReader , UserInputListener inputListener){
        BrickStrategyFactory strategyFactory = new BrickStrategyFactory(gameObjects(),this, imageReader , soundReader , inputListener);
        Renderable brickImage = imageReader.readImage("assets/brick.png",false);
        GameObject brick = new Brick(Vector2.ZERO,new Vector2(CalculateBrickWidth(windowDimensions,cols),15),brickImage,strategyFactory.getStrategy());
        brick.setCenter(new Vector2(x, y));
        this.gameObjects().addGameObject(brick);
        gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);
    }

    /**
     * Creating big or small object
     * @param imageReader
     * @param place
     * @param Big
     */
    public void CreateBigOrSmall(ImageReader imageReader, Vector2 place, Boolean Big){
        Renderable image;
        if(Big) {
            image = imageReader.readImage("assets/buffWiden.png", false);
        }else{
            image = imageReader.readImage("assets/buffNarrow.png", false);
        }
        BigOrSmall bigOrSmall = new BigOrSmall(new Vector2(place.x(),place.y()+15),new Vector2(50,15),image,Big,this);
        bigOrSmall.setVelocity(Vector2.DOWN.mult(200));
        this.gameObjects().addGameObject(bigOrSmall);
    }

    /**
     * Deleting bigorsmall object
     * @param bigorsmall
     */
    public void DeleteBigOrSmall(GameObject bigorsmall){
        this.gameObjects().removeGameObject(bigorsmall);
    }

    /**
     * Creating gravity object
     * @param imageReader
     * @param place
     */
    public void CreateGravity(ImageReader imageReader, Vector2 place){
        Renderable image;
        image = imageReader.readImage("assets/gravity.png", false);
        Gravity gravity = new Gravity(new Vector2(place.x(),place.y()+15),new Vector2(50,30),image,this);
        gravity.setVelocity(Vector2.DOWN.mult(200));
        this.gameObjects().addGameObject(gravity);
    }

    /**
     * Deleting gravity object
     * @param gravity
     */
    public void DeleteGravity(GameObject gravity){
        this.gameObjects().removeGameObject(gravity);
    }

    /**
     * Creating fast or slow object
     * @param imageReader
     * @param place
     * @param fast
     */
    public void CreateFastOrSlow(ImageReader imageReader, Vector2 place, boolean fast) {
        Renderable image;
        if(fast) {
            image = imageReader.readImage("assets/quicken.png", false);
        }else{
            image = imageReader.readImage("assets/slow.png", false);
        }
        FastOrSlow fastOrSlow = new FastOrSlow(new Vector2(place.x(),place.y()+15),new Vector2(50,15),image,fast,this);
        fastOrSlow.setVelocity(Vector2.DOWN.mult(200));
        this.gameObjects().addGameObject(fastOrSlow);
    }

    /**
     * Creating bot
     * @param imageReader
     * @param good
     * @param paddle
     */
    public void CreateBot(ImageReader imageReader, Boolean good, GameObject paddle){
        Renderable image;
        if(good) {
            image = imageReader.readImage("assets/botGood.png", false);
        }else{
            image = imageReader.readImage("assets/botBad.png", false);
        }
        Bot bot = new Bot(Vector2.ZERO,new Vector2(100,15),image, this, good,windowDimensions);
        bot.setCenter(new Vector2(paddle.getCenter().x(),paddle.getCenter().y()- Y_BOARD /3));
        this.gameObjects().addGameObject(bot);
    }

    /**
     * Deleting bot
     * @param bot
     */
    public void DeleteBot(GameObject bot){
        this.gameObjects().removeGameObject(bot);
    }
    

    /**
     * Creating the game bricks
     * @param imageReader
     * @param rows
     * @param cols
     * @param soundReader
     * @param inputListener
     */

    private void CreateBricks(ImageReader imageReader,int rows,int cols , SoundReader soundReader , UserInputListener inputListener){
        float x = CalculateBrickWidth(windowDimensions,cols)/2 + 5;
        float y = 15;
        for (int i = 0; i <rows; i++) {
            for (int j = 0; j<cols; j++) {
                CreateBrick(imageReader,x,y,cols,soundReader , inputListener);
                x+=CalculateBrickWidth(windowDimensions,cols)+1;
            }
            y+=16;
            x=CalculateBrickWidth(windowDimensions,cols)/2 + 5;
        }
    }

    /**
     * Checking if the game has ended and asking if want another round
     */
    private void CheckForGameEnd() {
        float ballHeight = ball.getCenter().y();
        String prompt = "";
        if(ballHeight > windowDimensions.y()){
            if (HEART_COUNTER.value() == 1) {
                prompt = "you Lose!";
                prompt += " play again?";
                if (windowController.openYesNoDialog(prompt)) {
                    windowController.resetGame();
                } else {
                    windowController.closeWindow();
                }
            }
            if(HEART_COUNTER.value() == 2){
                DeleteHeart(heart2);
            }
            if(HEART_COUNTER.value() == 3){
                DeleteHeart(heart3);
            }
            HEART_COUNTER.decrement();
            ball.setCenter(windowDimensions.mult(0.5f));
            StartLocation(ball);
        }
        if (BRICK_COUNTER.value() <= 0) {
            prompt = "you Win!";
            prompt += " play again?";
            if (windowController.openYesNoDialog(prompt)) {
                windowController.resetGame();
            } else {
                windowController.closeWindow();
            }
        }
    }


    /**
     * Placing the ball in random location and direction
     * @param ball
     */
    public void StartLocation(Ball ball){
        float ballVelX = BALL_SPEED;
        float ballVelY = BALL_SPEED*(-1);
        Random rand = new Random();
        if(rand.nextBoolean()){
            ballVelX*=-1;
        }
        ball.setVelocity(new Vector2(ballVelX,ballVelY));
    }

    /**
     * Creating the walls
     * @param windowDimensions
     */
    private void CreateWalls (Vector2 windowDimensions){
        GameObject leftWall = new GameObject(Vector2.ZERO,new Vector2(BORDER_WIDTH,windowDimensions.y()),null);
        GameObject rightWall = new GameObject(new Vector2(windowDimensions.x(),0),new Vector2(BORDER_WIDTH,windowDimensions.y()),null);
        GameObject topWall = new GameObject(Vector2.DOWN,new Vector2(windowDimensions.x(),BORDER_WIDTH),null);
        this.gameObjects().addGameObject(leftWall);
        this.gameObjects().addGameObject(rightWall);
        this.gameObjects().addGameObject(topWall);

    }

    /**
     * Calculating each brick width
     * @param windowDimensions
     * @param cols
     * @return
     */
    private float CalculateBrickWidth(Vector2 windowDimensions,int cols){
        float size = (windowDimensions.x()-16)/cols;
        return size;
    }

    /**
     * The main function
     * @param args
     */
    public static void main(String[] args) {
        new BrickerGameManager("Bricker", new Vector2(X_BOARD, Y_BOARD)).run();
    }
}
