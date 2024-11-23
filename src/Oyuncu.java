import java.util.ArrayList;
import java.util.List;

public abstract class Oyuncu {
    protected int oyuncuID;
    protected String oyuncuAdi;
    protected int skor;
    protected List<Object> kartListesi;

    // Parametresiz Constructor
    public Oyuncu() {
        this.oyuncuID = 0;
        this.oyuncuAdi = "Bilinmiyor";
        this.skor = 0;
        this.kartListesi = new ArrayList<>();
    }

    // Parametreli Constructor
    public Oyuncu(int oyuncuID, String oyuncuAdi, int skor) {
        this.oyuncuID = oyuncuID;
        this.oyuncuAdi = oyuncuAdi;
        this.skor = skor;
        this.kartListesi = new ArrayList<>();
    }

    // Skoru gösteren fonksiyon
    public void SkorGoster() {
        System.out.println(oyuncuAdi + " Skoru: " + skor);
    }

    // Kart seçme fonksiyonu (Soyut)
    public abstract List<Object> kartSec();
}
