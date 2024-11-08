abstract class Kara extends SavasAraclari {
    public abstract String altsinif();
    public abstract int denizVurusAvantaji();

    public Kara(int seviyePuani , String sinif) {
        super(seviyePuani);
    }

    @Override
    public void KartPuaniGoster() {
        super.KartPuaniGoster();
    }

    @Override
    public void DurumGuncelle(int SaldiriDegeri) {

    }
    
    @Override
    public String Sinif() {
        return "Kara";
    }
    
}
