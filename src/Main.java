import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();

        // Kart türlerinin sayısını rastgele belirleyelim (toplamda 6 kart olacak şekilde)
        int ucakSayisi = random.nextInt(3) + 1;  // 1 ile 3 arasında rastgele Ucak sayısı
        int obusSayisi = random.nextInt(3) + 1;  // 1 ile 3 arasında rastgele Obus sayısı
        int firkateynSayisi = 6 - (ucakSayisi + obusSayisi);  // Toplamda 6 kart olacak, kalan sayıyı Firkateyn kartları alacak

        // Eğer negatif bir sayı çıkarsa (yani 6'dan fazla kart sayısı), yeniden ayarla
        if (firkateynSayisi < 1 || firkateynSayisi > 3) {
            // Kart sayılarının toplamı her zaman 6 olacak şekilde yeniden ayarla
            obusSayisi = random.nextInt(3) + 1;
            ucakSayisi = random.nextInt(3) + 1;
            firkateynSayisi = 6 - (ucakSayisi + obusSayisi);
        }

        // Kartların listesi
        List<Object> tumKartlar = new ArrayList<>();

        // Sabit sayıdaki Ucak kartlarını ekleyelim
        for (int i = 0; i < ucakSayisi; i++) {
            tumKartlar.add(new Ucak(50, "Hava"));
        }

        // Sabit sayıdaki Obus kartlarını ekleyelim
        for (int i = 0; i < obusSayisi; i++) {
            tumKartlar.add(new Obus(40, "Kara"));
        }

        // Sabit sayıdaki Firkateyn kartlarını ekleyelim
        for (int i = 0; i < firkateynSayisi; i++) {
            tumKartlar.add(new Firkateyn(30, "Deniz"));
        }

        // Kartları karıştırma
        Collections.shuffle(tumKartlar);

        // Kullanıcı için kart listesi
        List<Object> kullaniciKartlari = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            kullaniciKartlari.add(tumKartlar.get(i)); // Kullanıcıya 6 kart
        }

        // Bilgisayar için yeni bir ArrayList oluşturuyoruz ve sıfırdan rastgele kartlar ekliyoruz
        List<Object> bilgisayarKartlari = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            int kartSecimi = random.nextInt(3); // 0, 1 veya 2 seçilecek

            // Seçilen karta göre kart ekleme ve isim seti
            if (kartSecimi == 0) {
                Ucak ucak = new Ucak(50, "Hava");
                bilgisayarKartlari.add(ucak);
            } else if (kartSecimi == 1) {
                Obus obus = new Obus(40, "Kara");
                bilgisayarKartlari.add(obus);
            } else {
                Firkateyn firkateyn = new Firkateyn(30, "Deniz");
                bilgisayarKartlari.add(firkateyn);
            }
        }

        // **Kullanıcı kartlarına isim atama**
        int kullaniciUcakIndex = 1, kullaniciObusIndex = 1, kullaniciFirkateynIndex = 1;
        for (Object kart : kullaniciKartlari) {
            if (kart instanceof Ucak) {
                ((Ucak) kart).setName("Ucak" + (kullaniciUcakIndex++));
            } else if (kart instanceof Obus) {
                ((Obus) kart).setName("Obus" + (kullaniciObusIndex++));
            } else if (kart instanceof Firkateyn) {
                ((Firkateyn) kart).setName("Firkateyn" + (kullaniciFirkateynIndex++));
            }
        }

        // **Bilgisayar kartlarına isim atama**
        int bilgisayarUcakIndex = 1, bilgisayarObusIndex = 1, bilgisayarFirkateynIndex = 1;
        for (Object kart : bilgisayarKartlari) {
            if (kart instanceof Ucak) {
                ((Ucak) kart).setName("Ucak" + (bilgisayarUcakIndex++));
            } else if (kart instanceof Obus) {
                ((Obus) kart).setName("Obus" + (bilgisayarObusIndex++));
            } else if (kart instanceof Firkateyn) {
                ((Firkateyn) kart).setName("Firkateyn" + (bilgisayarFirkateynIndex++));
            }
        }

        // **Kullanıcıya atanan 6 kartı yazdırma**
        Kullanici kullanici = new Kullanici(1, "Kullanıcı", 0);
        kullanici.kartListesi.addAll(kullaniciKartlari);
        System.out.println("Kullanıcıya atanan kartlar:");
        for (Object kart : kullanici.kartListesi) {
            System.out.print(kart + " "); // Kartın toString metodunu çağırarak yazdırıyoruz
        }
        System.out.println();

        // **Bilgisayarın atanan 6 kartını yazdırma**
        Bilgisayar bilgisayar = new Bilgisayar(2, 0);
        bilgisayar.kartListesi.addAll(bilgisayarKartlari);
        System.out.println("Bilgisayarın atanan 6 kartı:");
        for (Object kart : bilgisayar.kartListesi) {
            System.out.print(kart + " "); // Kartın toString metodunu çağırarak yazdırıyoruz
        }
        System.out.println();

        // **Bilgisayarın rastgele seçtiği 3 kartı yazdırma**
        List<Object> bilgisayarSecilenKartlar = bilgisayar.kartSec();
        System.out.println("Bilgisayarın rastgele seçtiği 3 kart:");
        for (Object kart : bilgisayarSecilenKartlar) {
            System.out.print(kart + " "); // Kartın toString metodunu çağırarak yazdırıyoruz
        }
        System.out.println();

        // Kullanıcı kart seçiyor
        List<Object> kullaniciSecilenKartlar = kullanici.kartSec();
        System.out.println("Kullanıcının seçtiği kartlar: " + kullaniciSecilenKartlar);
    }
}
