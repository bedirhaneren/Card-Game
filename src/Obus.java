public class Obus extends Kara {
    private String name;
    private int dayaniklilik;
    public Obus( String sinif) {
        super(0,sinif);  
        this.dayaniklilik=20;
    }

    public Obus(String sinif, String name) {
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
    public int Dayaniklilik() {
        return dayaniklilik; 
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

    public int getDayaniklilik() {
        return dayaniklilik;
    }

    public void setDayaniklilik(int dayaniklilik) {
        this.dayaniklilik = dayaniklilik;
    }

}

