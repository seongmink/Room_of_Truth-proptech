* ## 사용 API & Data
  - Kakao local api
  - Kakao map api
  - 국토교통부 실거래가공개시스템  
  - 다방 이미지

* ## 파일 설명

  * data_processing(0522수정).ipynb
    - Contract 테이블의 데이터  
    
    - [국토교통부 실거래가공개시스템](http://rtdown.molit.go.kr/)에서 제공하는 부동산 거래 데이터를 기반으로 데이터를 구축하는 과정이 있는 파일.  
    
    - 컬럼 내용 수정(시도, 시군구, 읍면동, 건물종류, 거래형태, 통합주소 등 생성 및 이름 변경)  
    
    - 카카오 API를 이용하여 위도, 경도 데이터 추가  
    
    - 결과물 : [원본데이터지역]_데이터최종v2.csv(git상에서는 없음)  
    
    ---
    
      

  
  * DataKit(수정).ipynb
    - around 테이블의 데이터  
    
- 이력에 저장된 데이터에서 도로명 주소로 중복 값을 없앤 후, 각 도로명 주소마다 카카오API를 이용하여 준비한 카테고리(교통, 마트편의점, 교육시설, 의료시설, 음식점카페, 문화공간)에 따른 갯수를 저장하는 과정의 파일.
    
      ---
    
      
  
  * 이미지.ipynb  
    
  - Selenium 라이브러리를 활용한 데이터 수집  
    
      ---
    
      
    
  * dataframe을mysql에 저장하기(contract,around).ipynb  
    - contract, around 데이터 전처리 한 내용을 mysql에 저장  
    
    - contract테이블에서 임의의 호(ho)를 넣어줌  
  
    - contract, around데이터프레임을 데이터베이스 스키마에 따라서 변경 및 편집  
    
      ---
    
      
    
  * 가상으로만든user,interest,favorite데이터  
    - 추천시스템을 위해서 가상의 user, interest, favorite 데이터를 데이터베이스에 올리는 파일  
    
    - interest의 관심지역 부분은 interest는 현재 around에 등록되어있는 주소 기반으로 랜덤 저장  
  
    - favorite의 평점은 유저의 관심 지역(시도)단위로 40~160개 랜덤, 관심지역(시도,시군구)단위로는 2~40개 랜덤 제작  
    
      ---
    
      
  
  * db_structure_xxxx.sql  
    
    - 2020-xx-xx자 데이터베이스 스키마 백업 파일  
      ---
    
      
  
  * 0610contract_around값수정후_db업로드.ipynb
    
    - contract, status 테이블 칼럼 수정(일부 추가, 속성값 변경)  
    - contract, status에 around_id를 FK로 등록해줌  
    - contract,status에 around_id 등록하면서 address, sd, sgg, emd 데이터 제거(중복제거)  