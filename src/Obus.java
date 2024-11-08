public class Obus extends Kara {

    public Obus(int seviyePuani, String sinif) {
        super(seviyePuani,sinif);  
    }

    @Override
    public String altsinif() {
        return "Obus"; 
    }

    @Override
    public String Sinif() {
        return super.Sinif();
    }

    @Override
    public int denizVurusAvantaji() {
        return 5;
    }

    @Override
    public int Dayaniklilik() {
        return 20; 
    }

    @Override
    public int Vurus() {
        return 10; 
    }

    @Override
    public void KartPuaniGoster() {
        super.KartPuaniGoster(); 
        System.out.println("Alt Sinif: " + altsinif()); 
    }

    @Override
    public void DurumGuncelle(int SaldiriDegeri) {
    }
    
}

