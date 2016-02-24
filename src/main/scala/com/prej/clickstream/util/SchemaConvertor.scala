package com.prej.clickstream.service;
/*package com.prej.stream

import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.flume.SparkFlumeEvent

class SchemaConvertor extends Serializable {
  
  def addSchema (dstream : DStream[SparkFlumeEvent])
  {
    val clickRecords = dstream;
    clickRecords.foreachRDD{ rDD => 
          rDD.foreachPartition { generateClickStream() }
      }
  }

  def generateClickStream(): Iterator[org.apache.spark.streaming.flume.SparkFlumeEvent] => Unit = {
    partitionRecord => 
          partitionRecord.foreach { record =>  
              var rec = new String(record.event.getBody().array());
              var arr = rec.split("\"");
              var temp = new Array[String](7);
              for (i <- 0 to (arr.length - 1)) {
                parseElement(arr, i, temp)
              }
              var clickStream = new ClickInfo(temp(0), temp(1), temp(2), temp(3).toInt , temp(4).toInt , temp(5),temp(6), null);
              
              new Sessionizer().sessionize(clickStream);  
           //   new ClickDAO().add(clickStream);
          } 
  }

  def parseElement(arr: Array[String], i: Int, temp: Array[String]) = { //<TODO :  change implementation,, make it simpler>
    var element = arr(i);
    if(null != element && " " != element){
      if (i == 0){
        var elemArr = element.trim().split(" ");
        temp(i) = elemArr(0);
        temp(i+1) = elemArr(3).substring(elemArr(3).indexOf("[") + 1, elemArr(3).length).trim();
      }
      else if (i == 2){
         var elemArr = element.trim().split(" ");
         temp(i+1) = elemArr(0).trim();
         temp(i+2) = elemArr(1).trim();
      }
      else if (null != temp(i+1)){
        temp(i+2) = element;
      }
      else{
         temp(i+1) = element;
      }
    }
  }
}*/ 