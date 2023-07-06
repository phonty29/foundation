public class HelloGoodbye {
    public static void main(String[] args) {
        String fName= args[0];
        String sName=args[1];
        System.out.println(String.format("Hello %s and %s", fName, sName));
        System.out.println(String.format("Goodbye %s and %s", sName, fName));
    }
}
