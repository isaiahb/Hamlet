package com.iball.hamlet.screens.instances.claudius;

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

public class ClaudiusHamletInstance extends Instance {
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
	int textures = 1;
	Texture[] backgrounds = new Texture[textures];
	int textureIndex = 0;

	//Dialog Manager
	DialogManager dialogManager;


	public ClaudiusHamletInstance(Main main) {
		this.main = main;
		//no character in this instance
		//player = new Player(Character.Hamlet, world);

		main.box2DCamera.zoom = 0.5f;
		ChatDialog c = new ChatDialog(Character.Claudius);
		ChatDialog h = new ChatDialog(Character.Hamlet);
		dialogManager = new DialogManager(main);

		dialogManager.addDialog(c, "Hamlet, my nephew, my son. How is it that the clouds still hang on you?");
		dialogManager.addDialog(h, "Not so, I am too much in the sun");
		dialogManager.addDialog(c, "what is wrong, you are constantly dressed in black, moping around");
		dialogManager.addDialog(c, "you just seem to be so, you know, depressed or something");
		dialogManager.addDialog(c, "come on chillax my boy, have a drink and celebrate!");

		dialogManager.addDialog(h, "seem?, I Know not seem");
		dialogManager.addDialog(h, "it's not alone my inky cloak");
		dialogManager.addDialog(h, "But I have that within which passeth show");
		dialogManager.addDialog(h, "These but the trappings and the suits of woe.");

		dialogManager.addDialog(c, "It is sweet and commendable in your nature, Hamlet");
		dialogManager.addDialog(c, "To give these mourning duties to your father");
		dialogManager.addDialog(c, "But you must know, your father lost a father");
		dialogManager.addDialog(c, "and your father's father lost a father");
		dialogManager.addDialog(c, "all of this behaviour is very unmanly, and your acting like a wimp");
		dialogManager.addDialog(c, "Besides my son, you should consider me as your father now");


		dialogManager.startDialogs();
		dialogManager.nextDialog();

		backgrounds[0] = new Texture("Claudius/Claudius Hamlet.png");
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
			if (textureIndex < textures-1) textureIndex++;
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
