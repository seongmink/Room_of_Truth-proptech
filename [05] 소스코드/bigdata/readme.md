# Getting started
작성자 : 김홍주  
작성일자 : 2020-05-19  
수정일자 : 2020-05-25  

## 개발 환경  
* Python 3.6.10  
* Django 3.0.5

## 가상환경 생성 및 실행  
* 가상환경 생성  
``` 
$ python -m venv [가상환경 이름]
```
* 가상환경 확인(설정한 가상환경 이름으로 폴더가 생성되어 있음을 확인)  
```
$ ls
```
* 가상환경 실행  
```
$ source [가상환경 이름]/Scripts/activate
```
([가상환경 이름])이 경로 앞에 붙음을 확인
* 가상환경 종료  
```
$ deactivate
```

## 프로젝트 실행  
* 프로젝트에 사용되는 라이브러리들을 설치
```
$ pip install -r requirements.txt
```
* 데이터베이스 설정
```
$ python manage.py migrate
```
* 프로젝트 실행
```
$ python manage.py runserver
```

