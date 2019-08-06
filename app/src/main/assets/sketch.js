function setup() {
  createCanvas(640, 480);
  fill(255, 0, 0);
  ellipse(40, 40, 80, 80);
}

function draw() {
  if (mouseIsPressed) {
    print(mouseX);
    fill(0);
  } else {
    fill(255);
  }
  ellipse(mouseX, mouseY, 80, 80);
}