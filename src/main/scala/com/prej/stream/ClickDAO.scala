package com.prej.stream

import org.apache.spark.sql.SQLContext
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.flume.SparkFlumeEvent
import org.apache.spark.rdd.PairRDDFunctions

class ClickDAO {
  
  def add(mappedRDD : RDD[ClickInfo])
  {
      val sqlContext = SQLContext.getOrCreate(mappedRDD.sparkContext);
      import sqlContext.implicits._
    
      val clickDF = mappedRDD.toDF("ip", "date", "uri", "status", "random", "referer", "userAgent", "sessionId");
      clickDF.registerTempTable("clickstream");

      val clickStream = sqlContext.sql("select * from clickstream");
      clickStream.show();
  }
}