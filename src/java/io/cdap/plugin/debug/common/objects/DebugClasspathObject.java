package io.cdap.plugin.debug.common.objects;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.gson.annotations.SerializedName;

import io.cdap.cdap.api.data.schema.Schema;
import io.cdap.plugin.debug.common.helpers.schema.ObjectDefinition;
import io.cdap.plugin.debug.common.helpers.schema.ObjectFieldDefinition;


/**
 * Debug Classpath object
 */
@ObjectDefinition(
  Name = "DebugClasspathData",
  ObjectType = ObjectDefinition.ObjectDefinitionType.CUSTOM
)
public class DebugClasspathObject {

  @SerializedName("classpath")
  @ObjectFieldDefinition(FieldType = Schema.Type.STRING)
  private String classpath;

  public DebugClasspathObject(String classpath) {
    this.classpath = classpath;
  }

  public String getClasspath() {
    return classpath;
  }
}
