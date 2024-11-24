public class Obus extends Kara {
    private String name;
    public Obus( String sinif) {
        super(0,sinif);  
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
    public String altsinif() {
        return "Obus"; 
    }

    @Override
    public String Sinif() {
        return super.Sinif();
    }

    @Override
    public int denizVurusAvantaji() {
        return 5;
    }

    @Override
    public int karaVurusAvantaji() {
        return 15; // Kara araçlarına avantaj
    }

    @Override
    public int havaVurusAvantaji() {
        return 0; // Hava araçlarına avantaj yok
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
    public void KartPuaniGoster() {
        super.KartPuaniGoster(); 
        System.out.println("Alt Sinif: " + altsinif()); 
    }

    @Override
    public void DurumGuncelle(int SaldiriDegeri) {
    }
    
}

