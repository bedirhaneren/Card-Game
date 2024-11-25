public class KFS extends Kara{
    public int havaVurusAvantaji;
    public KFS(int seviyePuani, String sinif) {
        super(0,sinif);  
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
        return "KFS";
    }

    @Override
    public int denizVurusAvantaji() {
        return 10;
    }

    @Override
    public int Dayaniklilik() {
        return 10;
    }

    @Override
    public int Vurus() {
        return 10;
    }
}
