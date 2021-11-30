package io.cdap.plugin.debug.batch.sink;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.cdap.cdap.api.data.batch.InputFormatProvider;
import io.cdap.cdap.api.data.batch.OutputFormatProvider;


public class DumpToLogFormatProvider implements OutputFormatProvider {
  public static final String PROPERTY_CONFIG_JSON = "cdap.debug.dumptolog.config";
  private static final Gson gson = new GsonBuilder().create();
  private final Map<String, String> conf;

  DumpToLogFormatProvider(DumpToLogConfig config){
    this.conf = new ImmutableMap.Builder<String, String>()
      .put(PROPERTY_CONFIG_JSON, gson.toJson(config))
      .build();
  }

  @Override
  public String getOutputFormatClassName() {
    return DumpToLogOutputFormat.class.getName();
  }

  @Override
  public Map<String, String> getOutputFormatConfiguration() {
    return conf;
  }
}
