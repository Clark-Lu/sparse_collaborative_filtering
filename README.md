# sparse_collaborative_filtering
协同过滤推荐算法
算法前提：物以类聚，人以群分。用户会根据自己的偏好对不同的事物打不同的分。可以根据这些历史数据同时分析出用户的偏好以及事物的分类。
从而可以预测用户对其他事物的评分，进行个性化推荐。

初步认识算法的效果可以运行SparseCollaborativeFilterSolver类的main方法。
        /**
         * 得分矩阵
         *       user1  user2  user3  user4  user5
         * movie1  1      0     1       0      1
         * movie2  ?      0     1       0      1
         * movie3  0      1     0       ?      0
         * movie4  ?      1     0       1      0
         *
         */
 从得分矩阵可以看出，电影1、2属于第一类，3、4属于第二类，用户1、3、5偏好第一类电影，用户2、4偏好第二类电影。
 已知得分矩阵中的部分值，可以预测出其他未知值。
         /**
         * 求解结果近似
         *       user1  user2  user3  user4  user5
         * movie1  1      0     1       0      1
         * movie2  1      0     1       0      1
         * movie3  0      1     0       1      0
         * movie4  0      1     0       1      0
         */
         
  实际中，大多只知道movie-user-score这类信息。通过这些信息可以构造出前面提到的得分矩阵。使用方法为：
  1、执行sql文件夹下collaborative_filtering.sql文件，创建相应的库表结构，修改数据库连接配置，连接到数据库。
  有实际生产数据的可以将数据写到cf_x_theta_score表中。
  无实际生产数据的，可以运行BaseTest中的initXThetaScore测试方法，该方法造了10000个用户，与1000个左右商品的数据，用户id的个位数与商品id的个位数相同，
则表明用户喜欢该商品，否则不喜欢。
  2、然后运行FeatureUpdateServiceTest中的testUpdateFeature方法，运行完成及模型训练完成，用户特征向量和商品特征向量
分别存储在cf_theta_feature表和cf_x_feature表中。如想计算某个用户对某个商品的评分，只需计算对应的特征向量点积。
  3、最后可以运行RecommendServiceTest中的testRecommend方法，控制台输出的列表前面的商品id的个位数与方法中指定的用户id的个位数大多一致。
