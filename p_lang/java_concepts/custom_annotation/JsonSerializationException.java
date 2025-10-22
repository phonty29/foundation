package p_lang.java_concepts.custom_annotation;

public class JsonSerializationException extends RuntimeException {
   public JsonSerializationException(String className) {
      System.err.println(String.format("The class %s is not JSON serializable", className));
   }
}
