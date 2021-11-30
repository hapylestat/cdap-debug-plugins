package io.cdap.plugin.debug.common.config;

import io.cdap.cdap.etl.api.FailureCollector;


public abstract class BaseConfigValidator {
  protected FailureCollector failureCollector;
  private BaseConfig config;

  public BaseConfigValidator(FailureCollector failureCollector, BaseConfig config) {
    this.failureCollector = failureCollector;
    this.config = config;
  }

  /**
   * Perform validation tasks which did not involve API Client usage
   */
  public abstract void doValidation();


  public void validate() {
    doValidation();
  }
}
