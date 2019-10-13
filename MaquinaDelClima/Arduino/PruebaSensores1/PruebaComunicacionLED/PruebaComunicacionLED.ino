#define LED 13 // pin 13

void setup() {
  Serial.begin(9600);
  pinMode(LED,OUTPUT);
}

void loop() {
  if(Serial.available() > 0){
    int input = Serial.read();
    if (input == '1'){
      digitalWrite(13, HIGH);
    }
    else{
      digitalWrite(13,LOW);
    }
  }
}
