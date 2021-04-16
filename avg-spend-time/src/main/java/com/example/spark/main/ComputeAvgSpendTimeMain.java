package com.example.spark.main;

import com.example.hive.HiveDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import java.util.List;
/**
 * @Author: kevin yang
 * @Description:
 * @Date: create in 2020/11/30 11:40
 */
@Slf4j
public class ComputeAvgSpendTimeMain {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("NaiveBayesTest").setMaster("local[*]");
        //.setMaster("local[*]");
        JavaSparkContext jsc = new JavaSparkContext(conf);
        //读取文件的每一行到RDD
//        if (args.length == 0) {
//            System.out.println("please input trainData path.");
//            return;
//        }
        long a = System.currentTimeMillis();
        //训练集 /home/ymdxtest/yangguang/linecommon/machine_learning/bonuli.txt
        JavaRDD<String> lines = jsc.textFile("hdfs://cdh01-xin:8020/home/ymdxtest/yangguang/linecommon/machine_learning/bonuli.txt");
//        long all = lines.count();
//        log.info("all=" + all);
        SparkSession sparkSession = SparkSession.builder().config(conf).getOrCreate();
        List<HiveDao.Result> resultList = HiveDao.getResult();
        sparkSession.createDataFrame(resultList, HiveDao.Result.class).distinct().show();
        System.out.println("b=" + (System.currentTimeMillis() - a));
    }
}