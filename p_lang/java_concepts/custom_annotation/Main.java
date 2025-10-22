package p_lang.java_concepts.custom_annotation;

public class Main {

   public static void main(String... args) {
      JsonSerializer serializer = new JsonSerializer();
      System.out.println(serializer.serialize(new Person("bekmukhamet", "amedov")));
   }
}
