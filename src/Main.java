import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public class Main {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        // Kullanıcıdan tur sayısını alıyoruz
        System.out.print("Kaç tur oynanacak? ");
        int turSayisi = scanner.nextInt();

        // Kart türlerinin sayısını rastgele belirleyelim (toplamda 6 kart olacak şekilde)
        int ucakSayisi = random.nextInt(3) + 1;  // 1 ile 3 arasında rastgele Ucak sayısı
        int obusSayisi = random.nextInt(3) + 1;  // 1 ile 3 arasında rastgele Obus sayısı
        int firkateynSayisi = 6 - (ucakSayisi + obusSayisi);  // Toplamda 6 kart olacak, kalan sayıyı Firkateyn kartları alacak

        // Eğer negatif bir sayı çıkarsa (yani 6'dan fazla kart sayısı), yeniden ayarla
        if (firkateynSayisi < 1 || firkateynSayisi > 3) {
            obusSayisi = random.nextInt(3) + 1;
            ucakSayisi = random.nextInt(3) + 1;
            firkateynSayisi = 6 - (ucakSayisi + obusSayisi);
        }

        // Kartların listesi
        List<Object> tumKartlar = new ArrayList<>();

        // Sabit sayıdaki Ucak kartlarını ekleyelim
        for (int i = 0; i < ucakSayisi; i++) {
            tumKartlar.add(new Ucak("Hava"));
        }

        // Sabit sayıdaki Obus kartlarını ekleyelim
        for (int i = 0; i < obusSayisi; i++) {
            tumKartlar.add(new Obus("Kara"));
        }

        // Sabit sayıdaki Firkateyn kartlarını ekleyelim
        for (int i = 0; i < firkateynSayisi; i++) {
            tumKartlar.add(new Firkateyn("Deniz"));
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
                Ucak ucak = new Ucak("Hava");
                bilgisayarKartlari.add(ucak);
            } else if (kartSecimi == 1) {
                Obus obus = new Obus("Kara");
                bilgisayarKartlari.add(obus);
            } else {
                Firkateyn firkateyn = new Firkateyn("Deniz");
                bilgisayarKartlari.add(firkateyn);
            }
        }

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

        // Tur döngüsü
        for (int tur = 1; tur <= turSayisi; tur++) {
            System.out.println("\n--- Tur " + tur + " Başlıyor ---");

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

            System.out.println("\n--- Tur " + tur + " Bitti ---");

            for (int i = 0; i < kullaniciSecilenKartlar.size(); i++) {
                Object kullaniciKart = kullaniciSecilenKartlar.get(i);
                Object bilgisayarKart = bilgisayarSecilenKartlar.get(i);
            
                // Kullanıcının kartı Uçak
                if (kullaniciKart instanceof Ucak) {
                    Ucak ucak = (Ucak) kullaniciKart;
            
                    if (bilgisayarKart instanceof Firkateyn) {
                        Firkateyn firkateyn = (Firkateyn) bilgisayarKart;
                        // Kullanıcı saldırıyor
                        int kullaniciVurus = ucak.Vurus() + ucak.karaVurusAvantaji();
                        firkateyn.setDayaniklilik(firkateyn.getDayaniklilik() - kullaniciVurus);
                        // Bilgisayar saldırıyor
                        int bilgisayarVurus = firkateyn.Vurus() + firkateyn.havaVurusAvantaji();
                        ucak.setDayaniklilik(ucak.getDayaniklilik() - bilgisayarVurus);
                    } 
                    else if (bilgisayarKart instanceof Obus) {
                        Obus obus = (Obus) bilgisayarKart;
                        int kullaniciVurus = ucak.Vurus() + ucak.karaVurusAvantaji();
                        obus.setDayaniklilik(obus.getDayaniklilik() - kullaniciVurus);
                        int bilgisayarVurus = obus.Vurus() + obus.denizVurusAvantaji();
                        ucak.setDayaniklilik(ucak.getDayaniklilik() - bilgisayarVurus);
                    }
                    else if (bilgisayarKart instanceof Ucak) {
                        Ucak ucak1 = (Ucak) bilgisayarKart;
                        int kullaniciVurus = ucak.Vurus() + ucak.karaVurusAvantaji();
                        ucak.setDayaniklilik(ucak1.getDayaniklilik() - kullaniciVurus);
                        int bilgisayarVurus = ucak1.Vurus()+ucak1.karaVurusAvantaji();
                        ucak.setDayaniklilik(ucak.getDayaniklilik() - bilgisayarVurus);
                    }
                    // Diğer ihtimaller burada işlenecek
                }
            
                // Kullanıcının kartı Obüs
                if (kullaniciKart instanceof Obus) {
                    Obus obus = (Obus) kullaniciKart;
            
                    if (bilgisayarKart instanceof Firkateyn) {
                        Firkateyn firkateyn = (Firkateyn) bilgisayarKart;
                        int kullaniciVurus = obus.Vurus() + obus.denizVurusAvantaji();
                        firkateyn.setDayaniklilik(firkateyn.getDayaniklilik() - kullaniciVurus);
                        int bilgisayarVurus = firkateyn.Vurus() + firkateyn.havaVurusAvantaji();
                        obus.setDayaniklilik(obus.getDayaniklilik() - bilgisayarVurus);
                    } 
                    else if (bilgisayarKart instanceof Ucak) {
                        Ucak ucak = (Ucak) bilgisayarKart;
                        int kullaniciVurus = obus.Vurus() + obus.denizVurusAvantaji();
                        ucak.setDayaniklilik(ucak.getDayaniklilik() - kullaniciVurus);
                        int bilgisayarVurus = ucak.Vurus()+ucak.karaVurusAvantaji();
                        obus.setDayaniklilik(obus.getDayaniklilik() - bilgisayarVurus);
                    }
                    else if (bilgisayarKart instanceof Obus) {
                        Obus obus1 = (Obus) bilgisayarKart;
                        int kullaniciVurus = obus.Vurus() + obus.denizVurusAvantaji();
                        obus.setDayaniklilik(obus1.getDayaniklilik() - kullaniciVurus);
                        int bilgisayarVurus = obus1.Vurus() + obus1.denizVurusAvantaji();
                        obus.setDayaniklilik(obus.getDayaniklilik() - bilgisayarVurus);
                    }
                    // Diğer ihtimaller burada işlenecek
                }
            
                // Kullanıcının kartı Firkateyn
                if (kullaniciKart instanceof Firkateyn) {
                    Firkateyn firkateyn = (Firkateyn) kullaniciKart;
            
                    if (bilgisayarKart instanceof Ucak) {
                        Ucak ucak = (Ucak) bilgisayarKart;
                        int kullaniciVurus = firkateyn.Vurus() + firkateyn.havaVurusAvantaji();
                        ucak.setDayaniklilik(ucak.getDayaniklilik() - kullaniciVurus);
                        int bilgisayarVurus = ucak.Vurus() + ucak.karaVurusAvantaji();
                        firkateyn.setDayaniklilik(firkateyn.getDayaniklilik() - bilgisayarVurus);
                    }
                    else if (bilgisayarKart instanceof Obus) {
                        Obus obus = (Obus) bilgisayarKart;
                        int kullaniciVurus = firkateyn.Vurus()+firkateyn.havaVurusAvantaji();
                        obus.setDayaniklilik(obus.getDayaniklilik() - kullaniciVurus);
                        int bilgisayarVurus = obus.Vurus()+obus.denizVurusAvantaji();
                        firkateyn.setDayaniklilik(firkateyn.getDayaniklilik() - bilgisayarVurus);
                    }
                    else if (bilgisayarKart instanceof Firkateyn) {
                        Firkateyn firkateyn1 = (Firkateyn) bilgisayarKart;
                        // Kullanıcı saldırıyor
                        int kullaniciVurus = firkateyn.Vurus() + firkateyn.havaVurusAvantaji();
                        firkateyn.setDayaniklilik(firkateyn1.getDayaniklilik() - kullaniciVurus);
                        // Bilgisayar saldırıyor
                        int bilgisayarVurus = firkateyn1.Vurus() + firkateyn1.havaVurusAvantaji();
                        firkateyn.setDayaniklilik(firkateyn.getDayaniklilik() - bilgisayarVurus);
                    }
                    // Diğer ihtimaller burada işlenecek
                }
            
                // Kartların öldürülmesi işlemleri
                if (kullaniciKart instanceof Ucak && ((Ucak) kullaniciKart).getDayaniklilik() <= 0) {
                    System.out.println(((Ucak) kullaniciKart).getName() + " öldü ve kullanıcı kartlarından çıkarılıyor.");
                    kullaniciKartlari.remove(kullaniciKart);
                    kullaniciSecilenKartlar.remove(i);
                    i--;
                }
            
                if (bilgisayarKart instanceof Firkateyn && ((Firkateyn) bilgisayarKart).getDayaniklilik() <= 0) {
                    System.out.println(((Firkateyn) bilgisayarKart).getName() + " öldü ve bilgisayar kartlarından çıkarılıyor.");
                    bilgisayarKartlari.remove(bilgisayarKart);
                    bilgisayarSecilenKartlar.remove(i);
                    i--;
                }
            }
            
            Object yeniKullaniciKart = rastgeleKartEkle(kullaniciKartlari, random, kullaniciUcakIndex, kullaniciObusIndex, kullaniciFirkateynIndex);
            if (yeniKullaniciKart instanceof Ucak) {
                kullaniciUcakIndex++;
            } else if (yeniKullaniciKart instanceof Obus) {
                kullaniciObusIndex++;
            } else if (yeniKullaniciKart instanceof Firkateyn) {
                kullaniciFirkateynIndex++;
            }

            Object yeniBilgisayarKart = rastgeleKartEkle(bilgisayarKartlari, random, bilgisayarUcakIndex, bilgisayarObusIndex, bilgisayarFirkateynIndex);
            if (yeniBilgisayarKart instanceof Ucak) {
                bilgisayarUcakIndex++;
            } else if (yeniBilgisayarKart instanceof Obus) {
                bilgisayarObusIndex++;
            } else if (yeniBilgisayarKart instanceof Firkateyn) {
                bilgisayarFirkateynIndex++;
            }

        }
        System.out.println("\nOyun bitti. Tüm turlar oynandı!");
    }
    private static Object rastgeleKartEkle(List<Object> kartListesi, Random random, int ucakIndex, int obusIndex, int firkateynIndex) {
        int kartTuru = random.nextInt(3); // 0: Uçak, 1: Obüs, 2: Firkateyn
        Object yeniKart;
        if (kartTuru == 0) {
            yeniKart = new Ucak("Hava");
            ((Ucak) yeniKart).setName("Ucak" + ucakIndex);
        } else if (kartTuru == 1) {
            yeniKart = new Obus("Kara");
            ((Obus) yeniKart).setName("Obus" + obusIndex);
        } else {
            yeniKart = new Firkateyn("Deniz");
            ((Firkateyn) yeniKart).setName("Firkateyn" + firkateynIndex);
        }
        kartListesi.add(yeniKart);
        return yeniKart;
    }
}
    