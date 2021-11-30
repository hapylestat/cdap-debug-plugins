package io.cdap.plugin.debug.common.helpers;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nullable;


//      try {
//        URLClassLoader classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
//        Arrays.stream(classLoader.getURLs()).map(URL::getFile).forEach(x -> {
//          System.out.println(String.format("  - %s", x));
//        });
//      } catch (ClassCastException e) {
//        System.out.println(" - <No support for Java 9+>");
//      }

public class ClassPathReader {
  public static @Nullable List<String> getClassPathString(@Nullable ClassLoader classLoader) {
    if (classLoader == null) {
      classLoader = ClassPathReader.class.getClassLoader();
    }
    try {
      URLClassLoader clazzLoader = (URLClassLoader) classLoader;
      return Arrays.stream(clazzLoader.getURLs()).map(URL::getFile).collect(Collectors.toList());
    } catch (ClassCastException e) {
      System.out.println("<No support for Java 9+>");
    }
    return Arrays.asList();
  }
}
