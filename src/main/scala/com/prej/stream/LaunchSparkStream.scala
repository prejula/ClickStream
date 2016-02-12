package com.prej.stream

import org.apache.spark.SparkConf
import org.apache.spark.streaming.Seconds
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.storage.StorageLevel

object LaunchSparkStream {
  
  def main(args : Array[String])
  {
    val sparkConf = new SparkConf().setAppName("Spark Streaming");
    val streamingContext = new StreamingContext(sparkConf, Seconds(30));
    
    import org.apache.spark.streaming.flume._

    val flumeStream = FlumeUtils.createPollingStream(streamingContext, "192.168.52.128", 9999);
    
    println("flume stream created ::::::: #####");
    
    new Parser().parse(flumeStream);
    
   /* val schemaConvertor = new SchemaConvertor();
    schemaConvertor.addSchema(flumeStream);*/
    
    
  /*  val count = flumeStream.count();
    
    count.saveAsTextFiles("clickstream_"+System.currentTimeMillis(), "log");*/

    streamingContext.start();
    streamingContext.awaitTermination();
  }
}