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

   path('buildings/', views.Building.as_view()),
   path('buildings/<int:pk>', views.BuildingChart.as_view()),
    
]
