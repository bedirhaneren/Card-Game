public class Sida extends Deniz{
    private int karaVurusAvantaji  ;
    public Sida(int seviyePuani, String sinif, int karaVurusAvantaji){
        super(seviyePuani,sinif);
        this.karaVurusAvantaji = karaVurusAvantaji;
    }
public int getKaraVurusAvantaji(){
return karaVurusAvantaji;
}
public void setKaraVurusAvantaji(int karaVurusAvantaji){
        this.karaVurusAvantaji = this.karaVurusAvantaji;
}
@Override
public void KartPuaniGoster() {
    super.KartPuaniGoster();
    System.out.println("Kara vurus avantaji: " + getKaraVurusAvantaji());
    }

    @Override
    public void DurumGuncelle(int SaldiriDegeri) {

    }
}






