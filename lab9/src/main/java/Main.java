import com.google.gson.Gson;

public class Main {
    public static void main(String[] args) {
        Product product = new Product();
        System.out.println(new Gson().toJson(product));
    }
}
