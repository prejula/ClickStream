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
    
      val clickDF = mappedRDD.toDF("ip", "date", "uri", "status", "random", "referer", "userAgent", "sessionId");
      clickDF.foreach { x => println(x.toString()) }
      
      clickDF.registerTempTable("clickstream");
      
      println("registered table clickstream");
  }
  
  def get(sc : SparkContext)
  {
      val sqlContext = SQLContext.getOrCreate(sc);
      import sqlContext.implicits._

      val clickStream = sqlContext.sql("select * from clickstream");
      clickStream.show();
  }
}