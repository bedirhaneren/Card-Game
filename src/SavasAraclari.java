abstract class SavasAraclari {

    protected int SeviyePuani;

    public SavasAraclari(int seviyePuani) {
        this.SeviyePuani = 0;
    }

    public abstract int Dayaniklilik();
    public abstract int Vurus();
    public abstract String Sinif(); // "Hava", "Kara", "Deniz"

    public void KartPuaniGoster() {
        System.out.println("Dayanıklılık: " + Dayaniklilik());
        System.out.println("Vuruş: " + Vurus());
        System.out.println("Seviye Puanı: " + SeviyePuani);
    }

    public abstract void DurumGuncelle(int yeniDayaniklilik);

    public int getSeviyePuani() {
        return SeviyePuani;
    }

    public void setSeviyePuani(int seviyePuani) {
        this.SeviyePuani = seviyePuani;
    }
}
