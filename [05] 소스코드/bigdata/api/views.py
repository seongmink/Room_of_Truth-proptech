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
import pandas as pd
from api.content_base import getContentBaseData
import time

DICT_KEY = {'교통':'trans','마트/편의점':'comforts','교육시설':'education','의료시설':'medical','음식점/카페':'eatery','문화시설':'culture'}

class SmallPagination(PageNumberPagination):
    page_size = 20
    page_size_query_param = "page_size"
    max_page_size = 50


class Contract(APIView, PaginationHandlerMixin):
    pagination_class = SmallPagination
    serializer_class = serializers.ContractSerializer

    def get(self, request, format=None, *args, **kwargs):
        area = request.query_params.get("area",None)
      
        if area is not None:
            instance = models.Contract.objects.filter(address__contains=area)
        else:
            instance = models.Contract.objects.all()

        page = self.paginate_queryset(instance)
        if page is not None:
            serializer = self.get_paginated_response(self.serializer_class(page,
 many=True).data)
        else:
            serializer = self.serializer_class(instance, many=True)
        return Response(serializer.data, status=status.HTTP_200_OK)
        

# TODO : 주소로 받아서 하는거
class ContractChart(APIView):

    def getAgg(self, instance_addr, instance_emd, target,key):
        label = {'2019-04':0,'2019-05':1,'2019-06':2,'2019-07':3,'2019-08':4,'2019-09':5,'2019-10':6,'2019-11':7,'2019-12':8,'2020-01':9,'2020-02':10,'2020-03':11,'2020-04':12}
        # --
        result_addr = (instance_addr.filter(detail=key)
        .values_list('contract_date__year', 'contract_date__month','detail')
        .annotate(Avg(target))
        .order_by('contract_date__year', 'contract_date__month'))
        # print(result_addr)
        addrs = [None]*13
        for r in result_addr:
            m=str(r[1])
            if r[1]<10:
                m = "0"+m
            m = str(r[0])+'-'+m
            addrs[label[m]]=round(r[-1],2)
        # --
        result_emd = (instance_emd.filter(detail=key)
        .values_list('contract_date__year', 'contract_date__month','detail')
        .annotate(Avg(target))
        .order_by('contract_date__year', 'contract_date__month'))
        # print(result_emd)
        emds = [None] * 13
        for r in result_emd:
            m=str(r[1])
            if r[1]<10:
                m = "0"+m
            m = str(r[0])+'-'+m
            emds[label[m]]=round(r[-1],2)
        return addrs, emds

    def get(self, request, format=None, *args, **kwargs):
        detail = request.query_params.get("detail",None)
        address = request.query_params.get("address",None)
        # print(address)
        # print(detail)
        # print('------------')
        if address is None:
            return Response("address를 입력해주세요",status=status.HTTP_400_BAD_REQUEST)

        if detail is None:
            return Response("detail을 mm(매매),js(전세),ws(월세) 중 하나를 입력해주세요",status=status.HTTP_400_BAD_REQUEST)
        if address is not None and detail is not None: # 둘다 있는 값이라면
            bd = models.Contract.objects.filter(address=address).first()
            sd = bd.sd
            sgg = bd.sgg # 시군구
            emd = bd.emd # 읍면동
            if sd=="세종특별자치시":
                calc_address = sd+" "+emd
            else:
                calc_address = sd+" "+sgg+" "+emd
            instance_addr = models.Contract.objects.all().filter(address=address)
            instance_emd = models.Contract.objects.all().filter(address__contains=calc_address)
            keywords = {"js":"전세", "ws":"월세","mm":"매매"}
            if detail in keywords:
                key = keywords[detail] # 입력받은 타입
                addrs, emds = self.getAgg(instance_addr, instance_emd, "cost", key)
                if key=='월세': # 월세의 경우 보증금도 같이 연산해서 줘야함
                    m_addrs, m_emds = self.getAgg(instance_addr, instance_emd, "monthly", key)
                    return_value = {
                        'road_address':address,
                        'dong_address':calc_address,
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
                        'dong_address':calc_address,
                        'label' : ['2019-04','2019-05','2019-06','2019-07','2019-08','2019-09','2019-10','2019-11','2019-12','2020-01','2020-02','2020-03','2020-04'],
                        'datatype': key,
                        'dong_data' :emds,
                        'addr_data':addrs
                    }
                # print(return_value)
                return Response(return_value, status=status.HTTP_200_OK)
            else:
                return Response("mm(매매),js(전세),ws(월세) 중 하나를 입력해주세요",status=status.HTTP_400_BAD_REQUEST)


