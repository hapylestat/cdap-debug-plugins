package io.cdap.plugin.debug.batch.source;

import io.cdap.cdap.etl.api.FailureCollector;
import io.cdap.plugin.debug.common.config.BaseConfig;
import io.cdap.plugin.debug.common.config.BaseConfigValidator;


public class ClasspathSourceConfigValidator extends BaseConfigValidator {
  public ClasspathSourceConfigValidator(FailureCollector failureCollector, BaseConfig config) {
    super(failureCollector, config);
  }

  @Override
  public void doValidation() {
    // Do nothing, everything is perfect here ;)
  }
}
