import processing.pdf.*;
import java.util.Calendar;



int actRandomSeed = 0;
int count = 1000;
color [] colors;

void setup() {
  size(1200,1200); 
  cursor(CROSS);
  smooth();
  
  colors = new color [ 3 ];
  
  colors [0] = #FF0000;
  colors [1] = color (120, 0, 5);
  colors [2] = color (random(255), random(255), random(255)); 
}

void draw() {
  background(255);
  noStroke();

  float faderX = (float)mouseX/width;

  randomSeed(actRandomSeed);
  float angle = radians(360/float(count));
  for (int i=0; i<count; i++){
    // positions
    float randomX = random(0,width);  
    float randomY = random(0,height);
    float circleX = width/2 + cos(angle*i)*300;
    float circleY = height/2 + sin(angle*i)*300;

    float x = lerp(randomX,circleX, faderX);
    float y = lerp(randomY,circleY, faderX);

    color c= colors [2];
    fill(c);
    ellipse(x,y,11,11);
  }

for (int i=0; i<count; i++){
    // positions
    float randomX = random(0,width);  
    float randomY = random(0,height);
    float circleX = width/2 + cos(angle*i)*300;
    float circleY = height/2 + sin(angle*i)*300;

    float x = lerp(randomX,circleX, faderX);
    float y = lerp(randomY,circleY, faderX);

    color c= colors[1];
    fill(c);
    ellipse(x,y,11,11);
  }
  
}

void mouseReleased() {
  actRandomSeed = (int) random(100000);
}

void setRandomColors ()
{
  int i = 0;
  while (i < colors. length)
  {
    colors [i] = color (random(255),random(255), random(255));
    i = i + 1;
  }
}

void mousePressed ()
{
  setRandomColors();
}

void keyReleased() {  
  if (key == 's' || key == 'S') saveFrame(timestamp()+"_####.png");
}

String timestamp() {
  Calendar now = Calendar.getInstance();
  return String.format("%1$ty%1$tm%1$td_%1$tH%1$tM%1$tS", now);
}
