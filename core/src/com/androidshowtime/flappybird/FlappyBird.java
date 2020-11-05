package com.androidshowtime.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {

    //batch to enable us draw textures
    SpriteBatch batch;

    //textures in games creations are just image resources
    Texture background;
    Texture[] birds;
    Texture bottomTube;
    Texture topTube;




    //track flap state
    int flapState = 0;

    //gravity variables

    float gravity = 2;
    float velocity = 0;
    int birdYPosition = 0;


    //track game state
    int gameState = 0;

    //Screen Dimensions
    int height;
    int width;


    //variable gap is alterable to increase game difficulty
    float gap = 400f;


    //offsets variables for x-axis
    float tubeVelocity = 4;

    int numberOfTubes = 4;

    float[] tubeX = new float[numberOfTubes];
    float distanceBetweenTubes;

    //offsets variable for y-axis
    float maxTubeOffset;
    Random randomGenerator;
    float[] tubeOffset = new float[numberOfTubes];

    /*shape renderer to enable us draw a shape and detect
    collisions( as textures don't detect collisions*/
    ShapeRenderer shapeRenderer;
    //circle shape to enclose the bird
    Circle birdCircle;
    Rectangle [] topPipeRectangles;
    Rectangle []bottomPipeRectangles;

    //called when the app is run
    @Override
    public void create() {

        //sprite is a collection of textures
        batch = new SpriteBatch();
        background = new Texture("bg.png");
        bottomTube = new Texture("bottomtube.png");
        topTube = new Texture("toptube.png");
        birds = new Texture[]{new Texture("bird.png"), new Texture("bird2.png")};

        //initialize screen dimensions
        height = Gdx.graphics.getHeight();
        width = Gdx.graphics.getWidth();

        //set the initial Y Position for the bird at the first bird's height
        birdYPosition = Gdx.graphics.getHeight() / 2 - birds[0].getHeight() / 2 - 2;


        //initialize offsets
        maxTubeOffset = height / 2f - 100 - gap / 2f;
        randomGenerator = new Random();

        //distance between tubes (multiply by 0.75 to increase the space)
        distanceBetweenTubes = (float) (width * 0.75);

        //initialize shapeRenderer,circle & pipe-rectangles
        shapeRenderer = new ShapeRenderer();
        birdCircle = new Circle();
        topPipeRectangles = new Rectangle[numberOfTubes];
        bottomPipeRectangles = new Rectangle[numberOfTubes];
        //create tubes
        for (int i = 0; i < numberOfTubes; i++) {


            //tubeX
            tubeX[i] = height / 2f - topTube.getHeight() / 2f + (i * distanceBetweenTubes);

            topPipeRectangles[i] = new Rectangle();
            bottomPipeRectangles[i] = new Rectangle();
        }



    }


    //called continuously in the app
    @Override
    public void render() {
        //perform initial game set Up (draw background)
        initialGameSetUp();

        //SCREEN TOUCHED - GAME ACTIVE
        if (gameState != 0) {

            //respond to touch when screen is touched
            if (Gdx.input.isTouched()) {


                velocity = -30;

                //if the screen is touched switch gameState
                gameState = 1;

            }


            //draw tubes
            drawTubes();

            //perform gravity manoeuvres
            performGravityManoeuvres();

        }

        //SCREEN NOT TOUCHED
        else {
            //respond to touch when screen is touched
            if (Gdx.input.isTouched()) {


                velocity = -30;

                //if the screen is touched switch gameState
                gameState = 1;

            }


        }

        //flap the bird
        flapBirdWings();


    }





    private void performGravityManoeuvres() {


        /*increase velocity of the bird each time the render-loop is called while
         bringing down the bird by subtracting the velocity with each loop resulting
         in bird falling faster and faster along the Y-Axis*/

        //increasingly falling speed
        velocity = velocity + gravity;


        //subtracting the height will make bird shoot into the air
        birdYPosition -= velocity;


        if (birdYPosition <= 0) {

            birdYPosition = 0;
        }
        //restrict bird from crossing the upper screen height
        if (birdYPosition >= Gdx.graphics.getHeight() - birds[flapState].getHeight()) {
            birdYPosition = Gdx.graphics.getHeight() - birds[flapState].getHeight();

        }


    }

    //initialize game and perform initial setups
    private void initialGameSetUp() {

        //tells the render method we gonna start displaying sprites
        batch.begin();

        /*display we use batch.draw() - the draw method takes
         * Texture - background
         * X-Co-ordinate - 0
         * Y-Co-Ordinate - 0
         * Width - we get the width from Gdx.graphics Library to match screen size
         * Length - we get the length from Gdx.graphics Library*/


        //draw background-
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        batch.end();
    }

    //make the bird flap

    private void flapBirdWings() {
        //flapState to switch between birds in birds array

        if (flapState == 0) {

            flapState = 1;

        } else {

            flapState = 0;


        }


        try {

            batch.begin();
            //draw bird
            batch.draw(birds[flapState],
                       (Gdx.graphics.getWidth() / 2f - birds[flapState].getWidth() / 2f),
                       (birdYPosition));
            batch.end();
            //slow the bird flaps
            Thread.sleep(100);


        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        //specify shape type i.e. filled shape
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        //set color
        shapeRenderer.setColor(Color.RED);

        //set the shape to be on the same position as the bird(x, y , radius)
        birdCircle.set(width/2f, birdYPosition +(birds[flapState].getHeight()/2f),
                       birds[flapState].getWidth());

        //render the circle

        shapeRenderer.circle(birdCircle.x, birdCircle.y,birdCircle.radius);
        shapeRenderer.end();
    }



    private void drawTubes() {
        batch.begin();

        int tubeHeight = topTube.getHeight();

        int screenCenterY = height / 2;

        for (int i = 0; i < numberOfTubes; i++) {
            //check if the tube is at the edge of the screen
            if (tubeX[i] < -topTube.getWidth()) {

                //shift tube to the right
                tubeX[i] += numberOfTubes * distanceBetweenTubes;
                /*r.nextFloat generates numbers between 0 and 1*/
                tubeOffset[i] = (randomGenerator.nextFloat() - 0.5f) * (height - (gap + 200));
            } else {
                //decrease tubeX by 4 and shift tubes to the left
                tubeX[i] = tubeX[i] - tubeVelocity;
            }

            //draw tubes while adding the tubeOffset to Y
            batch.draw(topTube, tubeX[i], (screenCenterY + gap / 2) + tubeOffset[i]);
            batch.draw(bottomTube, tubeX[i],
                       height / 2f - (gap / 2) - (tubeHeight) + tubeOffset[i]);



            //specify shape type i.e. filled shape
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            //set color
            shapeRenderer.setColor(Color.RED);

            //set the shape to be on the same position as the pipe(x, y , width, height)



            shapeRenderer.rect(tubeX[i], (screenCenterY + gap / 2) + tubeOffset[i],
                               topTube.getWidth(), topTube.getHeight());
            shapeRenderer.setColor(Color.GREEN);
            shapeRenderer.rect(tubeX[i], height / 2f - (gap / 2) - (tubeHeight) + tubeOffset[i],
                                  bottomTube.getWidth(), bottomTube.getHeight());


            shapeRenderer.end();

        }


        batch.end();
    }


    @Override
    public void dispose() {
        batch.dispose();

    }
}
