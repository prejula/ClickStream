package com.prej.clickstream.util

import org.apache.spark.rdd.RDD
import java.text.SimpleDateFormat
import com.prej.clickstream.info.ClickInfo
import org.apache.spark.rdd.RDD.rddToPairRDDFunctions

class Sessionizer {

  def sessionize(rdd: RDD[ClickInfo]): RDD[(String, ClickInfo)] =
    {
      val clickMap = rdd.map(clickInfo => (clickInfo.ip + "_" + clickInfo.referer, clickInfo));

      val sessionizedRDD = clickMap.reduceByKey { (clickInfo1, clickInfo2) =>
        var sessionCount = clickInfo1.sessionId;
        val formatter = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss");
        var date1 = formatter.parse(clickInfo1.date);
        var date2 = formatter.parse(clickInfo2.date);

        if (date2.getTime() - date1.getTime() > 1800000) {
          sessionCount = sessionCount + 1;
        }
        
        ClickInfo(clickInfo2.ip, clickInfo2.date, clickInfo2.uri, clickInfo2.status, clickInfo2.random, clickInfo2.referer, clickInfo2.userAgent, sessionCount, clickInfo2.month, clickInfo2.year, clickInfo2.day);
      };

      sessionizedRDD.foreach(f => println("key is: " + f._1 + " value is: " + f._2));
      return sessionizedRDD;
    }
}