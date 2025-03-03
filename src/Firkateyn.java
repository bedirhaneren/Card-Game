public class Firkateyn extends Deniz {

    private String name;
    private int dayaniklilik;
    public Firkateyn(String sinif) {
        super(0,sinif);  
        this.dayaniklilik=25;
    }

    
    public Firkateyn(String sinif, String name) {
        super(0, sinif);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeviyePuani() {
        return super.getSeviyePuani();
    }

    // Setter for seviyePuani
    public void setSeviyePuani(int puan) {
        super.setSeviyePuani(puan); 
        
    }

    @Override
    public String toString() {
        return name; 
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
        return dayaniklilik;
    }

    @Override
    public int Vurus() {
        return 10;
    }

    @Override
    public void DurumGuncelle(int SaldiriDegeri) {
        super.DurumGuncelle(SaldiriDegeri);
        dayaniklilik=dayaniklilik-SaldiriDegeri;        
    }

    @Override
    public void KartPuaniGoster() {
        super.KartPuaniGoster();
    }


    public int getDayaniklilik() {
        return dayaniklilik;
    }


    public void setDayaniklilik(int dayaniklilik) {
        this.dayaniklilik = dayaniklilik;
    }
    

    
}
