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
from django.db.models import Prefetch

class SmallPagination(PageNumberPagination):
    page_size = 10
    page_size_query_param = "page_size"
    max_page_size = 50

class Contract(APIView, PaginationHandlerMixin):
    pagination_class = SmallPagination
    serializer_class = serializers.ContractSerializer

    def get(self, request, format=None, *args, **kwargs):

        area = request.query_params.get("area",None)
      
        instance = models.Contract.objects.all()

        if area is not None:
            instance = instance.filter(address__contains=area)

        page = self.paginate_queryset(instance)
        if page is not None:
            serializer = self.get_paginated_response(self.serializer_class(page,
 many=True).data)
        else:
            serializer = self.serializer_class(instance, many=True)
            
        return Response(serializer.data, status=status.HTTP_200_OK)
        
class ContractChart(APIView, PaginationHandlerMixin):
    pagination_class = SmallPagination

    def get_object(self, id):
            bd = get_object_or_404(models.Contract, pk=id)
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
            instance_addr = models.Contract.objects.all().filter(address=address)
            instance_emd = models.Contract.objects.all().filter(sgg=sgg).filter(emd=emd)
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
class ContractAround(APIView, PaginationHandlerMixin):

    def get(self, request, pk):
        contract = get_object_or_404(models.Contract, pk=pk)
        # print(contract.address)
        instance = models.Around.objects.get(address=contract.address)
        serializer = serializers.AroundSerializer(instance)
        return Response(serializer.data, status=status.HTTP_200_OK)


# 로그인 하지않은 유저에게 보여줄 전체 상위 9개의 이력
class TotalRank(APIView):
    
    def get(self, request, format=None, *args, **kwargs):
        results = []
        favs = models.Favorite.objects.all().values_list('contract').annotate(sc = Avg('score')).order_by('-sc')
        print(favs[:9])
        for idx, f in enumerate(favs[:9]):
            b = models.Contract.objects.get(pk=f[0])
            results.append({
                "rank":idx+1,
                "num" : b.contract_id,
                "name" : b.address,
                "floor":b.floor,
                "ho": b.ho,
                "image": b.image,
            })
        return Response(results, status=status.HTTP_200_OK)

# 나이대에 따른 첫 화면 3개 선정
class Rank(APIView):

    def get(self, request, pk):
        by = request.query_params.get("by",None)
        if by is None:
            return Response("by={gender,age,cate} 입력해주세요", status=status.HTTP_400_BAD_REQUEST)
        
        user = get_object_or_404(models.User, pk=pk)
        interest = user.interest_user.get()

        if interest.sd =="세종특별자치시":
            addr = interest.sd
        else:
            addr = interest.sd + " "+interest.sgg
        # --------------------------------------------------------
        # 카테고리 분류
        if by=='cate':
            d = {'교통':'trans','마트/편의점':'comforts','교육시설':'education','의료시설':'medical','음식점/카페':'eatery','문화시설':'culture'}
            cate_user = d[interest.first]
            print(cate_user)
            arounds = models.Around.objects.filter(address__contains=addr).order_by('-'+cate_user)
            selected_around = arounds[:3]

            for sa in selected_around:
                contract = models.Contract.objects.filter(address=sa.address)
                favs = models.Favorite.objects.filter(contract__in=contract)
                selected_contract =  favs.values_list('contract').annotate(sc = Avg('score')).order_by('-sc')
                result = []
                if len(selected_contract)==0:
                    b = contract[:1].get()
                else:
                    b = models.Contract.objects.get(pk=selected_contract[0][0])
                fav = len(models.Favorite.objects.filter(contract =b).filter(user=user))
                result.append({
                "num" : b.contract_id,
                "name" : b.address,
                "floor":b.floor,
                "ho": b.ho,
                "image": b.image,
                "isLike": True if fav >=1 else False
            })

            return Response(result, status = status.HTTP_200_OK)

        elif by=='age':
            # print(interest)
            curr_year = datetime.today().year
            gen = ((curr_year - interest.birth+1)//10)*10 # 사용자의 연령대
            start = curr_year+1-gen
            end = start-9
            # print(str(start) +" ~ "+str(end))
            users = models.Interest.objects.filter(birth__gte=end).filter(birth__lte=start)
        elif by=='gender':
            users = models.Interest.objects.filter(gender=interest.gender)
        else:
            return Response("by={gender,age,cate} 입력해주세요", status=status.HTTP_400_BAD_REQUEST)
        
        q = Q()
        for u in users:
            q.add(Q(user=u.user_num), q.OR)
        instance = models.Favorite.objects.filter(q).order_by('contract').filter(contract__address__contains=addr)
        # print(instance)
        # print(str(instance.query))
        queryset = instance.values_list('contract').annotate(sc = Avg('score')).order_by('-sc')
        result = []
        if len(queryset) <3:
            # TODO : 각 지역별로 관심해논게 3개 이상씩은 되어야함 T-T
            instance = models.Favorite.objects.all().order_by('contract').filter(contract__address__contains=addr)
            queryset = instance.values_list('contract').annotate(sc = Avg('score')).order_by('-sc')
        for q in  queryset[:3]:
            b = models.Contract.objects.get(pk=q[0])
            fav = len(models.Favorite.objects.filter(user=pk).filter(contract=b))
            result.append({
                "num" : q[0],
                "name" : b.address,
                "floor":b.floor,
                "ho": b.ho,
                "image": b.image,
                "isLike": True if fav ==1 else False
            })

        return Response(result, status=status.HTTP_200_OK)


# 관심지역, 카테고리에 따른 리스트 보여주기(sort)
# 추천서비스 (가제)
class Prefer(APIView):

    def get(self, request, format=None, *args, **kwargs):

        sgg = request.query_params.get("sgg", None)
        sd = request.query_params.get("sd",None)
        cate = request.query_params.get("cate",None)

        cate_name = {"gt":"trans","mt":"comforts","ed":"education","md":"medical","fc":"eatery","ct":"culture"}
        if sgg is None or sd is None or cate is None or cate not in cate_name:
            return Response("값 입력 필요", status=  status.HTTP_400_BAD_REQUEST)
        if sd=="세종특별자치시":
            addr = sd
        else:
            addr = sd+" "+sgg
        cat = cate_name[cate]
        instance = models.Around.objects.filter(address__contains=addr).order_by('-'+cat)
        # serializer = serializers.AroundSerializer(instance, many=True)
        # print(serializer.data)
        results = []
        for inst in instance[:6]:
            addr = inst.address
            contract = models.Contract.objects.filter(address=addr)
            favs = models.Favorite.objects.filter(contract__in=contract)
            selected_contract =  favs.values_list('contract').annotate(sc = Avg('score')).order_by('-sc')
            if len(selected_contract)==0:
                b = contract[:1].get()
            else:
                # b = 
                b = models.Contract.objects.get(pk=selected_contract[0][0])
            data = {
                "num":b.contract_id,
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

    # TODO : 관심 지역에서 평가한 항목을 가지고 아이템(이력) 추천
class Recommend(APIView):

    def get(self, request, pk):
        user = get_object_or_404(models.User, pk=pk)
        results = []
            
        return Response(results, status=status.HTTP_200_OK)

