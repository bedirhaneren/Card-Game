public class Ucak extends Hava {

    private String name; // Kartın ismi
    public Ucak(int seviyePuani, String sinif) {
        super(seviyePuani, sinif);
    }

    // Getter
    public String getName() {
        return name;
    }

    // Setter
    public void setName(String name) {
        this.name = name;
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
        return 10; 
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