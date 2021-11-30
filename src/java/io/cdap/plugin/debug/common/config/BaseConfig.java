package io.cdap.plugin.debug.common.config;

import io.cdap.cdap.api.annotation.Description;
import io.cdap.cdap.api.annotation.Macro;
import io.cdap.cdap.api.annotation.Name;
import io.cdap.cdap.etl.api.FailureCollector;
import io.cdap.plugin.common.ReferencePluginConfig;


import javax.annotation.Nullable;

/**
 * Provides all required configuration for reading SendGrid information
 */
public abstract class BaseConfig extends ReferencePluginConfig {

  /**
   * Constructor
   *
   * @param referenceName uniquely identify source/sink for lineage, annotating metadata, etc.
   */
  public BaseConfig(String referenceName) {
    super(referenceName);
  }

  /**
   * Validate configuration for the issues
   *
   */
  protected abstract void validate(FailureCollector failureCollector);

}
