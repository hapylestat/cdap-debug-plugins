package io.cdap.plugin.debug.batch.sink;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;


import java.io.IOException;

/**
 * Writes {@link SendGridMail} into batches and submit them to SendGrid send API
 */
public class DumpToLogRecordWritter extends RecordWriter<NullWritable, String> {
  private static final Gson gson = new GsonBuilder().create();


  public DumpToLogRecordWritter(TaskAttemptContext taskAttemptContext) {
    Configuration conf = taskAttemptContext.getConfiguration();
    String serializedConfig = conf.get(DumpToLogFormatProvider.PROPERTY_CONFIG_JSON);
    DumpToLogConfig sgConfig  = gson.fromJson(serializedConfig, DumpToLogConfig.class);

  }

  @Override
  public void write(NullWritable nullWritable, String line) throws IOException {
    // NoOp
  }

  @Override
  public void close(TaskAttemptContext taskAttemptContext) {
    // no-op
  }
}
