package com.androidshowtime.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FlappyBird extends ApplicationAdapter {
	SpriteBatch batch;

	//textures in games creations are just image resources
	Texture background;
	Texture bird;


	//called when the app is run
	@Override
	public void create () {

		//sprite is a collection of textures
		batch = new SpriteBatch();
		background = new Texture("bg.png");
		bird = new Texture("bird.png");

	}


	//called continuously in the app
	@Override
	public void render () {
		//add background


		//tells the render method we gonna start displaying sprites
		batch.begin();

		/*display we use batch.draw() - the draw method takes
		* Texture - backgroung
		* X-Co-ordinate - 0
		* Y-Co-Ordinate - 0
		* Width - we get the width from Gdx.graphics Library to match screen size
		* Length - we get the length from Gdx.graphics Library*/


		//draw background
		batch.draw(background, 0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


		//draw bird at the center of the screen
		batch.draw(bird, (Gdx.graphics.getWidth()/2f -  bird.getWidth()/2f),
				   (Gdx.graphics.getHeight()/2f - bird.getHeight()/2f));

		batch.end();

	}

	@Override
	public void dispose () {
		batch.dispose();

	}
}
