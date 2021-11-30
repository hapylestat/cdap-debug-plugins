package io.cdap.plugin.debug.transform;


import io.cdap.cdap.api.data.format.StructuredRecord;
import io.cdap.cdap.api.data.schema.Schema;
import io.cdap.plugin.debug.common.objects.DebugClasspathObject;

public class ClasspathTranformTransformer {

  public static StructuredRecord transform(DebugClasspathObject object, Schema schema) {
    StructuredRecord.Builder builder = StructuredRecord.builder(schema);

    builder.set("classpath", object.getClasspath());

    return builder.build();
  }
}

