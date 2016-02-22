package com.prej.stream

import java.util.Date
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.flume.SparkFlumeEvent
import org.apache.http.client.utils.DateUtils
import java.util.Calendar
import java.text.SimpleDateFormat
import java.text.ParseException
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SQLContext
import org.apache.spark.SparkContext

class Parser extends Serializable {
  
  def parse (dstream : DStream[SparkFlumeEvent], sparkContext : SparkContext)
  {
    println("inside parse of Parser ::::::: #####");
    dstream.foreachRDD { rDD =>
      val mappedRDD = rDD
        .map(record => new String(record.event.getBody().array()).split("\""))
        .map {
          arr =>
            var temp = new Array[String](7);
            parseElement(arr, temp);
            ClickInfo(temp(0), temp(1), temp(2), temp(3).toInt, temp(4).toInt, temp(5), temp(6), 1);
        }

      val clickDAO = new ClickDAO().add(RDD.rddToPairRDDFunctions(new Sessionizer().sessionize(mappedRDD)).values, sparkContext);
      new ClickDAO().get(sparkContext);
    }
    

  }

  def parseElement(arr: Array[String], temp: Array[String]) = { //<TODO :  change implementation,, make it simpler>
    for (i <- 0 to (arr.length - 1)) {
      var element = arr(i);
      if (null != element && " " != element) {
        if (i == 0) {
          var elemArr = element.trim().split(" ");
          temp(i) = elemArr(0);
          temp(i + 1) = elemArr(3).substring(elemArr(3).indexOf("[") + 1, elemArr(3).length).trim();
        } else if (i == 2) {
          var elemArr = element.trim().split(" ");
          temp(i + 1) = elemArr(0).trim();
          temp(i + 2) = elemArr(1).trim();
        } else if (null != temp(i + 1)) {
          temp(i + 2) = element;
        } else 
          temp(i + 1) = element;
      }
    }
  }
}