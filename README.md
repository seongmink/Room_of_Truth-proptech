# Bloom

[![Bloom 메인사진 or icon]()



## Contents

- About Bloom
- Getting Started

## About Bloom

![Bloom 설명]()

Bloom 설명



## Team

팀 소개



## Getting Started

- Front-End
  - Vue.js

- Back-End
  - Spring-Boot

## Rule

- Front-End : 

- Back-End : Camel Case

- Git

  기본적으로 첫 글자는 대문자를 사용하며, 마침표를 제외합니다.

  - FIX : 올바르지 않은 동작을 고친 경우에 사용합니다.

    > Fix A  : A를 수정합니다.

    ```
    Fix login
    ```

  - ADD : 코드나 테스트, 예제, 문서 등의 추가가 있을 때 사용합니다.

    > Add A : A를 추가합니다.

    ```
    Add error pages
    ```

  - REMOVE : 코드의 삭제가 있을 때 사용합니다.

    > Remove A : A를 삭제합니다.

    ```
    Remove unnecessary login code
    Remove sentences from README.md
    ```

  - REFACTOR : 전면 수정이 있을 때 사용합니다.

    > Refactor A : A를 전면 수정합니다.

    ```
    Refactor argument validation
    Refactor login.XML
    ```

  - UPDATE : 개정이나 버전 업데이트가 있을 때 사용합니다. 코드보다는 주로 문서나 리소스, 라이브러리등에 사용합니다.

    > Update A to B : A를 B하기 위해 업데이트 합니다.

    ```
    Update repo docs to use HTTPS
    ```

  - RENAME : 이름 변경이 있을 때 사용합니다.

    > Rename A to B : A를 B로 이름 변경합니다.

    ```
    Rename login_info to loginInfo
    ```

## Design

아키텍쳐,  ERD



## Project Schedule

일정

```mermaid
gantt
       dateFormat                :YYYY-MM-DD
       title                      Bloom Project Schedule
       excludes                  :excludes the named dates/days from being included in a charted task..  
       section 기획
       주제 선정                  :done,    des1, 2020-05-04, 2020-05-12
       기술 스택 선정              :done,    des2, after des1, 12h
       역할 분담                  :done,    des3, after des2, 12h

       section 설계
       팀 규칙 결정               :done,    des4, after des3, 1d
       개발 환경 구축             :    des5, after des3, 1d
       도식화                    :    des6, after des5, 1d
       DB 설계                   :    des7, after des6, 1d
       
       section 구현
       Front-End                 :    des8, after des6, 28d
       Back-End                  :    des9, after des7, 28d
       블록체인 인프라 구축 및 체인코드 작성              :    des10, after des7, 28d
       Data-processing            :    des11, after des7, 14d
       
       section 테스트
       테스트                  :    des12, after des11, 16d
       
       section 배포
       배포                  :    des13, after des12, 2d
       
```

