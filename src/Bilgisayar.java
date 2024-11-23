import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bilgisayar extends Oyuncu {

    public Bilgisayar(int oyuncuID, int skor) {
        super(oyuncuID, "Bilgisayar", skor);
    }

    @Override
    public List<Object> kartSec() {
        Random random = new Random();
        List<Object> secilenKartlar = new ArrayList<>();

        // Kartları karıştırma
        while (secilenKartlar.size() < 3) {
            int index = random.nextInt(this.kartListesi.size());
            if (!secilenKartlar.contains(this.kartListesi.get(index))) {
                secilenKartlar.add(this.kartListesi.get(index));
            }
        }
        return secilenKartlar;
    }
}
