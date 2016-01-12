package com.iball.hamlet.screens.instances.rg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.iball.hamlet.Main;
import com.iball.hamlet.characters.Player;
import com.iball.hamlet.dialog_system.ChatDialog;
import com.iball.hamlet.dialog_system.DialogManager;
import com.iball.hamlet.enums.Character;
import com.iball.hamlet.screens.instances.Instance;

//Manually start dialogs and animations for the scene
//to go to next dialog press space
//to go to next cut scene animation press enter

public class GoodStuffInstance extends Instance {
	Player player;
	public Main main;
	Stage stage;

	//Box2D
	public World world = new World(new Vector2(0, 0), true);
	public Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();

	final float TIME_STEP = 1f/60f;
	final int VELOCITY_ITERATIONS = 6;
	final int POSITION_ITERATIONS = 2;
	float accumulator = 0;
	Texture[] backgrounds = new Texture[4];
	int textureIndex = 0;

	//Dialog Manager
	DialogManager dialogManager;


	public GoodStuffInstance(Main main) {
		this.main = main;
		//no character in this instance
		//player = new Player(Character.Hamlet, world);

		main.box2DCamera.zoom = 0.5f;
		ChatDialog RGDialog = new ChatDialog(Character.RG);
		ChatDialog HoratioDialog = new ChatDialog(Character.Horatio);
		dialogManager = new DialogManager(main);
		dialogManager.addDialog(RGDialog, "Yooooo Horatio my man whats up, how you been?");
		dialogManager.addDialog(HoratioDialog, "Hello RG. I'm fine, How are you?");
		dialogManager.addDialog(RGDialog, "ok coo, i'm aiight");
		dialogManager.addDialog(RGDialog, "Hey Horatio, my man do you have any of that good stuff?");
		dialogManager.addDialog(HoratioDialog, "What are you talking about?");
		dialogManager.addDialog(RGDialog, "Oh, I hurt my back I need some medicine or whatevs for pain");
		dialogManager.addDialog(HoratioDialog, "Yeah don't worry I got you fam.");
		dialogManager.pause();
		dialogManager.addDialog(RGDialog, "Thanks bro");
		dialogManager.addDialog(RGDialog, "I gotta go talk to Claudius. I'll see you and Hamlet later");
		dialogManager.addDialog(RGDialog, "PEACE!");

		dialogManager.startDialogs();
		dialogManager.nextDialog();

		backgrounds[0] = new Texture("RG/RG1.png");
		backgrounds[1] = new Texture("RG/RG2.png");
		backgrounds[2] = new Texture("RG/RG3.png");
		backgrounds[3] = new Texture("RG/RG4.png");

		main.box2DCamera.zoom = (1);
		main.box2DViewPort.update(128, 64, true);
		main.box2DViewPort.setUnitsPerPixel(1/6f);
		main.box2DCamera.update();
	}

	public void updateControls() {
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			dialogManager.nextDialog();
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
			if (textureIndex < 3) textureIndex++;
		}
	}

	public void updatePlayer() {

		//update player
		//no player in this instance
		//player.update(TIME_STEP);

		//update camera to follow player
		//main.box2DCamera.position.set(player.body.getPosition(), 1);
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
		main.batch.setProjectionMatrix(main.box2DCamera.combined);
		main.batch.begin();
		main.batch.draw(backgrounds[textureIndex], 0, 0);
		main.batch.end();

		//player.render(main.batch, main.box2DCamera);
		dialogManager.render(main.camera, main.shapeRenderer, main.batch);
//		debugRenderer.render(world, main.box2DCamera.combined);
	}
}
