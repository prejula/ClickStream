package com.prej.stream

import org.apache.spark.SparkConf
import org.apache.spark.streaming.Seconds
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.storage.StorageLevel
import org.apache.spark.SparkContext
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

object LaunchSparkStream {

  def main(args: Array[String]) {
    val sparkConf = new SparkConf().setAppName("Spark Streaming Analytics");
    val sc = new SparkContext(sparkConf);

    val flumeStream = new Thread(new Runnable() {
      def run() {

        val streamingContext = new StreamingContext(sc, Seconds(30));
        import org.apache.spark.streaming.flume._

        val flumeStream = FlumeUtils.createPollingStream(streamingContext, "192.168.52.128", 9999);
        println("flume stream created ::::::: #####");

        new Parser().parse(flumeStream, streamingContext.sparkContext);
        streamingContext.start();
        streamingContext.awaitTermination();
      }
    })
    flumeStream.start();
    println("started flume stream thread");

    val executorService = Executors.newScheduledThreadPool(10);
    val analytics = new Thread(new Runnable() {
      def run() {

        println("get from clickstream ::::::: #####");
        new ClickDAO().get(sc);
      }
    })
    executorService.schedule(analytics, 60, TimeUnit.SECONDS);
    println("started analytics thread");
  }
}