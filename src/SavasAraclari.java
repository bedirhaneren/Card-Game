    abstract class SavasAraclari {
        // abstract sınıflar icin
    public abstract int Dayaniklilik();
    public abstract String Sinif();
    public abstract String Vurus() ;

    private int SeviyePuani ;

    public SavasAraclari(int seviyePuani) {
        this.SeviyePuani=seviyePuani;
    }

    public void KartPuaniGoster(){
        System.out.println("Dayaniklilik : " + Dayaniklilik());
        System.out.println("Sinif: " + Sinif());
        System.out.println("Vurus: " + Vurus());
        System.out.println("Seviye Puani : " + SeviyePuani);
    }
    public abstract void DurumGuncelle(int SaldiriDegeri);
    }

