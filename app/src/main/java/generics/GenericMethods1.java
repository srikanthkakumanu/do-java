package generics;

class Data {
    // generic method with single parameter
    public <T> void getData(T data) {
        System.out.println("This is generic method and data passed: " + data);
    }


}

public class GenericMethods1 {
    public static void main(String[] args) {
        Data d = new Data();
        // generic method with string data
        d.getData("This is Generic Method");
        // d.<String>getData("This is Generic Method");
        d.getData(2738);
        // d.<Integer>getData(2738);
    }
}
