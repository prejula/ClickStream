package com.prej.stream

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.SaveMode
import org.apache.spark.SparkContext

class ClickDAO {
  
  def add(mappedRDD : RDD[ClickInfo], sc : SparkContext)
  {
     println("inside add of ClickDAO ::::::: #####");
     
      val sqlContext = SQLContext.getOrCreate(sc);
      import sqlContext.implicits._
    
      val clickDF = mappedRDD.toDF("ip", "date", "uri", "status", "random", "referer", "userAgent", "sessionId", "month", "year", "day");
      clickDF.write.partitionBy("year", "month", "day").parquet("hdfs://localhost:54310/user/spark/clickstream/clickdata.parquet");
  }
  
  def get(sc : SparkContext)
  {
      val sqlContext = SQLContext.getOrCreate(sc);
      import sqlContext.implicits._

      sqlContext.read.parquet("hdfs://localhost:54310/user/spark/clickstream/clickdata.parquet").registerTempTable("clickstream");
      val clickStream = sqlContext.sql("select * from clickstream");
      clickStream.show();
  }
}