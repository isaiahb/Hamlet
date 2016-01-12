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

public class ClaudiusLaertesInstance extends Instance {
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


	public ClaudiusLaertesInstance(Main main) {
		this.main = main;
		//no character in this instance
		//player = new Player(Character.Hamlet, world);

		main.box2DCamera.zoom = 0.5f;
		ChatDialog c = new ChatDialog(Character.Claudius);
		ChatDialog l = new ChatDialog(Character.Laertes);
		dialogManager = new DialogManager(main);

		dialogManager.addDialog(l, "Why have you not punished Hamlet for my fathers murder?");
		dialogManager.addDialog(c, "I love his mother Gertrude");
		dialogManager.addDialog(c, "and Hamlet is well loved by the people, punishing him may cause a revolt.");
		dialogManager.addDialog(c, "WHAT THE!!!! I have a message saying that Hamlet will be returning from England");
		dialogManager.addDialog(l, "Wow! I thought he was exiled");
		dialogManager.addDialog(c, "Yeah, he was. His return is a surprise to me too");
		dialogManager.addDialog(c, "*thought* like, OMG I specifically specified that this fool was to be executed");

		dialogManager.addDialog(c, "I know you love your father, but there is no better way to show your love for him then to avenge his death");
		dialogManager.addDialog(c, "at what lengths are you willing to seek revenge for your father's death?");
		dialogManager.addDialog(l, "I would cut Hamlet's throat in a church!");
		dialogManager.addDialog(c, "hmm I see, yes perfect I have a plan");
		dialogManager.addDialog(c, "I have this poison that will work perfectly");
		dialogManager.addDialog(c, "and your skills with a sword has recently aroused Hamlet's envy");
		dialogManager.addDialog(c, "you can challange Hamlet to a duel and we can poison your sword's tip");
		dialogManager.addDialog(l, "BRILLIANT!");
		dialogManager.addDialog(c, "if Hamlet wins, there will be a poisoned cup of wine that he shall receive as a reward");

		dialogManager.startDialogs();
		dialogManager.nextDialog();

		backgrounds[0] = new Texture("Claudius/Claudius Laertes.png");

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
