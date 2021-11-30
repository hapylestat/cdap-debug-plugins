package io.cdap.plugin.debug.common.helpers.schema;

import io.cdap.cdap.api.data.schema.Schema;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines entity fields
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ObjectFieldDefinition {

  Schema.Type FieldType() default Schema.Type.STRING;

  /**
   *  Internal name of the {@link ObjectDefinition#Name()}
   *  <p/>
   *  Only objects with type {@link io.cdap.plugin.debug.common.helpers.ObjectDefinition.ObjectDefinitionType#NESTED}
   *  allowed
   */
  String NestedClass() default "";
}
