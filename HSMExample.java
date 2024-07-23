import java.security.Key;
import java.security.KeyStore;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

public class HSMExample {

    public static void main(String[] args) {
        try {
            String configPath = "pkcs11.cfg"; // Đường dẫn tới file cấu hình PKCS#11
            Provider provider = Security.getProvider("SunPKCS11");
            provider = provider.configure(configPath);
            Security.addProvider(provider);

            // Tạo đối tượng KeyStore sử dụng PKCS#11
            KeyStore keyStore = KeyStore.getInstance("PKCS11", provider);
            keyStore.load(null, "1234".toCharArray()); // Nếu cần PIN, thay thế `null` bằng PIN thực tế
            PublicKey publicKey = keyStore.getCertificate("My Key").getPublicKey();
            // Hoặc
            Key myKey = keyStore.getKey("My Key", "1234".toCharArray());
            System.out.println("Public Key: " + publicKey);
            System.out.println("Private Key: " + myKey);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
