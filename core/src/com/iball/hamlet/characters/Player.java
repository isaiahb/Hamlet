package com.iball.hamlet.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.iball.hamlet.Main;
import com.iball.hamlet.enums.Character;
import com.iball.hamlet.tools.BodyFactory;

/**
 * Created by admin on 2016-01-10.
 */
public class Player {
	Vector2 direction = new Vector2();
	Vector2 velo = new Vector2();

	public Body body;
	World world;
	Character character;

	Animation idolAnimation;
	Animation walkRightAnimation;
	Animation walkUpAnimation;
	Animation walkDownAnimation;
	Animation currentAnimation;
	boolean lastDirectionRight = true;

	TextureRegion currentFrame;
	float stateTime = 0f;
	float speed = 15;

	public Player(Character character, World world) {
		this.character = character;
		this.world = world;
		idolAnimation = createAnimation(0, 2, 0.5f);
		walkRightAnimation = createAnimation(1, 4, 0.152f);
		walkDownAnimation = createAnimation(2, 4, 0.12f);
		walkUpAnimation = createAnimation(3, 4, 0.12f);
		currentAnimation = idolAnimation;
		currentFrame = currentAnimation.getKeyFrame(stateTime, true);
		createBody();
	}

	public void createBody() {
		body = BodyFactory.CreateRectangle(world, 2, 2, currentFrame.getRegionWidth()/ Main.PPM, currentFrame.getRegionHeight()/Main.PPM);
		body.setTransform(1, 1, 0);
	}
	public Animation createAnimation(int row, int numFrames, float timePerFrame) {
		int rows = 4;
		int cols = 4;
		Texture walkSheet = character.animationSheet;

		TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth() / cols, walkSheet.getHeight() / rows);
		TextureRegion[] frames = new TextureRegion[numFrames];
		int index = 0;
		for (int i = 0; i < numFrames; i++) {
			frames[index++] = tmp[row][i];
		}
		return new Animation(timePerFrame, frames);
	}

	public void updateAnimation() {
		float absX = Math.abs(direction.x);
		float absY = Math.abs(direction.y);
		if (direction.isZero()) {
			currentAnimation = idolAnimation;
		}
		else {
			if (direction.y > 0) {
				currentAnimation  = walkUpAnimation;
			}
			else {
				currentAnimation = walkDownAnimation;
			}
		}
		if (absX > absY) {
			currentAnimation = walkRightAnimation;
		}

		if(direction.x > 0) {
			if(currentFrame.isFlipX()) {
				currentFrame.flip(true, false);
			}
			lastDirectionRight = true;
		}
		if (direction.x < 0) {
			if(!currentFrame.isFlipX()) {
				currentFrame.flip(true, false);
			}
			lastDirectionRight = false;
		}

		if (direction.x == 0) {
			if (lastDirectionRight) {
				if(currentFrame.isFlipX()) {
					currentFrame.flip(true, false);
				}
			}
			else {
				if(!currentFrame.isFlipX()) {
					currentFrame.flip(true, false);
				}
			}
		}

	}

	public void updateControls() {
		direction.set(0, 0);
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			direction.y++;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			direction.y--;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			direction.x++;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			direction.x--;
		}
	}


	//Update and Render
	public void update(float delta) {
		stateTime += delta;
		currentFrame = currentAnimation.getKeyFrame(stateTime, true);
		updateAnimation();
		updateControls();

		velo.set(direction);
		velo.scl(speed);
		velo.setLength2(speed);
		body.setLinearVelocity(velo);
	}

	public void render(SpriteBatch batch, OrthographicCamera camera) {
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

		int width = currentFrame.getRegionWidth();
		int height = currentFrame.getRegionHeight();
		float x = body.getPosition().x - width/Main.PPM/2;
		float y = body.getPosition().y - height/Main.PPM/2;

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(currentFrame, x, y, width/Main.PPM, height/Main.PPM);
		batch.end();

	}
}
