package io.cdap.plugin.debug.batch.source;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.stream.Collectors;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.cdap.plugin.debug.common.helpers.ClassPathReader;
import io.cdap.plugin.debug.common.objects.DebugClasspathObject;


public class ClasspathRecordReader extends RecordReader<NullWritable, DebugClasspathObject> {
  private static final Gson gson = new GsonBuilder().create();

  private Iterator<DebugClasspathObject> recordIterator;
  private DebugClasspathObject currentRecord;

  @Override
  public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
    Configuration conf = context.getConfiguration();
    String serializedConfig = conf.get(ClasspathFormatProvider.PROPERTY_CONFIG_JSON);
    ClasspathSourceConfig sgConfig  = gson.fromJson(serializedConfig, ClasspathSourceConfig.class);

    ClassLoader classLoader = (sgConfig.useGlobalClasspath()) ? ClassLoader.getSystemClassLoader() : null;
    Iterator<DebugClasspathObject> objectsIterator = ClassPathReader.getClassPathString(classLoader)
      .stream()
      .map(DebugClasspathObject::new)
      .collect(Collectors.toList()).iterator();

    recordIterator = (objectsIterator.hasNext()) ? objectsIterator : Collections.emptyIterator();
  }

  @Override
  public boolean nextKeyValue() throws IOException, InterruptedException {
    boolean recordHasNext = recordIterator.hasNext();

    if (recordHasNext) {
      currentRecord = recordIterator.next();
    }
    return recordHasNext;
  }

  @Override
  public NullWritable getCurrentKey() throws IOException, InterruptedException {
    return null;
  }

  @Override
  public DebugClasspathObject getCurrentValue() throws IOException, InterruptedException {
    return currentRecord;
  }

  @Override
  public float getProgress() throws IOException, InterruptedException {
    return 0.0f;
  }

  @Override
  public void close() throws IOException {
   // no-op
  }
}
