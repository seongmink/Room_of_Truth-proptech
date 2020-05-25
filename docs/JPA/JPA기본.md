# 🎓 자바 ORM 표준 JPA 프로그래밍

<br/>

## #3 SQL  중심적인 개발의 문제점

- 무한 반복, 지루한 코드

- 필드 수정시, 관련된 모든 SQL 수정

- SQL 의존적인 개발을 피하기 어렵다.

- 패러다임의 불일치 "객체 vs RDBS"

  <br/>

- **객체와 관계형 데이터베이스의 차이**

  - 상속
    - 객체는 상속관계가 있지만 DB에는 없다.
  - 연관관계
    - 객체는 참조를 사용, 테이블은 외래키를 사용
    - 객체는 연관된 클래스를 가져올 수 있지만, DB는 조인을 해야 함. (번거로움) 😓
  - 데이터 타입
  - 데이터 식별 방법

<br/>

<hr/>

## #4 JPA 소개

- **JPA?**
  - Java Persistence API
  - 자바 진영의 **ORM** 기술 표준
- **ORM?**
  - Object-relational mapping (객체 관계 매핑)
  - 객체는 객체대로 설계
  - RDBMS는 RDBMS대로 설계
  - ORM 프레임워크가 중간에서 매핑
- JPA는 애플리케이션과 JDBC 사이에서 동작

<center><image src="./img/JPA동작.PNG"></image></center>
<br/>

### JPA를 왜 사용해야 하는가?

- SQL 중심적인 개발에서 객체 중심으로 개발

- 생산성

  - persist, find, member.setName, remove 등 간편하게 사용

- 유지보수

  - 기존 : 필드 변경시 모든 SQL을 수정해야 함

- 패러다임 불일치 해결

- 성능

  - 1차 캐시와 동일성 보장
  - 트랜잭션을 지원하는 쓰기 지연 (INSERT)
  - 지연 로딩과 즉시 로딩

  <br/>
  
  <hr/>

# #5 Hello JPA - 프로젝트 생성

### H2 데이터베이스 설치와 실행

- http//www.h2database.com/
- 최고의 실습용 DB
- 가볍다
- MySQL, Oracle 데이터베이스 시뮬레이션 기능

<br/>

<center><image src="./img/H2Console.PNG"></center>

<center><image src="./img/H2Init.PNG"></center>

<br/>

### 메이븐 소개

- https://maven.apache.org/

- 자바 라이브러리, 빌드 관리

- 라이브러리 자동 다운로드 및 의존성 관리

- 최근에는 그래들(Gradle)이 점점 유명

  <hr/>

  ### 라이브러리 추가 - pom.xml

```java
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>jpa-basic</groupId>
    <artifactId>ex1-hello-jpa</artifactId>
    <version>1.0.0</version>

    <dependencies>
        <!-- JPA 하이버네이트 -->
        <dependency>
            <groupId>org.hibernate</groupId>
            <artifactId>hibernate-entitymanager</artifactId>
            <version>5.3.10.Final</version>
        </dependency>

        <!-- H2 데이터베이스 -->
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <version>1.4.199</version>
        </dependency>

    </dependencies>

</project>
```

<hr/>

### JPA 설정하기 - persistence.xml

- JPA 설정 파일
- /META_INF/persistence.xml 위치
- javax.persistence로 시작 : JPA 표준 속성
- hibernate로 시작 : 하이버네이트 전용 속성







