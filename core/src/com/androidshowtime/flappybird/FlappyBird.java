package com.androidshowtime.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FlappyBird extends ApplicationAdapter {
    SpriteBatch batch;

    //textures in games creations are just image resources
    Texture background;
    Texture[] birds;
    int flapState;

    //gravity variables

    float gravity = 2;
    float velocity = 0;
    int birdYPosition = 0;


    //track game state
    int gameState = 0;


    //called when the app is run
    @Override
    public void create() {

        //sprite is a collection of textures
        batch = new SpriteBatch();
        background = new Texture("bg.png");
        flapState = 0;
        birds = new Texture[]{new Texture("bird.png"), new Texture("bird2.png")};

        //set the initial Y Position for the bird at the first bird's height
        birdYPosition = Gdx.graphics.getHeight() / 2 - birds[0].getHeight()/2 - 2;

    }


    //called continuously in the app
    @Override
    public void render() {
        //perform initial game set Up (draw background and flap wings)
        initialGameSetUp();

        //SCREEN TOUCHED - GAME ACTIVE
        if (gameState != 0) {

            //respond to touch when screen is touched
            if (Gdx.input.isTouched()) {


                velocity = -20;

                //if the screen is touched switch gameState
                gameState = 1;

            }

            //perform gravity manoeuvres
            performGravityManoeuvres();

        }

        //SCREEN NOT TOUCHED
        else {
            //respond to touch when screen is touched
            if (Gdx.input.isTouched()) {


                velocity = -20;

                //if the screen is touched switch gameState
                gameState = 1;

            }


        }




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
            //slow the bird flaps
            batch.draw(birds[flapState],
                       (Gdx.graphics.getWidth() / 2f - birds[flapState].getWidth() / 2f),
                       (birdYPosition));
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


        //draw background
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        //flap the bird
        flapBirdWings();

        batch.end();
    }


    @Override
    public void dispose() {
        batch.dispose();

    }
}
