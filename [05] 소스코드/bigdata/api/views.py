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
from django.db.models import Avg
from collections import OrderedDict

class SmallPagination(PageNumberPagination):
    page_size = 10
    page_size_query_param = "page_size"
    max_page_size = 50

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
# TODO : 2020-05-28
# q1. 월세의 경우 보증금도 뿌려줄건지
# q2. 동 단위랑 주소단위로 할때 같이뿌려준다고하면 동에는 7월 8월 9월이 있는데 주소단위에는 7월 9월밖에 없을땐 값을 어떻게 처리하면 좋을지
class BuildingChart(APIView, PaginationHandlerMixin):
    pagination_class = SmallPagination

    def get_object(self, id):
            bd = get_object_or_404(models.Building, pk=id)
            return bd
    def get(self, request, pk):
        bd = self.get_object(pk)
        detail = request.query_params.get("detail",None)
        if detail is None:
            return Response("mm(매매),js(전세),ws(월세) 중 하나를 입력해주세요",status=status.HTTP_400_BAD_REQUEST)
        else: # detail 값이 있는 경우
            address = bd.address # 도로명주소
            sgg = bd.sgg # 시군구
            emd = bd.emd # 읍면동
            instance_addr = models.Building.objects.all().filter(address=address)
            instance_emd = models.Building.objects.all().filter(sgg=sgg).filter(emd=emd)
            keywords = {"js":"전세", "ws":"월세","mm":"매매"}
            if detail in keywords:
                key = keywords[detail]
                result_addr = (instance_addr.filter(detail=key)
                .values_list('contractedat__year', 'contractedat__month','detail')
                .annotate(Avg('cost'))
                .order_by('contractedat__year', 'contractedat__month'))
                addrs = OrderedDict()
                for r in result_addr:
                    m=str(r[1])
                    if r[1]<10:
                        m = "0"+m
                    m = str(r[0])+'-'+m
                    addrs[m]=r[-1]
                result_emd = (instance_emd.filter(detail=key)
                .values_list('contractedat__year', 'contractedat__month','detail')
                .annotate(Avg('cost'))
                .order_by('contractedat__year', 'contractedat__month'))
                emds = OrderedDict()
                for r in result_emd:
                    print(r)
                    m=str(r[1])
                    if r[1]<10:
                        m = "0"+m
                    m = str(r[0])+'-'+m
                    emds[m]=r[-1]
                print(addrs)
                print(emds)
                return_value = {
                    'road_address':address,
                    'dong_address':sgg+" "+emd,
                    'datatype': key,
                    'done_data' :emds,
                    'addr_data':addrs
                }
                return Response(return_value, status=status.HTTP_200_OK)
            else:
                return Response("mm(매매),js(전세),ws(월세) 중 하나를 입력해주세요",status=status.HTTP_400_BAD_REQUEST)

