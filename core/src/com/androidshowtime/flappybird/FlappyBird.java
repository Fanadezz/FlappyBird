package com.androidshowtime.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import sun.util.resources.cldr.gd.CalendarData_gd_GB;

public class FlappyBird extends ApplicationAdapter {
	SpriteBatch batch;

	//textures in games creations are just image resources
	Texture background;


	//called when the app is run
	@Override
	public void create () {

		//sprite is a collection of textures
		batch = new SpriteBatch();
		background = new Texture("bg.png");

	}


	//called continuously in the app
	@Override
	public void render () {
		//add background


		//tells the render method we gonna start displaying sprites
		batch.begin();
	}

	@Override
	public void dispose () {
		batch.dispose();

	}
}
