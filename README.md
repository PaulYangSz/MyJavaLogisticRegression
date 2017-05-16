# MyJavaLogisticRegression
Learn Logistic Regression and implemented by Java

*This repository is used to practice learned knowledge about Java and Logistic Regression*
## 学习过程中用到的资料 
- 数据挖掘和机器学习方面： 
    - 《机器学习》 《统计学习方法》 《机器学习实战》  
    - 寒小阳的Blog[*逻辑回归初步*](http://blog.csdn.net/han_xiaoyang/article/details/49123419) 
    - 洞庭小哥的Blog[*Logistic回归总结*](http://blog.csdn.net/dongtingzhizi/article/details/15962797)
- Java学习方面： 
    - 《Head First Java》 《Core Java》
## 下面将介绍下整个工程的内容、思路以及接下来未完成的事
### data文件夹 
这个文件夹中存放了训练数据以及测试数据。其中：
- HxyLinearData1.txt和HxyNonlinearData2.txt 
    - 这个是从[*逻辑回归初步*](http://blog.csdn.net/han_xiaoyang/article/details/49123419) 中下载的训练数据集，后面在Java代码中也对这个训练集做了预测。 
- kaggle文件夹
    - train.csv 和 test.csv是从kaggle网站上下载的Titanic问题的训练集和测试集
    - HxyTitanicDataProc.py是从寒小阳的Blog[*逻辑回归应用之Kaggle泰坦尼克之灾*](http://blog.csdn.net/han_xiaoyang/article/details/49797143)中提取的对train.csv的数据特征工程处理Python代码。*（这部分后续可以考虑用Java实现之）*
### src文件夹 
这个文件夹包含了所有逻辑回归的Java代码，下面按照自己的解决问题思路来逐个介绍各个文件。
#### Main.java的目的是实现一个入口可以按照步骤来对Demo数据集进行学习和预测。具体思路是：
- Step1 使用一个FileHelper类来实现csv格式数据的读取和保存
- Step2 使用MapFunction类来映射读取数据到一个线性或者非线性的函数上
- Step3 创建一个OptiMethod，然后用上面的MapFunction和这个OptiMethod组成的Model来对MapFunction的参数Theta[]进行优化，最终生成一个学习之后的Model
- Step4 使用这个训练好的Model对训练集进行预测得到最终的结果
#### FilerHelper.java
这个类是第一个被实现的，目的就是从csv格式的文件中读取数据，保存在自己的ArrayList中。
#### MapFunction.java
这个类将FileHelper中的保存数据，转化为自己的X和Y数组中，并根据X的最大幂值degree映射出一维线性或者多维非线性的函数，其中**方法genXjOfTheta(int[] xiPwerV, int curDegree, int curXIdx)** 的实现过程最为有意思，虽然函数的有效行只有区区不到10行，但是却花了我一晚上的时间才写出来。这个方法的目的就是根据选取作为特征的X的个数以及degree的值来生成一个全展开的多项式。让Theta[]与这个多项式对应起来。
#### LinearMapFunction.java和NonlinearMapFunction.java 
这两个类是对MapFunction抽象类的继承，本来是想在这两个各自的子类中完成各自的关键方法，尤其是想分步走先做个线性简单的后面在对非线性的实现，但是后面在真正实现代码的时候不太想把线性的简单代码单独实现出来了，就一口气实现了**方法genXjOfTheta**，所以现在看起来Linear和Nonlinear这两个类中各自实现的东西已经没有什么太大差别了。甚至可以抛去不要了。不过为了在Main中看起来更好看一些，我还是先保留下来了。
#### OptiMethod.java
这个抽象类主要是作为其他优化方法的抽象接口
#### GradientDecent.java
实现了OptiMethod的方法，使用梯度下降法来求最优解。
#### MyMathApi.java
这个主要实现了一些静态的数学计算公式，包括Sigmoid、C(m, n)和对数组求和等。方便其他类使用
#### LogiRegModel.java
是使用一个（线性或非线性）的MapFunction以及对这个函数的系数Theta[]求最优解的一个OptiMethod构建组成一个model，在调用startOpti后，开始对model的参数进行训练。
#### TitanicMain.java 
这个是另外一个Main入口，用来处理Kaggle-Titanic的预测问题，不过train.csv的数据特征处理采用的是别人的Python代码做的。后面可以考虑用Java实现之。

上面基本上就是对1.0版本的主要介绍。接下来讨论下后面要做的事。
### 接下来的内容
#### 增加其他优化方法，比如牛顿法，拟牛顿法等
#### 数据特征工程的琢磨
#### 学习Kaggle的kernel和discussion
#### 优化Java代码包括注释的添加等
#### 还有一些睡觉前想到的，但是醒来后想不起来的。。。

## V2.0版本的更新
上面是V1.0版本的主要工作。主要是实现了GD方法的LR。后面对一维线性和二维非线性的影射函数都做了实验。在数据特征不变的情况下，发现没有太多的区别，而且还是线性的速度快而且在Titanic问题上表现的更好。V2.0版本主要是加入了AbaBoost。
### AbaBoost介绍
AbaBoost是一种ensemble learning算法。简单说就是产生一系列的基分类模型，及每个基模型的预测权重系数，然后综合出最终的预测结果。其中每个基模型都是从上一个基模型的结果中重新计算训练集的权重分布得到。也就是说对于上一次的预测，调整升高预测错误的那条X_i的训练权重系数，让下一次产生的基模型对错误数据的训练重点照顾。
### AdaBoost实现
对于AdaBoost算法的实现主要是在文件AdaBoostModel.java中。在这个类里面实现了基于一个LR模型和最大基模型个数的构造方法，以及从均匀分布开始训练基模型，逐步更新训练集的每条数据的权重分布，再用更新权重后的训练集学习出下一个基模型，直到达到最大基模型个数。不过中间加入了一些限制，比如错误率高于0.5时就停止继续产生基模型（其实低于0.5就是说这个模型的预测结果要反着用，不过感觉意义不大），而且为了让基模型的预测权重有一定的实际意义，尽量优化至错误率不高于0.45.
具体的算法步骤参考《统计方法学习》以及《机器学习》。
### 谈谈在Titanic上的结果
模型最终在Titanic问题上并没有带来什么多大的进步（甚至不如单次1000迭代的LR，不过比单次100迭代的要好。），总结下来就是这个数据特征已经被单次的LR学习到极限了。再多几次LR也不会有太高的进步。如果想要更好的结果，那么一是改变数据特征的选取，重建特征工程。二是更换学习方法来替代LR。
