public class KFS extends Kara{
    private int havaVurusAvantaji ;

    public KFS(int seviyePuani , String sinif, int havaVurusAvantaji){
        super(seviyePuani,sinif) ;
        this.havaVurusAvantaji = havaVurusAvantaji ;
    }
public int getHavaVurusAvantaji(){
        return havaVurusAvantaji;
}
public void setHavaVurusAvantaji(int havaVurusAvantaji){
        this.havaVurusAvantaji = havaVurusAvantaji ;
}
    @Override
    public void KartPuaniGoster() {
        super.KartPuaniGoster();
        System.out.println("Hava vurus avantaji: " + getHavaVurusAvantaji());  // KFS sınıfına özgü hava vuruş avantajını gösteriyoruz
    }

    @Override
    public void DurumGuncelle(int SaldiriDegeri) {

    }


}
