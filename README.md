Click stream processing using spark and flume.
This uses the following modules:
1.) Spark Streaming integrated with Flume
2.) Spark core
3.) Spark Sql

Steps to execute:

1.) Copy sparkflume.conf to /home/ubuntu/flume/apache-flume-1.6.0-bin/conf.

2.) Create ClickStream-0.0.1-jar-with-dependencies.jar and copy to /home/ubuntu/spark_examples/.

3.) 1st terminal:
ubuntu@ubuntu:~$ flume-ng agent --conf ./conf/ -f /home/ubuntu/flume/apache-flume-1.6.0-bin/conf/sparkflume.conf Dflume.root.logger=DEBUG,console -n agent1

4.) 2nd terminal:
ubuntu@ubuntu:~/spark-1.5.1-bin-hadoop2.6/bin$ ./spark-submit --master local[4] /home/ubuntu/spark_examples/ClickStream-0.0.1-jar-with-dependencies.jar
