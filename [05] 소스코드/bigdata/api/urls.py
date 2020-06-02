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

   path('contracts/', views.Contract.as_view()),
   path('contracts/<int:pk>', views.ContractChart.as_view()),
   
   path('arounds/<int:pk>/', views.ContractAround.as_view()),
   # 나이대, 성별에 따른 평점 순위(user id)
   path('rank/<int:pk>/', views.Rank.as_view()),

   path('rank/', views.TotalRank.as_view()),

   
   path('prefer/', views.Prefer.as_view()),

   path('recommend/<int:pk>/', views.Recommend.as_view()),
    
]
