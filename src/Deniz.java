abstract class Deniz extends SavasAraclari{
    public abstract String altsinif();
    public abstract int havaVurusAvantaji();

    public Deniz(int seviyePuani, String sinif) {
        super(seviyePuani);
        //this.sinif = sinif;
    }
    
    @Override
    public void DurumGuncelle(int SaldiriDegeri) { 

    }

    @Override
    public void KartPuaniGoster() {
        super.KartPuaniGoster();
    }

    @Override
    public String Sinif() {
        return "Deniz";
    }

}
