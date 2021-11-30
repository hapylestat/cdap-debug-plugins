package io.cdap.plugin.debug.batch.sink;

import java.util.stream.Collectors;

import org.apache.hadoop.io.NullWritable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cdap.cdap.api.annotation.Description;
import io.cdap.cdap.api.annotation.Name;
import io.cdap.cdap.api.annotation.Plugin;
import io.cdap.cdap.api.data.batch.Output;
import io.cdap.cdap.api.data.format.StructuredRecord;
import io.cdap.cdap.api.data.schema.Schema;
import io.cdap.cdap.api.dataset.lib.KeyValue;
import io.cdap.cdap.etl.api.Emitter;
import io.cdap.cdap.etl.api.FailureCollector;
import io.cdap.cdap.etl.api.PipelineConfigurer;
import io.cdap.cdap.etl.api.batch.BatchSink;
import io.cdap.cdap.etl.api.batch.BatchSinkContext;
import io.cdap.plugin.common.IdUtils;
import io.cdap.plugin.common.LineageRecorder;


@Plugin(type = BatchSink.PLUGIN_TYPE)
@Name(DumpToLogConfig.PLUGIN_NAME)
@Description("Dumping the input to the log")
public class DumpToLog extends BatchSink<StructuredRecord, NullWritable, String> {

  private static final Logger LOG = LoggerFactory.getLogger(DumpToLog.class);
  private final DumpToLogConfig config;

  public DumpToLog(DumpToLogConfig config) {
    this.config = config;
  }

  @Override
  @SuppressWarnings("ThrowableNotThrown")
  public void configurePipeline(PipelineConfigurer pipelineConfigurer) {
    FailureCollector failureCollector = pipelineConfigurer.getStageConfigurer().getFailureCollector();

    IdUtils.validateReferenceName(config.referenceName, failureCollector);

    config.validate(failureCollector);

    failureCollector.getOrThrowException();
  }

  @Override
  public void prepareRun(BatchSinkContext batchSinkContext) throws Exception {
    Schema inputSchema = batchSinkContext.getInputSchema();
    batchSinkContext.addOutput(Output.of(config.referenceName, new DumpToLogFormatProvider(config)));

    LineageRecorder lineageRecorder = new LineageRecorder(batchSinkContext, config.referenceName);
    lineageRecorder.createExternalDataset(inputSchema);
    if (inputSchema.getFields() != null && !inputSchema.getFields().isEmpty()) {
      lineageRecorder.recordWrite("Write", "Dump Input To Log",
        inputSchema.getFields().stream()
          .map(Schema.Field::getName)
          .collect(Collectors.toList()));
    }
  }

  @Override
  public void transform(StructuredRecord record, Emitter<KeyValue<NullWritable, String>> emitter) {
    Schema schema = record.getSchema();
    StringBuilder builder = new StringBuilder(String.format("[%s][%s] OUTPUT: ",
      DumpToLog.class.getSimpleName(),
      config.referenceName
    ));

    schema.getFields().forEach( x -> {
      Object obj =  record.get(x.getName());
      if (obj != null) {
        builder.append(obj).append(" ");
      }
    });
    LOG.info(builder.toString());
    System.out.println(builder);
  }

}
