package com.prej.clickstream.dao

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.SaveMode
import org.apache.spark.SparkContext
import com.prej.clickstream.info.ClickInfo
import scala.reflect.runtime.universe

class ClickDAO {

  def add(mappedRDD: RDD[ClickInfo], sc: SparkContext) {
    println("inside add of ClickDAO ::::::: #####");

    val sqlContext = SQLContext.getOrCreate(sc);
    import sqlContext.implicits._

    val clickDF = mappedRDD.toDF("ip", "date", "uri", "status", "random", "referer", "userAgent", "sessionId", "month", "year", "day");
    clickDF.write.mode(SaveMode.Append).partitionBy("year", "month", "day").parquet("hdfs://localhost:54310/user/spark/clickstream/clickdata.parquet");
  }

  def get(sc: SparkContext) {
    val sqlContext = SQLContext.getOrCreate(sc);
    import sqlContext.implicits._

    sqlContext.read.parquet("hdfs://localhost:54310/user/spark/clickstream/clickdata.parquet").registerTempTable("clickstream");
    val clickStream = sqlContext.sql("select * from clickstream");
    clickStream.show();
    clickStream.persist();    
  }
  
  def getPageCountByMonth(sc : SparkContext, month : Integer) : Integer =
  {
     val sqlContext = SQLContext.getOrCreate(sc);
     import sqlContext.implicits._
     
     println("month for which count is required::: ##### " + month);
     val counts = sqlContext.sql("select count(uri) from clickstream where month = " + month);
     counts.show();
     
     var count = 0;
     counts.map (t => t(0)).collect().foreach (cnt => count = cnt.toString().toInt);
     println("count for month : " + month + " is :: " + count);
     
     count;
  }
}