package app.mygdx.music;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.awt.Color;

import javax.xml.ws.BindingType;

public class MyGdxMusic extends ApplicationAdapter {

	//Explicit
	private SpriteBatch batch;
	private Texture wallpaperTexture, cloudTexture, pigTexture;//��С�ȵ�����ٻ�Ҿ
	private OrthographicCamera objOrthographicCamera;
	private BitmapFont nameBitmapFont; //��¹����ѡ�÷�����躹��
	private int xCloudAnInt, yCloudAnInt = 600; //��˹������٧�ͧ�Ҿ
	private boolean cloudABoolean = true;
	private Rectangle pigRectangle; //��ͧ�� rectangle �ͧ badlogic
	private Vector3 objVector3;//��ͧ�� Vector3 �ͧ badlogic
	private Sound pigSound; //��ͧ�� Sound �ͧ badlogic


	@Override
	public void create () {//�������˹����
		batch = new SpriteBatch();
		// ��͡�á�˹���Ҵ�ͧ�ͷ���ͧ���
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

		//Setup Pig ����˹��ٻ�Ҿ
		pigTexture = new Texture("pig.png");

		//Setup Rectangle Pic
		pigRectangle = new Rectangle();
		pigRectangle.x = 568;
		pigRectangle.y = 100;
		pigRectangle.width = 64; //��Ҵ�ͧ�Ҿ���
		pigRectangle.height = 64; //��Ҵ�ͧ�Ҿ���

		//Setup PigSound
		pigSound = Gdx.audio.newSound(Gdx.files.internal("pig.wav"));


	}//Create

	@Override
	public void render () {// render ��ǹ���� loop
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//Setup Screen ��Ѻ������ѵ��ѵ
		objOrthographicCamera.update();
		batch.setProjectionMatrix(objOrthographicCamera.combined);

		//�������Ҵ object
		batch.begin();

		//Drawable wallpaper
		batch.draw(wallpaperTexture, 0, 0);

		//Drawable Cloud
		batch.draw(cloudTexture, xCloudAnInt, yCloudAnInt);

		//Drawable BitMapFont
		nameBitmapFont.draw(batch, "Coins PBRU", 50, 750);

		//Drawable Pig
		batch.draw(pigTexture, pigRectangle.x, pigRectangle.y);

		batch.end();

		//Move Cloud
		moveCloud();

		//Active When Touch Screen
		activeTouchScreen();

	}//render

	private void activeTouchScreen() {
		if (Gdx.input.isTouched()) { //������չ����з��ͨзӧҹ

			//Sound Effect Pig
			pigSound.play();

			objVector3 = new Vector3(); //Vector3 ��� �纤�ҷ������ⴹ
			objVector3.set(Gdx.input.getX(), Gdx.input.getY(), 0);

			if (objVector3.x < 600) { //����͹���仫��¢�� �ҡ����Ш� ���ִ�ҡ��觡�ҧ��
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
