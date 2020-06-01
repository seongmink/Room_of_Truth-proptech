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
from datetime import datetime
from django.db.models import Q

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
        
class BuildingChart(APIView, PaginationHandlerMixin):
    pagination_class = SmallPagination

    def get_object(self, id):
            bd = get_object_or_404(models.Building, pk=id)
            return bd
    def getAgg(self, instance_addr, instance_emd, target,key):
        label = {'2019-04':0,'2019-05':1,'2019-06':2,'2019-07':3,'2019-08':4,'2019-09':5,'2019-10':6,'2019-11':7,'2019-12':8,'2020-01':9,'2020-02':10,'2020-03':11,'2020-04':12}
        # --
        result_addr = (instance_addr.filter(detail=key)
        .values_list('contractedat__year', 'contractedat__month','detail')
        .annotate(Avg(target))
        .order_by('contractedat__year', 'contractedat__month'))
        addrs = [None]*13
        for r in result_addr:
            m=str(r[1])
            if r[1]<10:
                m = "0"+m
            m = str(r[0])+'-'+m
            addrs[label[m]]=round(r[-1],2)
        # --
        result_emd = (instance_emd.filter(detail=key)
        .values_list('contractedat__year', 'contractedat__month','detail')
        .annotate(Avg(target))
        .order_by('contractedat__year', 'contractedat__month'))
        emds = [None] * 13
        for r in result_emd:
            m=str(r[1])
            if r[1]<10:
                m = "0"+m
            m = str(r[0])+'-'+m
            emds[label[m]]=round(r[-1],2)
        return addrs, emds

    def get(self, request, pk):
        bd = self.get_object(pk)
        detail = request.query_params.get("detail",None)
        if detail is None:
            return Response("detail을 mm(매매),js(전세),ws(월세) 중 하나를 입력해주세요",status=status.HTTP_400_BAD_REQUEST)
        else: # detail 값이 있는 경우
            address = bd.address # 도로명주소
            sgg = bd.sgg # 시군구
            emd = bd.emd # 읍면동
            instance_addr = models.Building.objects.all().filter(address=address)
            instance_emd = models.Building.objects.all().filter(sgg=sgg).filter(emd=emd)
            keywords = {"js":"전세", "ws":"월세","mm":"매매"}
            if detail in keywords:
                key = keywords[detail] # 입력받은 타입
                addrs, emds = self.getAgg(instance_addr, instance_emd, "cost", key)
                if key=='월세': # 월세의 경우 보증금도 같이 연산해서 줘야함
                    m_addrs, m_emds = self.getAgg(instance_addr, instance_emd, "monthly", key)
                    return_value = {
                        'road_address':address,
                        'dong_address':sgg+" "+emd,
                        'label' : ['2019-04','2019-05','2019-06','2019-07','2019-08','2019-09','2019-10','2019-11','2019-12','2020-01','2020-02','2020-03','2020-04'],
                        'datatype': key,
                        'dong_data' :emds,
                        'addr_data':addrs,
                        'ws_dong_data':m_emds,
                        'ws_addr_data':m_addrs,
                    }
                else:
                    return_value = {
                        'road_address':address,
                        'dong_address':sgg+" "+emd,
                        'label' : ['2019-04','2019-05','2019-06','2019-07','2019-08','2019-09','2019-10','2019-11','2019-12','2020-01','2020-02','2020-03','2020-04'],
                        'datatype': key,
                        'dong_data' :emds,
                        'addr_data':addrs
                    }

                return Response(return_value, status=status.HTTP_200_OK)
            else:
                return Response("mm(매매),js(전세),ws(월세) 중 하나를 입력해주세요",status=status.HTTP_400_BAD_REQUEST)


# 빌딩 아이디를 이용해서 around받아오기
class BuildingAround(APIView, PaginationHandlerMixin):

    def get(self, request, pk):
        building = get_object_or_404(models.Building, pk=pk)
        # print(building.address)
        instance = models.Around.objects.get(address=building.address)
        serializer = serializers.AroundSerializer(instance)
        return Response(serializer.data, status=status.HTTP_200_OK)


# 나이대에 따른 age
class rankByAge(APIView, PaginationHandlerMixin):

    def get(self, request, pk):
        user = get_object_or_404(models.User, pk=pk)
        curr_year = datetime.today().year
        gen = ((curr_year - user.birth+1)//10)*10 # 사용자의 연령대
        start = curr_year+1-gen
        end = start-9
        print(str(start) +" ~ "+str(end))
        users = models.User.objects.filter(birth__gte=end).filter(birth__lte=start)
        q = Q()
        
        for u in users:
            q.add(Q(user=u.num), q.OR)
        instance = models.Favorite.objects.filter(q).order_by('building')
        # print(str(instance.query))
        # TODO : 관심지역 한정 필터링
        for ins in instance:
            print(ins.fav_building.all())
        print("-----------")
        queryset = instance.values_list('building').annotate(sc = Avg('score')).order_by('-sc')
        # TODO : 조회수 합쳐서 정렬해야함
        result = []
        if len(queryset) <3:
            pass
        else:
            for q in  queryset[:3]:
                b = models.Building.objects.get(pk=q[0])
                result.append({
                    "num" : q[0],
                    "name" : b.address,
                    "floor":b.floor,
                    "ho": b.ho,
                    "image": b.image
                })

        return Response(result, status=status.HTTP_200_OK)


# 관심지역, 카테고리에 따른 리스트 보여주기(sort)
class Prefer(APIView):

    def get(self, request, format=None, *args, **kwargs):

        sgg = request.query_params.get("sgg", None)
        sd = request.query_params.get("sd",None)
        cate = request.query_params.get("cate",None)

        cate_name = {"gt":"trans","mt":"comforts","ed":"education","md":"medical","fc":"eatery","ct":"culture"}
        if sgg is None or sd is None or cate is None or cate not in cate_name:
            return Response("값 입력 필요", status=  status.HTTP_400_BAD_REQUEST)
        addr = sd+" "+sgg
        cat = cate_name[cate]
        instance = models.Around.objects.filter(address__contains=addr).order_by('-'+cat)
        # serializer = serializers.AroundSerializer(instance, many=True)
        # print(serializer.data)
        results = []
        for inst in instance[:6]:
            addr = inst.address
            building = models.Building.objects.filter(address=addr)
            # TODO : building 중에서 하나 뽑기(평점, 조회수 기반)
            # 지금 building들이 있어
            # favorite에서 building에 해당하는 평균평점을 구해
            # 가장 높은 building을 구해 -> 리턴대상
            b = building[:1].get()
            print(b.address)
            data = {
                "num":b.num,
                "name":b.address,
                "floor":b.floor,
                "ho":b.ho,
                "image":b.image,
                "trans":inst.trans,
                "comforts":inst.comforts,
                "education":inst.education,
                "medical":inst.medical,
                "eatery":inst.eatery,
                "culture":inst.culture
            }
            results.append(data)
            
        return Response(results, status=status.HTTP_200_OK)
