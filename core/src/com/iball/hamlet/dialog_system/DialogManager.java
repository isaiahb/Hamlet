package com.iball.hamlet.dialog_system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.iball.hamlet.Main;

import java.util.ArrayList;

/**
 * Created by admin on 2016-01-11.
 */
public class DialogManager {
	ChatDialog currentDialog = null;
	ArrayList<ChatDialog> dialogs = new ArrayList<ChatDialog>();
	int index = -1;
	Label label;
	Stage stage;

	Color dialogBackgroundColor;
	boolean start = false;

	public DialogManager(Main main) {
		label = new Label("Should you be seeing this?", new Label.LabelStyle(main.font, Color.WHITE));
		label.setPosition(75, 70/2 - label.getPrefHeight()/2);
		label.setWrap(true);
		label.setWidth(Gdx.graphics.getWidth() - 80);
		stage = new Stage(main.viewPort);
		stage.addActor(label);
		dialogBackgroundColor = new Color(0, 0, 0, 0.5f);
		dialogs.add(null);
		nextDialog();
	}

	public void addDialog(ChatDialog chatDialog, String text) {
		dialogs.add(chatDialog);
		chatDialog.putText(text);

	}

	public void pause() {
		dialogs.add(null);
	}
	public void nextDialog() {
		System.out.println("index: " + index + ", dialog.size(): " + dialogs.size());
		if (index+1 == dialogs.size()) {
			System.out.println("out of dialogs");
			return;
		}
		index++;
		currentDialog = dialogs.get(index);
		if (currentDialog == null) {
			System.out.println("null dialing, pausing dialog system");
		}
		else {
			String s = currentDialog.getText();
			label.setText(s);
//			label.setPosition(75, 70 - label.getPrefHeight());
//			label.setPosition(75, 70/2 - label.getPrefHeight()/2);

			System.out.println(s);
		}
	}
	public void startDialogs() {
		start = true;
		System.out.println("Starting the dialog System");
	}
	public void render(OrthographicCamera camera, ShapeRenderer shapeRenderer, SpriteBatch batch) {
		if (!start) return;
		if (currentDialog == null) return;
		float width = camera.viewportWidth;
		float height = 70;

		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(dialogBackgroundColor);
		shapeRenderer.rect(0,0,width,height);
		shapeRenderer.end();

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(currentDialog.character.chatTexture, 2,2, 66, 66);
		batch.end();

		stage.draw();

	}
}

/*

dialogManager.addDialog(jesterDialog, "hey what's up man, do you have some of the good stuff?");
dialogManager.addDialog(horatioDialog, "what do you mean?");
dialogManager.pause(pickup); //if an iteam was touched or w/e pickup will return true


 */