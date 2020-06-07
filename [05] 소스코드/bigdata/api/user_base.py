import pandas as pd
import surprise
import numpy as np
from surprise import SVD
from surprise import Dataset,Reader
from surprise import accuracy
from surprise.model_selection import train_test_split
from surprise.model_selection import cross_validate
from surprise import NormalPredictor

def getUserBaseData(user, addr, raw, count):
    # when importing from a DF, you only need to specify the scale of the ratings.
    reader = surprise.Reader(rating_scale=(1,4)) 
    #into surprise:
    dataframe = surprise.Dataset.load_from_df(raw,reader)
    trainset = dataframe.build_full_trainset()

    algo = surprise.SVD()
    algo.fit(trainset)

    iids = raw['around'].unique()
    iidsUsrnotVisited = raw.loc[raw['user']==user, 'around']
    iids_to_pred = np.setdiff1d(iids,iidsUsrnotVisited) # 안 간 가게 구함(차집합)
    # user_id가 가지않은 가게들로 testset 생성
    testset = [[user, iid, 4.] for iid in iids_to_pred]
    predictions = algo.test(testset)
    # print(surprise.accuracy.rmse(predictions))
    pred_ratings = np.array([pred.est for pred in predictions])
    # print(len(pred_ratings))
    if len(pred_ratings)< count:
        i_max = pred_ratings.argsort()[::-1]
    else:
        i_max = pred_ratings.argsort()[::-1][:count]
    #i_max = pred_ratings.argmax()
    iid = iids_to_pred[i_max]
    results = {}
    results_ids = []
    for i,m in zip(iid,i_max):
        # print('{0} : {1}'.format(i,pred_ratings[m]))
        results[i] = pred_ratings[m]
        results_ids.append(i)
    print(results_ids)
    return results_ids

