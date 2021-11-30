package io.cdap.plugin.debug.common.helpers.schema;

import io.cdap.cdap.api.data.schema.Schema;

/**
 *  Entity meta-info holder for the {@link ObjectFieldDefinition} annotation
 */
public class ObjectFieldInfo {
  private String name;
  private Schema.Type type;
  private String nestedClass;

  public ObjectFieldInfo(String name, Schema.Type type, String nestedClass) {
    this.name = name;
    this.type = type;
    this.nestedClass = nestedClass;
  }

  public String getName() {
    return name;
  }

  public Schema.Type getType() {
    return type;
  }

  public String getNestedClassName() {
    return nestedClass;
  }
}
