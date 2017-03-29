# -*- coding: cp936 -*-
import pandas as pd #���ݷ���
import numpy as np #��ѧ����
from pandas import Series,DataFrame

data_train = pd.read_csv("Train.csv")
print data_train.columns
data_train.info()
print data_train.describe()


import matplotlib.pyplot as plt
from matplotlib.font_manager import FontProperties
plt.rcParams['font.sans-serif']=['SimHei'] #����������ʾ���ı�ǩ
plt.rcParams['axes.unicode_minus']=False #����������ʾ����
fig = plt.figure()
fig.set(alpha=0.2)  # �趨ͼ����ɫalpha����

plt.subplot2grid((2,3),(0,0))             # ��һ�Ŵ�ͼ����м���Сͼ
data_train.Survived.value_counts().plot(kind='bar')# plots a bar graph of those who surived vs those who did not. 
plt.title(u"������ (1Ϊ���)") # puts a title on our graph
plt.ylabel(u"����")  

plt.subplot2grid((2,3),(0,1))
data_train.Pclass.value_counts().plot(kind="bar")
plt.ylabel(u"����")
plt.title(u"�˿͵ȼ��ֲ�")

plt.subplot2grid((2,3),(0,2))
plt.scatter(data_train.Survived, data_train.Age)
plt.ylabel(u"����")                         # sets the y axis lable
plt.grid(b=True, which='major', axis='y') # formats the grid line style of our graphs
plt.title(u"�����俴��ȷֲ� (1Ϊ���)")


plt.subplot2grid((2,3),(1,0), colspan=2)
data_train.Age[data_train.Pclass == 1].plot(kind='kde')   # plots a kernel desnsity estimate of the subset of the 1st class passanges's age
data_train.Age[data_train.Pclass == 2].plot(kind='kde')
data_train.Age[data_train.Pclass == 3].plot(kind='kde')
plt.xlabel(u"����")# plots an axis lable
plt.ylabel(u"�ܶ�") 
plt.title(u"���ȼ��ĳ˿�����ֲ�")
plt.legend((u'ͷ�Ȳ�', u'2�Ȳ�',u'3�Ȳ�'),loc='best') # sets our legend for our graph.


plt.subplot2grid((2,3),(1,2))
data_train.Embarked.value_counts().plot(kind='bar')
plt.title(u"���Ǵ��ڰ��ϴ�����")
plt.ylabel(u"����")  
plt.show()

#ticket�Ǵ�Ʊ��ţ�Ӧ����unique�ģ������Ľ��û��̫��Ĺ�ϵ�������뿼�ǵ���������
#cabinֻ��204���˿���ֵ�������ȿ�������һ���ֲ�
print data_train.Cabin.value_counts()

from sklearn.ensemble import RandomForestRegressor


### ʹ�� RandomForestClassifier �ȱʧ����������
def set_missing_ages(df):
    # �����е���ֵ������ȡ��������Random Forest Regressor��
    age_df = df[['Age', 'Fare', 'Parch', 'SibSp', 'Pclass']]

    # �˿ͷֳ���֪�����δ֪����������
    known_age = age_df[age_df.Age.notnull()].as_matrix()
    unknown_age = age_df[age_df.Age.isnull()].as_matrix()

    # y��Ŀ������
    y = known_age[:, 0]

    # X����������ֵ
    X = known_age[:, 1:]

    # fit��RandomForestRegressor֮��
    rfr = RandomForestRegressor(random_state=0, n_estimators=2000, n_jobs=-1)
    rfr.fit(X, y)

    # �õõ���ģ�ͽ���δ֪������Ԥ��
    predictedAges = rfr.predict(unknown_age[:, 1::])

    # �õõ���Ԥ�����ԭȱʧ����
    df.loc[(df.Age.isnull()), 'Age'] = predictedAges

    return df, rfr


def set_Cabin_type(df):
    df.loc[(df.Cabin.notnull()), 'Cabin'] = "Yes"
    df.loc[(df.Cabin.isnull()), 'Cabin'] = "No"
    return df


data_train, rfr = set_missing_ages(data_train)
data_train = set_Cabin_type(data_train)
print data_train
data_train.info()