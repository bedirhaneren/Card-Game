

public class KFS extends Kara{
    public int havaVurusAvantaji;
    public int dayaniklilik;
    private String name;

    public KFS(String sinif) {
        super(0,sinif);  
        this.dayaniklilik=10;
        this.name = name;
    }
  
    
    @Override
    public void DurumGuncelle(int SaldiriDegeri) {
        super.DurumGuncelle(SaldiriDegeri);
        dayaniklilik=dayaniklilik-SaldiriDegeri;        
    }
    

      public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
@Override
    public String toString() {
        return name; // Kartın ekranda isim olarak görünmesini sağlar
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
        return dayaniklilik;
    }

    @Override
    public int Vurus() {
        return 10;
    }
    public int getSeviyePuani() {
        return super.getSeviyePuani();  // Super sınıfındaki getSeviyePuani metodunu çağırıyoruz
    }

    public void setSeviyePuani(int puan) {
        super.setSeviyePuani(puan);  // Super sınıfındaki setSeviyePuani metodunu çağırıyoruz
    }

    public int getDayaniklilik() {
        return dayaniklilik;
    }

    public void setDayaniklilik(int dayaniklilik) {
        this.dayaniklilik = dayaniklilik;
    }

}
