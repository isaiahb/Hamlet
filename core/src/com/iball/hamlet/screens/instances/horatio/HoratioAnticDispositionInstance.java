package com.iball.hamlet.screens.instances.horatio;

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

public class HoratioAnticDispositionInstance extends Instance {
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
	int textures = 10;
	Texture[] backgrounds = new Texture[textures];
	int textureIndex = 0;

	//Dialog Manager
	DialogManager dialogManager;


	public HoratioAnticDispositionInstance(Main main) {
		this.main = main;
		//no character in this instance
		//player = new Player(Character.Hamlet, world);

		main.box2DCamera.zoom = 0.5f;
		ChatDialog hamlet = new ChatDialog(Character.Hamlet);
		ChatDialog horatio = new ChatDialog(Character.Horatio);
		ChatDialog ghost = new ChatDialog(Character.Ghost);
		dialogManager = new DialogManager(main);

		dialogManager.addDialog(horatio, "What happened, sir?");
		dialogManager.addDialog(hamlet, "It was incredible!");
		dialogManager.addDialog(hamlet, "Horatio, you must promise not to speak of any thing you saw tonight");
		dialogManager.addDialog(horatio, "I won't");
		dialogManager.addDialog(ghost, "Swear!");
		dialogManager.addDialog(horatio, "who, I swear I shall not speak of it");
		dialogManager.addDialog(hamlet, "Good");
		dialogManager.addDialog(hamlet, "my father has revealed to me that he was poisoned by Claudius");
		dialogManager.addDialog(hamlet, "I plan on avenging my father's murder");
		dialogManager.addDialog(hamlet, "I may find it appropriate to act crazy in the near future, to avoid suspision I will put on an antic dispotition");
		dialogManager.addDialog(horatio, "I got you man");

		dialogManager.addDialog(hamlet, "What did you do?");
		dialogManager.addDialog(horatio, "This one spell I learned effects the mind");
		dialogManager.addDialog(horatio, "You won't need to put on a disposition");

		dialogManager.startDialogs();
		dialogManager.nextDialog();

		backgrounds[0] = new Texture("Horatio/H1.png");
		backgrounds[1] = new Texture("Horatio/H2.png");
		backgrounds[2] = new Texture("Horatio/H1.png");
		backgrounds[3] = new Texture("Horatio/H2.png");
		backgrounds[4] = new Texture("Horatio/H1.png");
		backgrounds[5] = new Texture("Horatio/H2.png");

		backgrounds[6] = new Texture("Horatio/H5.png");
		backgrounds[7] = new Texture("Horatio/H2.png");
		backgrounds[8] = new Texture("Horatio/H5.png");
		backgrounds[9] = new Texture("Horatio/H2.png");
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
