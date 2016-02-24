package com.prej.clickstream.main

import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.streaming.Seconds
import org.apache.spark.streaming.StreamingContext

import com.prej.clickstream.service.AnalyticsService
import com.prej.clickstream.util.Parser

object LaunchSparkStream {

  def main(args: Array[String]) {
    
    
    val sparkConf = new SparkConf().setAppName("Spark Streaming Analytics");
    val sc = new SparkContext(sparkConf);
    

    val flumeStream = new Thread(new Runnable() {
      def run() {
        val streamingContext = new StreamingContext(sc, Seconds(30));
        import org.apache.spark.streaming.flume._

        val flumeStream = FlumeUtils.createPollingStream(streamingContext, "192.168.52.128", 9999);
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
        val analyticsService = new AnalyticsService();
        analyticsService.readClickStream(sc);
        println("Month::: " + analyticsService.comparePageCount(sc, 9, 9) + " has more page counts");
      }
    })
    executorService.schedule(analytics, 60, TimeUnit.SECONDS);
    println("started analytics thread");
    
    
  }
}