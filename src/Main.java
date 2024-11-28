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

        int kullaniciskor ;
        int bilgisayarskor;

        for (int tur = 1; tur <= turSayisi; tur++) {
            System.out.println("\n--- Tur " + tur + " Başlıyor ---");

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

            while (kullaniciKartlari.size() < 3) {
                Object yeniKullaniciKart = rastgeleKartEkle(kullaniciKartlari, random, kullaniciUcakIndex, kullaniciObusIndex, kullaniciFirkateynIndex , kullanici.getSkor());
                if (yeniKullaniciKart instanceof Ucak) {
                kullaniciUcakIndex++;
                } else if (yeniKullaniciKart instanceof Obus) {
                    kullaniciObusIndex++;
                } else if (yeniKullaniciKart instanceof Firkateyn) {
                    kullaniciFirkateynIndex++;
                }
            }

            while (bilgisayarKartlari.size() < 3) {
                Object yeniBilgisayarKart = rastgeleKartEkle(bilgisayarKartlari, random, bilgisayarUcakIndex, bilgisayarObusIndex, bilgisayarFirkateynIndex , bilgisayar.getSkor());
                if (yeniBilgisayarKart instanceof Ucak) {
                    bilgisayarUcakIndex++;
                } else if (yeniBilgisayarKart instanceof Obus) {
                     bilgisayarObusIndex++;
                } else if (yeniBilgisayarKart instanceof Firkateyn) {
                    bilgisayarFirkateynIndex++;
                }
            }
        
            for (int i = 0; i < kullaniciSecilenKartlar.size(); i++) {
                Object kullaniciKart = kullaniciSecilenKartlar.get(i);
                Object bilgisayarKart = bilgisayarSecilenKartlar.get(i);
            
                if (kullaniciKart instanceof Ucak) {
                    Ucak ucak = (Ucak) kullaniciKart;
            
                    if (bilgisayarKart instanceof Firkateyn) {
                        Firkateyn firkateyn = (Firkateyn) bilgisayarKart;
                        int kullaniciVurus = ucak.Vurus() + ucak.karaVurusAvantaji();
                        firkateyn.DurumGuncelle(kullaniciVurus);
                        //firkateyn.setDayaniklilik(firkateyn.getDayaniklilik() - kullaniciVurus);
                        if(firkateyn.getDayaniklilik()<=0 && firkateyn.getSeviyePuani()<=10){
                            ucak.setSeviyePuani(ucak.getSeviyePuani()+10);
                        }
                        else if(firkateyn.getDayaniklilik()<=0 && firkateyn.getSeviyePuani()>10){
                            ucak.setSeviyePuani(firkateyn.getSeviyePuani()+ucak.getSeviyePuani());
                        }
                        int bilgisayarVurus = firkateyn.Vurus();
                        ucak.DurumGuncelle(bilgisayarVurus);
                        //ucak.setDayaniklilik(ucak.getDayaniklilik() - bilgisayarVurus);
                        if(ucak.getDayaniklilik()<=0 && ucak.getSeviyePuani()<=10){
                            firkateyn.setSeviyePuani(firkateyn.getSeviyePuani()+10);
                        }
                        else if(ucak.getDayaniklilik()<=0 && ucak.getSeviyePuani()>10){
                            firkateyn.setSeviyePuani(firkateyn.getSeviyePuani()+ucak.getSeviyePuani());
                        }
                        System.out.println("ucak"+ucak.getSeviyePuani() + "firkateyn"+firkateyn.getSeviyePuani()+"\n");
                        kullanici.setSkor(ucak.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(firkateyn.getSeviyePuani()+bilgisayar.getSkor());
                    }
                    else if (bilgisayarKart instanceof Obus) {
                        Obus obus = (Obus) bilgisayarKart;
                        int kullaniciVurus = ucak.Vurus() + ucak.karaVurusAvantaji();
                        obus.DurumGuncelle(kullaniciVurus);
                        //obus.setDayaniklilik(obus.getDayaniklilik() - kullaniciVurus);
                        if(obus.getDayaniklilik()<=0 && obus.getSeviyePuani()<=10){
                            ucak.setSeviyePuani(ucak.getSeviyePuani()+10);
                        }
                        else if(obus.getDayaniklilik()<=0 && obus.getSeviyePuani()>10){
                            ucak.setSeviyePuani(obus.getSeviyePuani()+ucak.getSeviyePuani());
                        }
                        int bilgisayarVurus = obus.Vurus();
                        ucak.DurumGuncelle(bilgisayarVurus);
                        //ucak.setDayaniklilik(ucak.getDayaniklilik() - bilgisayarVurus);
                        if(ucak.getDayaniklilik()<=0 && ucak.getSeviyePuani()<=10){
                            obus.setSeviyePuani(obus.getSeviyePuani()+10);
                        }
                        else if(ucak.getDayaniklilik()<=0 && ucak.getSeviyePuani()>10){
                            obus.setSeviyePuani(obus.getSeviyePuani()+ucak.getSeviyePuani());
                        }
                        System.out.println("ucak"+ucak.getSeviyePuani() + "obus"+obus.getSeviyePuani()+"\n");
                        kullanici.setSkor(ucak.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(obus.getSeviyePuani()+bilgisayar.getSkor());
                    }
                    else if (bilgisayarKart instanceof Ucak) {
                        Ucak ucak1 = (Ucak) bilgisayarKart;
                        int kullaniciVurus = ucak.Vurus();
                        ucak1.DurumGuncelle(kullaniciVurus);
                        //ucak1.setDayaniklilik(ucak1.getDayaniklilik() - kullaniciVurus);
                        if(ucak1.getDayaniklilik()<=0 && ucak1.getSeviyePuani()<=10){
                            ucak.setSeviyePuani(ucak.getSeviyePuani()+10);
                        }
                        else if(ucak1.getDayaniklilik()<=0 && ucak1.getSeviyePuani()>10){
                            ucak.setSeviyePuani(ucak1.getSeviyePuani()+ucak.getSeviyePuani());
                        }
                        int bilgisayarVurus = ucak1.Vurus();
                        ucak.DurumGuncelle(bilgisayarVurus);
                        //ucak.setDayaniklilik(ucak.getDayaniklilik() - bilgisayarVurus);
                        if(ucak.getDayaniklilik()<=0 && ucak.getSeviyePuani()<=10){
                            ucak1.setSeviyePuani(ucak1.getSeviyePuani()+10);
                        }
                        else if(ucak.getDayaniklilik()<=0 && ucak.getSeviyePuani()>10){
                            ucak1.setSeviyePuani(ucak1.getSeviyePuani()+ucak.getSeviyePuani());
                        }
                        System.out.println("ucak"+ucak.getSeviyePuani() +"ucakpc"+ ucak1.getSeviyePuani()+"\n");
                        kullanici.setSkor(kullanici.getSkor()+ucak.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(ucak1.getSeviyePuani()+bilgisayar.getSkor());
                    }
                    else if (bilgisayarKart instanceof KFS) {
                        KFS Kfs = (KFS) bilgisayarKart;
                        int kullaniciVurus = ucak.Vurus() + ucak.karaVurusAvantaji();
                        Kfs.DurumGuncelle(kullaniciVurus);
                        //Kfs.setDayaniklilik(obus.getDayaniklilik() - kullaniciVurus);
                        if(Kfs.getDayaniklilik()<=0 && Kfs.getSeviyePuani()<=10){
                            ucak.setSeviyePuani(ucak.getSeviyePuani()+10);
                        }
                        else if(Kfs.getDayaniklilik()<=0 && Kfs.getSeviyePuani()>10){
                            ucak.setSeviyePuani(Kfs.getSeviyePuani()+ucak.getSeviyePuani());
                        }
                        int bilgisayarVurus = Kfs.Vurus() +Kfs.havaVurusAvantaji;
                        ucak.DurumGuncelle(bilgisayarVurus);
                        //ucak.setDayaniklilik(ucak.getDayaniklilik() - bilgisayarVurus);
                        if(ucak.getDayaniklilik()<=0 && ucak.getSeviyePuani()<=10){
                            Kfs.setSeviyePuani(Kfs.getSeviyePuani()+10);
                        }
                        else if(ucak.getDayaniklilik()<=0 && ucak.getSeviyePuani()>10){
                            Kfs.setSeviyePuani(Kfs.getSeviyePuani()+ucak.getSeviyePuani());
                        }
                        System.out.println("ucak"+ucak.getSeviyePuani() + "Kfs"+Kfs.getSeviyePuani()+"\n");
                        kullanici.setSkor(ucak.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(Kfs.getSeviyePuani()+bilgisayar.getSkor());
                    }
                    else if (bilgisayarKart instanceof Sida) {
                        Sida sida = (Sida) bilgisayarKart;
                        int kullaniciVurus = ucak.Vurus() ;
                        sida.DurumGuncelle(kullaniciVurus);
                        //sida.setDayaniklilik(obus.getDayaniklilik() - kullaniciVurus);
                        if(sida.getDayaniklilik()<=0 && sida.getSeviyePuani()<=10){
                            ucak.setSeviyePuani(ucak.getSeviyePuani()+10);
                        }
                        else if(sida.getDayaniklilik()<=0 && sida.getSeviyePuani()>10){
                            ucak.setSeviyePuani(sida.getSeviyePuani()+ucak.getSeviyePuani());
                        }
                        int bilgisayarVurus = sida.Vurus() +sida.havaVurusAvantaji();
                        ucak.DurumGuncelle(bilgisayarVurus);
                        //ucak.setDayaniklilik(ucak.getDayaniklilik() - bilgisayarVurus);
                        if(ucak.getDayaniklilik()<=0 && ucak.getSeviyePuani()<=10){
                            sida.setSeviyePuani(sida.getSeviyePuani()+10);
                        }
                        else if(ucak.getDayaniklilik()<=0 && ucak.getSeviyePuani()>10){
                            sida.setSeviyePuani(sida.getSeviyePuani()+ucak.getSeviyePuani());
                        }
                        System.out.println("ucak"+ucak.getSeviyePuani() + "sida"+sida.getSeviyePuani()+"\n");
                        kullanici.setSkor(ucak.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(sida.getSeviyePuani()+bilgisayar.getSkor());
                    }
                    else if (bilgisayarKart instanceof Siha) {
                        Siha siha = (Siha) bilgisayarKart;
                        int kullaniciVurus = ucak.Vurus();
                        siha.DurumGuncelle(kullaniciVurus);
                        //siha.setDayaniklilik(obus.getDayaniklilik() - kullaniciVurus);
                        if(siha.getDayaniklilik()<=0 && siha.getSeviyePuani()<=10){
                            ucak.setSeviyePuani(ucak.getSeviyePuani()+10);
                        }
                        else if(siha.getDayaniklilik()<=0 && siha.getSeviyePuani()>10){
                            ucak.setSeviyePuani(siha.getSeviyePuani()+ucak.getSeviyePuani());
                        }
                        int bilgisayarVurus = siha.Vurus();
                        ucak.DurumGuncelle(bilgisayarVurus);
                        //ucak.setDayaniklilik(ucak.getDayaniklilik() - bilgisayarVurus);
                        if(ucak.getDayaniklilik()<=0 && ucak.getSeviyePuani()<=10){
                            siha.setSeviyePuani(siha.getSeviyePuani()+10);
                        }
                        else if(ucak.getDayaniklilik()<=0 && ucak.getSeviyePuani()>10){
                            siha.setSeviyePuani(siha.getSeviyePuani()+ucak.getSeviyePuani());
                        }
                        System.out.println("ucak"+ucak.getSeviyePuani() + "siha"+siha.getSeviyePuani()+"\n");
                        kullanici.setSkor(ucak.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(siha.getSeviyePuani()+bilgisayar.getSkor());
                    }
                }
                if (kullaniciKart instanceof Siha) {
                    Siha siha = (Siha) kullaniciKart;
            
                    if (bilgisayarKart instanceof Firkateyn) {
                        Firkateyn firkateyn = (Firkateyn) bilgisayarKart;
                        int kullaniciVurus = siha.Vurus() ;
                        firkateyn.DurumGuncelle(kullaniciVurus);
                        //firkateyn.setDayaniklilik(firkateyn.getDayaniklilik() - kullaniciVurus);
                        if(firkateyn.getDayaniklilik()<=0 && firkateyn.getSeviyePuani()<=10){
                            siha.setSeviyePuani(siha.getSeviyePuani()+10);
                        }
                        else if(firkateyn.getDayaniklilik()<=0 && firkateyn.getSeviyePuani()>10){
                            siha.setSeviyePuani(firkateyn.getSeviyePuani()+siha.getSeviyePuani());
                        }                     
                        int bilgisayarVurus = firkateyn.Vurus() ;   
                        siha.DurumGuncelle(bilgisayarVurus);
                        //siha.setDayaniklilik(siha.getDayaniklilik() - bilgisayarVurus);
                        if(siha.getDayaniklilik()<=0 && siha.getSeviyePuani()<=10){
                            firkateyn.setSeviyePuani(firkateyn.getDayaniklilik()+10);
                        }
                        else if(siha.getDayaniklilik()<=0 && siha.getSeviyePuani()>10){
                            firkateyn.setSeviyePuani(firkateyn.getSeviyePuani()+siha.getSeviyePuani());
                        }
                        System.out.println("siha" + siha.getSeviyePuani() +"firkateyn"+ firkateyn.getSeviyePuani()+"\n"); 
                        
                        kullanici.setSkor(siha.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(firkateyn.getSeviyePuani()+bilgisayar.getSkor());
                    } 
                    else if (bilgisayarKart instanceof Obus) {
                        Obus obus = (Obus) bilgisayarKart;
                        int kullaniciVurus = siha.Vurus() + siha.karaVurusAvantaji();
                        obus.DurumGuncelle(kullaniciVurus);
                        //obus.setDayaniklilik(obus.getDayaniklilik() - kullaniciVurus);
                        if(obus.getDayaniklilik()<=0 && obus.getSeviyePuani()<=10){
                            siha.setSeviyePuani(siha.getSeviyePuani()+10);
                        }
                        else if(obus.getDayaniklilik()<=0 && obus.getSeviyePuani()>10){
                            siha.setSeviyePuani(obus.getSeviyePuani()+siha.getSeviyePuani());
                        }
                        int bilgisayarVurus = obus.Vurus();
                        siha.DurumGuncelle(bilgisayarVurus);
                        //siha.setDayaniklilik(siha.getDayaniklilik() - bilgisayarVurus);
                        if(siha.getDayaniklilik()<=0 && siha.getSeviyePuani()<=10){
                            obus.setSeviyePuani(obus.getSeviyePuani()+10);
                        }
                        else if(siha.getDayaniklilik()<=0 && siha.getSeviyePuani()>10){
                            obus.setSeviyePuani(obus.getSeviyePuani()+siha.getSeviyePuani());
                        }
                        System.out.println("siha"+siha.getSeviyePuani() + "obus"+obus.getSeviyePuani()+"\n");
                        kullanici.setSkor(siha.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(obus.getSeviyePuani()+bilgisayar.getSkor());
                    }
                    else if (bilgisayarKart instanceof Ucak) {
                        Ucak ucak1 = (Ucak) bilgisayarKart;
                        int kullaniciVurus = siha.Vurus();
                        ucak1.DurumGuncelle(kullaniciVurus);
                        //ucak1.setDayaniklilik(ucak1.getDayaniklilik() - kullaniciVurus);
                        if(ucak1.getDayaniklilik()<=0 && ucak1.getSeviyePuani()<=10){
                            siha.setSeviyePuani(siha.getSeviyePuani()+10);
                        }
                        else if(ucak1.getDayaniklilik()<=0 && ucak1.getSeviyePuani()>10){
                            siha.setSeviyePuani(ucak1.getSeviyePuani()+siha.getSeviyePuani());
                        }
                        int bilgisayarVurus = ucak1.Vurus();
                        siha.DurumGuncelle(bilgisayarVurus);
                        //siha.setDayaniklilik(siha.getDayaniklilik() - bilgisayarVurus);
                        if(siha.getDayaniklilik()<=0 && siha.getSeviyePuani()<=10){
                            ucak1.setSeviyePuani(ucak1.getSeviyePuani()+10);
                        }
                        else if(siha.getDayaniklilik()<=0 && siha.getSeviyePuani()>10){
                            ucak1.setSeviyePuani(ucak1.getSeviyePuani()+siha.getSeviyePuani());
                        }
                        System.out.println("siha"+siha.getSeviyePuani() +"ucak"+ ucak1.getSeviyePuani()+"\n");
                        kullanici.setSkor(kullanici.getSkor()+siha.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(ucak1.getSeviyePuani()+bilgisayar.getSkor());
                    }
                    else if (bilgisayarKart instanceof KFS) {
                        KFS Kfs = (KFS) bilgisayarKart;
                        int kullaniciVurus = siha.Vurus() + siha.karaVurusAvantaji();
                        Kfs.DurumGuncelle(kullaniciVurus);
                        //Kfs.setDayaniklilik(obus.getDayaniklilik() - kullaniciVurus);
                        if(Kfs.getDayaniklilik()<=0 && Kfs.getSeviyePuani()<=10){
                            siha.setSeviyePuani(siha.getSeviyePuani()+10);
                        }
                        else if(Kfs.getDayaniklilik()<=0 && Kfs.getSeviyePuani()>10){
                            siha.setSeviyePuani(Kfs.getSeviyePuani()+siha.getSeviyePuani());
                        }
                        int bilgisayarVurus = Kfs.Vurus() +Kfs.havaVurusAvantaji;
                        siha.DurumGuncelle(bilgisayarVurus);
                        //siha.setDayaniklilik(siha.getDayaniklilik() - bilgisayarVurus);
                        if(siha.getDayaniklilik()<=0 && siha.getSeviyePuani()<=10){
                            Kfs.setSeviyePuani(Kfs.getSeviyePuani()+10);
                        }
                        else if(siha.getDayaniklilik()<=0 && siha.getSeviyePuani()>10){
                            Kfs.setSeviyePuani(Kfs.getSeviyePuani()+siha.getSeviyePuani());
                        }
                        System.out.println("siha"+siha.getSeviyePuani() + "Kfs"+Kfs.getSeviyePuani()+"\n");
                        kullanici.setSkor(siha.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(Kfs.getSeviyePuani()+bilgisayar.getSkor());
                    }
                    else if (bilgisayarKart instanceof Sida) {
                        Sida sida = (Sida) bilgisayarKart;
                        int kullaniciVurus = siha.Vurus() ;
                        sida.DurumGuncelle(kullaniciVurus);
                        //sida.setDayaniklilik(obus.getDayaniklilik() - kullaniciVurus);
                        if(sida.getDayaniklilik()<=0 && sida.getSeviyePuani()<=10){
                            siha.setSeviyePuani(siha.getSeviyePuani()+10);
                        }
                        else if(sida.getDayaniklilik()<=0 && sida.getSeviyePuani()>10){
                            siha.setSeviyePuani(sida.getSeviyePuani()+siha.getSeviyePuani());
                        }
                        int bilgisayarVurus = sida.Vurus() +sida.havaVurusAvantaji();
                        siha.DurumGuncelle(bilgisayarVurus);
                        //siha.setDayaniklilik(siha.getDayaniklilik() - bilgisayarVurus);
                        if(siha.getDayaniklilik()<=0 && siha.getSeviyePuani()<=10){
                            sida.setSeviyePuani(sida.getSeviyePuani()+10);
                        }
                        else if(siha.getDayaniklilik()<=0 && siha.getSeviyePuani()>10){
                            sida.setSeviyePuani(sida.getSeviyePuani()+siha.getSeviyePuani());
                        }
                        System.out.println("siha"+siha.getSeviyePuani() + "sida"+sida.getSeviyePuani()+"\n");
                        kullanici.setSkor(siha.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(sida.getSeviyePuani()+bilgisayar.getSkor());
                    }
                    else if (bilgisayarKart instanceof Siha) {
                        Siha siha1 = (Siha) bilgisayarKart;
                        int kullaniciVurus = siha.Vurus();
                        siha1.DurumGuncelle(kullaniciVurus);
                        //siha.setDayaniklilik(obus.getDayaniklilik() - kullaniciVurus);
                        if(siha1.getDayaniklilik()<=0 && siha1.getSeviyePuani()<=10){
                            siha.setSeviyePuani(siha.getSeviyePuani()+10);
                        }
                        else if(siha1.getDayaniklilik()<=0 && siha1.getSeviyePuani()>10){
                            siha.setSeviyePuani(siha1.getSeviyePuani()+siha.getSeviyePuani());
                        }
                        int bilgisayarVurus = siha1.Vurus();
                        siha.DurumGuncelle(bilgisayarVurus);
                        //siha.setDayaniklilik(siha.getDayaniklilik() - bilgisayarVurus);
                        if(siha.getDayaniklilik()<=0 && siha.getSeviyePuani()<=10){
                            siha1.setSeviyePuani(siha1.getSeviyePuani()+10);
                        }
                        else if(siha.getDayaniklilik()<=0 && siha.getSeviyePuani()>10){
                            siha1.setSeviyePuani(siha1.getSeviyePuani()+siha.getSeviyePuani());
                        }
                        System.out.println("siha"+siha.getSeviyePuani() + "siha1"+siha1.getSeviyePuani()+"\n");
                        kullanici.setSkor(siha.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(siha1.getSeviyePuani()+bilgisayar.getSkor());
                    }
                }
                if (kullaniciKart instanceof Obus) {
                    Obus obus = (Obus) kullaniciKart;
            
                    if (bilgisayarKart instanceof Firkateyn) {
                        Firkateyn firkateyn = (Firkateyn) bilgisayarKart;
                        int kullaniciVurus = obus.Vurus() + obus.denizVurusAvantaji();
                        firkateyn.DurumGuncelle(kullaniciVurus);
                        //firkateyn.setDayaniklilik(firkateyn.getDayaniklilik() - kullaniciVurus);
                        if(firkateyn.getDayaniklilik()<=0 && firkateyn.getSeviyePuani()<=10){
                            obus.setSeviyePuani(obus.getSeviyePuani()+10);
                        }
                        else if(firkateyn.getDayaniklilik()<=0 && firkateyn.getSeviyePuani()>10){
                            obus.setSeviyePuani(firkateyn.getSeviyePuani()+obus.getSeviyePuani());
                        }
                        int bilgisayarVurus = firkateyn.Vurus() ;
                        obus.DurumGuncelle(bilgisayarVurus);
                        //obus.setDayaniklilik(obus.getDayaniklilik() - bilgisayarVurus);
                        if(obus.getDayaniklilik()<=0 && obus.getSeviyePuani()<=10){
                            firkateyn.setSeviyePuani(firkateyn.getSeviyePuani()+10);
                        }
                        else if(obus.getDayaniklilik()<=0 && obus.getSeviyePuani()>10){
                            firkateyn.setSeviyePuani(firkateyn.getSeviyePuani()+obus.getSeviyePuani());
                        }
                        System.out.println( "obus" + obus.getSeviyePuani() +"firkateyn"+ firkateyn.getSeviyePuani()+"\n");
                        kullanici.setSkor(obus.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(firkateyn.getSeviyePuani()+bilgisayar.getSkor());
                    } 
                    else if (bilgisayarKart instanceof Ucak) {
                        Ucak ucak = (Ucak) bilgisayarKart;
                        int kullaniciVurus = obus.Vurus();
                        ucak.DurumGuncelle(kullaniciVurus);
                        //ucak.setDayaniklilik(ucak.getDayaniklilik() - kullaniciVurus);
                        if(ucak.getDayaniklilik()<=0 && ucak.getSeviyePuani()<=10){
                            obus.setSeviyePuani(obus.getSeviyePuani()+10);
                        }
                        else if(ucak.getDayaniklilik()<=0 && ucak.getSeviyePuani()>0){
                            obus.setSeviyePuani(obus.getSeviyePuani()+ucak.getSeviyePuani());
                        }
                        int bilgisayarVurus = ucak.Vurus()+ucak.karaVurusAvantaji();
                        obus.DurumGuncelle(bilgisayarVurus);
                        //obus.setDayaniklilik(obus.getDayaniklilik() - bilgisayarVurus);
                        if(obus.getDayaniklilik()<=0 && obus.getSeviyePuani()<=10){
                            ucak.setSeviyePuani(ucak.getSeviyePuani()+10);
                        }
                        else if(obus.getDayaniklilik()<=0 && obus.getSeviyePuani()>0){
                            ucak.setSeviyePuani(obus.getSeviyePuani()+ucak.getSeviyePuani());
                        }
                        System.out.println("obus"+obus.getSeviyePuani() +"ucak"+ ucak.getSeviyePuani()+"\n");
                        kullanici.setSkor(obus.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(ucak.getSeviyePuani()+bilgisayar.getSkor());
                    }
                    else if (bilgisayarKart instanceof Obus) {
                        Obus obus1 = (Obus) bilgisayarKart;
                        int kullaniciVurus = obus.Vurus();
                        obus1.DurumGuncelle(kullaniciVurus);
                        //obus1.setDayaniklilik(obus1.getDayaniklilik() - kullaniciVurus);
                        if(obus1.getDayaniklilik()<=0 && obus1.getSeviyePuani()<=10){
                            obus.setSeviyePuani(obus.getSeviyePuani()+10);
                        }
                        else if(obus1.getDayaniklilik()<=0 && obus1.getSeviyePuani()>10){
                            obus.setSeviyePuani(obus1.getSeviyePuani()+obus.getSeviyePuani());
                        }
                        int bilgisayarVurus = obus1.Vurus() ;
                        obus.DurumGuncelle(bilgisayarVurus);
                        //obus.setDayaniklilik(obus.getDayaniklilik() - bilgisayarVurus);
                        if(obus.getDayaniklilik()<=0 && obus.getSeviyePuani()<=10){
                            obus1.setSeviyePuani(obus1.getSeviyePuani()+10);
                        }
                        else if(obus.getDayaniklilik()<=0 && obus.getSeviyePuani()>10){
                            obus1.setSeviyePuani(obus1.getSeviyePuani()+obus.getSeviyePuani());
                        }
                        System.out.println("obus"+ obus.getSeviyePuani() +"obuspc"+ obus1.getSeviyePuani()+"\n");
                        kullanici.setSkor(obus.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(obus1.getSeviyePuani()+bilgisayar.getSkor());
                    }
                    else if (bilgisayarKart instanceof KFS) {
                        KFS Kfs = (KFS) bilgisayarKart;
                        int kullaniciVurus = obus.Vurus();
                        Kfs.DurumGuncelle(kullaniciVurus);
                        //Kfs.setDayaniklilik(obus.getDayaniklilik() - kullaniciVurus);
                        if(Kfs.getDayaniklilik()<=0 && Kfs.getSeviyePuani()<=10){
                            obus.setSeviyePuani(obus.getSeviyePuani()+10);
                        }
                        else if(Kfs.getDayaniklilik()<=0 && Kfs.getSeviyePuani()>10){
                            obus.setSeviyePuani(Kfs.getSeviyePuani()+obus.getSeviyePuani());
                        }
                        int bilgisayarVurus = Kfs.Vurus();
                        obus.DurumGuncelle(bilgisayarVurus);
                        //obus.setDayaniklilik(obus.getDayaniklilik() - bilgisayarVurus);
                        if(obus.getDayaniklilik()<=0 && obus.getSeviyePuani()<=10){
                            Kfs.setSeviyePuani(Kfs.getSeviyePuani()+10);
                        }
                        else if(obus.getDayaniklilik()<=0 && obus.getSeviyePuani()>10){
                            Kfs.setSeviyePuani(Kfs.getSeviyePuani()+obus.getSeviyePuani());
                        }
                        System.out.println("obus"+obus.getSeviyePuani() + "Kfs"+Kfs.getSeviyePuani()+"\n");
                        kullanici.setSkor(obus.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(Kfs.getSeviyePuani()+bilgisayar.getSkor());
                    }
                    else if (bilgisayarKart instanceof Sida) {
                        Sida sida = (Sida) bilgisayarKart;
                        int kullaniciVurus = obus.Vurus() + obus.denizVurusAvantaji();
                        sida.DurumGuncelle(kullaniciVurus);
                        //sida.setDayaniklilik(obus.getDayaniklilik() - kullaniciVurus);
                        if(sida.getDayaniklilik()<=0 && sida.getSeviyePuani()<=10){
                            obus.setSeviyePuani(obus.getSeviyePuani()+10);
                        }
                        else if(sida.getDayaniklilik()<=0 && sida.getSeviyePuani()>10){
                            obus.setSeviyePuani(sida.getSeviyePuani()+obus.getSeviyePuani());
                        }
                        int bilgisayarVurus = sida.Vurus() + sida.karaVurusAvantaji;
                        obus.DurumGuncelle(bilgisayarVurus);
                        //obus.setDayaniklilik(obus.getDayaniklilik() - bilgisayarVurus);
                        if(obus.getDayaniklilik()<=0 && obus.getSeviyePuani()<=10){
                            sida.setSeviyePuani(sida.getSeviyePuani()+10);
                        }
                        else if(obus.getDayaniklilik()<=0 && obus.getSeviyePuani()>10){
                            sida.setSeviyePuani(sida.getSeviyePuani()+obus.getSeviyePuani());
                        }
                        System.out.println("obus"+obus.getSeviyePuani() + "sida"+sida.getSeviyePuani()+"\n");
                        kullanici.setSkor(obus.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(sida.getSeviyePuani()+bilgisayar.getSkor());
                    }
                    else if (bilgisayarKart instanceof Siha) {
                        Siha siha = (Siha) bilgisayarKart;
                        int kullaniciVurus = obus.Vurus();
                        siha.DurumGuncelle(kullaniciVurus);
                        //siha.setDayaniklilik(obus.getDayaniklilik() - kullaniciVurus);
                        if(siha.getDayaniklilik()<=0 && siha.getSeviyePuani()<=10){
                            obus.setSeviyePuani(obus.getSeviyePuani()+10);
                        }
                        else if(siha.getDayaniklilik()<=0 && siha.getSeviyePuani()>10){
                            obus.setSeviyePuani(siha.getSeviyePuani()+obus.getSeviyePuani());
                        }
                        int bilgisayarVurus = siha.Vurus() + siha.karaVurusAvantaji();
                        obus.DurumGuncelle(bilgisayarVurus);
                        //obus.setDayaniklilik(ucak.getDayaniklilik() - bilgisayarVurus);
                        if(obus.getDayaniklilik()<=0 && obus.getSeviyePuani()<=10){
                            siha.setSeviyePuani(siha.getSeviyePuani()+10);
                        }
                        else if(obus.getDayaniklilik()<=0 && obus.getSeviyePuani()>10){
                            siha.setSeviyePuani(obus.getSeviyePuani()+obus.getSeviyePuani());
                        }
                        System.out.println("obus"+obus.getSeviyePuani() + "siha"+siha.getSeviyePuani()+"\n");
                        kullanici.setSkor(obus.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(siha.getSeviyePuani()+bilgisayar.getSkor());
                    }
                }
                if (kullaniciKart instanceof KFS) {
                    KFS kfs = (KFS) kullaniciKart;
            
                    if (bilgisayarKart instanceof Firkateyn) {
                        Firkateyn firkateyn = (Firkateyn) bilgisayarKart;
                        int kullaniciVurus = kfs.Vurus() + kfs.denizVurusAvantaji();
                        firkateyn.DurumGuncelle(kullaniciVurus);
                        //firkateyn.setDayaniklilik(firkateyn.getDayaniklilik() - kullaniciVurus);
                        if(firkateyn.getDayaniklilik()<=0 && firkateyn.getSeviyePuani()<=10){
                            kfs.setSeviyePuani(kfs.getSeviyePuani()+10);
                        }
                        else if(firkateyn.getDayaniklilik()<=0 && firkateyn.getSeviyePuani()>10){
                            kfs.setSeviyePuani(firkateyn.getSeviyePuani()+kfs.getSeviyePuani());
                        }
                        int bilgisayarVurus = firkateyn.Vurus() ;
                        kfs.DurumGuncelle(bilgisayarVurus);
                        //kfs.setDayaniklilik(kfs.getDayaniklilik() - bilgisayarVurus);
                        if(kfs.getDayaniklilik()<=0 && kfs.getSeviyePuani()<=10){
                            firkateyn.setSeviyePuani(firkateyn.getSeviyePuani()+10);
                        }
                        else if(kfs.getDayaniklilik()<=0 && kfs.getSeviyePuani()>10){
                            firkateyn.setSeviyePuani(firkateyn.getSeviyePuani()+kfs.getSeviyePuani());
                        }
                        System.out.println( "kfs" + kfs.getSeviyePuani() +"firkateyn"+ firkateyn.getSeviyePuani()+"\n");
                        kullanici.setSkor(kfs.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(firkateyn.getSeviyePuani()+bilgisayar.getSkor());
                    } 
                    else if (bilgisayarKart instanceof Ucak) {
                        Ucak ucak = (Ucak) bilgisayarKart;
                        int kullaniciVurus = kfs.Vurus();
                        ucak.DurumGuncelle(kullaniciVurus);
                        //ucak.setDayaniklilik(ucak.getDayaniklilik() - kullaniciVurus);
                        if(ucak.getDayaniklilik()<=0 && ucak.getSeviyePuani()<=10){
                            kfs.setSeviyePuani(kfs.getSeviyePuani()+10);
                        }
                        else if(ucak.getDayaniklilik()<=0 && ucak.getSeviyePuani()>0){
                            kfs.setSeviyePuani(kfs.getSeviyePuani()+ucak.getSeviyePuani());
                        }
                        int bilgisayarVurus = ucak.Vurus()+ucak.karaVurusAvantaji();
                        kfs.DurumGuncelle(bilgisayarVurus);
                        //kfs.setDayaniklilik(kfs.getDayaniklilik() - bilgisayarVurus);
                        if(kfs.getDayaniklilik()<=0 && kfs.getSeviyePuani()<=10){
                            ucak.setSeviyePuani(ucak.getSeviyePuani()+10);
                        }
                        else if(kfs.getDayaniklilik()<=0 && kfs.getSeviyePuani()>0){
                            ucak.setSeviyePuani(kfs.getSeviyePuani()+ucak.getSeviyePuani());
                        }
                        System.out.println("kfs"+kfs.getSeviyePuani() +"ucak"+ ucak.getSeviyePuani()+"\n");
                        kullanici.setSkor(kfs.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(ucak.getSeviyePuani()+bilgisayar.getSkor());
                    }
                    else if (bilgisayarKart instanceof Obus) {
                        Obus obus = (Obus) bilgisayarKart;
                        int kullaniciVurus = kfs.Vurus();
                        obus.DurumGuncelle(kullaniciVurus);
                        //obus.setDayaniklilik(obus.getDayaniklilik() - kullaniciVurus);
                        if(obus.getDayaniklilik()<=0 && obus.getSeviyePuani()<=10){
                            kfs.setSeviyePuani(kfs.getSeviyePuani()+10);
                        }
                        else if(obus.getDayaniklilik()<=0 && obus.getSeviyePuani()>10){
                            kfs.setSeviyePuani(obus.getSeviyePuani()+kfs.getSeviyePuani());
                        }
                        int bilgisayarVurus = obus.Vurus() ;
                        kfs.DurumGuncelle(bilgisayarVurus);
                        //kfs.setDayaniklilik(kfs.getDayaniklilik() - bilgisayarVurus);
                        if(kfs.getDayaniklilik()<=0 && kfs.getSeviyePuani()<=10){
                            obus.setSeviyePuani(obus.getSeviyePuani()+10);
                        }
                        else if(kfs.getDayaniklilik()<=0 && kfs.getSeviyePuani()>10){
                            obus.setSeviyePuani(obus.getSeviyePuani()+kfs.getSeviyePuani());
                        }
                        System.out.println("kfs"+ kfs.getSeviyePuani() +"kfs"+ obus.getSeviyePuani()+"\n");
                        kullanici.setSkor(kfs.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(obus.getSeviyePuani()+bilgisayar.getSkor());
                    }
                    else if (bilgisayarKart instanceof KFS) {
                        KFS Kfs1 = (KFS) bilgisayarKart;
                        int kullaniciVurus = kfs.Vurus();
                        Kfs1.DurumGuncelle(kullaniciVurus);
                        //Kfs1.setDayaniklilik(kfs.getDayaniklilik() - kullaniciVurus);
                        if(Kfs1.getDayaniklilik()<=0 && Kfs1.getSeviyePuani()<=10){
                            kfs.setSeviyePuani(kfs.getSeviyePuani()+10);
                        }
                        else if(Kfs1.getDayaniklilik()<=0 && Kfs1.getSeviyePuani()>10){
                            kfs.setSeviyePuani(Kfs1.getSeviyePuani()+kfs.getSeviyePuani());
                        }
                        int bilgisayarVurus = Kfs1.Vurus();
                        kfs.DurumGuncelle(bilgisayarVurus);
                        //kfs.setDayaniklilik(kfs.getDayaniklilik() - bilgisayarVurus);
                        if(kfs.getDayaniklilik()<=0 && kfs.getSeviyePuani()<=10){
                            Kfs1.setSeviyePuani(Kfs1.getSeviyePuani()+10);
                        }
                        else if(kfs.getDayaniklilik()<=0 && kfs.getSeviyePuani()>10){
                            Kfs1.setSeviyePuani(Kfs1.getSeviyePuani()+kfs.getSeviyePuani());
                        }
                        System.out.println("kfs"+kfs.getSeviyePuani() + "Kfs1"+Kfs1.getSeviyePuani()+"\n");
                        kullanici.setSkor(kfs.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(Kfs1.getSeviyePuani()+bilgisayar.getSkor());
                    }
                    else if (bilgisayarKart instanceof Sida) {
                        Sida sida = (Sida) bilgisayarKart;
                        int kullaniciVurus = kfs.Vurus() + kfs.denizVurusAvantaji();
                        sida.DurumGuncelle(kullaniciVurus);
                        //sida.setDayaniklilik(kfs.getDayaniklilik() - kullaniciVurus);
                        if(sida.getDayaniklilik()<=0 && sida.getSeviyePuani()<=10){
                            kfs.setSeviyePuani(kfs.getSeviyePuani()+10);
                        }
                        else if(sida.getDayaniklilik()<=0 && sida.getSeviyePuani()>10){
                            kfs.setSeviyePuani(sida.getSeviyePuani()+kfs.getSeviyePuani());
                        }
                        int bilgisayarVurus = sida.Vurus() + sida.karaVurusAvantaji;
                        kfs.DurumGuncelle(bilgisayarVurus);
                        //kfs.setDayaniklilik(kfs.getDayaniklilik() - bilgisayarVurus);
                        if(kfs.getDayaniklilik()<=0 && kfs.getSeviyePuani()<=10){
                            sida.setSeviyePuani(sida.getSeviyePuani()+10);
                        }
                        else if(kfs.getDayaniklilik()<=0 && kfs.getSeviyePuani()>10){
                            sida.setSeviyePuani(sida.getSeviyePuani()+kfs.getSeviyePuani());
                        }
                        System.out.println("kfs"+kfs.getSeviyePuani() + "sida"+sida.getSeviyePuani()+"\n");
                        kullanici.setSkor(kfs.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(sida.getSeviyePuani()+bilgisayar.getSkor());
                    }
                    else if (bilgisayarKart instanceof Siha) {
                        Siha siha = (Siha) bilgisayarKart;
                        int kullaniciVurus = kfs.Vurus();
                        siha.DurumGuncelle(kullaniciVurus);
                        //siha.setDayaniklilik(kfs.getDayaniklilik() - kullaniciVurus);
                        if(siha.getDayaniklilik()<=0 && siha.getSeviyePuani()<=10){
                            kfs.setSeviyePuani(kfs.getSeviyePuani()+10);
                        }
                        else if(siha.getDayaniklilik()<=0 && siha.getSeviyePuani()>10){
                            kfs.setSeviyePuani(siha.getSeviyePuani()+kfs.getSeviyePuani());
                        }
                        int bilgisayarVurus = siha.Vurus() + siha.karaVurusAvantaji();
                        kfs.DurumGuncelle(bilgisayarVurus);
                        //kfs.setDayaniklilik(ucak.getDayaniklilik() - bilgisayarVurus);
                        if(kfs.getDayaniklilik()<=0 && kfs.getSeviyePuani()<=10){
                            siha.setSeviyePuani(siha.getSeviyePuani()+10);
                        }
                        else if(kfs.getDayaniklilik()<=0 && kfs.getSeviyePuani()>10){
                            siha.setSeviyePuani(kfs.getSeviyePuani()+kfs.getSeviyePuani());
                        }
                        System.out.println("kfs"+kfs.getSeviyePuani() + "siha"+siha.getSeviyePuani()+"\n");
                        kullanici.setSkor(kfs.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(siha.getSeviyePuani()+bilgisayar.getSkor());
                    }
                }
                if (kullaniciKart instanceof Firkateyn) {
                    Firkateyn firkateyn = (Firkateyn) kullaniciKart;
            
                    if (bilgisayarKart instanceof Ucak) {
                        Ucak ucak = (Ucak) bilgisayarKart;
                        int kullaniciVurus = firkateyn.Vurus() + firkateyn.havaVurusAvantaji();
                        ucak.DurumGuncelle(kullaniciVurus);  
                       //ucak.setDayaniklilik(ucak.getDayaniklilik() - kullaniciVurus);
                        if(ucak.getDayaniklilik()<=0 && ucak.getSeviyePuani()<=10){
                            firkateyn.setSeviyePuani(firkateyn.getSeviyePuani()+10);
                        }
                        else if(ucak.getDayaniklilik()<=0 && ucak.getSeviyePuani()>0){
                            firkateyn.setSeviyePuani(firkateyn.getSeviyePuani()+ucak.getSeviyePuani());
                        }
                        int bilgisayarVurus = ucak.Vurus();
                        firkateyn.DurumGuncelle(bilgisayarVurus);
                        //firkateyn.setDayaniklilik(firkateyn.getDayaniklilik() - bilgisayarVurus);
                        if(firkateyn.getDayaniklilik()<=0 && firkateyn.getSeviyePuani()<=10){
                            ucak.setSeviyePuani(ucak.getSeviyePuani()+10);
                        }
                        else if(firkateyn.getDayaniklilik()<=0 && firkateyn.getSeviyePuani()>0){
                            ucak.setSeviyePuani(firkateyn.getSeviyePuani()+ucak.getSeviyePuani());
                        }
                        System.out.println("firkateyn" +firkateyn.getSeviyePuani() + "ucak"+ucak.getSeviyePuani()+"\n");
                        kullanici.setSkor(firkateyn.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(ucak.getSeviyePuani()+bilgisayar.getSkor());
                    }
                    else if (bilgisayarKart instanceof Obus) {
                        Obus obus = (Obus) bilgisayarKart;
                        int kullaniciVurus = firkateyn.Vurus();
                        obus.DurumGuncelle(kullaniciVurus);
                        //obus.setDayaniklilik(obus.getDayaniklilik() - kullaniciVurus);
                        if(obus.getDayaniklilik()<=0 && obus.getSeviyePuani()<=10){
                            firkateyn.setSeviyePuani(firkateyn.getSeviyePuani()+10);
                        }
                        else if(obus.getDayaniklilik()<=0 && obus.getSeviyePuani()>10){
                            firkateyn.setSeviyePuani(obus.getSeviyePuani()+firkateyn.getSeviyePuani());
                        }
                        int bilgisayarVurus = obus.Vurus()+obus.denizVurusAvantaji();
                        firkateyn.DurumGuncelle(bilgisayarVurus);
                        //firkateyn.setDayaniklilik(firkateyn.getDayaniklilik() - bilgisayarVurus);
                        if(firkateyn.getDayaniklilik()<=0 && firkateyn.getSeviyePuani()<=10){
                            obus.setSeviyePuani(obus.getSeviyePuani()+10);
                        }
                        else if(firkateyn.getDayaniklilik()<=0 && firkateyn.getSeviyePuani()>10){
                            obus.setSeviyePuani(obus.getSeviyePuani()+firkateyn.getSeviyePuani());
                        }
                        System.out.println("firkateyn"+firkateyn.getSeviyePuani() + "obus"+obus.getSeviyePuani()+"\n");
                        kullanici.setSkor(firkateyn.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(obus.getSeviyePuani()+bilgisayar.getSkor());
                    }
                    else if (bilgisayarKart instanceof Firkateyn) {
                        Firkateyn firkateyn1 = (Firkateyn) bilgisayarKart;
                        int kullaniciVurus = firkateyn.Vurus();
                        firkateyn1.DurumGuncelle(kullaniciVurus);
                        //firkateyn1.setDayaniklilik(firkateyn1.getDayaniklilik() - kullaniciVurus);
                        if(firkateyn1.getDayaniklilik()<=0 && firkateyn1.getSeviyePuani()<=10){
                            firkateyn.setSeviyePuani(firkateyn.getSeviyePuani()+10);
                        }
                        else if(firkateyn1.getDayaniklilik()<=0 && firkateyn1.getSeviyePuani()>10){
                            firkateyn.setSeviyePuani(firkateyn1.getSeviyePuani()+firkateyn.getSeviyePuani());
                        }
                        int bilgisayarVurus = firkateyn1.Vurus() ;
                        firkateyn.DurumGuncelle(bilgisayarVurus);
                        //firkateyn.setDayaniklilik(firkateyn.getDayaniklilik() - bilgisayarVurus);
                        if(firkateyn.getDayaniklilik()<=0 && firkateyn.getSeviyePuani()<=10){
                            firkateyn1.setSeviyePuani(firkateyn1.getSeviyePuani()+10);
                        }
                        else if(firkateyn.getDayaniklilik()<=0 && firkateyn.getSeviyePuani()>10){
                            firkateyn1.setSeviyePuani(firkateyn1.getSeviyePuani()+firkateyn.getSeviyePuani());
                        }
                        System.out.println(" firkateyn"+firkateyn.getSeviyePuani() +"firkateyn1"+ firkateyn1.getSeviyePuani()+"\n");
                        kullanici.setSkor(firkateyn.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(firkateyn1.getSeviyePuani()+bilgisayar.getSkor());
                    }
                    else if (bilgisayarKart instanceof KFS) {
                        KFS Kfs = (KFS) bilgisayarKart;
                        int kullaniciVurus = firkateyn.Vurus();
                        Kfs.DurumGuncelle(kullaniciVurus);
                        //Kfs.setDayaniklilik(firkateyn.getDayaniklilik() - kullaniciVurus);
                        if(Kfs.getDayaniklilik()<=0 && Kfs.getSeviyePuani()<=10){
                            firkateyn.setSeviyePuani(firkateyn.getSeviyePuani()+10);
                        }
                        else if(Kfs.getDayaniklilik()<=0 && Kfs.getSeviyePuani()>10){
                            firkateyn.setSeviyePuani(Kfs.getSeviyePuani()+firkateyn.getSeviyePuani());
                        }
                        int bilgisayarVurus = Kfs.Vurus() + Kfs.denizVurusAvantaji();
                        firkateyn.DurumGuncelle(bilgisayarVurus);
                        //firkateyn.setDayaniklilik(firkateyn.getDayaniklilik() - bilgisayarVurus);
                        if(firkateyn.getDayaniklilik()<=0 && firkateyn.getSeviyePuani()<=10){
                            Kfs.setSeviyePuani(Kfs.getSeviyePuani()+10);
                        }
                        else if(firkateyn.getDayaniklilik()<=0 && firkateyn.getSeviyePuani()>10){
                            Kfs.setSeviyePuani(Kfs.getSeviyePuani()+firkateyn.getSeviyePuani());
                        }
                        System.out.println("firkateyn"+firkateyn.getSeviyePuani() + "Kfs"+Kfs.getSeviyePuani()+"\n");
                        kullanici.setSkor(firkateyn.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(Kfs.getSeviyePuani()+bilgisayar.getSkor());
                    }
                    else if (bilgisayarKart instanceof Sida) {
                        Sida sida = (Sida) bilgisayarKart;
                        int kullaniciVurus = firkateyn.Vurus();
                        sida.DurumGuncelle(kullaniciVurus);
                        //sida.setDayaniklilik(firkateyn.getDayaniklilik() - kullaniciVurus);
                        if(sida.getDayaniklilik()<=0 && sida.getSeviyePuani()<=10){
                            firkateyn.setSeviyePuani(firkateyn.getSeviyePuani()+10);
                        }
                        else if(sida.getDayaniklilik()<=0 && sida.getSeviyePuani()>10){
                            firkateyn.setSeviyePuani(sida.getSeviyePuani()+firkateyn.getSeviyePuani());
                        }
                        int bilgisayarVurus = sida.Vurus();
                        firkateyn.DurumGuncelle(bilgisayarVurus);
                        //firkateyn.setDayaniklilik(obus.getDayaniklilik() - bilgisayarVurus);
                        if(firkateyn.getDayaniklilik()<=0 && firkateyn.getSeviyePuani()<=10){
                            sida.setSeviyePuani(sida.getSeviyePuani()+10);
                        }
                        else if(firkateyn.getDayaniklilik()<=0 && firkateyn.getSeviyePuani()>10){
                            sida.setSeviyePuani(sida.getSeviyePuani()+firkateyn.getSeviyePuani());
                        }
                        System.out.println("firkateyn"+firkateyn.getSeviyePuani() + "sida"+sida.getSeviyePuani()+"\n");
                        kullanici.setSkor(firkateyn.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(sida.getSeviyePuani()+bilgisayar.getSkor());
                    }
                    else if (bilgisayarKart instanceof Siha) {
                        Siha siha = (Siha) bilgisayarKart;
                        int kullaniciVurus = firkateyn.Vurus() + firkateyn.havaVurusAvantaji();
                        siha.DurumGuncelle(kullaniciVurus);
                        //siha.setDayaniklilik(firkateyn.getDayaniklilik() - kullaniciVurus);
                        if(siha.getDayaniklilik()<=0 && siha.getSeviyePuani()<=10){
                            firkateyn.setSeviyePuani(firkateyn.getSeviyePuani()+10);
                        }
                        else if(siha.getDayaniklilik()<=0 && siha.getSeviyePuani()>10){
                            firkateyn.setSeviyePuani(siha.getSeviyePuani()+firkateyn.getSeviyePuani());
                        }
                        int bilgisayarVurus = siha.Vurus() + siha.denizVurusAvantaji;
                        firkateyn.DurumGuncelle(bilgisayarVurus);
                        //firkateyn.setDayaniklilik(ucak.getDayaniklilik() - bilgisayarVurus);
                        if(firkateyn.getDayaniklilik()<=0 && firkateyn.getSeviyePuani()<=10){
                            siha.setSeviyePuani(siha.getSeviyePuani()+10);
                        }
                        else if(firkateyn.getDayaniklilik()<=0 && firkateyn.getSeviyePuani()>10){
                            siha.setSeviyePuani(firkateyn.getSeviyePuani()+firkateyn.getSeviyePuani());
                        }
                        System.out.println("firkateyn"+firkateyn.getSeviyePuani() + "siha"+siha.getSeviyePuani()+"\n");
                        kullanici.setSkor(firkateyn.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(siha.getSeviyePuani()+bilgisayar.getSkor());
                    }
                }
                if (kullaniciKart instanceof Sida) {
                    Sida sida = (Sida) kullaniciKart;
            
                    if (bilgisayarKart instanceof Ucak) {
                        Ucak ucak = (Ucak) bilgisayarKart;
                        int kullaniciVurus = sida.Vurus() + sida.havaVurusAvantaji();
                        ucak.DurumGuncelle(kullaniciVurus);  
                       //ucak.setDayaniklilik(ucak.getDayaniklilik() - kullaniciVurus);
                        if(ucak.getDayaniklilik()<=0 && ucak.getSeviyePuani()<=10){
                            sida.setSeviyePuani(sida.getDayaniklilik()+10);
                        }
                        else if(ucak.getDayaniklilik()<=0 && ucak.getSeviyePuani()>0){
                            sida.setSeviyePuani(sida.getSeviyePuani()+ucak.getSeviyePuani());
                        }
                        int bilgisayarVurus = ucak.Vurus();
                        sida.DurumGuncelle(bilgisayarVurus);
                        //sida.setDayaniklilik(sida.getDayaniklilik() - bilgisayarVurus);
                        if(sida.getDayaniklilik()<=0 && sida.getSeviyePuani()<=10){
                            ucak.setSeviyePuani(ucak.getSeviyePuani()+10);
                        }
                        else if(sida.getDayaniklilik()<=0 && sida.getSeviyePuani()>0){
                            ucak.setSeviyePuani(sida.getSeviyePuani()+ucak.getSeviyePuani());
                        }
                        System.out.println("sida" +sida.getSeviyePuani() + "ucak"+ucak.getSeviyePuani()+"\n");
                        kullanici.setSkor(sida.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(ucak.getSeviyePuani()+bilgisayar.getSkor());
                    }
                    else if (bilgisayarKart instanceof Obus) {
                        Obus obus = (Obus) bilgisayarKart;
                        int kullaniciVurus = sida.Vurus();
                        obus.DurumGuncelle(kullaniciVurus);
                        //obus.setDayaniklilik(obus.getDayaniklilik() - kullaniciVurus);
                        if(obus.getDayaniklilik()<=0 && obus.getSeviyePuani()<=10){
                            sida.setSeviyePuani(sida.getSeviyePuani()+10);
                        }
                        else if(obus.getDayaniklilik()<=0 && obus.getSeviyePuani()>10){
                            sida.setSeviyePuani(obus.getSeviyePuani()+sida.getSeviyePuani());
                        }
                        int bilgisayarVurus = obus.Vurus()+obus.denizVurusAvantaji();
                        sida.DurumGuncelle(bilgisayarVurus);
                        //sida.setDayaniklilik(sida.getDayaniklilik() - bilgisayarVurus);
                        if(sida.getDayaniklilik()<=0 && sida.getSeviyePuani()<=10){
                            obus.setSeviyePuani(obus.getSeviyePuani()+10);
                        }
                        else if(sida.getDayaniklilik()<=0 && sida.getSeviyePuani()>10){
                            obus.setSeviyePuani(obus.getSeviyePuani()+sida.getSeviyePuani());
                        }
                        System.out.println("sida"+sida.getSeviyePuani() + "obus"+obus.getSeviyePuani()+"\n");
                        kullanici.setSkor(sida.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(obus.getSeviyePuani()+bilgisayar.getSkor());
                    }
                    else if (bilgisayarKart instanceof Firkateyn) {
                        Firkateyn firkateyn = (Firkateyn) bilgisayarKart;
                        int kullaniciVurus = sida.Vurus();
                        firkateyn.DurumGuncelle(kullaniciVurus);
                        //firkateyn.setDayaniklilik(firkateyn.getDayaniklilik() - kullaniciVurus);
                        if(firkateyn.getDayaniklilik()<=0 && firkateyn.getSeviyePuani()<=10){
                            sida.setSeviyePuani(sida.getSeviyePuani()+10);
                        }
                        else if(firkateyn.getDayaniklilik()<=0 && firkateyn.getSeviyePuani()>10){
                            sida.setSeviyePuani(firkateyn.getSeviyePuani()+sida.getSeviyePuani());
                        }
                        int bilgisayarVurus = firkateyn.Vurus() ;
                        sida.DurumGuncelle(bilgisayarVurus);
                        //sida.setDayaniklilik(sida.getDayaniklilik() - bilgisayarVurus);
                        if(sida.getDayaniklilik()<=0 && sida.getSeviyePuani()<=10){
                            firkateyn.setSeviyePuani(firkateyn.getSeviyePuani()+10);
                        }
                        else if(sida.getDayaniklilik()<=0 && sida.getSeviyePuani()>10){
                            firkateyn.setSeviyePuani(firkateyn.getSeviyePuani()+sida.getSeviyePuani());
                        }
                        System.out.println(" sida"+sida.getSeviyePuani() +"firkateyn"+ firkateyn.getSeviyePuani()+"\n");
                        kullanici.setSkor(sida.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(firkateyn.getSeviyePuani()+bilgisayar.getSkor());
                    }
                    else if (bilgisayarKart instanceof KFS) {
                        KFS Kfs = (KFS) bilgisayarKart;
                        int kullaniciVurus = sida.Vurus();
                        Kfs.DurumGuncelle(kullaniciVurus);
                        //Kfs.setDayaniklilik(sida.getDayaniklilik() - kullaniciVurus);
                        if(Kfs.getDayaniklilik()<=0 && Kfs.getSeviyePuani()<=10){
                            sida.setSeviyePuani(sida.getSeviyePuani()+10);
                        }
                        else if(Kfs.getDayaniklilik()<=0 && Kfs.getSeviyePuani()>10){
                            sida.setSeviyePuani(Kfs.getSeviyePuani()+sida.getSeviyePuani());
                        }
                        int bilgisayarVurus = Kfs.Vurus() + Kfs.denizVurusAvantaji();
                        sida.DurumGuncelle(bilgisayarVurus);
                        //sida.setDayaniklilik(sida.getDayaniklilik() - bilgisayarVurus);
                        if(sida.getDayaniklilik()<=0 && sida.getSeviyePuani()<=10){
                            Kfs.setSeviyePuani(Kfs.getSeviyePuani()+10);
                        }
                        else if(sida.getDayaniklilik()<=0 && sida.getSeviyePuani()>10){
                            Kfs.setSeviyePuani(Kfs.getSeviyePuani()+sida.getSeviyePuani());
                        }
                        System.out.println("sida"+sida.getSeviyePuani() + "Kfs"+Kfs.getSeviyePuani()+"\n");
                        kullanici.setSkor(sida.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(Kfs.getSeviyePuani()+bilgisayar.getSkor());
                    }
                    else if (bilgisayarKart instanceof Sida) {
                        Sida sida1 = (Sida) bilgisayarKart;
                        int kullaniciVurus = sida.Vurus();
                        sida1.DurumGuncelle(kullaniciVurus);
                        //sida1.setDayaniklilik(sida.getDayaniklilik() - kullaniciVurus);
                        if(sida1.getDayaniklilik()<=0 && sida1.getSeviyePuani()<=10){
                            sida.setSeviyePuani(sida.getSeviyePuani()+10);
                        }
                        else if(sida1.getDayaniklilik()<=0 && sida1.getSeviyePuani()>10){
                            sida.setSeviyePuani(sida1.getSeviyePuani()+sida.getSeviyePuani());
                        }
                        int bilgisayarVurus = sida1.Vurus();
                        sida.DurumGuncelle(bilgisayarVurus);
                        //sida.setDayaniklilik(obus.getDayaniklilik() - bilgisayarVurus);
                        if(sida.getDayaniklilik()<=0 && sida.getSeviyePuani()<=10){
                            sida1.setSeviyePuani(sida1.getSeviyePuani()+10);
                        }
                        else if(sida.getDayaniklilik()<=0 && sida.getSeviyePuani()>10){
                            sida1.setSeviyePuani(sida1.getSeviyePuani()+sida.getSeviyePuani());
                        }
                        System.out.println("sida"+sida.getSeviyePuani() + "sida1"+sida1.getSeviyePuani()+"\n");
                        kullanici.setSkor(sida.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(sida1.getSeviyePuani()+bilgisayar.getSkor());
                    }
                    else if (bilgisayarKart instanceof Siha) {
                        Siha siha = (Siha) bilgisayarKart;
                        int kullaniciVurus = sida.Vurus() + sida.havaVurusAvantaji();
                        siha.DurumGuncelle(kullaniciVurus);
                        //siha.setDayaniklilik(sida.getDayaniklilik() - kullaniciVurus);
                        if(siha.getDayaniklilik()<=0 && siha.getSeviyePuani()<=10){
                            sida.setSeviyePuani(sida.getSeviyePuani()+10);
                        }
                        else if(siha.getDayaniklilik()<=0 && siha.getSeviyePuani()>10){
                            sida.setSeviyePuani(siha.getSeviyePuani()+sida.getSeviyePuani());
                        }
                        int bilgisayarVurus = siha.Vurus() + siha.denizVurusAvantaji;
                        sida.DurumGuncelle(bilgisayarVurus);
                        //sida.setDayaniklilik(ucak.getDayaniklilik() - bilgisayarVurus);
                        if(sida.getDayaniklilik()<=0 && sida.getSeviyePuani()<=10){
                            siha.setSeviyePuani(siha.getSeviyePuani()+10);
                        }
                        else if(sida.getDayaniklilik()<=0 && sida.getSeviyePuani()>10){
                            siha.setSeviyePuani(sida.getSeviyePuani()+sida.getSeviyePuani());
                        }
                        System.out.println("sida"+sida.getSeviyePuani() + "siha"+siha.getSeviyePuani()+"\n");
                        kullanici.setSkor(sida.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(siha.getSeviyePuani()+bilgisayar.getSkor());
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
                    else if (kart instanceof Sida && ((Sida) kart).getDayaniklilik() <= 0) {
                        System.out.println(((Sida) kart).getName() + " öldü ve kullanıcı kartlarından çıkarılıyor.");
                        kullaniciKartlari.remove(k);
                    }
                    else if (kart instanceof Siha && ((Siha) kart).getDayaniklilik() <= 0) {
                        System.out.println(((Siha) kart).getName() + " öldü ve kullanıcı kartlarından çıkarılıyor.");
                        kullaniciKartlari.remove(k);
                    }
                    else if (kart instanceof KFS && ((KFS) kart).getDayaniklilik() <= 0) {
                        System.out.println(((KFS) kart).getName() + " öldü ve kullanıcı kartlarından çıkarılıyor.");
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
                    }else if (kart instanceof Sida && ((Sida) kart).getDayaniklilik() <= 0) {
                        System.out.println(((Sida) kart).getName() + " öldü ve bilgisayar kartlarından çıkarılıyor.");
                        bilgisayarKartlari.remove(j);
                    }
                    else if (kart instanceof Siha && ((Siha) kart).getDayaniklilik() <= 0) {
                        System.out.println(((Siha) kart).getName() + " öldü ve bilgisayar kartlarından çıkarılıyor.");
                        bilgisayarKartlari.remove(j);
                    }
                    else if (kart instanceof KFS && ((KFS) kart).getDayaniklilik() <= 0) {
                        System.out.println(((KFS) kart).getName() + " öldü ve bilgisayar kartlarından çıkarılıyor.");
                        bilgisayarKartlari.remove(j);
                    }
                }
            }
            
            Object yeniKullaniciKart = rastgeleKartEkle(kullaniciKartlari, random, kullaniciUcakIndex, kullaniciObusIndex, kullaniciFirkateynIndex , kullanici.getSkor());
            if (yeniKullaniciKart instanceof Ucak) {
                kullaniciUcakIndex++;
            } else if (yeniKullaniciKart instanceof Obus) {
                kullaniciObusIndex++;
            } else if (yeniKullaniciKart instanceof Firkateyn) {
                kullaniciFirkateynIndex++;
            }

            Object yeniBilgisayarKart = rastgeleKartEkle(bilgisayarKartlari, random, bilgisayarUcakIndex, bilgisayarObusIndex, bilgisayarFirkateynIndex , bilgisayar.getSkor());
            if (yeniBilgisayarKart instanceof Ucak) {
                bilgisayarUcakIndex++;
            } else if (yeniBilgisayarKart instanceof Obus) {
                bilgisayarObusIndex++;
            } else if (yeniBilgisayarKart instanceof Firkateyn) {
                bilgisayarFirkateynIndex++;
            }
            if(kullaniciKartlari.isEmpty()){
                System.out.println("Bilgisayar kazandi..");
                break;
            }
            else if(bilgisayarKartlari.isEmpty()){
                System.out.println("Kullanici kazandi..");
                break;
            }
            kullaniciskor = kullanici.getSkor();
            bilgisayarskor = bilgisayar.getSkor();
        }
        System.out.println("\nOyun bitti. Tüm turlar oynandı!");    
    }
    
private static Object rastgeleKartEkle(List<Object> kartListesi, Random random, int ucakIndex, int obusIndex, int firkateynIndex, int skor) {
    int kartTuru;

    if (skor >= 20) {
        kartTuru = random.nextInt(6); 
    } else {
        kartTuru = random.nextInt(3); 
    }

    Object yeniKart;
    if (kartTuru == 0) {
        yeniKart = new Ucak("Hava");
        ((Ucak) yeniKart).setName("Ucak" + ucakIndex);
    } else if (kartTuru == 1) {
        yeniKart = new Obus("Kara");
        ((Obus) yeniKart).setName("Obus" + obusIndex);
    } else if (kartTuru == 2) {
        yeniKart = new Firkateyn("Deniz");
        ((Firkateyn) yeniKart).setName("Firkateyn" + firkateynIndex);
    } else if (kartTuru == 3) {
        yeniKart = new Siha( "Hava");
        ((Siha) yeniKart).setName("Siha" + (kartListesi.size() + 1));
    } else if (kartTuru == 4) {
        yeniKart = new Sida( "Deniz");
        ((Sida) yeniKart).setName("Sida" + (kartListesi.size() + 1));
    } else {
        yeniKart = new KFS("Kara");
        ((KFS) yeniKart).setName("KFS" + (kartListesi.size() + 1));
    }

    kartListesi.add(yeniKart);
    return yeniKart;
}
}
/* 
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
private static Object rastgeleKartEkle(List<Object> kartListesi, Random random, int ucakIndex, int obusIndex, int firkateynIndex, int skor) {
    int kartTuru;

    if (skor >= 20) {
        kartTuru = random.nextInt(6); // 0: Uçak, 1: Obüs, 2: Firkateyn, 3: Siha, 4: Sida, 5: KFS
    } else {
        kartTuru = random.nextInt(3); // 0: Uçak, 1: Obüs, 2: Firkateyn
    }

    Object yeniKart;
    if (kartTuru == 0) {
        yeniKart = new Ucak("Hava");
        ((Ucak) yeniKart).setName("Ucak" + ucakIndex);
    } else if (kartTuru == 1) {
        yeniKart = new Obus("Kara");
        ((Obus) yeniKart).setName("Obus" + obusIndex);
    } else if (kartTuru == 2) {
        yeniKart = new Firkateyn("Deniz");
        ((Firkateyn) yeniKart).setName("Firkateyn" + firkateynIndex);
    } else if (kartTuru == 3) {
        yeniKart = new Siha(10, "Hava");
        ((Siha) yeniKart).setName("Siha" + (kartListesi.size() + 1));
    } else if (kartTuru == 4) {
        yeniKart = new Sida(10, "Deniz");
        ((Sida) yeniKart).setName("Sida" + (kartListesi.size() + 1));
    } else {
        yeniKart = new KFS(10, "Kara");
        ((KFS) yeniKart).setName("KFS" + (kartListesi.size() + 1));
    }

    kartListesi.add(yeniKart);
    return yeniKart;
}*/