import java.io.File;
public class Main {
    public static void main(String[] args) {
        String filePath = "resources" + File.separator + "config.properties";
        PayrollSystem ps = new PayrollSystem(filePath);
        ps.run();

    }
}