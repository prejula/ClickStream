package com.prej.stream

import org.apache.spark.SparkContext
import java.util.Calendar

class AnalyticsService {
  
  def readClickStream(sc : SparkContext)
  {
    new ClickDAO().get(sc);
  }
  
  def pageCount(sc : SparkContext)
  {
    new ClickDAO().getPageCountByMonth(sc, /*Calendar.getInstance().get(Calendar.MONTH)*/9);
  }
  
}