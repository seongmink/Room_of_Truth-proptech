(작성중 20.05.20)
# 데이터프레임 조작 기본기
## 왜 pandas를 사용할까?
* pandas 사이트에 있는 소개  
  pandas is a fast, powerful, flexible and easy to use open source data analysis and manipulation tool,built on top of the Python programming language.  
  파파고 : 팬더는 빠르고 강력하며 유연하며 사용하기 쉬운 Python 프로그래밍 언어 위에 구축된 오픈 소스 데이터 분석 및 조작 도구다.
  
    
* 특징(발번역..)
in-memory 데이터 구조와 CSV 및 텍스트 파일, Microsoft Excel, SQL 데이터베이스 및 고속 HDF5 형식 같은 데이터 형식 읽고 쓰기 가능  
지능형 데이터 정렬 및 누락 데이터의 통합 처리: 연산 시 자동 label-based 정렬, 데이터를 정렬형태로 쉽게 조작가능  
데이터 구조에서 열을 삽입 및 삭제 가능(가변성)  
고성능 데이터 세트 병합 및 결합 등등  
**요약하자면 큰 사이즈의 데이터를 조작하기 쉽다는 장점, 오픈소스 그리고 python으로 되어있다.**

## 프로젝트에 사용되는 문법 몇가지 소개
## (jupyter notebook 기준으로 설명할거에요)

### pandas 임포트하기
```python
import pandas as pd
```
만약 설치가 안되어있다면,
* jupyter notebook를 사용하는 경우(cell 안에 같이 작성)  
```
$ !pip install pandas
```

* cmd
```
pip install pandas
```



### 데이터 불러오기


데이터를 불러올때는 어떤 형식의 데이터를 불러오냐(excel, text, csv ...)에 따라 다른데요  
우리는 .csv 확장자 파일을 쓰기 때문에 csv기준으로 봅시다.  
```python
df = pd.read_csv("./data/전국_4월_통합본.csv", sep=",", index_col=False)
```
* 설명  
 df -> 읽어온 데이터프레임을 담을 변수 이름  
 pd.read_csv() -> csv형식 파일을 읽어온다는 것  
 sep -> csv파일의 구분자  
 index_col -> 읽어오는 파일에 인덱스가 있는경우 사용하지 않겠다는 뜻  



### 데이터 확인하기


지금 읽어온 데이터가 df에 저장되어 있읍니다..  


#### * 전체 데이터 확인하기(row가 많은 경우 중간에 ... 으로 표시)
```python
df
``` 


#### * 위에서부터 5개만 보기
```python
df.head()
```
 만일 10개가 보고싶다 하면 head(10)을 써주면 됩니다. 기본이 5개라는 거에요
 
 
#### * 데이터 프레임의 타입 확인하기  
```python
df.dtypes
```
결과 : 
```
시군구            object
전용면적          float64
건물명            object
계약년월            int64
계약일             int64
거래금액           object
층               int64
건축년도          float64
도로명            object
건물종류           object
거래형태           object
월세              int64
dtype: object
```


#### * 컬럼 목록 확인하기(전체)  
```python
df.columns
```
결과 :
```python
Index(['시군구', '전용면적', '건물명', '계약년월', '계약일', '거래금액', '층', '건축년도',
       '도로명', '건물종류', '거래형태', '월세'],
      dtype='object')
```
#### * 일부 컬럼만 확인하기  
  <code>df[[컬럼 이름들]]</code> 이런식으로 확인할 수 있습니다  
```python
 df[['건물종류','도로명']]
```  
결과 :  
![image alt <](./image/pandas_1.JPG)


#### * 크기 확인하기(row 수, column 수)  
```python
df.shape
```
결과 :  
(60036, 12)  


#### * 조건에 따른 데이터 확인하기  
간단하게 <code>df[df[(조건]]</code>로 확인할 수 있어요  
월세가 100인 경우,
``python
df[df['월세']==100]
```
결과 :  
![image alt <](./image/pandas_3.JPG)  
30층 이상이고 시군구에 '대전'이 포함된 경우


### 데이터 변경하기    
```python
df[( (df['층']>=30) & (df['시군구'].str.contains("대전")) )]
```
결과 :  
![image alt <](./image/pandas_4.JPG) 
#### * 칼럼 이름 변경하기  
```python
df.rename(columns={'건물명':'건물이름', '도로명':'도로명주소'}, inplace=True)
```
columns -> '원래이름':'변경할이름' 형태로 적어줍니다.  
inplace = True -> inplace가 없으면 보여질때만 변경된 이름으로 보여주고 실제로는 반영이 되어있지 않아요  
  df = df.rename(...) 과 같은 기능을 합니다. 변경내용을 df에 적용 해주겠다는 것  
  
  
#### * 새 칼럼 생성 및 기본값 넣기  
```python
df['새 칼럼'] = 888
```
결과 :  
새로운 칼럼인 '새 칼럼'이 생기고 전체적으로 '888' 값이 들어가있음이 확인
![image alt <](./image/pandas_2.JPG)  


#### * 칼럼 타입 변경하기  
아까 columns의 타입은 <code>df.dtype</code> 이렇게 확인하는걸 봤어요  
이제 그 dtype을 변경해 봅시다  
변경 전 :  
새 칼럼       int64
'새 칼럼'을 string(object)으로 변경할게요  
```python
df = df.astype({'새 칼럼':str})
```
결과 :  
새 칼럼      object


#### * 문자열 데이터 자르기  
'계약년월'을 년. 월로 나눠서 '계약년','계약월'로 저장을 해봅시당  
dtype이 int64네요..인덱싱할거니까 일단 string(object)로 바꿉시다..
<code>df = df.astype({'계약년월':str})</code>  
6자리(yyyymm)가 아닌게 있는지도 체크체크  
<code>df[df['계약년월'].str.len()!=6]</code>  
자 잘라봅시다 휴~~
```python
df['계약년'] = df['계약년월'].str[:4]
df['계약월'] = df['계약년월'].str[4:]
```

---
추후 추가

#### * 조건에 맞는 열 삭제하기  


#### * apply 함수 사용해서 변경하기  


### 5. 데이터 저장하기

