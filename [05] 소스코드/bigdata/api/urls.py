from rest_framework import permissions
from django.urls import path, include
from django.conf.urls.static import static
from django.conf import settings
from . import views

from drf_yasg.views import get_schema_view
from drf_yasg import openapi
from django.conf.urls import url


schema_view = get_schema_view(
   openapi.Info(
      title="Snippets API",
      default_version='v1',
      description="진실의 방",
      terms_of_service="https://www.google.com/policies/terms/",
      license=openapi.License(name="BSD License"),
   ),
   public=True,
   permission_classes=(permissions.AllowAny,),
)


urlpatterns = [
   url(r'^swagger(?P<format>\.json|\.yaml)$', schema_view.without_ui(cache_timeout=0), name='schema-json'),
   url(r'^swagger/$', schema_view.with_ui('swagger', cache_timeout=0), name='schema-swagger-ui'),
   url(r'^redoc/$', schema_view.with_ui('redoc', cache_timeout=0), name='schema-redoc'),

   # get : 페이지네이션으로 전체 거래이력 출력
   # params(필수) : area=[검색할 지역주소] -> 이력 주소  contains로 검색
   path('contracts/', views.Contract.as_view()),

   # get [거래이력pk] : 거래 이력에 대한 가격 변동 그래프 출력
   # params(필수) : detail=[mm,js,ws] -> 매매,전세,월세에 따른 그래프 결과값 넘겨줌
   path('contracts/<int:pk>', views.ContractChart.as_view()),
   
   # get [거래이력pk] : 거래이력의 주소를 가지고 around값을 받아옴
   # params : 없음
   path('arounds/<int:pk>/', views.ContractAround.as_view()),

   # get : 전체 around에서 상위 9개를 뽑아서 돌려줌(비회원 전용)
   # params : 없음
   path('rank/', views.TotalRank.as_view()),

   # get [유저pk] : 회원 정보를 가지고 건물 상위 3개를 돌려줌
   # params(필수) : by=[gender,age,cate] -> 해당하는 회원 정보에 따라 점수높은 3개 건물을 돌려줌
   path('rank/<int:pk>/', views.Rank.as_view()),
   
   # get : 지역과 카테고리에 해당하는 상위 건물 6개 돌려줌
   # params(필수) :
   #              sd = 시도
   #              sgg = 시군구
   #              cate = 카테고리
   path('prefer/', views.Prefer.as_view()),

   # get [유저pk] : 유저의 찜 평가 내용을 기반으로 추천 건물 뽑아서 돌려줌
   path('recommend/<int:pk>/', views.Recommend.as_view()),
    
]
