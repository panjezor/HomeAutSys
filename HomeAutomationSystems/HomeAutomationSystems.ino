  /*
   Chat Server - A simple server that distributes any incoming messages to all
   connected clients.  To use, telnet to your device's IP address and type.
   You can see the client's input in the serial monitor as well.
   Using an Arduino Wiznet Ethernet shield.
   Circuit:
   * Ethernet will be shield attached to pins 10, 11, 12, 13
   */
   ///libabrys 
  #include <SPI.h>
  #include <Ethernet.h>
  #include <DS1631.h>
// include the library code:
#include <LiquidCrystal.h>
#include <Wire.h>
#include <VL6180X.h>

//buzzers
boolean alarmSet =true;
const int buzz = 8;

// initialize the library by associating any needed LCD interface pin
// with the arduino pin number it is connected to

  //global vars
  String comm;
  boolean heatState = false;
  boolean heatOpt = false;
  float maxTemp = 0; 
 
  //lcd pins 
  const int rs = A1, en = A2, d4 = 3, d5 = 4, d6 = 5, d7 = 6;
  LiquidCrystal lcd(rs, en, d4, d5, d6, d7);

  

  //heat pins and intturpt for pins
  int heatLight = A3; // heat
  int heatBtn = 2;   // choose the input pin (for a pushbutton)


  
  int count = 0;
  
  // Enter a MAC address and IP address for your controller below.
  // The IP address will be dependent on your local network.
  // gateway and subnet are optional:
   byte mac[] = {
    0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED
  };
  //
  IPAddress ip(192, 168, 1, 2);
  IPAddress myDns(0,0,0, 0);
  IPAddress gateway(0, 0, 0, 0);
  IPAddress subnet(255, 255, 255, 0);
  
  // telnet defaults to port 23
  EthernetServer server(23);
  boolean alreadyConnected = false; // whether or not the client was connected previously
  //setup
  void setup() {
//////////////pins setup////////////////////
  //setup arduino pins (I/O)
  for(int i = 3; i<=5;i++){
    pinMode(i, OUTPUT);
    }
    //for lights
    pinMode(A3, OUTPUT);
    pinMode(A2, INPUT);
    pinMode(A1, OUTPUT);
    pinMode(A0, OUTPUT);
  for(int i= 6; i<9;i++){
    pinMode(i, INPUT);
    }

//////////////////////sets heat pin////////////////////////////////////////////////////
  attachInterrupt(heatBtn,heatInt, HIGH);


    /////lcd///////
lcd.begin(16, 2);
 ///////////////Internet/////////////////////////////////////////////////   
    // initialize the ethernet device
   Serial.println("Trying to get an IP address using DHCP");
  if (Ethernet.begin(mac) == 0) {
    Serial.println("Failed to configure Ethernet using DHCP");
    // initialize the Ethernet device not using DHCP:
    Ethernet.begin(mac, ip, myDns, gateway, subnet);
  }
    // start listening for clients
    server.begin();
    // Open serial communications and wait for port to open:
    Serial.begin(9600);
    while (!Serial) {
      ; // wait for serial port to connect. Needed for native USB port only
    }
    Serial.print("Chat server address:");
    Serial.println(Ethernet.localIP());
// start wire
  Wire.begin(); 
////////////////////VL6180x///////////////////////////////////////////////
}
  
      void loop() {
        //float heatvalue = tempSensorRead(0);
      
//////////////////////////Internet/////////////////////////////////////////////////////////////
      // wait for a new client:
      EthernetClient client = server.available();
      // when the client sends the first byte, say hello:
      
      if (client) {
      if (!alreadyConnected) {
        // clear out the input buffer:
        client.flush();
        alreadyConnected = true;
      }
      //input for user (us)
      //add string for this section
      if (client.available() > 0) {
       
        // read the bytes incoming from the client:
        for(int i =0; i<=3;i++){
         char thisChar = client.read();
        comm +=thisChar;
      }
        Serial.println(comm);
        //if command letter written
      //  if(thisChar =='\n'){
          comm.trim() ;
        //split to commands
        Serial.println("Hello convert to test");
        int value = comm.toInt();
        int arg1 = (value / 1) % 10;
        int arg2 = (value / 10) % 10;
        int arg3 = (value / 100) % 10 ;
      //if less than 700 it means a simple setting change
         if (value <= 700){    
          Serial.println("less than 700");
         //if less than 75, it is for lights
          if(value <75){
            //turn on/off lights (e.g. 11 turns on lights)
            light(arg2,arg1);
            }
            //if more than 75 its for heat(or alarm) on or off
            if(75 < value){
              //to decide what setting (e.g. 91)
              switch(arg2){
                case 8:
                  Serial.println("heat");
                  heatInt(arg1);
                  break;
                case 9:
                  Serial.println("alarm");
                  setAlarm(arg1);
                  break;
                }
              //heat on here
              
              }
         }
            //heating to be changed
          else if(value>700){
                Serial.println("more than 700");
            switch(arg3){
              case 9:
              server.print((String)1);
              Serial.println("Sending output");
              break;
              case 7:
                int c = ((String)arg2 + (String)arg3).toInt(); 
                setMaxHeat(c);
                //code to change setting of temp
       
                Serial.println(c);
                //want to change temperature
                Serial.println("hello world");
            //switchcase
              break;
             }       
            
      
          }
         }
         comm = "";
       // }else{
      //       if(isDigit(thisChar)){
       //        comm += thisChar;
        //    }
        Ethernet.maintain();
         }
        //if not new line
        // echo the bytes back to the client:
          

        /////alarm sytem/////////////////////////////////////////
          int r = rangeSensor(41);
                if((r< 200)){
                  if(alarmSet){
                    Serial.println(r);
                    Serial.println("Alarm");
                    tone(buzz, 1000);
                  }
                  else{
                    noTone(buzz);
                    }
                  
                  Serial.println("Less than 200");
                }
  
   if(heatState){
     float tempReading = tempSensorRead(72);
     if(tempReading < maxTemp){
		heat(1);
      }else if(maxTemp<tempReading){
		heat(0);
	  }
    }
   }
