[Nginx 설정방법]

- Nginx 설치 후 부터

- /etc/nginx/sites-available 로 이동

- default 파일을 사용해도되고 복사해서 원하는 이름으로 하나 생성 ex) sudo cp default bloom

- sudo vi bloom으로 파일 수정할것

  ```
  server {
  
          server_name     i02b201.p.ssafy.io;
          root    /home/ubuntu/s02p23b201/frontend/dist;
          index   index.html;
          
          location / {
                  try_files $uri $uri/ /index.html;
          }
  
          location /api/ {
                  proxy_pass http://127.0.0.1:8080;
          }
          
          location /images/ {
                  alias /home/ubuntu/images/;
          }
  
  
  ```

  

- sudo ln -s /etc/nginx/sites-available/bloom /etc/nginx/sites-enabled/ 으로 링크걸어주기
- ssl 설정
- https://certbot.eff.org/ 접속해서 OS환경 체크하고 맞는 명령어 맞게 수행하면 ssl 자동으로 설치.

- sudo service nginx restart
- sudo service nginx status

[백앤드 구동방법]

- jar 파일 생성 후 ftp로 이동 
- java -jar bloom-1.0.0.jar //실행
- sudo nohup java -jar bloom-1.0.3.jar & //백그라운드 실행
- *재실행시 ps -ef 명령어로 nohup, java 관련 PID 죽이고 재실행 ex) sudo kill -9 1234