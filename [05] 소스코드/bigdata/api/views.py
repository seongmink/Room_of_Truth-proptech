from django.shortcuts import render, get_object_or_404
from rest_framework.response import Response
from rest_framework.decorators import api_view
from rest_framework.views import APIView
from rest_framework import status
from api.pagination import PaginationHandlerMixin
from rest_framework.pagination import PageNumberPagination
from api import models, serializers

# import os
# import sys
# import urllib.request
# import json


class SmallPagination(PageNumberPagination):
    page_size = 10
    page_size_query_param = "page_size"
    max_page_size = 50
# stores/

class Building(APIView, PaginationHandlerMixin):
    pagination_class = SmallPagination
    serializer_class = serializers.BuildingSerializer

    def get(self, request, format=None, *args, **kwargs):

        area = request.query_params.get("area",None)
      
        instance = models.Building.objects.all()

        if area is not None:
            instance = instance.filter(address__contains=area)

        page = self.paginate_queryset(instance)
        if page is not None:
            serializer = self.get_paginated_response(self.serializer_class(page,
 many=True).data)
        else:
            serializer = self.serializer_class(instance, many=True)
            
        return Response(serializer.data, status=status.HTTP_200_OK)
