package io.cdap.plugin.debug.common.helpers.schema;


import java.util.List;
import java.util.stream.Collectors;

/**
 * Entity meta-info holder for {@link ObjectDefinition}
 */
public class ObjectInfo {
  private String cdapObjectName;

  private Class objectClass;
  private List<ObjectFieldInfo> fieldDefinitions;
  private List<String> requiredArguments;
  private ObjectDefinition.ObjectDefinitionType objectType;

  public ObjectInfo(String cdapObjectName, List<ObjectFieldInfo> fieldDefinitions, Class objectClass,
                    List<String> requiredArguments, ObjectDefinition.ObjectDefinitionType objectType) {

    this.cdapObjectName = cdapObjectName;
    this.fieldDefinitions = fieldDefinitions;
    this.objectClass = objectClass;
    this.requiredArguments = requiredArguments;
    this.objectType = objectType;
  }

  public String getCdapObjectName() {
    return cdapObjectName;
  }

  public List<ObjectFieldInfo> getFieldDefinitions() {
    return fieldDefinitions;
  }

  public List<ObjectFieldInfo> getFieldsDefinitions(List<String> fields) {
    return fieldDefinitions.stream()
      .filter(x -> fields.stream().anyMatch(y -> x.getName().equals(y)))
      .collect(Collectors.toList());
  }

  public Class getObjectClass() {
    return objectClass;
  }

  public List<String> getRequiredArguments() {
    return requiredArguments.stream().filter(x -> !x.equals("")).collect(Collectors.toList());
  }

  public ObjectDefinition.ObjectDefinitionType getObjectType() {
    return objectType;
  }
}

