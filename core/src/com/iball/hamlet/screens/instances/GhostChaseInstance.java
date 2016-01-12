package com.iball.hamlet.screens.instances;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.iball.hamlet.Main;
import com.iball.hamlet.characters.Player;
import com.iball.hamlet.dialog_system.ChatDialog;
import com.iball.hamlet.dialog_system.DialogManager;
import com.iball.hamlet.enums.Character;

//Manually start dialogs and animations for the scene
//to go to next dialog press space
//to go to next cut scene animation press enter

public class GhostChaseInstance extends Instance {
	Player player;
	Player ghost;
	public Main main;
	Stage stage;

	//Box2D
	public World world = new World(new Vector2(0, 0), true);
	public Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();

	final float TIME_STEP = 1f/60f;
	final int VELOCITY_ITERATIONS = 6;
	final int POSITION_ITERATIONS = 2;
	float accumulator = 0;
	Touchpad touchpad;


	public GhostChaseInstance(Main main) {
		this.main = main;
		//no character in this instance
		player = new Player(Character.RG, world);
		ghost = new Player(Character.Ghost, world);
		ghost.body.setTransform(5, 0, 0);

		stage = new Stage(main.viewPort, main.batch);
		touchpad = new Touchpad(Gdx.graphics.getHeight()/7f, new Touchpad.TouchpadStyle());
		stage.addActor(touchpad);


		//main.box2DCamera.zoom = 0.5f;
		main.box2DCamera.zoom = (1);
		main.box2DViewPort.update(128, 64, true);
		main.box2DViewPort.setUnitsPerPixel(1/16f);
		main.box2DCamera.update();
	}

	public void updateControls() {

	}

	public void updatePlayer() {

		//update player
		//no player in this instance
		player.update(TIME_STEP);

		//update camera to follow player
		main.box2DCamera.position.set(player.body.getPosition(), 1);
		main.box2DCamera.update();
	}

	// Update And Render
	public void update(float delta) {
		float frameTime = Math.min(delta, 0.25f);
		accumulator += frameTime;
		while (accumulator >= TIME_STEP) {
			world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
			accumulator -= TIME_STEP;

			//Update Things here, solid time step updating
			updatePlayer();
			updateControls();

		}
	}
	public void render() {
		main.shapeRenderer.setProjectionMatrix(main.box2DCamera.combined);
		main.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		main.shapeRenderer.setColor(main.darkGreen);
		main.shapeRenderer.rect(0, 0, main.box2DCamera.viewportWidth, main.box2DCamera.viewportHeight);
		main.shapeRenderer.end();

		player.render(main.batch, main.box2DCamera);
		ghost.render(main.batch, main.box2DCamera);
		debugRenderer.render(world, main.box2DCamera.combined);

		stage.draw();
	}
}
