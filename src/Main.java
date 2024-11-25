import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.print("Kaç tur oynanacak? ");
        int turSayisi = scanner.nextInt();

        int ucakSayisi = random.nextInt(3) + 1;  // 1 ile 3 arasında rastgele Ucak sayısı
        int obusSayisi = random.nextInt(3) + 1;  // 1 ile 3 arasında rastgele Obus sayısı
        int firkateynSayisi = 6 - (ucakSayisi + obusSayisi);  // Toplamda 6 kart olacak, kalan sayıyı Firkateyn kartları alacak

        if (firkateynSayisi < 1 || firkateynSayisi > 3) {
            obusSayisi = random.nextInt(3) + 1;
            ucakSayisi = random.nextInt(3) + 1;
            firkateynSayisi = 6 - (ucakSayisi + obusSayisi);
        }

        List<Object> tumKartlar = new ArrayList<>();

        for (int i = 0; i < ucakSayisi; i++) {
            tumKartlar.add(new Ucak("Hava"));
        }

        for (int i = 0; i < obusSayisi; i++) {
            tumKartlar.add(new Obus("Kara"));
        }

        for (int i = 0; i < firkateynSayisi; i++) {
            tumKartlar.add(new Firkateyn("Deniz"));
        }

        Collections.shuffle(tumKartlar);

        List<Object> kullaniciKartlari = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            kullaniciKartlari.add(tumKartlar.get(i)); // Kullanıcıya 6 kart
        }

        List<Object> bilgisayarKartlari = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            int kartSecimi = random.nextInt(3); // 0, 1 veya 2 seçilecek

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
        SaldiriHesapla(turSayisi, kullaniciKartlari, bilgisayarKartlari, random);
    }

    public static void SaldiriHesapla(int turSayisi, List<Object> kullaniciKartlari, List<Object> bilgisayarKartlari, Random random) {
        int kullaniciUcakIndex = 1, kullaniciObusIndex = 1, kullaniciFirkateynIndex = 1;
        int bilgisayarUcakIndex = 1, bilgisayarObusIndex = 1, bilgisayarFirkateynIndex = 1;

    
        for (Object kart : kullaniciKartlari) {
            if (kart instanceof Ucak) {
                ((Ucak) kart).setName("Ucak" + (kullaniciUcakIndex++));
            } else if (kart instanceof Obus) {
                ((Obus) kart).setName("Obus" + (kullaniciObusIndex++));
            } else if (kart instanceof Firkateyn) {
                ((Firkateyn) kart).setName("Firkateyn" + (kullaniciFirkateynIndex++));
            }
        }

        for (Object kart : bilgisayarKartlari) {
            if (kart instanceof Ucak) {
                ((Ucak) kart).setName("Ucak" + (bilgisayarUcakIndex++));
            } else if (kart instanceof Obus) {
                ((Obus) kart).setName("Obus" + (bilgisayarObusIndex++));
            } else if (kart instanceof Firkateyn) {
                ((Firkateyn) kart).setName("Firkateyn" + (bilgisayarFirkateynIndex++));
            }
        }

        for (int tur = 1; tur <= turSayisi; tur++) {
            System.out.println("\n--- Tur " + tur + " Başlıyor ---");

            while (kullaniciKartlari.size() < 3) {
                Object yeniKullaniciKart = rastgeleKartEkle(kullaniciKartlari, random, kullaniciUcakIndex, kullaniciObusIndex, kullaniciFirkateynIndex);
                if (yeniKullaniciKart instanceof Ucak) {
                kullaniciUcakIndex++;
                } else if (yeniKullaniciKart instanceof Obus) {
                    kullaniciObusIndex++;
                } else if (yeniKullaniciKart instanceof Firkateyn) {
                    kullaniciFirkateynIndex++;
                }
            }

            while (bilgisayarKartlari.size() < 3) {
                Object yeniBilgisayarKart = rastgeleKartEkle(bilgisayarKartlari, random, bilgisayarUcakIndex, bilgisayarObusIndex, bilgisayarFirkateynIndex);
                if (yeniBilgisayarKart instanceof Ucak) {
                    bilgisayarUcakIndex++;
                } else if (yeniBilgisayarKart instanceof Obus) {
                     bilgisayarObusIndex++;
                } else if (yeniBilgisayarKart instanceof Firkateyn) {
                    bilgisayarFirkateynIndex++;
                }
            }
            Kullanici kullanici = new Kullanici(1, "Kullanıcı", 0);
            kullanici.kartListesi.addAll(kullaniciKartlari);
            System.out.println("Kullanıcıya atanan kartlar:");
            for (Object kart : kullanici.kartListesi) {
                System.out.print(kart + " "); // Kartın toString metodunu çağırarak yazdırıyoruz
            }
            System.out.println();

            Bilgisayar bilgisayar = new Bilgisayar(2, 0);
            bilgisayar.kartListesi.addAll(bilgisayarKartlari);
            System.out.println("Bilgisayarın atanan 6 kartı:");
            for (Object kart : bilgisayar.kartListesi) {
                System.out.print(kart + " "); // Kartın toString metodunu çağırarak yazdırıyoruz
            }
            System.out.println();

            List<Object> bilgisayarSecilenKartlar = bilgisayar.kartSec();
            System.out.println("Bilgisayarın rastgele seçtiği 3 kart:");
            for (Object kart : bilgisayarSecilenKartlar) {
                System.out.print(kart + " "); // Kartın toString metodunu çağırarak yazdırıyoruz
            }
            System.out.println();

            List<Object> kullaniciSecilenKartlar = kullanici.kartSec();
            System.out.println("Kullanıcının seçtiği kartlar: " + kullaniciSecilenKartlar);

            System.out.println("\n--- Tur " + tur + " Bitti ---");

            for (int i = 0; i < kullaniciSecilenKartlar.size(); i++) {
                Object kullaniciKart = kullaniciSecilenKartlar.get(i);
                Object bilgisayarKart = bilgisayarSecilenKartlar.get(i);
            
                if (kullaniciKart instanceof Ucak) {
                    Ucak ucak = (Ucak) kullaniciKart;
            
                    if (bilgisayarKart instanceof Firkateyn) {
                        Firkateyn firkateyn = (Firkateyn) bilgisayarKart;
                        int kullaniciVurus = ucak.Vurus() ;
                        firkateyn.setDayaniklilik(firkateyn.getDayaniklilik() - kullaniciVurus);
                        if(firkateyn.getDayaniklilik()<=0 && firkateyn.getSeviyePuani()<=10){
                            ucak.setSeviyePuani(ucak.getSeviyePuani()+10);
                        }
                        else if(firkateyn.getDayaniklilik()<=0 && firkateyn.getSeviyePuani()>10){
                            ucak.setSeviyePuani(firkateyn.getSeviyePuani()+ucak.getSeviyePuani());
                        }
                        int bilgisayarVurus = firkateyn.Vurus() ;
                        ucak.setDayaniklilik(ucak.getDayaniklilik() - bilgisayarVurus);
                        if(ucak.getDayaniklilik()<=0 && ucak.getSeviyePuani()<=10){
                            firkateyn.setSeviyePuani(firkateyn.getDayaniklilik()+10);
                        }
                        else if(ucak.getDayaniklilik()<=0 && ucak.getSeviyePuani()>10){
                            firkateyn.setSeviyePuani(firkateyn.getSeviyePuani()+ucak.getSeviyePuani());
                        }
                        System.out.println("ucak" + ucak.getSeviyePuani() +"firkateyn"+ firkateyn.getSeviyePuani()+"\n");
                    } 
                    else if (bilgisayarKart instanceof Obus) {
                        Obus obus = (Obus) bilgisayarKart;
                        int kullaniciVurus = ucak.Vurus() + ucak.karaVurusAvantaji();
                        obus.setDayaniklilik(obus.getDayaniklilik() - kullaniciVurus);
                        if(obus.getDayaniklilik()<=0 && obus.getSeviyePuani()<=10){
                            ucak.setSeviyePuani(ucak.getSeviyePuani()+10);
                        }
                        else if(obus.getDayaniklilik()<=0 && obus.getSeviyePuani()>10){
                            ucak.setSeviyePuani(obus.getSeviyePuani()+ucak.getSeviyePuani());
                        }
                        int bilgisayarVurus = obus.Vurus();
                        ucak.setDayaniklilik(ucak.getDayaniklilik() - bilgisayarVurus);
                        if(ucak.getDayaniklilik()<=0 && ucak.getSeviyePuani()<=10){
                            obus.setSeviyePuani(obus.getSeviyePuani()+10);
                        }
                        else if(ucak.getDayaniklilik()<=0 && ucak.getSeviyePuani()>10){
                            obus.setSeviyePuani(obus.getSeviyePuani()+ucak.getSeviyePuani());
                        }
                        System.out.println("ucak"+ucak.getSeviyePuani() + "obus"+obus.getSeviyePuani()+"\n");
                    }
                    else if (bilgisayarKart instanceof Ucak) {
                        Ucak ucak1 = (Ucak) bilgisayarKart;
                        int kullaniciVurus = ucak.Vurus();
                        ucak1.setDayaniklilik(ucak1.getDayaniklilik() - kullaniciVurus);
                        if(ucak1.getDayaniklilik()<=0 && ucak1.getSeviyePuani()<=10){
                            ucak.setSeviyePuani(ucak.getSeviyePuani()+10);
                        }
                        else if(ucak1.getDayaniklilik()<=0 && ucak1.getSeviyePuani()>10){
                            ucak.setSeviyePuani(ucak1.getSeviyePuani()+ucak.getSeviyePuani());
                        }
                        int bilgisayarVurus = ucak1.Vurus();
                        ucak.setDayaniklilik(ucak.getDayaniklilik() - bilgisayarVurus);
                        if(ucak.getDayaniklilik()<=0 && ucak.getSeviyePuani()<=10){
                            ucak1.setSeviyePuani(ucak1.getSeviyePuani()+10);
                        }
                        else if(ucak.getDayaniklilik()<=0 && ucak.getSeviyePuani()>10){
                            ucak1.setSeviyePuani(ucak1.getSeviyePuani()+ucak.getSeviyePuani());
                        }
                        System.out.println("ucak"+ucak.getSeviyePuani() +"ucakpc"+ ucak1.getSeviyePuani()+"\n");
                    }
                }
            
                if (kullaniciKart instanceof Obus) {
                    Obus obus = (Obus) kullaniciKart;
            
                    if (bilgisayarKart instanceof Firkateyn) {
                        Firkateyn firkateyn = (Firkateyn) bilgisayarKart;
                        int kullaniciVurus = obus.Vurus() + obus.denizVurusAvantaji();
                        firkateyn.setDayaniklilik(firkateyn.getDayaniklilik() - kullaniciVurus);
                        if(firkateyn.getDayaniklilik()<=0 && firkateyn.getSeviyePuani()<=10){
                            obus.setSeviyePuani(obus.getSeviyePuani()+10);
                        }
                        else if(firkateyn.getDayaniklilik()<=0 && firkateyn.getSeviyePuani()>10){
                            obus.setSeviyePuani(firkateyn.getSeviyePuani()+obus.getSeviyePuani());
                        }
                        int bilgisayarVurus = firkateyn.Vurus() ;
                        obus.setDayaniklilik(obus.getDayaniklilik() - bilgisayarVurus);
                        if(obus.getDayaniklilik()<=0 && obus.getSeviyePuani()<=10){
                            firkateyn.setSeviyePuani(firkateyn.getSeviyePuani()+10);
                        }
                        else if(obus.getDayaniklilik()<=0 && obus.getSeviyePuani()>10){
                            firkateyn.setSeviyePuani(firkateyn.getSeviyePuani()+obus.getSeviyePuani());
                        }
                        System.out.println( "obus" + obus.getSeviyePuani() +"firkateyn"+ firkateyn.getSeviyePuani()+"\n");
                    } 
                    else if (bilgisayarKart instanceof Ucak) {
                        Ucak ucak = (Ucak) bilgisayarKart;
                        int kullaniciVurus = obus.Vurus();
                        ucak.setDayaniklilik(ucak.getDayaniklilik() - kullaniciVurus);
                        if(ucak.getDayaniklilik()<=0 && ucak.getSeviyePuani()<=10){
                            obus.setSeviyePuani(obus.getSeviyePuani()+10);
                        }
                        else if(ucak.getDayaniklilik()<=0 && ucak.getSeviyePuani()>0){
                            obus.setSeviyePuani(obus.getSeviyePuani()+ucak.getSeviyePuani());
                        }
                        int bilgisayarVurus = ucak.Vurus()+ucak.karaVurusAvantaji();
                        obus.setDayaniklilik(obus.getDayaniklilik() - bilgisayarVurus);
                        if(obus.getDayaniklilik()<=0 && obus.getSeviyePuani()<=10){
                            ucak.setSeviyePuani(ucak.getSeviyePuani()+10);
                        }
                        else if(obus.getDayaniklilik()<=0 && obus.getSeviyePuani()>0){
                            ucak.setSeviyePuani(obus.getSeviyePuani()+ucak.getSeviyePuani());
                        }
                        System.out.println("obus"+obus.getSeviyePuani() +"ucak"+ ucak.getSeviyePuani()+"\n");
                    }
                    else if (bilgisayarKart instanceof Obus) {
                        Obus obus1 = (Obus) bilgisayarKart;
                        int kullaniciVurus = obus.Vurus();
                        obus.setDayaniklilik(obus1.getDayaniklilik() - kullaniciVurus);
                        if(obus1.getDayaniklilik()<=0 && obus1.getSeviyePuani()<=10){
                            obus.setSeviyePuani(obus.getSeviyePuani()+10);
                        }
                        else if(obus1.getDayaniklilik()<=0 && obus1.getSeviyePuani()>10){
                            obus.setSeviyePuani(obus1.getSeviyePuani()+obus.getSeviyePuani());
                        }
                        int bilgisayarVurus = obus1.Vurus() ;
                        obus.setDayaniklilik(obus.getDayaniklilik() - bilgisayarVurus);
                        if(obus.getDayaniklilik()<=0 && obus.getSeviyePuani()<=10){
                            obus1.setSeviyePuani(obus1.getSeviyePuani()+10);
                        }
                        else if(obus.getDayaniklilik()<=0 && obus.getSeviyePuani()>10){
                            obus1.setSeviyePuani(obus1.getSeviyePuani()+obus.getSeviyePuani());
                        }
                        System.out.println("obus"+ obus.getSeviyePuani() +"obuspc"+ obus1.getSeviyePuani()+"\n");
                    }
                }
            
                if (kullaniciKart instanceof Firkateyn) {
                    Firkateyn firkateyn = (Firkateyn) kullaniciKart;
            
                    if (bilgisayarKart instanceof Ucak) {
                        Ucak ucak = (Ucak) bilgisayarKart;
                        int kullaniciVurus = firkateyn.Vurus() + firkateyn.havaVurusAvantaji();
                        if(ucak.getDayaniklilik()<=0 && ucak.getSeviyePuani()<=10){
                            firkateyn.setSeviyePuani(10);
                        }
                        else if(ucak.getDayaniklilik()<=0 && ucak.getSeviyePuani()>0){
                            firkateyn.setSeviyePuani(firkateyn.getSeviyePuani()+ucak.getSeviyePuani());
                        }
                        ucak.setDayaniklilik(ucak.getDayaniklilik() - kullaniciVurus);
                        int bilgisayarVurus = ucak.Vurus();
                        firkateyn.setDayaniklilik(firkateyn.getDayaniklilik() - bilgisayarVurus);
                        if(firkateyn.getDayaniklilik()<=0 && firkateyn.getSeviyePuani()<=10){
                            ucak.setSeviyePuani(10);
                        }
                        else if(firkateyn.getDayaniklilik()<=0 && firkateyn.getSeviyePuani()>0){
                            ucak.setSeviyePuani(firkateyn.getSeviyePuani()+ucak.getSeviyePuani());
                        }
                        System.out.println("firkateyn" +firkateyn.getSeviyePuani() + "ucak"+ucak.getSeviyePuani()+"\n");
                    }
                    else if (bilgisayarKart instanceof Obus) {
                        Obus obus = (Obus) bilgisayarKart;
                        int kullaniciVurus = firkateyn.Vurus();
                        obus.setDayaniklilik(obus.getDayaniklilik() - kullaniciVurus);
                        if(obus.getDayaniklilik()<=0 && obus.getSeviyePuani()<=10){
                            firkateyn.setSeviyePuani(10);
                        }
                        else if(obus.getDayaniklilik()<=0 && obus.getSeviyePuani()>10){
                            firkateyn.setSeviyePuani(obus.getSeviyePuani()+firkateyn.getSeviyePuani());
                        }
                        int bilgisayarVurus = obus.Vurus()+obus.denizVurusAvantaji();
                        firkateyn.setDayaniklilik(firkateyn.getDayaniklilik() - bilgisayarVurus);
                        if(firkateyn.getDayaniklilik()<=0 && firkateyn.getSeviyePuani()<=10){
                            obus.setSeviyePuani(10);
                        }
                        else if(firkateyn.getDayaniklilik()<=0 && firkateyn.getSeviyePuani()>10){
                            obus.setSeviyePuani(obus.getSeviyePuani()+firkateyn.getSeviyePuani());
                        }
                        System.out.println("firkateyn"+firkateyn.getSeviyePuani() + "obus"+obus.getSeviyePuani()+"\n");
                    }
                    else if (bilgisayarKart instanceof Firkateyn) {
                        Firkateyn firkateyn1 = (Firkateyn) bilgisayarKart;
                        int kullaniciVurus = firkateyn.Vurus();
                        firkateyn.setDayaniklilik(firkateyn1.getDayaniklilik() - kullaniciVurus);
                        if(firkateyn1.getDayaniklilik()<=0 && firkateyn1.getSeviyePuani()<=10){
                            firkateyn.setSeviyePuani(10);
                        }
                        else if(firkateyn1.getDayaniklilik()<=0 && firkateyn1.getSeviyePuani()>10){
                            firkateyn.setSeviyePuani(firkateyn1.getSeviyePuani()+firkateyn.getSeviyePuani());
                        }
                        int bilgisayarVurus = firkateyn1.Vurus() ;
                        firkateyn.setDayaniklilik(firkateyn.getDayaniklilik() - bilgisayarVurus);
                        if(firkateyn.getDayaniklilik()<=0 && firkateyn.getSeviyePuani()<=10){
                            firkateyn1.setSeviyePuani(10);
                        }
                        else if(firkateyn.getDayaniklilik()<=0 && firkateyn.getSeviyePuani()>10){
                            firkateyn1.setSeviyePuani(firkateyn1.getSeviyePuani()+firkateyn.getSeviyePuani());
                        }
                        System.out.println(" firkateyn"+firkateyn.getSeviyePuani() +"firkateyn1"+ firkateyn1.getSeviyePuani()+"\n");
                    }
                }

                for (int k = kullaniciKartlari.size() - 1; k >= 0; k--) { 
                    Object kart = kullaniciKartlari.get(k);
                    if (kart instanceof Ucak && ((Ucak) kart).getDayaniklilik() <= 0) {
                        System.out.println(((Ucak) kart).getName() + " öldü ve kullanıcı kartlarından çıkarılıyor.");
                        kullaniciKartlari.remove(k);
                    } else if (kart instanceof Obus && ((Obus) kart).getDayaniklilik() <= 0) {
                        System.out.println(((Obus) kart).getName() + " öldü ve kullanıcı kartlarından çıkarılıyor.");
                        kullaniciKartlari.remove(k);
                    } else if (kart instanceof Firkateyn && ((Firkateyn) kart).getDayaniklilik() <= 0) {
                        System.out.println(((Firkateyn) kart).getName() + " öldü ve kullanıcı kartlarından çıkarılıyor.");
                        kullaniciKartlari.remove(k);
                    }
                }

                for (int j = bilgisayarKartlari.size() - 1; j >= 0; j--) {
                    Object kart = bilgisayarKartlari.get(j);
                    if (kart instanceof Ucak && ((Ucak) kart).getDayaniklilik() <= 0) {
                        System.out.println(((Ucak) kart).getName() + " öldü ve bilgisayar kartlarından çıkarılıyor.");
                        bilgisayarKartlari.remove(j);
                    } else if (kart instanceof Obus && ((Obus) kart).getDayaniklilik() <= 0) {
                         System.out.println(((Obus) kart).getName() + " öldü ve bilgisayar kartlarından çıkarılıyor.");
                        bilgisayarKartlari.remove(j);
                    } else if (kart instanceof Firkateyn && ((Firkateyn) kart).getDayaniklilik() <= 0) {
                        System.out.println(((Firkateyn) kart).getName() + " öldü ve bilgisayar kartlarından çıkarılıyor.");
                        bilgisayarKartlari.remove(j);
                    }
                }

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
