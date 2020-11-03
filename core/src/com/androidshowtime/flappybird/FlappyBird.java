package com.androidshowtime.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {
    SpriteBatch batch;

    //textures in games creations are just image resources
    Texture background;
    Texture[] birds;
    Texture bottomTube;
    Texture topTube;


    //track flap state
    int flapState =0;

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
    float []tubeOffset = new float[numberOfTubes];

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
        birdYPosition = Gdx.graphics.getHeight() / 2 - birds[0].getHeight()/2 - 2;




        //initialize offsets
        maxTubeOffset = height/2f - 100 - gap/2f;
      randomGenerator = new Random();

      //distance between tubes is s
        distanceBetweenTubes = (float) width/2;


        //create tubes
        for (int i = 0; i < numberOfTubes; i++) {
            /*r.nextFloat generates numbers between 0 and 1*/
            tubeOffset [i]= (randomGenerator.nextFloat() -0.5f) * (height - (gap+200));


            //tubeX
            tubeX[i] = height/2f - topTube.getHeight()/2f + (i* distanceBetweenTubes);
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
    }


    private void performGravityManoeuvres() {


        /*increase velocity of the bird each time the render-loop is called while
         bringing down the bird by subtracting the velocity with each loop resulting
         in bird falling faster and faster along the Y-Axis*/

        //increasingly falling speed
        velocity = velocity + gravity;


        //subtracting the height will make bird shoot into the air
        birdYPosition -= velocity;


        if (birdYPosition <= 0 ) {

            birdYPosition = 0;
        }
       //restrict bird from crossing the upper screen height
        if (birdYPosition >= Gdx.graphics.getHeight() -birds[flapState].getHeight()){
            birdYPosition= Gdx.graphics.getHeight() -birds[flapState].getHeight();

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


    @Override
    public void dispose() {
        batch.dispose();

    }

    private void drawTubes(){
batch.begin();

        int tubeHeight = topTube.getHeight();

        int screenCenterY = height/2;

        for (int i = 0; i < numberOfTubes; i++) {
            //check if the tube is at the edge of the screen
            if (tubeX[i]) {

            }

            //decrease tubeX by 4
            tubeX[i] = tubeX[i]-tubeVelocity;
            //draw tubes while adding the tubeOffset to Y
            batch.draw(topTube, tubeX[i],(screenCenterY + gap/2) + tubeOffset[i]);
            batch.draw(bottomTube ,tubeX[i],height/2f - (gap/2) -(tubeHeight) +tubeOffset[i]);



        }


batch.end();
    }
}
