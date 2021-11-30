package io.cdap.plugin.debug.batch.source;

import java.io.IOException;
import java.util.Collections;
import java.util.List;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.InputFormat;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class ClasspathInputFormat extends InputFormat {
  private static final Gson gson = new GsonBuilder().create();

  @Override
  public List<InputSplit> getSplits(JobContext jobContext) throws IOException, InterruptedException {
    return Collections.singletonList(new ClasspathSplit());
  }

  @Override
  public RecordReader createRecordReader(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
    Configuration conf = context.getConfiguration();
    String serializedConfig = conf.get(ClasspathFormatProvider.PROPERTY_CONFIG_JSON);
    ClasspathSourceConfig sgConfig  = gson.fromJson(serializedConfig, ClasspathSourceConfig.class);

    return new ClasspathRecordReader();
  }
}
