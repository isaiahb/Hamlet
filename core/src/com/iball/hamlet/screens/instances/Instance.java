package com.iball.hamlet.screens.instances;

import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by admin on 2016-01-10.
 */
public class Instance {
	World world;
	public void update(float delta) {}
	public void render(){}
	public void dispose(){world.dispose();}

}
