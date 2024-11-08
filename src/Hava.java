abstract class Hava extends SavasAraclari {

    public abstract int karaVurusAvantaji();
    public abstract String altsinif();

    public Hava(int seviyePuani, String sinif) {
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
        return "Hava";
    }
}