public class Ucak extends Hava {

    private String name; // Kartın ismi

    // Constructor
    public Ucak(String sinif) {
        super(0, sinif);  // seviyePuani başlangıçta 0 olacak şekilde super'e gönderiyoruz
    }

    public Ucak(String sinif, String name) {
        super(0, sinif);
        this.name = name;
    }

    // Getter
    public String getName() {
        return name;
    }

    // Setter
    public void setName(String name) {
        this.name = name;
    }

    public int getSeviyePuani() {
        return super.getSeviyePuani();  // Super sınıfındaki getSeviyePuani metodunu çağırıyoruz
    }

    // Setter for seviyePuani
    public void setSeviyePuani(int puan) {
        super.setSeviyePuani(puan);  // Super sınıfındaki setSeviyePuani metodunu çağırıyoruz
    }

    @Override
    public String toString() {
        return name; // Kartın ekranda isim olarak görünmesini sağlar
    }

    @Override
    public int Dayaniklilik() {
        return 20; 
    }

    @Override
    public int Vurus() {
        return 10;
    }

    @Override
    public int karaVurusAvantaji() {
        return 15; // Kara araçlarına avantaj
    }

    @Override
    public int denizVurusAvantaji() {
        return 0; // Deniz araçlarına avantaj yok
    }

    @Override
    public int havaVurusAvantaji() {
        return 0; // Hava araçlarına avantaj yok
    }
    @Override
    public String Sinif() {
        return super.Sinif();
    }

    @Override
    public String altsinif() {
        return "Ucak"; 
    }

    @Override
    public void DurumGuncelle(int SaldiriDegeri) {
        super.DurumGuncelle(SaldiriDegeri);
    }

    @Override
    public void KartPuaniGoster() {
        super.KartPuaniGoster();
    }
} 