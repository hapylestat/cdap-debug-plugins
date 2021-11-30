package io.cdap.plugin.debug.batch.source;

import java.util.stream.Collectors;

import org.apache.hadoop.io.NullWritable;

import com.google.common.base.Preconditions;

import io.cdap.cdap.api.annotation.Description;
import io.cdap.cdap.api.annotation.Name;
import io.cdap.cdap.api.annotation.Plugin;
import io.cdap.cdap.api.data.batch.Input;
import io.cdap.cdap.api.data.format.StructuredRecord;
import io.cdap.cdap.api.data.schema.Schema;
import io.cdap.cdap.api.dataset.lib.KeyValue;
import io.cdap.cdap.etl.api.Emitter;
import io.cdap.cdap.etl.api.FailureCollector;
import io.cdap.cdap.etl.api.PipelineConfigurer;
import io.cdap.cdap.etl.api.batch.BatchSource;
import io.cdap.cdap.etl.api.batch.BatchSourceContext;
import io.cdap.plugin.common.IdUtils;
import io.cdap.plugin.common.LineageRecorder;
import io.cdap.plugin.debug.common.helpers.schema.ObjectHelper;
import io.cdap.plugin.debug.common.objects.DebugClasspathObject;


/**
 * Batch Sink Plugin
 */
@Plugin(type = BatchSource.PLUGIN_TYPE)
@Name(ClasspathSourceConfig.PLUGIN_NAME)
@Description("Dumping classpath, from where the sink is used")
public class ClasspathSource extends BatchSource<NullWritable, DebugClasspathObject, StructuredRecord> {

  private final ClasspathSourceConfig config;

  static {
    ObjectHelper.addObjectClasses(DebugClasspathObject.class);
  }

  public ClasspathSource(ClasspathSourceConfig config) {
    this.config = config;
  }

  @SuppressWarnings("ThrowableNotThrown")
  private void validateConfiguration(FailureCollector failureCollector) {
    config.validate(failureCollector);
    failureCollector.getOrThrowException();
  }

  @Override
  public void prepareRun(BatchSourceContext batchSourceContext) throws Exception {
    validateConfiguration(batchSourceContext.getFailureCollector());

    LineageRecorder lineageRecorder = new LineageRecorder(batchSourceContext, config.referenceName);
    lineageRecorder.createExternalDataset(config.getSchema());
    lineageRecorder.recordRead(
      "Read",
      "Reading SendGrid Objects",
      Preconditions.checkNotNull(config.getSchema().getFields())
        .stream()
        .map(Schema.Field::getName)
        .collect(Collectors.toList())
    );

    batchSourceContext.setInput(Input.of(config.referenceName, new ClasspathFormatProvider(config)));
  }

  @Override
  public void configurePipeline(PipelineConfigurer pipelineConfigurer) {
    FailureCollector failureCollector = pipelineConfigurer.getStageConfigurer().getFailureCollector();

    IdUtils.validateReferenceName(config.referenceName, failureCollector);
    validateConfiguration(failureCollector);
    pipelineConfigurer.getStageConfigurer().setOutputSchema(config.getSchema());
  }

  @Override
  public void transform(KeyValue<NullWritable, DebugClasspathObject> input, Emitter<StructuredRecord> emitter) {
    Schema schema = config.getSchema();

    emitter.emit(ClasspathSourceTransformer.transform(input.getValue(), schema));
  }


}
