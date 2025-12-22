
##dockerfile ile image oluşturmak
 

1-localde çalıştırılıcak image için kullanım

  docker build -t isim:version .
2- docker hub üzerinde yayınlamak ise

  docker build -t donkerhubdakiname/name:version .

3- Eğer M chip bir macOs var ise
   
  docker build --platform linux/amd64 -t dockerhubname/projename:version .


docker build -t mihribantanc/configserver:v01 . 
terminalden projenin içine yani dockerfile olduğu yere gel ve bu komutu çalıştır
docker desktoba gel image gör ve pushla tıkla
  
