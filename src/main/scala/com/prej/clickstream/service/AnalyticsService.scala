package com.prej.clickstream.service

import org.apache.spark.SparkContext
import java.util.Calendar
import com.prej.clickstream.dao.ClickDAO

class AnalyticsService {
  
  def readClickStream(sc : SparkContext)
  {
    new ClickDAO().get(sc);
  }
  
  def pageCountByMonth(sc : SparkContext, month : Integer) : Integer = 
  {
    new ClickDAO().getPageCountByMonth(sc, /*Calendar.getInstance().get(Calendar.MONTH)*/9);
  }
  
  def comparePageCount(sc : SparkContext, month : Integer, monthToBeCompared : Integer) : Integer = 
  {
    val count = pageCountByMonth(sc, month);
    val countForMonthToBeCompared = pageCountByMonth(sc, monthToBeCompared)
    
    if (count >= countForMonthToBeCompared)
      month
    else
      monthToBeCompared
  }
}