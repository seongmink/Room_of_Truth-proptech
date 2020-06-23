import pandas as pd
import os
import sklearn
from sklearn.metrics.pairwise import cosine_similarity

def getContentBaseData(user_profile, around_profile):
    ids = list(around_profile.index)
    # print(user_profile)
    cosine_sim = pd.DataFrame(sklearn.metrics.pairwise.cosine_similarity(around_profile,user_profile),index=ids)
    result =  cosine_sim.sort_values([0],ascending = False) # 유사도가 높은 상위 9개의 around id를 리턴한다
    # print(result.head(10))
    return list(result[:9].index)

