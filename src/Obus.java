public class Obus extends Kara {
    private String name;
    public Obus(int seviyePuani, String sinif) {
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

