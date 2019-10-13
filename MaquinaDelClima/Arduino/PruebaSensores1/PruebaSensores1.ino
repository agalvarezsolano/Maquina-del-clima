#include <DHT.h>

#define LDR 0 //pin A0
#define DHTPIN 2 //pin 2
#define DHTTYPE DHT11
DHT dht(DHTPIN, DHTTYPE);



void setup() {
  Serial.begin(9600);
  dht.begin();
  pinMode(LDR,INPUT);
  pinMode(DHTPIN,INPUT);
}

void loop() {
  int valor_hum = dht.readHumidity();
  int valor_temp = dht.readTemperature();
  int valor_luz = analogRead(LDR);
  int luz = (100-((1.0 * valor_luz * 100) /1024));

  String output = " ";
  output += luz;
  output += " ";
  output += valor_temp;
  output += " ";
  output += valor_hum;
  output += " ";

  Serial.println(output);
  delay (1000);
}