void setAlarm(int value){
  if(value <= 1){
    alarmSet =true;
    }else{
    alarmSet= false;
      }
}            
void setAlarm(){
  alarmSet = !alarmSet;  
}   
      
 ///////////////////////Range Sensor///////////////////////////////////////////////   

int rangeSensor(int device){
  //creates sensor
  VL6180X sensor;
  sensor.setAddress(device);
  sensor.init();
  sensor.configureDefault();
  sensor.setTimeout(500);
  int range = sensor.readRangeSingleMillimeters();  
  if (sensor.timeoutOccurred()) { Serial.print(" TIMEOUT"); }
  return range;
}
  
/////////////////////////////heating///////////////////////////////////////////

float tempSensorRead(int addr){
  //create the vars
  DS1631 Temp1 = DS1631(addr);
  float output = 0;
  //configure sensor
  int config = Temp1.readConfig();
  Temp1.writeConfig(13);
  config = Temp1.readConfig();
  
  //read the sensor
  output = Temp1.readTempOneShot(); 
  //return to loop with data
 return output;
}


//code to turn on/off heat relay(and redLED)
//switch by software
void heat(int input){
  if(input == 1 ){
  digitalWrite(heatLight, HIGH);
   }else{
   digitalWrite(heatLight, LOW);

  }
}
//switches button state if inturrpt
void heatInt(){
heatOpt = !heatOpt;
}
void heatInt(int input){
  if(input == 1 ){
		heatState = true;
   }else{
		heatState=false;
  }
}
void setMaxHeat(int input){
	maxTemp = input;
}

///////////////////////lighting/////////////////////////////////////////////
//to turn lights on/off
void light(int pin, int value){
  if(value <=1){
    Serial.println("Turning lights on");
     digitalWrite(pin, HIGH);
    }else{
     digitalWrite(pin, LOW);
     }
}
///////////////lcd program///////////////////////////////////
void lcdInput(String sInput, int timeInput){
  
  lcd.setCursor(0, 0);
  lcd.clear();
  // print the number of seconds since reset:
  lcd.print(sInput);
  wait(timeInput);
  lcd.clear();
  }

void wait(int t){
  int cmilli = 0, smilli = 0;
  smilli = millis();
  
  while((cmilli - smilli) <= t){
    cmilli = millis();
    }

 }


