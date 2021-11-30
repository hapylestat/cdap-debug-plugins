package io.cdap.plugin.debug.batch.source;

import io.cdap.cdap.api.annotation.Description;
import io.cdap.cdap.api.annotation.Name;
import io.cdap.cdap.etl.api.FailureCollector;
import io.cdap.plugin.debug.common.config.BaseConfig;
import io.cdap.cdap.api.data.schema.Schema;


public class ClasspathSourceConfig extends BaseConfig {
  public static final String PLUGIN_NAME = "ClassPath";
  public static final String PROPERTY_USE_PUBLIC_CLASSPATH = "usePublicClassPath";

  private transient Schema schema;

  @Name(PROPERTY_USE_PUBLIC_CLASSPATH)
  @Description("Instead of plugin class path incuire global classpath")
  private String useGlobalClasspath;

  /**
   * Constructor
   *
   * @param referenceName uniquely identify source/sink for lineage, annotating metadata, etc.
   */
  public ClasspathSourceConfig(String referenceName) {
    super(referenceName);
  }

  @Override
  protected void validate(FailureCollector failureCollector) {
      new ClasspathSourceConfigValidator(failureCollector, this).validate();
  }

  /**
   * Generated schema according to user configuration
   *
   * @return user configured schema
   */
  public Schema getSchema(){
    if (schema == null) {
       //schema = ObjectHelper.buildSchema("DebugClasspathData", null);
      schema = Schema.recordOf("output",
          Schema.Field.of("classpath", Schema.of(Schema.Type.STRING))
        );
    }
    return schema;
  }

  public boolean useGlobalClasspath() {
    return useGlobalClasspath.equals("on");
  }
}
