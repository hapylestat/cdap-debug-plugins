package io.cdap.plugin.debug.transform;

import io.cdap.cdap.api.annotation.Description;
import io.cdap.cdap.api.annotation.Name;
import io.cdap.cdap.api.annotation.Plugin;
import io.cdap.cdap.api.data.format.StructuredRecord;
import io.cdap.cdap.etl.api.Emitter;
import io.cdap.cdap.etl.api.InvalidEntry;
import io.cdap.cdap.etl.api.PipelineConfigurer;
import io.cdap.cdap.etl.api.StageSubmitterContext;
import io.cdap.cdap.etl.api.Transform;
import io.cdap.cdap.etl.api.TransformContext;
import io.cdap.plugin.debug.common.helpers.ClassPathReader;
import io.cdap.plugin.debug.common.objects.DebugClasspathObject;


@Plugin(type = Transform.PLUGIN_TYPE)
@Name(ClasspathTransformConfig.PLUGIN_NAME)
@Description("Dumping classpath to ErrorCollector, classpath column")
public class ClasspathTransform extends Transform<StructuredRecord, StructuredRecord> {

  private final ClasspathTransformConfig config;
  private volatile boolean emitErrors = true;

  public ClasspathTransform(ClasspathTransformConfig config) {
    this.config = config;
  }

  @Override
  public void initialize(TransformContext context) throws Exception {
    super.initialize(context);
  }


  @Override
  public void configurePipeline(PipelineConfigurer pipelineConfigurer) {
    super.configurePipeline(pipelineConfigurer);

    pipelineConfigurer.getStageConfigurer().setOutputSchema(
      pipelineConfigurer.getStageConfigurer().getInputSchema()
    );

//    pipelineConfigurer.getStageConfigurer().setOutputSchema(config.getSchema());
  }

  @Override
  public void onRunFinish(boolean succeeded, StageSubmitterContext context) {
    emitErrors = true;
  }

  @Override
  public void transform(StructuredRecord input, Emitter<StructuredRecord> emitter) throws Exception {
    if (emitErrors) {
      ClassLoader classLoader = (config.useGlobalClasspath())
        ? ClassLoader.getSystemClassLoader()
        : this.getClass().getClassLoader();
      ClassPathReader.getClassPathString(classLoader)
        .stream()
        .map(DebugClasspathObject::new)
        .map(x -> new InvalidEntry<StructuredRecord>(
          0,
          x.getClasspath(),
          ClasspathTranformTransformer.transform(x, config.getSchema())
        ))
        .forEach(x -> {
          emitter.emitError(x);
        });
      emitErrors = false;
    }
    emitter.emit(input);
  }
}
