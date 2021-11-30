package io.cdap.plugin.debug.batch.source;


import io.cdap.cdap.api.data.format.StructuredRecord;
import io.cdap.cdap.api.data.schema.Schema;
import io.cdap.plugin.debug.common.objects.DebugClasspathObject;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ClasspathSourceTransformer {


  public static StructuredRecord transform(DebugClasspathObject object, Schema schema) {
    StructuredRecord.Builder builder = StructuredRecord.builder(schema);

    builder.set("classpath", object.getClasspath());

    return builder.build();
  }
}


//import io.cdap.cdap.api.data.format.StructuredRecord;
//  import io.cdap.cdap.api.data.schema.Schema;
//  import io.cdap.plugin.sendgrid.common.helpers.EmptyObject;
//  import io.cdap.plugin.sendgrid.common.helpers.IBaseObject;
//
//  import java.util.List;
//  import java.util.Map;
//  import java.util.Objects;
//  import java.util.stream.Collectors;
//
///**
// * {@link IBaseObject} to {@link StructuredRecord} transformer
// */
//public class SendGridSourceTransformer {
//
//  @SuppressWarnings("unchecked")
//  private static void transformValue(String k, Object v, Schema schema, StructuredRecord.Builder builder) {
//
//    if (v instanceof Map) {
//      Schema mapSchema = Objects.requireNonNull(schema.getField(k)).getSchema();
//      builder.set(k, transform((Map<String, Object>) v, mapSchema));
//    } else if (v instanceof EmptyObject) {
//      // no-op
//    } else if (v instanceof IBaseObject) {
//      Schema mapSchema = Objects.requireNonNull(schema.getField(k)).getSchema();
//      builder.set(k, transform((IBaseObject) v, mapSchema));
//    } else if (v instanceof List) {
//      Schema componentSchema = Objects.requireNonNull(schema.getField(k)).getSchema().getComponentSchema();
//      if (componentSchema == null) {
//        throw new IllegalArgumentException(String.format("Unable to extract schema for the field '%s'", k));
//      }
//      Object values = ((List) v).stream()
//        .map(arrItem -> transform((Map<String, Object>) arrItem, componentSchema)).collect(Collectors.toList());
//      builder.set(k, values);
//    } else {
//      builder.set(k, v);
//    }
//  }
//
//  public static StructuredRecord transform(Map<String, Object> object, Schema schema) {
//    StructuredRecord.Builder builder = StructuredRecord.builder(schema);
//
//    object.entrySet().stream()
//      .filter(k -> schema.getField(k.getKey()) != null)  // filter absent fields in the schema
//      .forEach(k -> transformValue(k.getKey(), k.getValue(), schema, builder));
//
//    return builder.build();
//  }
//
//  public static StructuredRecord transform(IBaseObject object, Schema schema) {
//    StructuredRecord.Builder builder = StructuredRecord.builder(schema);
//
//    object.asFilteredMap(schema)
//      .forEach((k, v) -> transformValue(k, v, schema, builder));
//
//    return builder.build();
//  }
//}
