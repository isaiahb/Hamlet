package com.iball.hamlet.dialog_system;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.iball.hamlet.enums.Character;

import java.util.ArrayList;

/**
 * Created by admin on 2016-01-11.
 */
public class ChatDialog {
	public Character character;
	ArrayList<String> texts = new ArrayList<String>();
	int index = -1;

	public ChatDialog(Character character) {
		this.character = character;
	}

	public TextureRegion textureRegion;
	public void putText(String string) {
		texts.add(string);
	}
	public String getText() {
		index++;
		return (character.name() + ": " + texts.get(index));
	}
}
