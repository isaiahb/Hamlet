package com.iball.hamlet;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.iball.hamlet.characters.Player;
import com.iball.hamlet.enums.Character;
import com.iball.hamlet.screens.GameScreen;

public class Main extends Game {
	//Static game variables
	public static float PPM = 16;
	public static int width = 128*6;
	public static int height = 64*6;
	public static final int oWidth = width;
	public static final int oHeight = height;

	//Object Variables
	public BitmapFont font;
	public ScreenViewport viewPort;
	public ScreenViewport box2DViewPort;
	public OrthographicCamera camera;
	public OrthographicCamera box2DCamera;
	public Color darkGreen = new Color(18/255f, 47/255f, 5/255f, 1);

	//
	public SpriteBatch batch;
	public ShapeRenderer shapeRenderer;
	Player player;


	
	@Override
	public void create () {
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();

		//Freetype font
		FreeTypeFontGenerator openSans = new FreeTypeFontGenerator(Gdx.files.internal("OpenSans-Regular.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = (int)(24 * Gdx.graphics.getDensity());
		font = openSans.generateFont(parameter);

		//Camera and Viewport stuff
		camera = new OrthographicCamera();
		viewPort = new ScreenViewport(camera);
		viewPort.update(width, height, true);
		camera.update();

		box2DCamera = new OrthographicCamera();
		box2DViewPort = new ScreenViewport(box2DCamera);
		box2DViewPort.setUnitsPerPixel(1/PPM);
		box2DViewPort.update(width, height, true);
		box2DCamera.update();

		setScreen(new GameScreen(this));
	}

	@Override
	public void render () {
//		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);                        // #14

		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		box2DViewPort.update(width, height, true);
		box2DCamera.update();
	}
}
