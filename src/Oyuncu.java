import java.util.ArrayList;
import java.util.List;

public abstract class Oyuncu {
    protected int oyuncuID;
    protected String oyuncuAdi;
    protected int skor;
    protected List<Object> kartListesi;

    public Oyuncu() {
        this.oyuncuID = 0;
        this.oyuncuAdi = "Bilinmiyor";
        this.skor = 0;
        this.kartListesi = new ArrayList<>();
    }

    public Oyuncu(int oyuncuID, String oyuncuAdi, int skor) {
        this.oyuncuID = oyuncuID;
        this.oyuncuAdi = oyuncuAdi;
        this.skor = skor;
        this.kartListesi = new ArrayList<>();
    }

    public void SkorGoster() {
        System.out.println(oyuncuAdi + " Skoru: " + skor);
    }

    public int getSkor() {
        return skor;
    }

    public void setSkor(int skor) {
        this.skor = skor;
    }

    public abstract List<Object> kartSec();
}
