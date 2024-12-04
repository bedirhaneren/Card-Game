import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Kullanici extends Oyuncu {

    public Kullanici(int oyuncuID, String oyuncuAdi, int skor) {
        super(oyuncuID, oyuncuAdi, skor);
    }

    @Override
    public List<Object> kartSec() {
        Scanner scanner = new Scanner(System.in);
        List<Object> secilenKartlar = new ArrayList<>();
        System.out.println("Kullanıcıya atanan 6 karttan 3 tane seçin (kart isimlerini girin):");
        
        while (secilenKartlar.size() < 3) {
            String secim = scanner.nextLine();
            boolean bulundu = false;
            for (Object kart : this.kartListesi) {
                if (kart.toString().equals(secim) && !secilenKartlar.contains(kart)) {
                    secilenKartlar.add(kart);
                    bulundu = true;
                    break;
                }
            }
            if (!bulundu) {
                System.out.println("Geçerli bir kart seçmediniz. Tekrar deneyin.");
            }
        }
        return secilenKartlar;
    }
}
