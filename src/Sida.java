public class Sida extends Deniz{
    public int karaVurusAvantaji;
    public Sida(int seviyePuani, String sinif) {
        super(seviyePuani,sinif);  
    }
    @Override
    public void DurumGuncelle(int SaldiriDegeri) {
        super.DurumGuncelle(SaldiriDegeri);
    }
    @Override
    public void KartPuaniGoster() {
        super.KartPuaniGoster();
    }
    @Override
    public String Sinif() {
        return super.Sinif();
    }
    @Override
    public String altsinif() {
        return "Sida";
    }
    @Override
    public int havaVurusAvantaji() {
        return 10;
    }
    @Override
    public int Dayaniklilik() {
        return 15;
    }
    @Override
    public int Vurus() {
        return 10;
    }
    
}
