public class Firkateyn extends Deniz {

    private String name;
    public Firkateyn(int seviyePuani, String sinif) {
        super(seviyePuani,sinif);  
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
    public String Sinif() {
        return super.Sinif();
    }

    @Override
    public String altsinif() {
        return "Firkateyn";
    }

    @Override
    public int havaVurusAvantaji() {
        return 5;
    }

    @Override
    public int Dayaniklilik() {
        return 25;
    }

    @Override
    public int Vurus() {
        return 10;
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
