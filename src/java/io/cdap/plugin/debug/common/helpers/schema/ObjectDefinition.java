package io.cdap.plugin.debug.common.helpers.schema;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines Entity Object with all related options which defines entity
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ObjectDefinition {
  /**
   *  Object definition type
   */
  enum ObjectDefinitionType {
    /**
     * Describes top-level object
     */
    BASE,

    /**
     * Describes object, designed to be used as part of another objects
     */
    NESTED,

    /**
     * Custom standalone object
     */
    CUSTOM
  }

  /**
   * Entity internal name
   */
  String Name() default "";

  /**
   * List of argument names, required to made request
   */
  String[] RequiredArguments() default "";

  ObjectDefinitionType ObjectType() default ObjectDefinitionType.BASE;
}