# 거래이력 아이디를 이용해서 around받아오기
class ContractAround(APIView):

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
        favs = models.Favorite.objects.all().values_list('around').annotate(sc = Avg('score')).order_by('-sc')[:9]
        # print(favs[:9])
        for idx, f in enumerate(favs):
            arnd = models.Around.objects.get(pk=f[0])
            b = models.Contract.objects.filter(address=arnd.address).first()
            results.append({
                "rank":idx+1,
                "num" : arnd.around_id,
                "name" : arnd.address,
                "image": b.image,
                "latitude":b.latitude,
                "longitude":b.longitude
            })
        return Response(results, status=status.HTTP_200_OK)

# 나이대,성별, 카테고리에 따른 첫 화면 3개 선정
class Rank(APIView):
    def getResult(self, users,addr,pk,interest):
        start_time = time.time()
        # q = Q()
        # for u in users:
        #     q.add(Q(user=u.user_num), q.OR)
        
        instance = models.Favorite.objects.filter(user__in=users).order_by('around').filter(around__address__contains=addr)
        # print(instance)
        # instance = models.Favorite.objects.filter(q).order_by('around').filter(around__address__contains=addr)
        queryset = instance.values_list('around').annotate(sc = Avg('score')).order_by('-sc')[:3]
        result = []
        if len(queryset) <3:
            # TODO : 각 지역별로 관심해논게 3개 이하라면, 그냥 유저 선호도를 기반으로 상위 3개를 준다
            instance = models.Around.objects.filter(address__contains=addr).order_by('-'+DICT_KEY[interest.first],'-'+DICT_KEY[interest.second],'-'+DICT_KEY[interest.third])
            queryset = [[id.around_id] for id in instance[:3]]
        for q in  queryset:
            arnd = models.Around.objects.get(pk=q[0])
            b = models.Contract.objects.filter(address=arnd.address).first()
            fav = len(models.Favorite.objects.filter(user=pk).filter(around=arnd))
            result.append({
                "num" : q[0],
                "name" : b.address,
                "latitude":b.latitude,
                "longitude":b.longitude,
                "image": b.image,
                "isLike": True if fav ==1 else False
            })
        print(time.time()-start_time)
        return result

    def get(self, request, pk):
        
        user = get_object_or_404(models.User, pk=pk)
        interest = user.interest_user.get()

        if interest.sd =="세종특별자치시":
            addr = interest.sd
        else:
            addr = interest.sd + " "+interest.sgg
        # --------------------------------------------------------
    # 카테고리 분류
        # d = {'교통':'trans','마트/편의점':'comforts','교육시설':'education','의료시설':'medical','음식점/카페':'eatery','문화시설':'culture'}
        start_time = time.time()
        cate_user = DICT_KEY[interest.first]
        arounds = models.Around.objects.filter(address__contains=addr).order_by('-'+cate_user)[:5]
        result_category = []
        favs = models.Favorite.objects.filter(around__address__contains=addr)
        # print(favs) # 사용자 선호도에 따른 순위
        tmp = {}
        for c in arounds:
            tmp[c.around_id]=getattr(c,cate_user)*0.5 + getattr(c,DICT_KEY[interest.second])*0.3 + getattr(c,DICT_KEY[interest.third])*0.2
        calc_around = favs.values_list('around').annotate(sc = Avg('score')).order_by('-sc')[:5] # 평점순위
        # print(calc_around)
        for c in calc_around:
            if c[0] in tmp:
                tmp[c[0]]+=c[1]/5
        calc_scores = {k: v for k, v in sorted(tmp.items(), key=lambda item: item[1], reverse=True)}
        idx=0
        for item in calc_scores:
            if idx==3:
                break
            arnd = models.Around.objects.get(pk=item)
            b = models.Contract.objects.filter(address=arnd.address).first()
            # print(models.Favorite.objects.filter(around=arnd).filter(user=user))
            fav = len(models.Favorite.objects.filter(around=arnd).filter(user=user))
            result_category.append({
            "num" : arnd.around_id,
            "name" : arnd.address,
            "image": b.image,
            "latitude":b.latitude,
            "longitude":b.longitude,
            "isLike": True if fav >=1 else False
            })
            idx+=1
        print(time.time()-start_time)
        # --------------------------------------------------------
        # 연령대 분류
        curr_year = datetime.today().year
        gen = ((curr_year - interest.birth+1)//10)*10 # 사용자의 연령대
        start = curr_year+1-gen
        end = start-9
        # print(str(start) +" ~ "+str(end))
        users = models.Interest.objects.filter(birth__gte=end).filter(birth__lte=start)
        user_ids = users.values_list('user_num',flat=True)
        result_age = self.getResult(user_ids,addr,pk,interest)

        # --------------------------------------------------------
        # 성별 분류
        gen_users = models.Interest.objects.filter(gender=interest.gender)
        gen_user_ids = gen_users.values_list('user_num',flat=True)
        result_gender = self.getResult(gen_user_ids,addr,pk,interest)

        # 3개 합쳐서 돌려주기
        result = {
            'genders' : result_gender,
            'ages' : result_age,
            'categorys' : result_category
        }

        return Response(result, status=status.HTTP_200_OK)


# 관심지역, 카테고리에 따른 리스트 보여주기(sort)
# 추천서비스 (가제)
class Prefer(APIView):

    def get(self, request, format=None, *args, **kwargs):

        sgg = request.query_params.get("sgg", None)
        sd = request.query_params.get("sd",None)
        cate = request.query_params.get("cate",None)

        cate_name = {"gt":"trans","mt":"comforts","ed":"education","md":"medical","fc":"eatery","ct":"culture"}
        if sd is None or cate is None or cate not in cate_name:
            return Response("값 입력 필요", status=  status.HTTP_400_BAD_REQUEST)
        if sd=="세종특별자치시":
            addr = sd
        else:
            if sgg is None:
                return Response("sgg 값 입력 필요", status=  status.HTTP_400_BAD_REQUEST)
            addr = sd+" "+sgg
        cat = cate_name[cate]
        instance = models.Around.objects.filter(address__contains=addr).order_by('-'+cat)[:6]
        results = []
        for arnd in instance:
            addr = arnd.address
            b = models.Contract.objects.filter(address=addr).first()
            data = {
                "num":arnd.around_id,
                "name":arnd.address,
                "image":b.image,
                "longitude":b.longitude,
                "latitude":b.latitude,
                "trans":arnd.trans,
                "comforts":arnd.comforts,
                "education":arnd.education,
                "medical":arnd.medical,
                "eatery":arnd.eatery,
                "culture":arnd.culture
            }
            results.append(data)
            
        return Response(results, status=status.HTTP_200_OK)

    # TODO : 관심 지역에서 평가한 항목을 가지고 아이템(이력) 추천
class Recommend(APIView):

    def get(self, request, pk):
        user = get_object_or_404(models.User, pk=pk)
        interest = user.interest_user.get()

        if interest.sd =='세종특별자치시':
            addr = interest.sd
        else:
            addr = interest.sd+" "+interest.sgg

        # 유저가 관심 지역 내에서 평가를 내린 것들의 모임
        favs = user.fav_user.filter(around__address__contains=addr)
        print(len(favs))
        if len(favs)==0:
            # 평가 내린게 없다면 around를 관심도에 따라 정렬을 한 다음 걍 준다;;
            # TODO : 평가가 없는 경우 관심도 9개에 따라 평가를 내리게끔 유도하면 어떨까
            sorted_around = models.Around.objects.filter(address__contains=addr).order_by('-'+DICT_KEY[interest.first],'-'+DICT_KEY[interest.second],'-'+DICT_KEY[interest.third])[:9]
            results=[]
            for arnd in sorted_around:
                cont = models.Contract.objects.filter(address=arnd.address).first()
                results.append({
                "num" : arnd.around_id,
                "name" : arnd.address,
                "image": cont.image,
                "latitude":cont.latitude,
                "longitude":cont.longitude,
                "isLike": False
                })
            return Response(results, status=status.HTTP_200_OK)

        elif len(favs)<=15:
            # 유저가 평가한 건물들의 평균으로 유저 프로필을 생성하자
            # user profile
            results = []
            t=[0,0,0,0,0,0]
            keys = ['trans','comforts','education','medical','eatery','culture']
            for idx,k in enumerate(keys):
                t[idx] = favs.values_list('around').aggregate(avg_score=Avg('around__'+k))['avg_score']
            index = []
            index.append(pk)
            user_df = pd.DataFrame(data=[t], columns = keys, index=index)


            # 해당 유저가 평가하지 않았고, 관심지역에 속하는 around의 모임
            favs_idx = [f.around.around_id for f in favs]
            arounds = models.Around.objects.filter(address__contains=addr).exclude(around_id__in=favs_idx)
            idx = [arnd.around_id for arnd in arounds]
            q = arounds.values('trans', 'comforts','education','medical','eatery','culture')
            # item profile
            item_df = pd.DataFrame.from_records(q,index=idx)
            results_item_id = getContentBaseData(user_df,item_df)
            # print(results_item_id)
            for id in results_item_id:
                arnd = models.Around.objects.get(pk=id)
                cont = models.Contract.objects.filter(address=arnd.address).first()
                # print(str(getattr(arnd,DICT_KEY[interest.first]))+"/"+str(getattr(arnd,DICT_KEY[interest.second]))+"/"+str(getattr(arnd,DICT_KEY[interest.third])))
                results.append({
                "num" : arnd.around_id,
                "name" : arnd.address,
                "image": cont.image,
                "latitude":cont.latitude,
                "longitude":cont.longitude,
                "isLike": True if arnd.around_id in favs_idx else False
                })
            return Response(results, status=status.HTTP_200_OK)
        else:
            # 유저가 관심지역에 남긴 평점이 15개 초과일 경우
            # 혹은 관심지역에 대한 전체 평점의 갯수가 일정개수 이상일 경우
            pass
            
        return Response(results, status=status.HTTP_200_OK)

