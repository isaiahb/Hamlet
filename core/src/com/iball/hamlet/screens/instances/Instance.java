package com.iball.hamlet.screens.instances;

import com.badlogic.gdx.physics.box2d.World;
import com.iball.hamlet.dialog_system.DialogManager;

/**
 * Created by admin on 2016-01-10.
 */
public class Instance {
	World world;
	DialogManager dialogs;
	public void update(float delta) {}
	public void render(){}
	public void dispose(){world.dispose(); dialogs.dispose();}

}
