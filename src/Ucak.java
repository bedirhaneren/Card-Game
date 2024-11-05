public class Ucak extends Hava{
    public Ucak(int seviyePuani ,String sinif){
        super(seviyePuani,sinif);
    }
@Override
    public void KartPuaniGoster(){
super.KartPuaniGoster();
    System.out.printf("Bu kart ucak kartidir.");
}
@Override
    public void DurumGuncelle(int SaldiriDegeri){
}


}