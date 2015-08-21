package app.mygdx.music;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.awt.Color;
import java.util.Iterator;

import javax.xml.ws.BindingType;

public class MyGdxMusic extends ApplicationAdapter {

	//Explicit
	private SpriteBatch batch;
	private Texture wallpaperTexture, cloudTexture, pigTexture, coinsTexture;//
	private OrthographicCamera objOrthographicCamera;
	private BitmapFont nameBitmapFont; //
	private int xCloudAnInt, yCloudAnInt = 600; //
	private boolean cloudABoolean = true;
	private Rectangle pigRectangle, coinsRectangle; //rectangle ==> badlogic
	private Vector3 objVector3;//Vector3 ==> badlogic
	private Sound pigSound, waterDropSound, coinsDropSound; //Sound ==> badlogic
	private Array<Rectangle> coinsArray; //Array of Badlogic
	private long lastDropCoins; //random coins and new position
	private Iterator<Rectangle> coinsIterator; //Interator ==> Java.util


	@Override
	public void create () {//
		batch = new SpriteBatch();
		//
		objOrthographicCamera = new OrthographicCamera();
		objOrthographicCamera.setToOrtho(false, 1200, 800);

		//setup wallpaper
		wallpaperTexture = new Texture("wallpapers_a_01.png");

		//setup BitMapFont
		nameBitmapFont = new BitmapFont();
		nameBitmapFont.setColor(com.badlogic.gdx.graphics.Color.RED); //font color
		nameBitmapFont.setScale(4); //font size

		//Setup Cloud
		cloudTexture = new Texture("cloud.png");

		//Setup Pig
		pigTexture = new Texture("pig.png");

		//Setup Rectangle Pic
		pigRectangle = new Rectangle();
		pigRectangle.x = 568;
		pigRectangle.y = 100;
		pigRectangle.width = 64; //ความกว้างของรูปหมู
		pigRectangle.height = 64; //ความสูงของรูปหมู

		//Setup PigSound
		pigSound = Gdx.audio.newSound(Gdx.files.internal("pig.wav"));

		//Setup Coins
		coinsTexture = new Texture("coins.png");

		//Create coinsArray จำนวนเหรียญที่ตกลงมา
		coinsArray = new Array<Rectangle>();
		coinsRandomDrop(); //ตำแหน่งที่ต้องการสุ่มให้เหรียญตก

		//Setup WaterDrop
		waterDropSound = Gdx.audio.newSound(Gdx.files.internal("water_drop.wav"));

		//Setup CoinsDropSound
		coinsDropSound = Gdx.audio.newSound(Gdx.files.internal("coins_drop.wav"));


	}//Create

	private void coinsRandomDrop() {
		coinsRectangle = new Rectangle();
		coinsRectangle.x = MathUtils.random(0, 1136);
		//พิกัดของเหรียญด้านแกน x ==> badlogic เลือก random(float start) 1200-64 = 1136
		coinsRectangle.y = 800; //เหรียญตกมาจากด้านบน
		coinsRectangle.width = 64;
		coinsRectangle.height = 64;
		coinsArray.add(coinsRectangle);
		lastDropCoins = TimeUtils.nanoTime(); // TimeUtils ==> badlogic


	} //coinsRandomDrop

	@Override
	public void render () {// render loop
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//Setup Screen
		objOrthographicCamera.update();
		batch.setProjectionMatrix(objOrthographicCamera.combined);

		//object
		batch.begin();

		//Drawable wallpaper
		batch.draw(wallpaperTexture, 0, 0);

		//Drawable Cloud
		batch.draw(cloudTexture, xCloudAnInt, yCloudAnInt);

		//Drawable BitMapFont
		nameBitmapFont.draw(batch, "Coins PBRU", 50, 750);

		//Drawable Pig
		batch.draw(pigTexture, pigRectangle.x, pigRectangle.y);

		//Drawable Coins
		for (Rectangle forCoins: coinsArray) {
			batch.draw(coinsTexture, forCoins.x, forCoins.y);

		}

		batch.end();

		//Move Cloud
		moveCloud();

		//Active When Touch Screen
		activeTouchScreen();

		//Random Drop Coins
		randomDropCoins();


	}//render

	private void randomDropCoins() {
		if (TimeUtils.nanoTime() - lastDropCoins > 1E9) {//1E9 => 10^9 random every 1 second
			coinsRandomDrop();
		}
		coinsIterator = coinsArray.iterator();
		while (coinsIterator.hasNext()) {//มีค่าต่อไปเรื่อยๆ
			Rectangle myCoinsRectangle = coinsIterator.next();
			myCoinsRectangle.y -= 50 * Gdx.graphics.getDeltaTime(); //coins drop speed 50

			//When Coins into Floor
			if (myCoinsRectangle.y +64 < 0 ) {
				waterDropSound.play(); //เมื่อเหรียญหล่นพื้นให้เกิดเสียง
				coinsIterator.remove(); //clear coins when coins drop to floor เคลียค่าหน่วยความจำเมื่อเหรียญหล่นพื้น
			}//if

			//When Coins Overlap Pig
			if (myCoinsRectangle.overlaps(pigRectangle)) {//เหรียญซ้อนทับกับหมู
				coinsDropSound.play();
				coinsIterator.remove(); //ให้เหรียญหายไปตอนเหรียญซ้อนทับกับหมู
			}//if


		}//while loop

	} //randomDropCoins

	private void activeTouchScreen() {
		if (Gdx.input.isTouched()) { //มีการสัมผัสที่จอภาพ

			//Sound Effect Pig
			pigSound.play();

			objVector3 = new Vector3(); //Vector3
			objVector3.set(Gdx.input.getX(), Gdx.input.getY(), 0);


			if (objVector3.x < 600) { //
				if (pigRectangle.x < 0) {
					pigRectangle.x = 0;
				} else {
					pigRectangle.x -= 10;
				}

			} else {
				if (pigRectangle.x > 1136) {
					pigRectangle.x = 1136;
				} else {
					pigRectangle.x += 10;
				}

			}


		}// if

	} //activeTouchScreen

	private void moveCloud() {
		if (cloudABoolean) {
			if (xCloudAnInt < 937) {
				xCloudAnInt += 100 * Gdx.graphics.getDeltaTime();
			} else {
				cloudABoolean = !cloudABoolean;
			}
		} else {
			if (xCloudAnInt > 0) {
				xCloudAnInt -= 100 * Gdx.graphics.getDeltaTime();
			} else {
				cloudABoolean = !cloudABoolean;
			}

		}
	} //moveCloud
}//Main Class
