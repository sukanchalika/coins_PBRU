package app.mygdx.music;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
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
	private Texture wallpaperTexture, cloudTexture, pigTexture,
			coinsTexture,rainTexture;//รูปภาพจะอยู่ที่นี่
	private OrthographicCamera objOrthographicCamera;
	private BitmapFont nameBitmapFont, scoreBitmapFont,showScoreBitmapFont; // font on game
	private int xCloudAnInt, yCloudAnInt = 600; //size of picture cloud
	private boolean cloudABoolean = true, finishABoolean = false;
	private Rectangle pigRectangle, coinsRectangle, rainRectangle; //rectangle ==> badlogic
	private Vector3 objVector3;//Vector3 ==> badlogic
	private Sound pigSound, waterDropSound, coinsDropSound; //Sound ==> badlogic
	private Array<Rectangle> coinsArray, rainArray; //Array of Badlogic
	private long lastDropCoins,lastDropRain; //random coins and new position
	private Iterator<Rectangle> coinsIterator,rainIterator; //Interator ==> Java.util
	private int scoreAnInt = 0, falseAnInt = 0, finalScoreAnInt;//falseAnInt=เหรียญหล่นพื้น
	private Music rainMusic, backgroundMusic; //เสียงครั้งเดียวใช้ .wav, sound background ใช้ .mp3


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

		//Setup ScoreBitMapFont
		scoreBitmapFont = new BitmapFont();
		scoreBitmapFont.setColor(com.badlogic.gdx.graphics.Color.BLUE);
		scoreBitmapFont.setScale(4); //font size

		//Setup rainTexture
		rainTexture = new Texture("droplet.png");

		//Create rainArray
		rainArray = new Array<Rectangle>();
		rainRandomDrop();

		//Setup rainMusic = sound background
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

		//Setup background
		backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("bggame.mp3"));

		//Setup ShowScore
		showScoreBitmapFont = new BitmapFont();
		showScoreBitmapFont.setColor(230, 28, 223, 255);//setColor type RGB
		showScoreBitmapFont.setScale(5);


	}//Create เอาไว้กำหนดค่า

	private void rainRandomDrop() {
		rainRectangle = new Rectangle();
		rainRectangle.x = MathUtils.random(0, 1136); //MathUtils ==> badlogic , 1136 = 1200-64
		rainRectangle.y = 800; //monitor height
		rainRectangle.width = 64; //rain width 64
		rainRectangle.height = 64; //rain height 64

		rainArray.add(rainRectangle);
		lastDropRain = TimeUtils.nanoTime();




	}//rainRandomDrop

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

		//เอาไว้วาด object
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

		//Drawable Score
		scoreBitmapFont.draw(batch, "Score = " + Integer.toString(scoreAnInt), 700, 750);
		//integer.toString เป็นตัวเลขไปเป็นตัวอักษร ตำแหน่งตัวอักษร 700, 750

		//Drawable Rain
		for (Rectangle forRain : rainArray) {
			batch.draw(rainTexture, forRain.x, forRain.y);
		}

		if (finishABoolean) {
			batch.draw(wallpaperTexture, 0, 0);
			showScoreBitmapFont.draw(batch, "Your Score ==> " + Integer.toString(finalScoreAnInt), 500, 750);
		}//if


		batch.end();

		//Move Cloud
		moveCloud();

		//Active When Touch Screen
		activeTouchScreen();

		//Random Drop Coins
		randomDropCoins();

		//Random Drop Rain
		randomDropRain();

		//Play rainMusic
		rainMusic.play();

		//Play backgroundMusic
		backgroundMusic.play();


	}//render ตัวนี้คือ loop

	private void randomDropRain() {//Rain Picture
		if (TimeUtils.nanoTime() - lastDropRain > 1E9) {
			rainRandomDrop();
		}//if
		rainIterator = rainArray.iterator();
		while (rainIterator.hasNext()) {
			Rectangle myRainRectangle = rainIterator.next();
			myRainRectangle.y -= 20 * Gdx.graphics.getDeltaTime(); //speed of rain =20

			//When Rain drop into Floor
			if (myRainRectangle.y +64 < 0) {
				waterDropSound.play();
				rainIterator.remove();
			}//if

			//When Rain overlap Pig
			if (myRainRectangle.overlaps(pigRectangle)) {
				scoreAnInt -= 1; // ลดคะแนน
				waterDropSound.play();
				rainIterator.remove();
			}//if

		}//while

	}//randomDropRain

	private void randomDropCoins() {//Coins Picture
		if (TimeUtils.nanoTime() - lastDropCoins > 1E9) {
		//1E9 => 10^9 random every 1 second ให้ทำการหยดน้ำมาใหม่
			coinsRandomDrop();
		}
		coinsIterator = coinsArray.iterator();
		while (coinsIterator.hasNext()) {//มีค่าต่อไปเรื่อยๆ
			Rectangle myCoinsRectangle = coinsIterator.next();
			myCoinsRectangle.y -= 50 * Gdx.graphics.getDeltaTime(); //coins drop speed 50

			//When Coins drop into Floor
			if (myCoinsRectangle.y +64 < 0 ) {
				falseAnInt += 1;
				waterDropSound.play(); //เมื่อเหรียญหล่นพื้นให้เกิดเสียง
				coinsIterator.remove(); //clear coins when coins drop to floor เคลียค่าหน่วยความจำเมื่อเหรียญหล่นพื้น
				checkFalse();
			}//if

			//When Coins Overlap Pig
			if (myCoinsRectangle.overlaps(pigRectangle)) {//เหรียญซ้อนทับกับหมู
				scoreAnInt += 1;//เพิ่มค่าคะแนน
				coinsDropSound.play();
				coinsIterator.remove(); //ให้เหรียญหายไปตอนเหรียญซ้อนทับกับหมู
			}//if


		}//while loop

	} //randomDropCoins

	private void checkFalse() {
		if (falseAnInt > 20) {//เหรียญตกพื้นเกิน 20 เหรียญ
			dispose();

			if (!finishABoolean){
				finalScoreAnInt = scoreAnInt;
			}
			finishABoolean=true;
		}//if

	}//checkFalse => end game

	@Override
	public void dispose() {//หยุดเกม alt+Ins => Override Method => dispose
		super.dispose();

		backgroundMusic.dispose(); //stop sound
		rainMusic.dispose();// stop rain
		pigSound.dispose();
		waterDropSound.dispose();
		coinsDropSound.dispose();

	}//dispose

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
