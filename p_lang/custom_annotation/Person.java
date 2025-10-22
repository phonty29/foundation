package p_lang.custom_annotation;

@JsonSerializable
public class Person {
   @JsonElement(key = "first_name")
   private String firstName;
   @JsonElement(key = "last_name")
   private String lastName;

   @Capitalize
   private void capitalizeName() {
      this.firstName = this.firstName.substring(0, 1).toUpperCase() + this.firstName.substring(1);
      this.lastName = this.lastName.substring(0, 1).toUpperCase() + this.lastName.substring(1);
   }


   Person(String firstName, String lastName) {
      this.firstName = firstName;
      this.lastName = lastName;
   }
}
