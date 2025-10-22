package p_lang.java_concepts.custom_annotation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class JsonSerializer {
   public JsonSerializer() {
   }

   public String serialize(Object obj) throws JsonSerializationException {
      try {
         this.checkIfSerializable(obj);
         this.capitalizeName(obj);
         return this.getJsonString(obj);
      } catch (Exception ex) {
         System.err.println(ex.getMessage());
         throw new JsonSerializationException(obj.getClass().getName());
      }
   }

   private void capitalizeName(Object obj) throws Exception {
      Class<?> clazz = obj.getClass();
      for (Method method : clazz.getDeclaredMethods()) {
         if (method.isAnnotationPresent(Capitalize.class)) {
               method.setAccessible(true);
               method.invoke(obj);
         }
      }
   }

   private String getJsonString(Object object) throws Exception {	
      Class<?> clazz = object.getClass();
      Map<String, String> jsonElementsMap = new HashMap<>();
      for (Field field : clazz.getDeclaredFields()) {
         field.setAccessible(true);
         if (field.isAnnotationPresent(JsonElement.class)) {
               jsonElementsMap.put(getKey(field), (String) field.get(object));
         }
      }		
      
      String jsonString = jsonElementsMap.entrySet()
         .stream()
         .map(entry -> "\"" + entry.getKey() + "\":\"" 
            + entry.getValue() + "\"")
         .collect(Collectors.joining(","));
      return "{" + jsonString + "}";
   }

   private String getKey(Field field) {
      String key = field.getName();
      JsonElement annotation = field.getAnnotation(JsonElement.class);
      if (!annotation.key().isEmpty()) {
         key = annotation.key();
      } else {
         key = field.getName();
      }
      return key;
   }

   private void checkIfSerializable(Object obj) {
      if (Objects.isNull(obj)) {
         throw new JsonSerializationException("null");
      }
         
      Class<?> clazz = obj.getClass();
      if (!clazz.isAnnotationPresent(JsonSerializable.class)) {
         throw new JsonSerializationException(obj.getClass().getName());
      }
   }
}