package com.iball.hamlet.screens.instances.hamlet;

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

public class HamletChurchClaudiusInstance extends Instance {
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


	public HamletChurchClaudiusInstance(Main main) {
		this.main = main;
		//no character in this instance
		//player = new Player(Character.Hamlet, world);

		main.box2DCamera.zoom = 0.5f;
		ChatDialog c = new ChatDialog(Character.Claudius);
		ChatDialog h = new ChatDialog(Character.Hamlet);
		dialogManager = new DialogManager(main);

		dialogManager.addDialog(c, "My offences are rank, they smell to heaven");
		dialogManager.addDialog(c, "my sin hath the primal eldest curse upon them...");
		dialogManager.addDialog(c, "a brothers murder. I can not pray.");
		dialogManager.addDialog(c, "These cursed hands are thicker than itself with brother's blood...");
		dialogManager.addDialog(c, "Is there not rain enough in the sweet heavens to wash them white as snow?");
		dialogManager.addDialog(c, "My crime has past. But what form of prayer can forgive me my foul murder?");
		dialogManager.addDialog(c, "I cannot be forgiven, I still possess all the effects for which I did the murder");

		dialogManager.addDialog(h, "Now is my chance");
		dialogManager.addDialog(h, "Now he is praying");
		dialogManager.addDialog(h, "So now I kill him");
		dialogManager.addDialog(h, "and his soul goes to heaven while my father's soul is in purgatory?");
		dialogManager.addDialog(h, "this is hire and salary, not revenge");
		dialogManager.addDialog(h, "When he is drunk asleep, or in his rage");
		dialogManager.addDialog(h, "or in the incestuous pleasure of his bed");
		dialogManager.addDialog(h, "At gaming swearing, or about some act that has no relish of salvation in it");
		dialogManager.addDialog(h, "Then I'll do it, and trip him that his heels may kick at heaven, and that his soul may be as danm'd and black as hell whereto it goes!");

		dialogManager.addDialog(c, "My words fly up, my thoughts remain below");
		dialogManager.addDialog(c, "Words without thoughts never to heaven go.");

		dialogManager.startDialogs();
		dialogManager.nextDialog();

		backgrounds[0] = new Texture("Hamlet/H1.png");
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
