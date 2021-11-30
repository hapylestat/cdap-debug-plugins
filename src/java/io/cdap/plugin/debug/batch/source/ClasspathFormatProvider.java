package io.cdap.plugin.debug.batch.source;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.cdap.cdap.api.data.batch.InputFormatProvider;


public class ClasspathFormatProvider implements InputFormatProvider {
  public static final String PROPERTY_CONFIG_JSON = "cdap.debug.classpath.config";
  private static final Gson gson = new GsonBuilder().create();
  private final Map<String, String> conf;

  ClasspathFormatProvider(ClasspathSourceConfig config){
    this.conf = new ImmutableMap.Builder<String, String>()
      .put(PROPERTY_CONFIG_JSON, gson.toJson(config))
      .build();
  }

  @Override
  public String getInputFormatClassName() {
    return ClasspathInputFormat.class.getName();
  }

  @Override
  public Map<String, String> getInputFormatConfiguration() {
    return conf;
  }
}
