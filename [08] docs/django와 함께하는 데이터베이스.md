# django와 함께하는 데이터베이스
파이썬,, 그리고 django로의 영업사원 김홍주입니다..  
sql짜는거 너무 어렵고 복잡합니다.. 그래서 준비한 상품을 소개드릴게요..  

## Django ORM  
 * ORM (Object-Relational Mapping)  
객체(Object)와 관계형 데이터베이스(Relational Database)의 데이터를 매핑(Mapping)해주는 것을 의미한다.  
객체 간의 관계를 바탕으로 SQL을 자동을 생성해서 sql 쿼리문 없이도 데이터베이스의 데이터들을 다룰 수 있다.  

[ORM(Object-Relational Mapping)이란?](https://ychae-leah.tistory.com/134)

## 테이블 형태  
우리 프로젝트의 테이블을 참고해서 간단히 표현해 봤읍니다..
다시보니 user테이블의 프라이머리키가 빠져있네요  
하지만 설명하는데에 중요할까??? 중요하지않아..  
![image alt <](./image/200603.png)  


## 해결해야 할 문제 리스트  
단계별로 준비해보았습니다..(사실 거즘 다 간단한 수준..)  
간단하게 프로젝트에 들어가는 6개만 볼게요  
* Level 1. user_id가 1인 User 가져오기  
* Level 2. Around의 address에 대전이 들어간 것들만 모아보기  
* Level 3. around_id가 3333인 Around를 외래키(around)로 가진 Favorite 확인하기   
* Level 4. Favorite의 around 별로 score 평균 내서 평균 금액 높은 순으로 정렬하기  
* Level 5. Favorite에 있는 around들 중에서 address에 대전이 들어간 것들만 확인하기
* Level 6. Contract의 연도-월별에 따른 매매 평균가 구하기  

## 해결하기  
해결하기에 앞서서 클래스들은 models.py에 타입들과 외래키 등 관계를 지정해줘야합니다.  
```python
class Favorite(models.Model):
    favorite_id = models.AutoField(primary_key=True)
    score = models.IntegerField()
    around = models.ForeignKey('Around', on_delete=models.CASCADE, db_column='around', blank=True, null=True, related_name="fav_around")
    user = models.ForeignKey('User', on_delete=models.CASCADE, db_column='user', blank=True, null=True, related_name="fav_user")

```
이런 식으로요 !  
User테이블은 User, Favorite테이블은 Favorite 이렇게 지정을 했습니다 !  
orm에서 get은 1개를 가져옵니다.  
여러개를 불러오는경우, (all(),filter() ...) 인 경우는 <code>str([결과값].query)</code>을 작성하면 sql문으로 변환해줍니다.
### Level 1. user_id가 1인 User 가져오기  
```python
results = models.User.objects.get(pk=1)
```
쉽다.. 쉽습니다..
```sql
SELECT `user`.`num`, `user`.`auth` FROM `user` WHERE `user`.`num` = 1
```  
### Level 2. Around의 address에 대전이 들어간 것들만 모아보기  
```python
result = models.Around.objects.filter(address__contains='대전')
```
Around들 중에서 address에 '대전'이 포함된 것들을 필터링해서 주는 것입니다. 역시 쉬워요  
```sql
SELECT `around`.`around_id`, `around`.`address`, `around`.`trans`, `around`.`comforts`, `around`.`eatery` FROM `around` WHERE `around`.`address` LIKE BINARY %대전%
```
### Level 3. around_id가 3333인 Around를 외래키(around)로 가진 Favorite 확인하기  
```python
result = models.Around.objects.get(pk=3333).fav_around.all()
```
Around에서 기본키가 3333인 것을 가져온 다음, 역참조로 all()합니다.  
**역참조 : 나를 외래키로 지정한 테이블에 접근하는 것.**
```sql
SELECT `favorite`.`favorite_id`, `favorite`.`score`, `favorite`.`around`, `favorite`.`user` FROM `favorite` WHERE `favorite`.`around` = 3333
```
### Level 4. Favorite에 있는 around들 중에서 address에 대전이 들어간 것들만 확인하기
```python
result = models.Favorite.objects.filter(around__address__contains='대전')
```
외래키컬럼__참조테이블컬럼값__contains 로 들어갑니다..  
Favorite.around에서 연관된 Around.address에서 contains를 한다는 뜻..  
```sql
SELECT `favorite`.`favorite_id`, `favorite`.`score`, `favorite`.`around`, `favorite`.`user` FROM `favorite` INNER JOIN `around` ON (`favorite`.`around` = `around`.`around_id`) WHERE `around`.`address` LIKE BINARY %대전%
```
### Level 5. Favorite의 around별로 score 평균 내서 평균 금액 높은 순으로 정렬하기  
```python
result = models.Favorite.objects.all().values_list('around').annotate(sc = Avg('score')).order_by('-sc')
```
Favorite테이블에서 around별로 score를 평균내서 sc에 저장한 후 , sc 내림차순으로 정렬하는 것입니다.  
```sql
SELECT `favorite`.`around`, AVG(`favorite`.`score`) AS `sc` FROM `favorite` GROUP BY `favorite`.`around` ORDER BY `sc` DESC
```
### Level 6. Contract의 연도-월별에 따른 매매 평균가 구하기  
5번처럼 하면 쉽습니다 !  
근데문제는 contract_date에서 year,month를 분리해야함..ㅋ  
detail은 '매매', 'cost'를 평균해서 연도 월별로 어떻게 묶나요 !!!  
```python
result = (models.Contract.objects.all().filter(detail='매매')
        .values_list('contract_date__year', 'contract_date__month')
        .annotate(Avg('cost'))
        .order_by('contract_date__year', 'contract_date__month'))
```
결과는 이렇게 줍니다
```
[(2019, 4, 22340.2268), (2019, 5, 22926.6877), (2019, 6, 23807.0461), (2019, 7, 25184.428), (2019, 8, 25839.0), (2019, 9, 25764.2671), (2019, 10, 26622.1339), ...]
```
우선 매매인 것들만 추린다음에, contract_date에서 year, month를 가지고 기준으로 잡습니다.  
다음 cost를 평균한 다음, year, month로 기준을 잡고 정렬합니다.
```sql
SELECT EXTRACT(YEAR FROM `contract`.`contract_date`), EXTRACT(MONTH FROM `contract`.`contract_date`), AVG(`contract`.`cost`) AS `cost__avg` FROM `contract` WHERE `contract`.`detail` = 매매 GROUP BY EXTRACT(YEAR
FROM `contract`.`contract_date`), EXTRACT(MONTH FROM `contract`.`contract_date`) ORDER BY EXTRACT(YEAR FROM `contract`.`contract_date`) ASC, EXTRACT(MONTH FROM `contract`.`contract_date`) ASC
```
