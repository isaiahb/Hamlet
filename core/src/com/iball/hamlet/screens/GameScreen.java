package com.iball.hamlet.screens;

import com.badlogic.gdx.Screen;
import com.iball.hamlet.Main;
import com.iball.hamlet.screens.instances.GhostChaseInstance;
import com.iball.hamlet.screens.instances.Instance;
import com.iball.hamlet.screens.instances.claudius.ClaudiusHamletInstance;
import com.iball.hamlet.screens.instances.claudius.ClaudiusLaertesInstance;
import com.iball.hamlet.screens.instances.hamlet.HamletChurchClaudiusInstance;
import com.iball.hamlet.screens.instances.horatio.HoratioAnticDispositionInstance;
import com.iball.hamlet.screens.instances.laertes.LaertesDeathInstance;
import com.iball.hamlet.screens.instances.rg.GhostScareInstance;
import com.iball.hamlet.screens.instances.rg.GoodStuffInstance;
import com.iball.hamlet.screens.instances.rg.RGJustSawGhostInstance;

/**
 * Created by admin on 2016-01-10.
 */
public class GameScreen implements Screen {
	Instance currentInstance;
	Main main;
	public GameScreen(Main main) {
//		currentInstance = new GoodStuffInstance(main);
//		currentInstance = new RGJustSawGhostInstance(main);
//		currentInstance = new ClaudiusLaertesInstance(main);
//		currentInstance = new ClaudiusHamletInstance(main);
//		currentInstance = new HamletChurchClaudiusInstance(main);
//		currentInstance = new LaertesDeathInstance(main);
//		currentInstance = new HoratioAnticDispositionInstance(main);
//		currentInstance = new GhostScareInstance(main);
		currentInstance = new GhostChaseInstance(main);
	}
	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		currentInstance.update(delta);

		currentInstance.render();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {
		currentInstance.dispose();
	}
}
