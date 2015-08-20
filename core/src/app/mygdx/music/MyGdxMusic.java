package app.mygdx.music;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.Color;

import javax.xml.ws.BindingType;

public class MyGdxMusic extends ApplicationAdapter {

	//Explicit
	private SpriteBatch batch;
	private Texture wallpaperTexture;
	private OrthographicCamera objOrthographicCamera;
	private BitmapFont nameBitmapFont; //เขียนตัวอักษรที่อยู่บนเกม


	
	@Override
	public void create () {//เอาไว้กำหนดค่า
		batch = new SpriteBatch();
		// คือการกำหนดขนาดของจอที่ต้องการ
		objOrthographicCamera = new OrthographicCamera();
		objOrthographicCamera.setToOrtho(false, 1200, 800);

		//setup wallpaper
		wallpaperTexture = new Texture("wallpapers_a_01.png");

		//setup BitMapFont
		nameBitmapFont = new BitmapFont();
		nameBitmapFont.setColor(com.badlogic.gdx.graphics.Color.BLACK); //font color
		nameBitmapFont.setScale(4); //font size

	}//Create

	@Override
	public void render () {// render ตัวนี้คือ loop
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//Setup Screen ปรับจอให้อัตโนมัต
		objOrthographicCamera.update();
		batch.setProjectionMatrix(objOrthographicCamera.combined);

		//เอาไว้วาด object
		batch.begin();

		//Drawable wallpaper
		batch.draw(wallpaperTexture, 0, 0);

		//Drawable BitMapFont
		nameBitmapFont.draw(batch, "Coins PBRU", 50, 600);


		batch.end();
	}//render
}//Main Class
