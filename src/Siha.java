public class Siha extends Hava{
private int denizVurusAvantaji ;
    public Siha(int seviyePuani,String sinif, int denizVurusAvantaji){
        super(seviyePuani,sinif);
        this.denizVurusAvantaji = denizVurusAvantaji;
    }

    public int getDenizVurusAvantaji() {
        return denizVurusAvantaji;
    }

    public void setDenizVurusAvantaji(int denizVurusAvantaji) {
        this.denizVurusAvantaji = denizVurusAvantaji;
    }

    @Override
    public void KartPuaniGoster(){
        super.KartPuaniGoster();
        System.out.println("Bu bir Siha Kartidir");
        System.out.println("Deniz vurus avantaji degeri : " +denizVurusAvantaji);

    }
@Override
    public void DurumGuncelle(int SaldiriDegeri){

}



}

