# 진실의 방(Room of Truth)

![Room of Truth](./image/roomoftruth_logo.png)

## Contents

- #### About RoT

- #### Team

- #### Getting Started

- #### Rule

- #### Architecture

- #### Project Schedule

- #### Docs

- #### License

## About RoT

![RoT 메인사진 or icon](./image/roomoftruth_mainpage.png)

진실의 방(Room of Truth)은 하이퍼레저 패브릭으로 건물 거래 이력 조회하는 서비스입니다.

- 등록된(허가된) 공인중개사는 이력 등록을 할 수 있습니다.

- 사용자는 회원가입시에 등록한 성별, 나이, 선호도(교통, 마트/편의점, 교육시설, 의료시설, 음식점/카페, 문화시설)를 토대로 추천서비스를 제공받을 수 있습니다.

- 거래 이력을 바탕으로 계약 금액 변경 추이를 그래프로 확인할 수 있습니다.

## Team

- ##### 박창현 : Project Manager

  - 블록체인 네트워크 구축, 배포

- ##### 김성민 : Back-End Developer

  - 로그인, 유저, 공인중개사 등록, 도로명 주소 검색, 검색 기록, 파일 업로드, 찜하기, 선호도

- ##### 김홍주 : DBA

  - 데이터(거래 이력, 주변 시설 정보, 이미지) 전처리 및 수집, 추천 서비스, 차트 시각화

- ##### 이정건 : Back-End Developer

  - 거래, 상태 이력 등록 및 조회, 블록체인 네트워크 연동

- ##### 장우영 : Front-End Developer

  - Front-End 총괄, UI/UX, UCC 제작

## Getting Started

- ##### Front-End
  
  - Visual Studio Code(Vue CLI 3.0)
    - command CLI(Ctrl + `) - npm install - npm run serve 
  
- ##### Back-End
  
  - IntelliJ (Java(1.8)) 
    - Open
  - Visual Studio Code (Python(3.6.10), Django(3.0.5))
    - <a href="./%5B05%5D%20소스코드/bigdata/readme.md">readme 참조</a>

## Rule

- ##### Front-End 

  - ##### Vue.js

    - lowerCamelCase

- ##### Back-End

  - ##### Java

    - lowerCamelCase : 지역 변수, private 변수, 메소드
    - UpperCamelCase : 클래스 이름, public 변수, 메소드, 객체 

  - ##### Python

    - snake_case : 변수
    - lowerCamelCase : 함수, 클래스

- ##### Git (feature branch 단위)

  ##### 기본적으로 첫 글자는 대문자를 사용하며, 마침표를 제외합니다.

  - ##### FIX : 올바르지 않은 동작을 고친 경우에 사용합니다.

    > Fix A  : A를 수정합니다.

    ```
    Fix login
    ```

  - ##### ADD : 코드나 테스트, 예제, 문서 등의 추가가 있을 때 사용합니다.

    > Add A : A를 추가합니다.

    ```
    Add error pages
    ```

  - ##### REMOVE : 코드의 삭제가 있을 때 사용합니다.

    > Remove A : A를 삭제합니다.

    ```
    Remove unnecessary login code
    Remove sentences from README.md
    ```

  - ##### REFACTOR : 전면 수정이 있을 때 사용합니다.

    > Refactor A : A를 전면 수정합니다.

    ```
    Refactor argument validation
    Refactor login.XML
    ```

  - ##### UPDATE : 개정이나 버전 업데이트가 있을 때 사용합니다. 코드보다는 주로 문서나 리소스, 라이브러리등에 사용합니다.

    > Update A to B : A를 B하기 위해 업데이트 합니다.

    ```
    Update repo docs to use HTTPS
    ```

  - ##### RENAME : 이름 변경이 있을 때 사용합니다.

    > Rename A to B : A를 B로 이름 변경합니다.

    ```
    Rename login_info to loginInfo
    ```

## Architecture

![ERD](./image/roomoftruth_architecture.png)


## Project Schedule

```mermaid
gantt
    title 진실의 방(Room of Truth)
    dateFormat  YYYY-MM-DD #바꾸지 않음 
    section 기획
    주제 선정:done, 2020-05-04, 11d  #완료되면 done을 기입
    기획서작성:done, 2020-05-07, 8d
    기술스택:done, 2020-05-15, 3d
    역할/규칙:done, 2020-05-15, 3d
    section 설계
    개발환경:done, 2020-05-18, 2d
    페이지 도식화	:done, 2020-05-18, 5d
    상세기능 정의 : done, 2020-05-18, 5d 
    DB 설계	:done, 2020-05-19, 4d
    section Front-End
    스켈레톤: done, 2020-05-23, 3d
   	이러닝: 2020-05-23, 12d
    회원가입: done, 2020-05-25, 2d
    로그인/로그아웃: done, 2020-05-26, 2d
    카테고리별 추천: done, 2020-05-26, 2d
    메인페이지: done, 2020-05-26, 3d
    이력페이지:done, 2020-06-01, 2d
    상세페이지: done, 2020-06-01, 2d
    찜하기: done, 2020-06-03, 2d
    최근 본 방: done, 2020-06-03, 2d
    분석/추천: done, 2020-06-05, 3d
    section back-End
    스켈레톤: done, 2020-05-23, 3d
   	이러닝: 2020-05-23, 12d
    회원가입: done, 2020-05-28, 4d
    로그인/로그아웃: done, 2020-05-29, 4d
   	건물이력: done, 2020-06-01, 3d
    최근본 방: done, 2020-06-02, 3d
    찜하기: done, 2020-06-03, 3d
	카테고리별 추천: done, 2020-06-04, 4d
	section Data-Processing
   	이러닝: 2020-05-23, 12d
    데이터 전처리: done, 2020-05-18, 8d
    카테고리 데이터 수집: done, 2020-05-26, 5d
    이미지 수집: done, 2020-05-27, 5d
    추천 모듈: done, 2020-06-01, 5d
    section Blockchain
    네트워크 구축: 2020-05-22, 10d
    체인코드: done, 2020-06-01, 3d 
   	백엔드 연동 : done, 2020-06-03, 3d
   	section 테스트 및 배포
   	오류수정: 2020-06-07,2d
   	발표준비: 2020-06-07,2d
   	최종배포: 2020-06-07,2d
   	UCC: 2020-06-04, 5d
   	section 평가
   	1차: crit, done,2020-05-15, 1d  #중요일정은 crit 기입
    최종: crit, 2020-06-09, 1d  #중요일정은 crit 기입
    section 산출물 작성
 	산출물 작성: 2020-06-10,4d
   
```



## Docs

[공부 및 개념 정리](./%5B08%5D%20docs)



## License

This is released under the MIT license. See [LICENSE](./LICENSE) for details.