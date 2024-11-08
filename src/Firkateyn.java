public class Firkateyn extends Deniz {
    public Firkateyn(int seviyePuani, String sinif) {
        super(seviyePuani,sinif);  
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
