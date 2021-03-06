package com.iball.hamlet.screens.instances.laertes;

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

public class LaertesDeathInstance extends Instance {
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
	int textures = 2;
	Texture[] backgrounds = new Texture[textures];
	int textureIndex = 0;

	//Dialog Manager
	DialogManager dialogManager;


	public LaertesDeathInstance(Main main) {
		this.main = main;
		//no character in this instance
		//player = new Player(Character.Hamlet, world);

		main.box2DCamera.zoom = 0.5f;
		ChatDialog l = new ChatDialog(Character.Laertes);
		ChatDialog h = new ChatDialog(Character.Hamlet);
		dialogManager = new DialogManager(main);

		dialogManager.addDialog(l, "*aside* gg no re");

		dialogManager.addDialog(h, "THE QUEEN IS SLAIN!!, O VILLAINY!!");
		dialogManager.addDialog(h, "LOCK THE DOORS!! TREACHERY!! SEEK IT OUT!!");

		dialogManager.addDialog(l, "It is here Hamlet");
		dialogManager.addDialog(l, "Hamlet, you are slain");
		dialogManager.addDialog(l, "No medicine in the world can do thee good");
		dialogManager.addDialog(l, "The treacherous instrument is in your hand");
		dialogManager.addDialog(l, "the tip, poisoned. It has turned on me, and here I lie never to rise again");
		dialogManager.addDialog(l, "I can no more, the king");
		dialogManager.addDialog(l, "The King is to blame");
		dialogManager.addDialog(l, "Forgive me noble Hamlet");
		dialogManager.addDialog(l, "I am slain");
		dialogManager.addDialog(l, "*dies*");


		dialogManager.startDialogs();
		dialogManager.nextDialog();

		backgrounds[0] = new Texture("Laertes/L1.png");
		backgrounds[1] = new Texture("Laertes/L2.png");
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
