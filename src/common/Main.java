package common;

import javax.mail.Folder;
import javax.mail.Message;

import processing.core.PApplet;

public class Main extends PApplet {

	private static final long serialVersionUID = -5956874486128003923L;

	int actRandomSeed = 0;
	int count = 150;

	private int readCount = 0;
	private int unreadCount = 0;

	@Override
	public void setup() {
		size(800, 800);
		cursor(CROSS);
		smooth();

		MailApi api = new MailApi();
		count = api.getMessagesCount();
		
		Folder[] folderList = api.getFolderList();
		unreadCount = 0;
		for (Folder folder : folderList) {
			Message[] checkUnreadMails = api.checkUnreadMails(folder);
			unreadCount += checkUnreadMails.length;
		}
		System.out.println(unreadCount);

		readCount = count - unreadCount;
		System.out.println(readCount);
		
	}

	@Override
	public void draw() {
		background(255);
		noStroke();

		float faderX = (float) mouseX / width;

		randomSeed(actRandomSeed);
		float angle = radians(360 / (float) count);
		for (int i = 0; i < readCount; i++) {
			// positions
			float randomX = random(0, width);
			float randomY = random(0, height);
			float circleX = width / 2 + cos(angle * i) * 300;
			float circleY = height / 2 + sin(angle * i) * 300;

			float x = lerp(randomX, circleX, faderX);
			float y = lerp(randomY, circleY, faderX);

			fill(0, 130, 164);
			ellipse(x, y, 11, 11);
		}
		
		for (int i = 0; i < unreadCount; i++) {
			// positions
			float randomX = random(0, width);
			float randomY = random(0, height);
			float circleX = width / 2 + cos(angle * i) * 300;
			float circleY = height / 2 + sin(angle * i) * 300;

			float x = lerp(randomX, circleX, faderX);
			float y = lerp(randomY, circleY, faderX);

			fill(250, 130, 164);
			ellipse(x, y, 11, 11);
		}
	}

	@Override
	public void mouseReleased() {
		actRandomSeed = (int) random(100000);
	}

}
