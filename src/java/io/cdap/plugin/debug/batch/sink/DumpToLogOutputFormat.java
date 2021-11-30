package io.cdap.plugin.debug.batch.sink;

import java.io.IOException;
import java.util.Collections;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.OutputCommitter;
import org.apache.hadoop.mapreduce.OutputFormat;

import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.cdap.plugin.debug.batch.source.ClasspathFormatProvider;
import io.cdap.plugin.debug.batch.source.ClasspathRecordReader;
import io.cdap.plugin.debug.batch.source.ClasspathSourceConfig;


public class DumpToLogOutputFormat extends OutputFormat<NullWritable, String> {
  private static final Gson gson = new GsonBuilder().create();


  @Override
  public RecordWriter<NullWritable, String> getRecordWriter(TaskAttemptContext context){
    Configuration conf = context.getConfiguration();
    String serializedConfig = conf.get(ClasspathFormatProvider.PROPERTY_CONFIG_JSON);
    ClasspathSourceConfig sgConfig  = gson.fromJson(serializedConfig, ClasspathSourceConfig.class);

    return new DumpToLogRecordWritter(context);
  }

  @Override
  public void checkOutputSpecs(JobContext jobContext) throws IOException, InterruptedException {

  }

  @Override
  public OutputCommitter getOutputCommitter(TaskAttemptContext taskAttemptContext) throws IOException, InterruptedException {
    return new OutputCommitter() {
      @Override
      public void setupJob(JobContext jobContext) throws IOException {

      }

      @Override
      public void setupTask(TaskAttemptContext taskAttemptContext) throws IOException {

      }

      @Override
      public boolean needsTaskCommit(TaskAttemptContext taskAttemptContext) throws IOException {
        return false;
      }

      @Override
      public void commitTask(TaskAttemptContext taskAttemptContext) throws IOException {

      }

      @Override
      public void abortTask(TaskAttemptContext taskAttemptContext) throws IOException {

      }
    };
  }
}
