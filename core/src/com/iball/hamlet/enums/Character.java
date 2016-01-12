package com.iball.hamlet.enums;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by admin on 2016-01-10.
 */
//for now the follow characters are using hamlets animation sheet.
//TODO create animation sheet for, Claudius, Gertude, Polonius, Laertes, Fortinbras
public enum Character {
	RG,
	Hamlet,
	Horatio,
	Ghost,
	Claudius,
	Gertrude,
	Polonius,
	Laertes,
	Fortinbras;

	public Texture animationSheet;
	public TextureRegion chatTexture;
	Character() {
		animationSheet = new Texture(this.name() + "-Animations.png");
		chatTexture = new TextureRegion(animationSheet, 0, 0, 22, 22);
		System.out.println(this.name());
	}

}
