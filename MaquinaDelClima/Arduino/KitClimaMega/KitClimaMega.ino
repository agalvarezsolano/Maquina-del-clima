#include <DHT.h> // importacion de la libreria para sensor de temperatura y humedad
#include <BH1750FVI.h> //importacion de la libreria para sensor de luz

#define temp0 0
#define temp1 1
#define temp2 2
#define temp3 3// puertos digitales para sensores de temperatura y humedad

DHT st0(DTHPIN, DTHTYPE); 
DHT st1(DTHPIN, DTHTYPE); 
DHT st2(DTHPIN, DTHTYPE); 
DHT st3(DTHPIN, DTHTYPE);// declaracion de las variables del sensor de temperatura y humedad 

BH1750FVI ls0; //declaracion de las variables del sensor de luz
BH1750FVI ls1;


void setup() {
Serial.begin(9600);
st0.begin();
st1.begin();
st2.begin();
st3.begin();

ls0.begin();

pinMode(temp0,INPUT);
pinMode(temp1,INPUT);
pinMode(temp2,INPUT);
pinMode(temp3,INPUT);

}

void loop() {
  
uint16_t luzSensor0 = ls0.GetLightIntensity();

int valor_hum0 = st0.readHumidity();
int valor_temp0 = st0.readTemperature();
int valor_hum1 = st1.readHumidity();
int valor_temp1 = st1.readTemperature();
int valor_hum2 = st2.readHumidity();
int valor_temp2 = st2.readTemperature();
int valor_hum3 = st3.readHumidity();
int valor_temp3 = st3.readTemperature();

int sensorLL0 = analogRead(A0);
int sensorLL0 = analogRead(A1);


}
