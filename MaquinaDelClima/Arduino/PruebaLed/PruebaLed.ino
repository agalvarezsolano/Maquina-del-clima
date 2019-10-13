/*
   Este código permite controlar el LED asociado al pin 13 del Arduino. A través
   del Monitor Serie, enviando un "1" encendemos un LED y con cualquier otro caracter
   apagamos el LED. Cuando se produce una acción, ya sea de encendido o apagado,
   Arduino devuelve un mensaje ("Encendido" o "Apagado"), seguido del tiempo en
   milisegundos en el cual se produjo la acción desde el inicio de la ejecución
   del algoritmo en Arduino(función millis())
*/

void setup() {
  // Declaramos que utilizaremos el pin 13 como salida
  pinMode(13, OUTPUT);
  pinMode(8, OUTPUT);
  pinMode(12,OUTPUT);
  pinMode(11,OUTPUT);
  pinMode(10,OUTPUT);
  
  //Iniciamos la comunicación con el puerto serie
  Serial.begin(9600);
}

void loop() {
  //En caso que haya información en el Serial Port, se entra en esta estructura
  if (Serial.available() > 0) {
    //Se lee la información entrante en el Serial Port
    int input = Serial.read();
    
        if(input == '0') 
    {
      //Si el valor de input es diferente de 1, se apaga el LED
      digitalWrite(13, HIGH);
      digitalWrite(8,LOW);
      /**
         Se envía el mensaje "Apagado", seguido de un símbolo de @
         y el tiempo de ejecución en milisegundos, obtenido a través de millis()
      */
      String output = "Apagado @";
      output += millis();
      output += "ms";
      //Se imprime el mensaje en el puerto serie
      Serial.println(output);
    }
    if (input == '1') {
      //Si el valor de input es 1, se enciende el led
      digitalWrite(13, LOW);
      digitalWrite(8, HIGH);
      /**
         Se envía el mensaje "Encendido", seguido de un símbolo de @
         y el tiempo de ejecución en milisegundos, obtenido a través de millis()
      */
      String output = "Encendido @";
      output += millis();
      output += "ms";
      //Se imprime el mensaje en el puerto serie
      Serial.println(output);
    }
    
    if(input == '2') 
    {
            //Si el valor de input es -1, se apagan los LEDs
      digitalWrite(13, HIGH);
      digitalWrite(8,HIGH);
      /**
         Se envía el mensaje "Apagado", seguido de un símbolo de @
         y el tiempo de ejecución en milisegundos, obtenido a través de millis()
      */
      String output = "Apagado @";
      output += millis();
      output += "ms";
      //Se imprime el mensaje en el puerto serie
      Serial.println(output);
    }
    if(input == '3'){
      digitalWrite(12, LOW);

      String output = "Apagado @";
      output += millis();
      output += "ms";
      //Se imprime el mensaje en el puerto serie
      Serial.println(output);
    }
        if(input == '4'){
      digitalWrite(11, LOW);
      digitalWrite(12, HIGH);
      String output = "Apagado @";
      output += millis();
      output += "ms";
      //Se imprime el mensaje en el puerto serie
      Serial.println(output);
    }
        if(input == '5'){
      digitalWrite(10, LOW);
      digitalWrite(11, HIGH);
      digitalWrite(12, HIGH);
      String output = "Apagado @";
      output += millis();
      output += "ms";
      //Se imprime el mensaje en el puerto serie
      Serial.println(output);
    }
        if(input == '6'){
      digitalWrite(12, HIGH);
      digitalWrite(11, HIGH);
      digitalWrite(10, HIGH);

      String output = "Apagado @";
      output += millis();
      output += "ms";
      //Se imprime el mensaje en el puerto serie
      Serial.println(output);
    }
    
    
  }
}

