import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public class GameUI extends Application {

    private int turSayisi;
    private int mevcutTur = 1;
    private List<Object> kullaniciKartlari = new ArrayList<>();
    private List<Object> bilgisayarKartlari = new ArrayList<>();
    private List<Object> secilenKartlar = new ArrayList<>();
    private Map<Integer, List<Object>> turBilgileri = new LinkedHashMap<>();
    private Label secilenKartlarLabel = new Label("Seçilen Kartlar: ");
    ListView<HBox> turGecmisiListesi = new ListView<>();

    @Override
    public void start(Stage primaryStage) {
        girişEkranı(primaryStage);
    }

    private static String rastgeleKartTuru() {
        Random random = new Random();
        int kartTuru = random.nextInt(3); // 0: Uçak, 1: Obüs, 2: Firkateyn
        if (kartTuru == 0) return "Ucak";
        if (kartTuru == 1) return "Obus";
        return "Firkateyn";
    }
    
    private static Object rastgeleKartEkle(List<Object> kartListesi, String kartTuru) {
        int mevcutSayisi = (int) kartListesi.stream().filter(kart -> {
            if (kart instanceof Ucak && kartTuru.equals("Ucak")) return true;
            if (kart instanceof Obus && kartTuru.equals("Obus")) return true;
            if (kart instanceof Firkateyn && kartTuru.equals("Firkateyn")) return true;
            return false;
        }).count();
    
        Object yeniKart;
        if (kartTuru.equals("Ucak")) {
            yeniKart = new Ucak("Hava");
            ((Ucak) yeniKart).setName("Ucak" + (mevcutSayisi + 1));
        } else if (kartTuru.equals("Obus")) {
            yeniKart = new Obus("Kara");
            ((Obus) yeniKart).setName("Obus" + (mevcutSayisi + 1));
        } else { // Firkateyn
            yeniKart = new Firkateyn("Deniz");
            ((Firkateyn) yeniKart).setName("Firkateyn" + (mevcutSayisi + 1));
        }
    
        kartListesi.add(yeniKart);
        return yeniKart;
    }
    

   // Giriş ekranı
private void girişEkranı(Stage primaryStage) {
    // Arka plan resmi
    BackgroundImage backgroundImage = new BackgroundImage(
            new Image("file:C:/Users/Ömer/Desktop/CardGame/src/images/background.png", 800, 600, false, true),
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(100, 100, true, true, true, true) // Tam ekran için %100 genişlik ve yükseklik
    );
    Background background = new Background(backgroundImage);

    // Root düzeni
    VBox root = new VBox(20);
    root.setStyle("-fx-alignment: center; -fx-padding: 20px;");
    root.setBackground(background); // Arka plan ayarla

    // Düğmeler
    Button oyunaBaslaButton = new Button("Oyuna Başla");
    oyunaBaslaButton.setStyle("-fx-font-size: 16px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
    oyunaBaslaButton.setOnAction(e -> oyunBaslangicEkrani(primaryStage));

    Button kartBilgileriButton = new Button("Kart Bilgileri");
    kartBilgileriButton.setStyle("-fx-font-size: 16px; -fx-background-color: #2196F3; -fx-text-fill: white;");
    kartBilgileriButton.setOnAction(e -> kartBilgileriEkrani(primaryStage));

    VBox buttonBox = new VBox(30, oyunaBaslaButton, kartBilgileriButton);
    buttonBox.setStyle("-fx-alignment: center; -fx-padding: 20px;");

    root.getChildren().addAll(buttonBox);

    Scene scene = new Scene(root, 800, 600);
    primaryStage.setTitle("Kart Oyunu");
    primaryStage.setScene(scene);
    primaryStage.setFullScreen(true);
    primaryStage.setFullScreenExitHint("");
    primaryStage.show();
}
    // Oyuna başla ekranı
    private void oyunBaslangicEkrani(Stage primaryStage) {
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image("file:C:/Users/Ömer/Desktop/CardGame/src/images/background.png", 800, 600, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT
        );
        Background background = new Background(backgroundImage);

        ImageView logo = new ImageView(new Image("file:C:/Users/Ömer/Desktop/CardGame/src/images/logo.png"));
        logo.setFitWidth(300);
        logo.setFitHeight(150);

        
        Label turSayisiLabel = new Label("Kaç tur oynanacak?");
        turSayisiLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: white;");
        TextField turSayisiField = new TextField();

        turSayisiField.setPromptText("Tur sayısını girin");
        turSayisiField.setMaxWidth(200);

        Button baslaButton = new Button("Başla");
        baslaButton.setStyle("-fx-font-size: 16px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        baslaButton.setOnAction(e -> {
            try {
                String turSayisiGirdisi = turSayisiField.getText();
                if (turSayisiGirdisi == null || turSayisiGirdisi.trim().isEmpty()) {
                    showAlert("Hata", "Lütfen bir tur sayısı girin!");
                    return;
                }
                turSayisi = Integer.parseInt(turSayisiGirdisi);
                if (turSayisi <= 0) {
                    showAlert("Hata", "Tur sayısı pozitif bir sayı olmalıdır!");
                    return;
                }
        
                // Kartları oluştur ve oyun ekranına geç
                baslangicKartlariOlustur();
                turEkraniniGoster(primaryStage);
            } catch (NumberFormatException ex) {
                showAlert("Hata", "Lütfen geçerli bir sayı girin!");
            }
        });

        VBox formBox = new VBox(10, turSayisiLabel, turSayisiField, baslaButton);
        formBox.setStyle("-fx-alignment: center; -fx-padding: 20px;");

        VBox root = new VBox(10, logo, formBox);
        root.setBackground(background);
        root.setStyle("-fx-alignment: center;");

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");
    }

    private void turEkraniniGoster(Stage primaryStage) {
  // Arka plan ve ana düzen için root
  BorderPane root = new BorderPane();

  // Arkaplan resmi
  BackgroundImage backgroundImage = new BackgroundImage(
          new Image("file:C:/Users/Ömer/Desktop/CardGame/src/images/kartsecimbackground.jpg", 800, 600, false, true), // Resim yolu
          BackgroundRepeat.NO_REPEAT,   // Tekrarlanmasın
          BackgroundRepeat.NO_REPEAT,   // Tekrarlanmasın
          BackgroundPosition.CENTER,    // Ortalanmış pozisyon
          new BackgroundSize(100, 100, true, true, false, true) // %100 genişlik ve yükseklik
  );
  root.setBackground(new Background(backgroundImage)); // Arkaplanı uygula
    // Bilgisayar kartlarını üst tarafa ekle
    HBox bilgisayarKartPane = new HBox(10);
    bilgisayarKartPane.setStyle("-fx-alignment: center;");
    for (Object kart : bilgisayarKartlari) {
        String imagePath = getImagePath(kart);
        ImageView imageView = new ImageView(new Image(imagePath));
        imageView.setFitWidth(100);
        imageView.setFitHeight(150);

        VBox kartBox = createCardBox(imageView, kart.toString());
        bilgisayarKartPane.getChildren().add(kartBox);
    }
    root.setTop(bilgisayarKartPane);

    // Kullanıcı bilgileri ve seçilen kartlar
    VBox kullaniciBilgisiBox = new VBox(10);  // kullaniciBilgisiBox burada tanımlandı
    secilenKartlarLabel.setText("Seçilen Kartlar: ");

    kullaniciBilgisiBox.getChildren().addAll(secilenKartlarLabel, new Label("Tur Geçmişi:"), turGecmisiListesi);
    turGecmisiListesi.setStyle("-fx-background-color: transparent; -fx-control-inner-background: transparent;"); // Şeffaflık ayarları

    root.setCenter(kullaniciBilgisiBox);

    // Kullanıcı kartlarını alt tarafa ekle
    VBox kullaniciKartBox = new VBox(10);
    HBox kartPane = new HBox(10);
    kartPane.setStyle("-fx-alignment: center;");
    for (Object kart : kullaniciKartlari) {
        String imagePath = getImagePath(kart);
        ImageView imageView = new ImageView(new Image(imagePath));
        imageView.setFitWidth(100);
        imageView.setFitHeight(150);

        VBox kartBox = createCardBox(imageView, kart.toString());
        kartBox.setOnMouseClicked(e -> {
            if (secilenKartlar.size() < 3 && !secilenKartlar.contains(kart)) {
                secilenKartlar.add(kart);
                kartBox.setDisable(true);
                secilenKartlarLabel.setText("Seçilen Kartlar: " + secilenKartlar);
            }
        });
        kartPane.getChildren().add(kartBox);
    }
    kullaniciKartBox.getChildren().add(kartPane);
    root.setBottom(kullaniciKartBox);

    // "Seçimi Tamamla" butonu
    Button tamamlaButton = new Button("Seçimi Tamamla");
    tamamlaButton.setOnAction(e -> {
        if (secilenKartlar.size() != 3) {
            showAlert("Hata", "Lütfen 3 kart seçin!");
            return;
        }
    
        Random random = new Random();
    
        // Bilgisayarın mevcut kart havuzundan rastgele 3 kart seçmesi
        List<Object> bilgisayarKartHavuzu = new ArrayList<>(bilgisayarKartlari); // Bilgisayarın mevcut kartlarını kopyalayın
        List<Object> bilgisayarSecilenKartlar = new ArrayList<>(); // Eksik olan tanımlama burada yapıldı
        while (bilgisayarSecilenKartlar.size() < 3 && !bilgisayarKartHavuzu.isEmpty()) {
            Object rastgeleKart = bilgisayarKartHavuzu.remove(random.nextInt(bilgisayarKartHavuzu.size()));
            bilgisayarSecilenKartlar.add(rastgeleKart);
        }

        int kullaniciUcakIndex = 1, kullaniciObusIndex = 1, kullaniciFirkateynIndex = 1;
        int bilgisayarUcakIndex = 1, bilgisayarObusIndex = 1, bilgisayarFirkateynIndex = 1;

    
       

        int kullaniciskor ;
        int bilgisayarskor;

           
            Kullanici kullanici = new Kullanici(1, "Kullanıcı", 0);
            kullanici.kartListesi.addAll(kullaniciKartlari);

            Bilgisayar bilgisayar = new Bilgisayar(2, 0);
            bilgisayar.kartListesi.addAll(bilgisayarKartlari);
           

        
           
        
            for (int i = 0; i < secilenKartlar.size(); i++) {
                Object kullaniciKart = secilenKartlar.get(i);
                Object bilgisayarKart = bilgisayarSecilenKartlar.get(i);
            
                if (kullaniciKart instanceof Ucak) {
                    Ucak ucak = (Ucak) kullaniciKart;
            
                    if (bilgisayarKart instanceof Firkateyn) {
                        Firkateyn firkateyn = (Firkateyn) bilgisayarKart;
                        int kullaniciVurus = ucak.Vurus() + ucak.karaVurusAvantaji();
                        firkateyn.DurumGuncelle(kullaniciVurus);
                        //firkateyn.setDayaniklilik(firkateyn.getDayaniklilik() - kullaniciVurus);
                        if(firkateyn.getDayaniklilik()<=0 && firkateyn.getSeviyePuani()<=10){
                            ucak.setSeviyePuani(ucak.getSeviyePuani()+10);
                        }
                        else if(firkateyn.getDayaniklilik()<=0 && firkateyn.getSeviyePuani()>10){
                            ucak.setSeviyePuani(firkateyn.getSeviyePuani()+ucak.getSeviyePuani());
                        }
                        int bilgisayarVurus = firkateyn.Vurus();
                        ucak.DurumGuncelle(bilgisayarVurus);
                        //ucak.setDayaniklilik(ucak.getDayaniklilik() - bilgisayarVurus);
                        if(ucak.getDayaniklilik()<=0 && ucak.getSeviyePuani()<=10){
                            firkateyn.setSeviyePuani(firkateyn.getSeviyePuani()+10);
                        }
                        else if(ucak.getDayaniklilik()<=0 && ucak.getSeviyePuani()>10){
                            firkateyn.setSeviyePuani(firkateyn.getSeviyePuani()+ucak.getSeviyePuani());
                        }
                        System.out.println("ucak"+ucak.getSeviyePuani() + "firkateyn"+firkateyn.getSeviyePuani()+"\n");
                        kullanici.setSkor(ucak.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(firkateyn.getSeviyePuani()+bilgisayar.getSkor());
                    }
                    else if (bilgisayarKart instanceof Obus) {
                        Obus obus = (Obus) bilgisayarKart;
                        int kullaniciVurus = ucak.Vurus() + ucak.karaVurusAvantaji();
                        obus.DurumGuncelle(kullaniciVurus);
                        //obus.setDayaniklilik(obus.getDayaniklilik() - kullaniciVurus);
                        if(obus.getDayaniklilik()<=0 && obus.getSeviyePuani()<=10){
                            ucak.setSeviyePuani(ucak.getSeviyePuani()+10);
                        }
                        else if(obus.getDayaniklilik()<=0 && obus.getSeviyePuani()>10){
                            ucak.setSeviyePuani(obus.getSeviyePuani()+ucak.getSeviyePuani());
                        }
                        int bilgisayarVurus = obus.Vurus();
                        ucak.DurumGuncelle(bilgisayarVurus);
                        //ucak.setDayaniklilik(ucak.getDayaniklilik() - bilgisayarVurus);
                        if(ucak.getDayaniklilik()<=0 && ucak.getSeviyePuani()<=10){
                            obus.setSeviyePuani(obus.getSeviyePuani()+10);
                        }
                        else if(ucak.getDayaniklilik()<=0 && ucak.getSeviyePuani()>10){
                            obus.setSeviyePuani(obus.getSeviyePuani()+ucak.getSeviyePuani());
                        }
                        System.out.println("ucak"+ucak.getSeviyePuani() + "obus"+obus.getSeviyePuani()+"\n");
                        kullanici.setSkor(ucak.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(obus.getSeviyePuani()+bilgisayar.getSkor());
                    }
                    else if (bilgisayarKart instanceof Ucak) {
                        Ucak ucak1 = (Ucak) bilgisayarKart;
                        int kullaniciVurus = ucak.Vurus();
                        ucak1.DurumGuncelle(kullaniciVurus);
                        //ucak1.setDayaniklilik(ucak1.getDayaniklilik() - kullaniciVurus);
                        if(ucak1.getDayaniklilik()<=0 && ucak1.getSeviyePuani()<=10){
                            ucak.setSeviyePuani(ucak.getSeviyePuani()+10);
                        }
                        else if(ucak1.getDayaniklilik()<=0 && ucak1.getSeviyePuani()>10){
                            ucak.setSeviyePuani(ucak1.getSeviyePuani()+ucak.getSeviyePuani());
                        }
                        int bilgisayarVurus = ucak1.Vurus();
                        ucak.DurumGuncelle(bilgisayarVurus);
                        //ucak.setDayaniklilik(ucak.getDayaniklilik() - bilgisayarVurus);
                        if(ucak.getDayaniklilik()<=0 && ucak.getSeviyePuani()<=10){
                            ucak1.setSeviyePuani(ucak1.getSeviyePuani()+10);
                        }
                        else if(ucak.getDayaniklilik()<=0 && ucak.getSeviyePuani()>10){
                            ucak1.setSeviyePuani(ucak1.getSeviyePuani()+ucak.getSeviyePuani());
                        }
                        System.out.println("ucak"+ucak.getSeviyePuani() +"ucakpc"+ ucak1.getSeviyePuani()+"\n");
                        kullanici.setSkor(kullanici.getSkor()+ucak.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(ucak1.getSeviyePuani()+bilgisayar.getSkor());
                    }
                    else if (bilgisayarKart instanceof KFS) {
                        KFS Kfs = (KFS) bilgisayarKart;
                        int kullaniciVurus = ucak.Vurus() + ucak.karaVurusAvantaji();
                        Kfs.DurumGuncelle(kullaniciVurus);
                        //Kfs.setDayaniklilik(obus.getDayaniklilik() - kullaniciVurus);
                        if(Kfs.getDayaniklilik()<=0 && Kfs.getSeviyePuani()<=10){
                            ucak.setSeviyePuani(ucak.getSeviyePuani()+10);
                        }
                        else if(Kfs.getDayaniklilik()<=0 && Kfs.getSeviyePuani()>10){
                            ucak.setSeviyePuani(Kfs.getSeviyePuani()+ucak.getSeviyePuani());
                        }
                        int bilgisayarVurus = Kfs.Vurus() +Kfs.havaVurusAvantaji;
                        ucak.DurumGuncelle(bilgisayarVurus);
                        //ucak.setDayaniklilik(ucak.getDayaniklilik() - bilgisayarVurus);
                        if(ucak.getDayaniklilik()<=0 && ucak.getSeviyePuani()<=10){
                            Kfs.setSeviyePuani(Kfs.getSeviyePuani()+10);
                        }
                        else if(ucak.getDayaniklilik()<=0 && ucak.getSeviyePuani()>10){
                            Kfs.setSeviyePuani(Kfs.getSeviyePuani()+ucak.getSeviyePuani());
                        }
                        System.out.println("ucak"+ucak.getSeviyePuani() + "Kfs"+Kfs.getSeviyePuani()+"\n");
                        kullanici.setSkor(ucak.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(Kfs.getSeviyePuani()+bilgisayar.getSkor());
                    }
                    else if (bilgisayarKart instanceof Sida) {
                        Sida sida = (Sida) bilgisayarKart;
                        int kullaniciVurus = ucak.Vurus() ;
                        sida.DurumGuncelle(kullaniciVurus);
                        //sida.setDayaniklilik(obus.getDayaniklilik() - kullaniciVurus);
                        if(sida.getDayaniklilik()<=0 && sida.getSeviyePuani()<=10){
                            ucak.setSeviyePuani(ucak.getSeviyePuani()+10);
                        }
                        else if(sida.getDayaniklilik()<=0 && sida.getSeviyePuani()>10){
                            ucak.setSeviyePuani(sida.getSeviyePuani()+ucak.getSeviyePuani());
                        }
                        int bilgisayarVurus = sida.Vurus() +sida.havaVurusAvantaji();
                        ucak.DurumGuncelle(bilgisayarVurus);
                        //ucak.setDayaniklilik(ucak.getDayaniklilik() - bilgisayarVurus);
                        if(ucak.getDayaniklilik()<=0 && ucak.getSeviyePuani()<=10){
                            sida.setSeviyePuani(sida.getSeviyePuani()+10);
                        }
                        else if(ucak.getDayaniklilik()<=0 && ucak.getSeviyePuani()>10){
                            sida.setSeviyePuani(sida.getSeviyePuani()+ucak.getSeviyePuani());
                        }
                        System.out.println("ucak"+ucak.getSeviyePuani() + "sida"+sida.getSeviyePuani()+"\n");
                        kullanici.setSkor(ucak.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(sida.getSeviyePuani()+bilgisayar.getSkor());
                    }
                    else if (bilgisayarKart instanceof Siha) {
                        Siha siha = (Siha) bilgisayarKart;
                        int kullaniciVurus = ucak.Vurus();
                        siha.DurumGuncelle(kullaniciVurus);
                        //siha.setDayaniklilik(obus.getDayaniklilik() - kullaniciVurus);
                        if(siha.getDayaniklilik()<=0 && siha.getSeviyePuani()<=10){
                            ucak.setSeviyePuani(ucak.getSeviyePuani()+10);
                        }
                        else if(siha.getDayaniklilik()<=0 && siha.getSeviyePuani()>10){
                            ucak.setSeviyePuani(siha.getSeviyePuani()+ucak.getSeviyePuani());
                        }
                        int bilgisayarVurus = siha.Vurus();
                        ucak.DurumGuncelle(bilgisayarVurus);
                        //ucak.setDayaniklilik(ucak.getDayaniklilik() - bilgisayarVurus);
                        if(ucak.getDayaniklilik()<=0 && ucak.getSeviyePuani()<=10){
                            siha.setSeviyePuani(siha.getSeviyePuani()+10);
                        }
                        else if(ucak.getDayaniklilik()<=0 && ucak.getSeviyePuani()>10){
                            siha.setSeviyePuani(siha.getSeviyePuani()+ucak.getSeviyePuani());
                        }
                        System.out.println("ucak"+ucak.getSeviyePuani() + "siha"+siha.getSeviyePuani()+"\n");
                        kullanici.setSkor(ucak.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(siha.getSeviyePuani()+bilgisayar.getSkor());
                    }
                }
                if (kullaniciKart instanceof Siha) {
                    Siha siha = (Siha) kullaniciKart;
            
                    if (bilgisayarKart instanceof Firkateyn) {
                        Firkateyn firkateyn = (Firkateyn) bilgisayarKart;
                        int kullaniciVurus = siha.Vurus() ;
                        firkateyn.DurumGuncelle(kullaniciVurus);
                        //firkateyn.setDayaniklilik(firkateyn.getDayaniklilik() - kullaniciVurus);
                        if(firkateyn.getDayaniklilik()<=0 && firkateyn.getSeviyePuani()<=10){
                            siha.setSeviyePuani(siha.getSeviyePuani()+10);
                        }
                        else if(firkateyn.getDayaniklilik()<=0 && firkateyn.getSeviyePuani()>10){
                            siha.setSeviyePuani(firkateyn.getSeviyePuani()+siha.getSeviyePuani());
                        }                     
                        int bilgisayarVurus = firkateyn.Vurus() ;   
                        siha.DurumGuncelle(bilgisayarVurus);
                        //siha.setDayaniklilik(siha.getDayaniklilik() - bilgisayarVurus);
                        if(siha.getDayaniklilik()<=0 && siha.getSeviyePuani()<=10){
                            firkateyn.setSeviyePuani(firkateyn.getDayaniklilik()+10);
                        }
                        else if(siha.getDayaniklilik()<=0 && siha.getSeviyePuani()>10){
                            firkateyn.setSeviyePuani(firkateyn.getSeviyePuani()+siha.getSeviyePuani());
                        }
                        System.out.println("siha" + siha.getSeviyePuani() +"firkateyn"+ firkateyn.getSeviyePuani()+"\n"); 
                        
                        kullanici.setSkor(siha.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(firkateyn.getSeviyePuani()+bilgisayar.getSkor());
                    } 
                    else if (bilgisayarKart instanceof Obus) {
                        Obus obus = (Obus) bilgisayarKart;
                        int kullaniciVurus = siha.Vurus() + siha.karaVurusAvantaji();
                        obus.DurumGuncelle(kullaniciVurus);
                        //obus.setDayaniklilik(obus.getDayaniklilik() - kullaniciVurus);
                        if(obus.getDayaniklilik()<=0 && obus.getSeviyePuani()<=10){
                            siha.setSeviyePuani(siha.getSeviyePuani()+10);
                        }
                        else if(obus.getDayaniklilik()<=0 && obus.getSeviyePuani()>10){
                            siha.setSeviyePuani(obus.getSeviyePuani()+siha.getSeviyePuani());
                        }
                        int bilgisayarVurus = obus.Vurus();
                        siha.DurumGuncelle(bilgisayarVurus);
                        //siha.setDayaniklilik(siha.getDayaniklilik() - bilgisayarVurus);
                        if(siha.getDayaniklilik()<=0 && siha.getSeviyePuani()<=10){
                            obus.setSeviyePuani(obus.getSeviyePuani()+10);
                        }
                        else if(siha.getDayaniklilik()<=0 && siha.getSeviyePuani()>10){
                            obus.setSeviyePuani(obus.getSeviyePuani()+siha.getSeviyePuani());
                        }
                        System.out.println("siha"+siha.getSeviyePuani() + "obus"+obus.getSeviyePuani()+"\n");
                        kullanici.setSkor(siha.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(obus.getSeviyePuani()+bilgisayar.getSkor());
                    }
                    else if (bilgisayarKart instanceof Ucak) {
                        Ucak ucak1 = (Ucak) bilgisayarKart;
                        int kullaniciVurus = siha.Vurus();
                        ucak1.DurumGuncelle(kullaniciVurus);
                        //ucak1.setDayaniklilik(ucak1.getDayaniklilik() - kullaniciVurus);
                        if(ucak1.getDayaniklilik()<=0 && ucak1.getSeviyePuani()<=10){
                            siha.setSeviyePuani(siha.getSeviyePuani()+10);
                        }
                        else if(ucak1.getDayaniklilik()<=0 && ucak1.getSeviyePuani()>10){
                            siha.setSeviyePuani(ucak1.getSeviyePuani()+siha.getSeviyePuani());
                        }
                        int bilgisayarVurus = ucak1.Vurus();
                        siha.DurumGuncelle(bilgisayarVurus);
                        //siha.setDayaniklilik(siha.getDayaniklilik() - bilgisayarVurus);
                        if(siha.getDayaniklilik()<=0 && siha.getSeviyePuani()<=10){
                            ucak1.setSeviyePuani(ucak1.getSeviyePuani()+10);
                        }
                        else if(siha.getDayaniklilik()<=0 && siha.getSeviyePuani()>10){
                            ucak1.setSeviyePuani(ucak1.getSeviyePuani()+siha.getSeviyePuani());
                        }
                        System.out.println("siha"+siha.getSeviyePuani() +"ucak"+ ucak1.getSeviyePuani()+"\n");
                        kullanici.setSkor(kullanici.getSkor()+siha.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(ucak1.getSeviyePuani()+bilgisayar.getSkor());
                    }
                    else if (bilgisayarKart instanceof KFS) {
                        KFS Kfs = (KFS) bilgisayarKart;
                        int kullaniciVurus = siha.Vurus() + siha.karaVurusAvantaji();
                        Kfs.DurumGuncelle(kullaniciVurus);
                        //Kfs.setDayaniklilik(obus.getDayaniklilik() - kullaniciVurus);
                        if(Kfs.getDayaniklilik()<=0 && Kfs.getSeviyePuani()<=10){
                            siha.setSeviyePuani(siha.getSeviyePuani()+10);
                        }
                        else if(Kfs.getDayaniklilik()<=0 && Kfs.getSeviyePuani()>10){
                            siha.setSeviyePuani(Kfs.getSeviyePuani()+siha.getSeviyePuani());
                        }
                        int bilgisayarVurus = Kfs.Vurus() +Kfs.havaVurusAvantaji;
                        siha.DurumGuncelle(bilgisayarVurus);
                        //siha.setDayaniklilik(siha.getDayaniklilik() - bilgisayarVurus);
                        if(siha.getDayaniklilik()<=0 && siha.getSeviyePuani()<=10){
                            Kfs.setSeviyePuani(Kfs.getSeviyePuani()+10);
                        }
                        else if(siha.getDayaniklilik()<=0 && siha.getSeviyePuani()>10){
                            Kfs.setSeviyePuani(Kfs.getSeviyePuani()+siha.getSeviyePuani());
                        }
                        System.out.println("siha"+siha.getSeviyePuani() + "Kfs"+Kfs.getSeviyePuani()+"\n");
                        kullanici.setSkor(siha.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(Kfs.getSeviyePuani()+bilgisayar.getSkor());
                    }
                    else if (bilgisayarKart instanceof Sida) {
                        Sida sida = (Sida) bilgisayarKart;
                        int kullaniciVurus = siha.Vurus() ;
                        sida.DurumGuncelle(kullaniciVurus);
                        //sida.setDayaniklilik(obus.getDayaniklilik() - kullaniciVurus);
                        if(sida.getDayaniklilik()<=0 && sida.getSeviyePuani()<=10){
                            siha.setSeviyePuani(siha.getSeviyePuani()+10);
                        }
                        else if(sida.getDayaniklilik()<=0 && sida.getSeviyePuani()>10){
                            siha.setSeviyePuani(sida.getSeviyePuani()+siha.getSeviyePuani());
                        }
                        int bilgisayarVurus = sida.Vurus() +sida.havaVurusAvantaji();
                        siha.DurumGuncelle(bilgisayarVurus);
                        //siha.setDayaniklilik(siha.getDayaniklilik() - bilgisayarVurus);
                        if(siha.getDayaniklilik()<=0 && siha.getSeviyePuani()<=10){
                            sida.setSeviyePuani(sida.getSeviyePuani()+10);
                        }
                        else if(siha.getDayaniklilik()<=0 && siha.getSeviyePuani()>10){
                            sida.setSeviyePuani(sida.getSeviyePuani()+siha.getSeviyePuani());
                        }
                        System.out.println("siha"+siha.getSeviyePuani() + "sida"+sida.getSeviyePuani()+"\n");
                        kullanici.setSkor(siha.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(sida.getSeviyePuani()+bilgisayar.getSkor());
                    }
                    else if (bilgisayarKart instanceof Siha) {
                        Siha siha1 = (Siha) bilgisayarKart;
                        int kullaniciVurus = siha.Vurus();
                        siha1.DurumGuncelle(kullaniciVurus);
                        //siha.setDayaniklilik(obus.getDayaniklilik() - kullaniciVurus);
                        if(siha1.getDayaniklilik()<=0 && siha1.getSeviyePuani()<=10){
                            siha.setSeviyePuani(siha.getSeviyePuani()+10);
                        }
                        else if(siha1.getDayaniklilik()<=0 && siha1.getSeviyePuani()>10){
                            siha.setSeviyePuani(siha1.getSeviyePuani()+siha.getSeviyePuani());
                        }
                        int bilgisayarVurus = siha1.Vurus();
                        siha.DurumGuncelle(bilgisayarVurus);
                        //siha.setDayaniklilik(siha.getDayaniklilik() - bilgisayarVurus);
                        if(siha.getDayaniklilik()<=0 && siha.getSeviyePuani()<=10){
                            siha1.setSeviyePuani(siha1.getSeviyePuani()+10);
                        }
                        else if(siha.getDayaniklilik()<=0 && siha.getSeviyePuani()>10){
                            siha1.setSeviyePuani(siha1.getSeviyePuani()+siha.getSeviyePuani());
                        }
                        System.out.println("siha"+siha.getSeviyePuani() + "siha1"+siha1.getSeviyePuani()+"\n");
                        kullanici.setSkor(siha.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(siha1.getSeviyePuani()+bilgisayar.getSkor());
                    }
                }
                if (kullaniciKart instanceof Obus) {
                    Obus obus = (Obus) kullaniciKart;
            
                    if (bilgisayarKart instanceof Firkateyn) {
                        Firkateyn firkateyn = (Firkateyn) bilgisayarKart;
                        int kullaniciVurus = obus.Vurus() + obus.denizVurusAvantaji();
                        firkateyn.DurumGuncelle(kullaniciVurus);
                        //firkateyn.setDayaniklilik(firkateyn.getDayaniklilik() - kullaniciVurus);
                        if(firkateyn.getDayaniklilik()<=0 && firkateyn.getSeviyePuani()<=10){
                            obus.setSeviyePuani(obus.getSeviyePuani()+10);
                        }
                        else if(firkateyn.getDayaniklilik()<=0 && firkateyn.getSeviyePuani()>10){
                            obus.setSeviyePuani(firkateyn.getSeviyePuani()+obus.getSeviyePuani());
                        }
                        int bilgisayarVurus = firkateyn.Vurus() ;
                        obus.DurumGuncelle(bilgisayarVurus);
                        //obus.setDayaniklilik(obus.getDayaniklilik() - bilgisayarVurus);
                        if(obus.getDayaniklilik()<=0 && obus.getSeviyePuani()<=10){
                            firkateyn.setSeviyePuani(firkateyn.getSeviyePuani()+10);
                        }
                        else if(obus.getDayaniklilik()<=0 && obus.getSeviyePuani()>10){
                            firkateyn.setSeviyePuani(firkateyn.getSeviyePuani()+obus.getSeviyePuani());
                        }
                        System.out.println( "obus" + obus.getSeviyePuani() +"firkateyn"+ firkateyn.getSeviyePuani()+"\n");
                        kullanici.setSkor(obus.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(firkateyn.getSeviyePuani()+bilgisayar.getSkor());
                    } 
                    else if (bilgisayarKart instanceof Ucak) {
                        Ucak ucak = (Ucak) bilgisayarKart;
                        int kullaniciVurus = obus.Vurus();
                        ucak.DurumGuncelle(kullaniciVurus);
                        //ucak.setDayaniklilik(ucak.getDayaniklilik() - kullaniciVurus);
                        if(ucak.getDayaniklilik()<=0 && ucak.getSeviyePuani()<=10){
                            obus.setSeviyePuani(obus.getSeviyePuani()+10);
                        }
                        else if(ucak.getDayaniklilik()<=0 && ucak.getSeviyePuani()>0){
                            obus.setSeviyePuani(obus.getSeviyePuani()+ucak.getSeviyePuani());
                        }
                        int bilgisayarVurus = ucak.Vurus()+ucak.karaVurusAvantaji();
                        obus.DurumGuncelle(bilgisayarVurus);
                        //obus.setDayaniklilik(obus.getDayaniklilik() - bilgisayarVurus);
                        if(obus.getDayaniklilik()<=0 && obus.getSeviyePuani()<=10){
                            ucak.setSeviyePuani(ucak.getSeviyePuani()+10);
                        }
                        else if(obus.getDayaniklilik()<=0 && obus.getSeviyePuani()>0){
                            ucak.setSeviyePuani(obus.getSeviyePuani()+ucak.getSeviyePuani());
                        }
                        System.out.println("obus"+obus.getSeviyePuani() +"ucak"+ ucak.getSeviyePuani()+"\n");
                        kullanici.setSkor(obus.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(ucak.getSeviyePuani()+bilgisayar.getSkor());
                    }
                    else if (bilgisayarKart instanceof Obus) {
                        Obus obus1 = (Obus) bilgisayarKart;
                        int kullaniciVurus = obus.Vurus();
                        obus1.DurumGuncelle(kullaniciVurus);
                        //obus1.setDayaniklilik(obus1.getDayaniklilik() - kullaniciVurus);
                        if(obus1.getDayaniklilik()<=0 && obus1.getSeviyePuani()<=10){
                            obus.setSeviyePuani(obus.getSeviyePuani()+10);
                        }
                        else if(obus1.getDayaniklilik()<=0 && obus1.getSeviyePuani()>10){
                            obus.setSeviyePuani(obus1.getSeviyePuani()+obus.getSeviyePuani());
                        }
                        int bilgisayarVurus = obus1.Vurus() ;
                        obus.DurumGuncelle(bilgisayarVurus);
                        //obus.setDayaniklilik(obus.getDayaniklilik() - bilgisayarVurus);
                        if(obus.getDayaniklilik()<=0 && obus.getSeviyePuani()<=10){
                            obus1.setSeviyePuani(obus1.getSeviyePuani()+10);
                        }
                        else if(obus.getDayaniklilik()<=0 && obus.getSeviyePuani()>10){
                            obus1.setSeviyePuani(obus1.getSeviyePuani()+obus.getSeviyePuani());
                        }
                        System.out.println("obus"+ obus.getSeviyePuani() +"obuspc"+ obus1.getSeviyePuani()+"\n");
                        kullanici.setSkor(obus.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(obus1.getSeviyePuani()+bilgisayar.getSkor());
                    }
                    else if (bilgisayarKart instanceof KFS) {
                        KFS Kfs = (KFS) bilgisayarKart;
                        int kullaniciVurus = obus.Vurus();
                        Kfs.DurumGuncelle(kullaniciVurus);
                        //Kfs.setDayaniklilik(obus.getDayaniklilik() - kullaniciVurus);
                        if(Kfs.getDayaniklilik()<=0 && Kfs.getSeviyePuani()<=10){
                            obus.setSeviyePuani(obus.getSeviyePuani()+10);
                        }
                        else if(Kfs.getDayaniklilik()<=0 && Kfs.getSeviyePuani()>10){
                            obus.setSeviyePuani(Kfs.getSeviyePuani()+obus.getSeviyePuani());
                        }
                        int bilgisayarVurus = Kfs.Vurus();
                        obus.DurumGuncelle(bilgisayarVurus);
                        //obus.setDayaniklilik(obus.getDayaniklilik() - bilgisayarVurus);
                        if(obus.getDayaniklilik()<=0 && obus.getSeviyePuani()<=10){
                            Kfs.setSeviyePuani(Kfs.getSeviyePuani()+10);
                        }
                        else if(obus.getDayaniklilik()<=0 && obus.getSeviyePuani()>10){
                            Kfs.setSeviyePuani(Kfs.getSeviyePuani()+obus.getSeviyePuani());
                        }
                        System.out.println("obus"+obus.getSeviyePuani() + "Kfs"+Kfs.getSeviyePuani()+"\n");
                        kullanici.setSkor(obus.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(Kfs.getSeviyePuani()+bilgisayar.getSkor());
                    }
                    else if (bilgisayarKart instanceof Sida) {
                        Sida sida = (Sida) bilgisayarKart;
                        int kullaniciVurus = obus.Vurus() + obus.denizVurusAvantaji();
                        sida.DurumGuncelle(kullaniciVurus);
                        //sida.setDayaniklilik(obus.getDayaniklilik() - kullaniciVurus);
                        if(sida.getDayaniklilik()<=0 && sida.getSeviyePuani()<=10){
                            obus.setSeviyePuani(obus.getSeviyePuani()+10);
                        }
                        else if(sida.getDayaniklilik()<=0 && sida.getSeviyePuani()>10){
                            obus.setSeviyePuani(sida.getSeviyePuani()+obus.getSeviyePuani());
                        }
                        int bilgisayarVurus = sida.Vurus() + sida.karaVurusAvantaji;
                        obus.DurumGuncelle(bilgisayarVurus);
                        //obus.setDayaniklilik(obus.getDayaniklilik() - bilgisayarVurus);
                        if(obus.getDayaniklilik()<=0 && obus.getSeviyePuani()<=10){
                            sida.setSeviyePuani(sida.getSeviyePuani()+10);
                        }
                        else if(obus.getDayaniklilik()<=0 && obus.getSeviyePuani()>10){
                            sida.setSeviyePuani(sida.getSeviyePuani()+obus.getSeviyePuani());
                        }
                        System.out.println("obus"+obus.getSeviyePuani() + "sida"+sida.getSeviyePuani()+"\n");
                        kullanici.setSkor(obus.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(sida.getSeviyePuani()+bilgisayar.getSkor());
                    }
                    else if (bilgisayarKart instanceof Siha) {
                        Siha siha = (Siha) bilgisayarKart;
                        int kullaniciVurus = obus.Vurus();
                        siha.DurumGuncelle(kullaniciVurus);
                        //siha.setDayaniklilik(obus.getDayaniklilik() - kullaniciVurus);
                        if(siha.getDayaniklilik()<=0 && siha.getSeviyePuani()<=10){
                            obus.setSeviyePuani(obus.getSeviyePuani()+10);
                        }
                        else if(siha.getDayaniklilik()<=0 && siha.getSeviyePuani()>10){
                            obus.setSeviyePuani(siha.getSeviyePuani()+obus.getSeviyePuani());
                        }
                        int bilgisayarVurus = siha.Vurus() + siha.karaVurusAvantaji();
                        obus.DurumGuncelle(bilgisayarVurus);
                        //obus.setDayaniklilik(ucak.getDayaniklilik() - bilgisayarVurus);
                        if(obus.getDayaniklilik()<=0 && obus.getSeviyePuani()<=10){
                            siha.setSeviyePuani(siha.getSeviyePuani()+10);
                        }
                        else if(obus.getDayaniklilik()<=0 && obus.getSeviyePuani()>10){
                            siha.setSeviyePuani(obus.getSeviyePuani()+obus.getSeviyePuani());
                        }
                        System.out.println("obus"+obus.getSeviyePuani() + "siha"+siha.getSeviyePuani()+"\n");
                        kullanici.setSkor(obus.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(siha.getSeviyePuani()+bilgisayar.getSkor());
                    }
                }
                if (kullaniciKart instanceof KFS) {
                    KFS kfs = (KFS) kullaniciKart;
            
                    if (bilgisayarKart instanceof Firkateyn) {
                        Firkateyn firkateyn = (Firkateyn) bilgisayarKart;
                        int kullaniciVurus = kfs.Vurus() + kfs.denizVurusAvantaji();
                        firkateyn.DurumGuncelle(kullaniciVurus);
                        //firkateyn.setDayaniklilik(firkateyn.getDayaniklilik() - kullaniciVurus);
                        if(firkateyn.getDayaniklilik()<=0 && firkateyn.getSeviyePuani()<=10){
                            kfs.setSeviyePuani(kfs.getSeviyePuani()+10);
                        }
                        else if(firkateyn.getDayaniklilik()<=0 && firkateyn.getSeviyePuani()>10){
                            kfs.setSeviyePuani(firkateyn.getSeviyePuani()+kfs.getSeviyePuani());
                        }
                        int bilgisayarVurus = firkateyn.Vurus() ;
                        kfs.DurumGuncelle(bilgisayarVurus);
                        //kfs.setDayaniklilik(kfs.getDayaniklilik() - bilgisayarVurus);
                        if(kfs.getDayaniklilik()<=0 && kfs.getSeviyePuani()<=10){
                            firkateyn.setSeviyePuani(firkateyn.getSeviyePuani()+10);
                        }
                        else if(kfs.getDayaniklilik()<=0 && kfs.getSeviyePuani()>10){
                            firkateyn.setSeviyePuani(firkateyn.getSeviyePuani()+kfs.getSeviyePuani());
                        }
                        System.out.println( "kfs" + kfs.getSeviyePuani() +"firkateyn"+ firkateyn.getSeviyePuani()+"\n");
                        kullanici.setSkor(kfs.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(firkateyn.getSeviyePuani()+bilgisayar.getSkor());
                    } 
                    else if (bilgisayarKart instanceof Ucak) {
                        Ucak ucak = (Ucak) bilgisayarKart;
                        int kullaniciVurus = kfs.Vurus();
                        ucak.DurumGuncelle(kullaniciVurus);
                        //ucak.setDayaniklilik(ucak.getDayaniklilik() - kullaniciVurus);
                        if(ucak.getDayaniklilik()<=0 && ucak.getSeviyePuani()<=10){
                            kfs.setSeviyePuani(kfs.getSeviyePuani()+10);
                        }
                        else if(ucak.getDayaniklilik()<=0 && ucak.getSeviyePuani()>0){
                            kfs.setSeviyePuani(kfs.getSeviyePuani()+ucak.getSeviyePuani());
                        }
                        int bilgisayarVurus = ucak.Vurus()+ucak.karaVurusAvantaji();
                        kfs.DurumGuncelle(bilgisayarVurus);
                        //kfs.setDayaniklilik(kfs.getDayaniklilik() - bilgisayarVurus);
                        if(kfs.getDayaniklilik()<=0 && kfs.getSeviyePuani()<=10){
                            ucak.setSeviyePuani(ucak.getSeviyePuani()+10);
                        }
                        else if(kfs.getDayaniklilik()<=0 && kfs.getSeviyePuani()>0){
                            ucak.setSeviyePuani(kfs.getSeviyePuani()+ucak.getSeviyePuani());
                        }
                        System.out.println("kfs"+kfs.getSeviyePuani() +"ucak"+ ucak.getSeviyePuani()+"\n");
                        kullanici.setSkor(kfs.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(ucak.getSeviyePuani()+bilgisayar.getSkor());
                    }
                    else if (bilgisayarKart instanceof Obus) {
                        Obus obus = (Obus) bilgisayarKart;
                        int kullaniciVurus = kfs.Vurus();
                        obus.DurumGuncelle(kullaniciVurus);
                        //obus.setDayaniklilik(obus.getDayaniklilik() - kullaniciVurus);
                        if(obus.getDayaniklilik()<=0 && obus.getSeviyePuani()<=10){
                            kfs.setSeviyePuani(kfs.getSeviyePuani()+10);
                        }
                        else if(obus.getDayaniklilik()<=0 && obus.getSeviyePuani()>10){
                            kfs.setSeviyePuani(obus.getSeviyePuani()+kfs.getSeviyePuani());
                        }
                        int bilgisayarVurus = obus.Vurus() ;
                        kfs.DurumGuncelle(bilgisayarVurus);
                        //kfs.setDayaniklilik(kfs.getDayaniklilik() - bilgisayarVurus);
                        if(kfs.getDayaniklilik()<=0 && kfs.getSeviyePuani()<=10){
                            obus.setSeviyePuani(obus.getSeviyePuani()+10);
                        }
                        else if(kfs.getDayaniklilik()<=0 && kfs.getSeviyePuani()>10){
                            obus.setSeviyePuani(obus.getSeviyePuani()+kfs.getSeviyePuani());
                        }
                        System.out.println("kfs"+ kfs.getSeviyePuani() +"kfs"+ obus.getSeviyePuani()+"\n");
                        kullanici.setSkor(kfs.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(obus.getSeviyePuani()+bilgisayar.getSkor());
                    }
                    else if (bilgisayarKart instanceof KFS) {
                        KFS Kfs1 = (KFS) bilgisayarKart;
                        int kullaniciVurus = kfs.Vurus();
                        Kfs1.DurumGuncelle(kullaniciVurus);
                        //Kfs1.setDayaniklilik(kfs.getDayaniklilik() - kullaniciVurus);
                        if(Kfs1.getDayaniklilik()<=0 && Kfs1.getSeviyePuani()<=10){
                            kfs.setSeviyePuani(kfs.getSeviyePuani()+10);
                        }
                        else if(Kfs1.getDayaniklilik()<=0 && Kfs1.getSeviyePuani()>10){
                            kfs.setSeviyePuani(Kfs1.getSeviyePuani()+kfs.getSeviyePuani());
                        }
                        int bilgisayarVurus = Kfs1.Vurus();
                        kfs.DurumGuncelle(bilgisayarVurus);
                        //kfs.setDayaniklilik(kfs.getDayaniklilik() - bilgisayarVurus);
                        if(kfs.getDayaniklilik()<=0 && kfs.getSeviyePuani()<=10){
                            Kfs1.setSeviyePuani(Kfs1.getSeviyePuani()+10);
                        }
                        else if(kfs.getDayaniklilik()<=0 && kfs.getSeviyePuani()>10){
                            Kfs1.setSeviyePuani(Kfs1.getSeviyePuani()+kfs.getSeviyePuani());
                        }
                        System.out.println("kfs"+kfs.getSeviyePuani() + "Kfs1"+Kfs1.getSeviyePuani()+"\n");
                        kullanici.setSkor(kfs.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(Kfs1.getSeviyePuani()+bilgisayar.getSkor());
                    }
                    else if (bilgisayarKart instanceof Sida) {
                        Sida sida = (Sida) bilgisayarKart;
                        int kullaniciVurus = kfs.Vurus() + kfs.denizVurusAvantaji();
                        sida.DurumGuncelle(kullaniciVurus);
                        //sida.setDayaniklilik(kfs.getDayaniklilik() - kullaniciVurus);
                        if(sida.getDayaniklilik()<=0 && sida.getSeviyePuani()<=10){
                            kfs.setSeviyePuani(kfs.getSeviyePuani()+10);
                        }
                        else if(sida.getDayaniklilik()<=0 && sida.getSeviyePuani()>10){
                            kfs.setSeviyePuani(sida.getSeviyePuani()+kfs.getSeviyePuani());
                        }
                        int bilgisayarVurus = sida.Vurus() + sida.karaVurusAvantaji;
                        kfs.DurumGuncelle(bilgisayarVurus);
                        //kfs.setDayaniklilik(kfs.getDayaniklilik() - bilgisayarVurus);
                        if(kfs.getDayaniklilik()<=0 && kfs.getSeviyePuani()<=10){
                            sida.setSeviyePuani(sida.getSeviyePuani()+10);
                        }
                        else if(kfs.getDayaniklilik()<=0 && kfs.getSeviyePuani()>10){
                            sida.setSeviyePuani(sida.getSeviyePuani()+kfs.getSeviyePuani());
                        }
                        System.out.println("kfs"+kfs.getSeviyePuani() + "sida"+sida.getSeviyePuani()+"\n");
                        kullanici.setSkor(kfs.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(sida.getSeviyePuani()+bilgisayar.getSkor());
                    }
                    else if (bilgisayarKart instanceof Siha) {
                        Siha siha = (Siha) bilgisayarKart;
                        int kullaniciVurus = kfs.Vurus();
                        siha.DurumGuncelle(kullaniciVurus);
                        //siha.setDayaniklilik(kfs.getDayaniklilik() - kullaniciVurus);
                        if(siha.getDayaniklilik()<=0 && siha.getSeviyePuani()<=10){
                            kfs.setSeviyePuani(kfs.getSeviyePuani()+10);
                        }
                        else if(siha.getDayaniklilik()<=0 && siha.getSeviyePuani()>10){
                            kfs.setSeviyePuani(siha.getSeviyePuani()+kfs.getSeviyePuani());
                        }
                        int bilgisayarVurus = siha.Vurus() + siha.karaVurusAvantaji();
                        kfs.DurumGuncelle(bilgisayarVurus);
                        //kfs.setDayaniklilik(ucak.getDayaniklilik() - bilgisayarVurus);
                        if(kfs.getDayaniklilik()<=0 && kfs.getSeviyePuani()<=10){
                            siha.setSeviyePuani(siha.getSeviyePuani()+10);
                        }
                        else if(kfs.getDayaniklilik()<=0 && kfs.getSeviyePuani()>10){
                            siha.setSeviyePuani(kfs.getSeviyePuani()+kfs.getSeviyePuani());
                        }
                        System.out.println("kfs"+kfs.getSeviyePuani() + "siha"+siha.getSeviyePuani()+"\n");
                        kullanici.setSkor(kfs.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(siha.getSeviyePuani()+bilgisayar.getSkor());
                    }
                }
                if (kullaniciKart instanceof Firkateyn) {
                    Firkateyn firkateyn = (Firkateyn) kullaniciKart;
            
                    if (bilgisayarKart instanceof Ucak) {
                        Ucak ucak = (Ucak) bilgisayarKart;
                        int kullaniciVurus = firkateyn.Vurus() + firkateyn.havaVurusAvantaji();
                        ucak.DurumGuncelle(kullaniciVurus);  
                       //ucak.setDayaniklilik(ucak.getDayaniklilik() - kullaniciVurus);
                        if(ucak.getDayaniklilik()<=0 && ucak.getSeviyePuani()<=10){
                            firkateyn.setSeviyePuani(firkateyn.getSeviyePuani()+10);
                        }
                        else if(ucak.getDayaniklilik()<=0 && ucak.getSeviyePuani()>0){
                            firkateyn.setSeviyePuani(firkateyn.getSeviyePuani()+ucak.getSeviyePuani());
                        }
                        int bilgisayarVurus = ucak.Vurus();
                        firkateyn.DurumGuncelle(bilgisayarVurus);
                        //firkateyn.setDayaniklilik(firkateyn.getDayaniklilik() - bilgisayarVurus);
                        if(firkateyn.getDayaniklilik()<=0 && firkateyn.getSeviyePuani()<=10){
                            ucak.setSeviyePuani(ucak.getSeviyePuani()+10);
                        }
                        else if(firkateyn.getDayaniklilik()<=0 && firkateyn.getSeviyePuani()>0){
                            ucak.setSeviyePuani(firkateyn.getSeviyePuani()+ucak.getSeviyePuani());
                        }
                        System.out.println("firkateyn" +firkateyn.getSeviyePuani() + "ucak"+ucak.getSeviyePuani()+"\n");
                        kullanici.setSkor(firkateyn.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(ucak.getSeviyePuani()+bilgisayar.getSkor());
                    }
                    else if (bilgisayarKart instanceof Obus) {
                        Obus obus = (Obus) bilgisayarKart;
                        int kullaniciVurus = firkateyn.Vurus();
                        obus.DurumGuncelle(kullaniciVurus);
                        //obus.setDayaniklilik(obus.getDayaniklilik() - kullaniciVurus);
                        if(obus.getDayaniklilik()<=0 && obus.getSeviyePuani()<=10){
                            firkateyn.setSeviyePuani(firkateyn.getSeviyePuani()+10);
                        }
                        else if(obus.getDayaniklilik()<=0 && obus.getSeviyePuani()>10){
                            firkateyn.setSeviyePuani(obus.getSeviyePuani()+firkateyn.getSeviyePuani());
                        }
                        int bilgisayarVurus = obus.Vurus()+obus.denizVurusAvantaji();
                        firkateyn.DurumGuncelle(bilgisayarVurus);
                        //firkateyn.setDayaniklilik(firkateyn.getDayaniklilik() - bilgisayarVurus);
                        if(firkateyn.getDayaniklilik()<=0 && firkateyn.getSeviyePuani()<=10){
                            obus.setSeviyePuani(obus.getSeviyePuani()+10);
                        }
                        else if(firkateyn.getDayaniklilik()<=0 && firkateyn.getSeviyePuani()>10){
                            obus.setSeviyePuani(obus.getSeviyePuani()+firkateyn.getSeviyePuani());
                        }
                        System.out.println("firkateyn"+firkateyn.getSeviyePuani() + "obus"+obus.getSeviyePuani()+"\n");
                        kullanici.setSkor(firkateyn.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(obus.getSeviyePuani()+bilgisayar.getSkor());
                    }
                    else if (bilgisayarKart instanceof Firkateyn) {
                        Firkateyn firkateyn1 = (Firkateyn) bilgisayarKart;
                        int kullaniciVurus = firkateyn.Vurus();
                        firkateyn1.DurumGuncelle(kullaniciVurus);
                        //firkateyn1.setDayaniklilik(firkateyn1.getDayaniklilik() - kullaniciVurus);
                        if(firkateyn1.getDayaniklilik()<=0 && firkateyn1.getSeviyePuani()<=10){
                            firkateyn.setSeviyePuani(firkateyn.getSeviyePuani()+10);
                        }
                        else if(firkateyn1.getDayaniklilik()<=0 && firkateyn1.getSeviyePuani()>10){
                            firkateyn.setSeviyePuani(firkateyn1.getSeviyePuani()+firkateyn.getSeviyePuani());
                        }
                        int bilgisayarVurus = firkateyn1.Vurus() ;
                        firkateyn.DurumGuncelle(bilgisayarVurus);
                        //firkateyn.setDayaniklilik(firkateyn.getDayaniklilik() - bilgisayarVurus);
                        if(firkateyn.getDayaniklilik()<=0 && firkateyn.getSeviyePuani()<=10){
                            firkateyn1.setSeviyePuani(firkateyn1.getSeviyePuani()+10);
                        }
                        else if(firkateyn.getDayaniklilik()<=0 && firkateyn.getSeviyePuani()>10){
                            firkateyn1.setSeviyePuani(firkateyn1.getSeviyePuani()+firkateyn.getSeviyePuani());
                        }
                        System.out.println(" firkateyn"+firkateyn.getSeviyePuani() +"firkateynpc"+ firkateyn1.getSeviyePuani()+"\n");
                        kullanici.setSkor(firkateyn.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(firkateyn1.getSeviyePuani()+bilgisayar.getSkor());
                    }
                    else if (bilgisayarKart instanceof KFS) {
                        KFS Kfs = (KFS) bilgisayarKart;
                        int kullaniciVurus = firkateyn.Vurus();
                        Kfs.DurumGuncelle(kullaniciVurus);
                        //Kfs.setDayaniklilik(firkateyn.getDayaniklilik() - kullaniciVurus);
                        if(Kfs.getDayaniklilik()<=0 && Kfs.getSeviyePuani()<=10){
                            firkateyn.setSeviyePuani(firkateyn.getSeviyePuani()+10);
                        }
                        else if(Kfs.getDayaniklilik()<=0 && Kfs.getSeviyePuani()>10){
                            firkateyn.setSeviyePuani(Kfs.getSeviyePuani()+firkateyn.getSeviyePuani());
                        }
                        int bilgisayarVurus = Kfs.Vurus() + Kfs.denizVurusAvantaji();
                        firkateyn.DurumGuncelle(bilgisayarVurus);
                        //firkateyn.setDayaniklilik(firkateyn.getDayaniklilik() - bilgisayarVurus);
                        if(firkateyn.getDayaniklilik()<=0 && firkateyn.getSeviyePuani()<=10){
                            Kfs.setSeviyePuani(Kfs.getSeviyePuani()+10);
                        }
                        else if(firkateyn.getDayaniklilik()<=0 && firkateyn.getSeviyePuani()>10){
                            Kfs.setSeviyePuani(Kfs.getSeviyePuani()+firkateyn.getSeviyePuani());
                        }
                        System.out.println("firkateyn"+firkateyn.getSeviyePuani() + "Kfs"+Kfs.getSeviyePuani()+"\n");
                        kullanici.setSkor(firkateyn.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(Kfs.getSeviyePuani()+bilgisayar.getSkor());
                    }
                    else if (bilgisayarKart instanceof Sida) {
                        Sida sida = (Sida) bilgisayarKart;
                        int kullaniciVurus = firkateyn.Vurus();
                        sida.DurumGuncelle(kullaniciVurus);
                        //sida.setDayaniklilik(firkateyn.getDayaniklilik() - kullaniciVurus);
                        if(sida.getDayaniklilik()<=0 && sida.getSeviyePuani()<=10){
                            firkateyn.setSeviyePuani(firkateyn.getSeviyePuani()+10);
                        }
                        else if(sida.getDayaniklilik()<=0 && sida.getSeviyePuani()>10){
                            firkateyn.setSeviyePuani(sida.getSeviyePuani()+firkateyn.getSeviyePuani());
                        }
                        int bilgisayarVurus = sida.Vurus();
                        firkateyn.DurumGuncelle(bilgisayarVurus);
                        //firkateyn.setDayaniklilik(obus.getDayaniklilik() - bilgisayarVurus);
                        if(firkateyn.getDayaniklilik()<=0 && firkateyn.getSeviyePuani()<=10){
                            sida.setSeviyePuani(sida.getSeviyePuani()+10);
                        }
                        else if(firkateyn.getDayaniklilik()<=0 && firkateyn.getSeviyePuani()>10){
                            sida.setSeviyePuani(sida.getSeviyePuani()+firkateyn.getSeviyePuani());
                        }
                        System.out.println("firkateyn"+firkateyn.getSeviyePuani() + "sida"+sida.getSeviyePuani()+"\n");
                        kullanici.setSkor(firkateyn.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(sida.getSeviyePuani()+bilgisayar.getSkor());
                    }
                    else if (bilgisayarKart instanceof Siha) {
                        Siha siha = (Siha) bilgisayarKart;
                        int kullaniciVurus = firkateyn.Vurus() + firkateyn.havaVurusAvantaji();
                        siha.DurumGuncelle(kullaniciVurus);
                        //siha.setDayaniklilik(firkateyn.getDayaniklilik() - kullaniciVurus);
                        if(siha.getDayaniklilik()<=0 && siha.getSeviyePuani()<=10){
                            firkateyn.setSeviyePuani(firkateyn.getSeviyePuani()+10);
                        }
                        else if(siha.getDayaniklilik()<=0 && siha.getSeviyePuani()>10){
                            firkateyn.setSeviyePuani(siha.getSeviyePuani()+firkateyn.getSeviyePuani());
                        }
                        int bilgisayarVurus = siha.Vurus() + siha.denizVurusAvantaji;
                        firkateyn.DurumGuncelle(bilgisayarVurus);
                        //firkateyn.setDayaniklilik(ucak.getDayaniklilik() - bilgisayarVurus);
                        if(firkateyn.getDayaniklilik()<=0 && firkateyn.getSeviyePuani()<=10){
                            siha.setSeviyePuani(siha.getSeviyePuani()+10);
                        }
                        else if(firkateyn.getDayaniklilik()<=0 && firkateyn.getSeviyePuani()>10){
                            siha.setSeviyePuani(firkateyn.getSeviyePuani()+firkateyn.getSeviyePuani());
                        }
                        System.out.println("firkateyn"+firkateyn.getSeviyePuani() + "siha"+siha.getSeviyePuani()+"\n");
                        kullanici.setSkor(firkateyn.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(siha.getSeviyePuani()+bilgisayar.getSkor());
                    }
                }
                if (kullaniciKart instanceof Sida) {
                    Sida sida = (Sida) kullaniciKart;
            
                    if (bilgisayarKart instanceof Ucak) {
                        Ucak ucak = (Ucak) bilgisayarKart;
                        int kullaniciVurus = sida.Vurus() + sida.havaVurusAvantaji();
                        ucak.DurumGuncelle(kullaniciVurus);  
                       //ucak.setDayaniklilik(ucak.getDayaniklilik() - kullaniciVurus);
                        if(ucak.getDayaniklilik()<=0 && ucak.getSeviyePuani()<=10){
                            sida.setSeviyePuani(sida.getDayaniklilik()+10);
                        }
                        else if(ucak.getDayaniklilik()<=0 && ucak.getSeviyePuani()>0){
                            sida.setSeviyePuani(sida.getSeviyePuani()+ucak.getSeviyePuani());
                        }
                        int bilgisayarVurus = ucak.Vurus();
                        sida.DurumGuncelle(bilgisayarVurus);
                        //sida.setDayaniklilik(sida.getDayaniklilik() - bilgisayarVurus);
                        if(sida.getDayaniklilik()<=0 && sida.getSeviyePuani()<=10){
                            ucak.setSeviyePuani(ucak.getSeviyePuani()+10);
                        }
                        else if(sida.getDayaniklilik()<=0 && sida.getSeviyePuani()>0){
                            ucak.setSeviyePuani(sida.getSeviyePuani()+ucak.getSeviyePuani());
                        }
                        System.out.println("sida" +sida.getSeviyePuani() + "ucak"+ucak.getSeviyePuani()+"\n");
                        kullanici.setSkor(sida.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(ucak.getSeviyePuani()+bilgisayar.getSkor());
                    }
                    else if (bilgisayarKart instanceof Obus) {
                        Obus obus = (Obus) bilgisayarKart;
                        int kullaniciVurus = sida.Vurus();
                        obus.DurumGuncelle(kullaniciVurus);
                        //obus.setDayaniklilik(obus.getDayaniklilik() - kullaniciVurus);
                        if(obus.getDayaniklilik()<=0 && obus.getSeviyePuani()<=10){
                            sida.setSeviyePuani(sida.getSeviyePuani()+10);
                        }
                        else if(obus.getDayaniklilik()<=0 && obus.getSeviyePuani()>10){
                            sida.setSeviyePuani(obus.getSeviyePuani()+sida.getSeviyePuani());
                        }
                        int bilgisayarVurus = obus.Vurus()+obus.denizVurusAvantaji();
                        sida.DurumGuncelle(bilgisayarVurus);
                        //sida.setDayaniklilik(sida.getDayaniklilik() - bilgisayarVurus);
                        if(sida.getDayaniklilik()<=0 && sida.getSeviyePuani()<=10){
                            obus.setSeviyePuani(obus.getSeviyePuani()+10);
                        }
                        else if(sida.getDayaniklilik()<=0 && sida.getSeviyePuani()>10){
                            obus.setSeviyePuani(obus.getSeviyePuani()+sida.getSeviyePuani());
                        }
                        System.out.println("sida"+sida.getSeviyePuani() + "obus"+obus.getSeviyePuani()+"\n");
                        kullanici.setSkor(sida.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(obus.getSeviyePuani()+bilgisayar.getSkor());
                    }
                    else if (bilgisayarKart instanceof Firkateyn) {
                        Firkateyn firkateyn = (Firkateyn) bilgisayarKart;
                        int kullaniciVurus = sida.Vurus();
                        firkateyn.DurumGuncelle(kullaniciVurus);
                        //firkateyn.setDayaniklilik(firkateyn.getDayaniklilik() - kullaniciVurus);
                        if(firkateyn.getDayaniklilik()<=0 && firkateyn.getSeviyePuani()<=10){
                            sida.setSeviyePuani(sida.getSeviyePuani()+10);
                        }
                        else if(firkateyn.getDayaniklilik()<=0 && firkateyn.getSeviyePuani()>10){
                            sida.setSeviyePuani(firkateyn.getSeviyePuani()+sida.getSeviyePuani());
                        }
                        int bilgisayarVurus = firkateyn.Vurus() ;
                        sida.DurumGuncelle(bilgisayarVurus);
                        //sida.setDayaniklilik(sida.getDayaniklilik() - bilgisayarVurus);
                        if(sida.getDayaniklilik()<=0 && sida.getSeviyePuani()<=10){
                            firkateyn.setSeviyePuani(firkateyn.getSeviyePuani()+10);
                        }
                        else if(sida.getDayaniklilik()<=0 && sida.getSeviyePuani()>10){
                            firkateyn.setSeviyePuani(firkateyn.getSeviyePuani()+sida.getSeviyePuani());
                        }
                        System.out.println(" sida"+sida.getSeviyePuani() +"firkateyn"+ firkateyn.getSeviyePuani()+"\n");
                        kullanici.setSkor(sida.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(firkateyn.getSeviyePuani()+bilgisayar.getSkor());
                    }
                    else if (bilgisayarKart instanceof KFS) {
                        KFS Kfs = (KFS) bilgisayarKart;
                        int kullaniciVurus = sida.Vurus();
                        Kfs.DurumGuncelle(kullaniciVurus);
                        //Kfs.setDayaniklilik(sida.getDayaniklilik() - kullaniciVurus);
                        if(Kfs.getDayaniklilik()<=0 && Kfs.getSeviyePuani()<=10){
                            sida.setSeviyePuani(sida.getSeviyePuani()+10);
                        }
                        else if(Kfs.getDayaniklilik()<=0 && Kfs.getSeviyePuani()>10){
                            sida.setSeviyePuani(Kfs.getSeviyePuani()+sida.getSeviyePuani());
                        }
                        int bilgisayarVurus = Kfs.Vurus() + Kfs.denizVurusAvantaji();
                        sida.DurumGuncelle(bilgisayarVurus);
                        //sida.setDayaniklilik(sida.getDayaniklilik() - bilgisayarVurus);
                        if(sida.getDayaniklilik()<=0 && sida.getSeviyePuani()<=10){
                            Kfs.setSeviyePuani(Kfs.getSeviyePuani()+10);
                        }
                        else if(sida.getDayaniklilik()<=0 && sida.getSeviyePuani()>10){
                            Kfs.setSeviyePuani(Kfs.getSeviyePuani()+sida.getSeviyePuani());
                        }
                        System.out.println("sida"+sida.getSeviyePuani() + "Kfs"+Kfs.getSeviyePuani()+"\n");
                        kullanici.setSkor(sida.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(Kfs.getSeviyePuani()+bilgisayar.getSkor());
                    }
                    else if (bilgisayarKart instanceof Sida) {
                        Sida sida1 = (Sida) bilgisayarKart;
                        int kullaniciVurus = sida.Vurus();
                        sida1.DurumGuncelle(kullaniciVurus);
                        //sida1.setDayaniklilik(sida.getDayaniklilik() - kullaniciVurus);
                        if(sida1.getDayaniklilik()<=0 && sida1.getSeviyePuani()<=10){
                            sida.setSeviyePuani(sida.getSeviyePuani()+10);
                        }
                        else if(sida1.getDayaniklilik()<=0 && sida1.getSeviyePuani()>10){
                            sida.setSeviyePuani(sida1.getSeviyePuani()+sida.getSeviyePuani());
                        }
                        int bilgisayarVurus = sida1.Vurus();
                        sida.DurumGuncelle(bilgisayarVurus);
                        //sida.setDayaniklilik(obus.getDayaniklilik() - bilgisayarVurus);
                        if(sida.getDayaniklilik()<=0 && sida.getSeviyePuani()<=10){
                            sida1.setSeviyePuani(sida1.getSeviyePuani()+10);
                        }
                        else if(sida.getDayaniklilik()<=0 && sida.getSeviyePuani()>10){
                            sida1.setSeviyePuani(sida1.getSeviyePuani()+sida.getSeviyePuani());
                        }
                        System.out.println("sida"+sida.getSeviyePuani() + "sida1"+sida1.getSeviyePuani()+"\n");
                        kullanici.setSkor(sida.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(sida1.getSeviyePuani()+bilgisayar.getSkor());
                    }
                    else if (bilgisayarKart instanceof Siha) {
                        Siha siha = (Siha) bilgisayarKart;
                        int kullaniciVurus = sida.Vurus() + sida.havaVurusAvantaji();
                        siha.DurumGuncelle(kullaniciVurus);
                        //siha.setDayaniklilik(sida.getDayaniklilik() - kullaniciVurus);
                        if(siha.getDayaniklilik()<=0 && siha.getSeviyePuani()<=10){
                            sida.setSeviyePuani(sida.getSeviyePuani()+10);
                        }
                        else if(siha.getDayaniklilik()<=0 && siha.getSeviyePuani()>10){
                            sida.setSeviyePuani(siha.getSeviyePuani()+sida.getSeviyePuani());
                        }
                        int bilgisayarVurus = siha.Vurus() + siha.denizVurusAvantaji;
                        sida.DurumGuncelle(bilgisayarVurus);
                        //sida.setDayaniklilik(ucak.getDayaniklilik() - bilgisayarVurus);
                        if(sida.getDayaniklilik()<=0 && sida.getSeviyePuani()<=10){
                            siha.setSeviyePuani(siha.getSeviyePuani()+10);
                        }
                        else if(sida.getDayaniklilik()<=0 && sida.getSeviyePuani()>10){
                            siha.setSeviyePuani(sida.getSeviyePuani()+sida.getSeviyePuani());
                        }
                        System.out.println("sida"+sida.getSeviyePuani() + "siha"+siha.getSeviyePuani()+"\n");
                        kullanici.setSkor(sida.getSeviyePuani()+kullanici.getSkor());
                        bilgisayar.setSkor(siha.getSeviyePuani()+bilgisayar.getSkor());
                    }
                }

                for (int k = kullaniciKartlari.size() - 1; k >= 0; k--) { 
                    Object kart = kullaniciKartlari.get(k);
                    if (kart instanceof Ucak && ((Ucak) kart).getDayaniklilik() <= 0) {
                        System.out.println(((Ucak) kart).getName() + " öldü ve kullanıcı kartlarından çıkarılıyor.");
                        kullaniciKartlari.remove(k);
                    } else if (kart instanceof Obus && ((Obus) kart).getDayaniklilik() <= 0) {
                        System.out.println(((Obus) kart).getName() + " öldü ve kullanıcı kartlarından çıkarılıyor.");
                        kullaniciKartlari.remove(k);
                    } else if (kart instanceof Firkateyn && ((Firkateyn) kart).getDayaniklilik() <= 0) {
                        System.out.println(((Firkateyn) kart).getName() + " öldü ve kullanıcı kartlarından çıkarılıyor.");
                        kullaniciKartlari.remove(k);
                    }
                    else if (kart instanceof Sida && ((Sida) kart).getDayaniklilik() <= 0) {
                        System.out.println(((Sida) kart).getName() + " öldü ve kullanıcı kartlarından çıkarılıyor.");
                        kullaniciKartlari.remove(k);
                    }
                    else if (kart instanceof Siha && ((Siha) kart).getDayaniklilik() <= 0) {
                        System.out.println(((Siha) kart).getName() + " öldü ve kullanıcı kartlarından çıkarılıyor.");
                        kullaniciKartlari.remove(k);
                    }
                    else if (kart instanceof KFS && ((KFS) kart).getDayaniklilik() <= 0) {
                        System.out.println(((KFS) kart).getName() + " öldü ve kullanıcı kartlarından çıkarılıyor.");
                        kullaniciKartlari.remove(k);
                    }
                }

                for (int j = bilgisayarKartlari.size() - 1; j >= 0; j--) {
                    Object kart = bilgisayarKartlari.get(j);
                    if (kart instanceof Ucak && ((Ucak) kart).getDayaniklilik() <= 0) {
                        System.out.println(((Ucak) kart).getName() + " öldü ve bilgisayar kartlarından çıkarılıyor.");
                        bilgisayarKartlari.remove(j);
                    } else if (kart instanceof Obus && ((Obus) kart).getDayaniklilik() <= 0) {
                         System.out.println(((Obus) kart).getName() + " öldü ve bilgisayar kartlarından çıkarılıyor.");
                        bilgisayarKartlari.remove(j);
                    } else if (kart instanceof Firkateyn && ((Firkateyn) kart).getDayaniklilik() <= 0) {
                        System.out.println(((Firkateyn) kart).getName() + " öldü ve bilgisayar kartlarından çıkarılıyor.");
                        bilgisayarKartlari.remove(j);
                    }else if (kart instanceof Sida && ((Sida) kart).getDayaniklilik() <= 0) {
                        System.out.println(((Sida) kart).getName() + " öldü ve bilgisayar kartlarından çıkarılıyor.");
                        bilgisayarKartlari.remove(j);
                    }
                    else if (kart instanceof Siha && ((Siha) kart).getDayaniklilik() <= 0) {
                        System.out.println(((Siha) kart).getName() + " öldü ve bilgisayar kartlarından çıkarılıyor.");
                        bilgisayarKartlari.remove(j);
                    }
                    else if (kart instanceof KFS && ((KFS) kart).getDayaniklilik() <= 0) {
                        System.out.println(((KFS) kart).getName() + " öldü ve bilgisayar kartlarından çıkarılıyor.");
                        bilgisayarKartlari.remove(j);
                    }
                }
            }
            
            if(kullaniciKartlari.isEmpty()){
                System.out.println("Bilgisayar kazandi..");
            }
            else if(bilgisayarKartlari.isEmpty()){
                System.out.println("Kullanici kazandi..");
            }
            kullaniciskor = kullanici.getSkor();
            bilgisayarskor = bilgisayar.getSkor();
        
        System.out.println("\nOyun bitti. Tüm turlar oynandı!");    

    
        Object kullaniciYeniKart = rastgeleKartEkle(kullaniciKartlari, rastgeleKartTuru());
        Object bilgisayarYeniKart = rastgeleKartEkle(bilgisayarKartlari, rastgeleKartTuru());
        
    
        // Yeni eklenen kart görselleri
        String kullaniciKartImagePath = getImagePath(kullaniciYeniKart);
        ImageView kullaniciKartImageView = new ImageView(new Image(kullaniciKartImagePath));
        kullaniciKartImageView.setFitWidth(100);
        kullaniciKartImageView.setFitHeight(150);
    
        String bilgisayarKartImagePath = getImagePath(bilgisayarYeniKart);
        ImageView bilgisayarKartImageView = new ImageView(new Image(bilgisayarKartImagePath));
        bilgisayarKartImageView.setFitWidth(100);
        bilgisayarKartImageView.setFitHeight(150);
    
        // Kullanıcı kartlarını göster
        HBox kullaniciKartlarBox = new HBox(10);
        for (Object kart : secilenKartlar) {
            String imagePath = getImagePath(kart);
            ImageView imageView = new ImageView(new Image(imagePath));
            imageView.setFitWidth(100);
            imageView.setFitHeight(150);
            kullaniciKartlarBox.getChildren().add(imageView);
        }
    
        // Bilgisayar kartlarını göster
        HBox bilgisayarKartlarBox = new HBox(10);
        for (Object kart : bilgisayarSecilenKartlar) {
            String imagePath = getImagePath(kart);
            ImageView imageView = new ImageView(new Image(imagePath));
            imageView.setFitWidth(100);
            imageView.setFitHeight(150);
            bilgisayarKartlarBox.getChildren().add(imageView);
        }
    
        // Yeni eklenen kartların ayrı olarak gösterimi
        VBox kullaniciYeniKartBox = new VBox(5, new Label("Yeni Eklenen Kart:"), kullaniciKartImageView);
        VBox bilgisayarYeniKartBox = new VBox(5, new Label("Yeni Eklenen Kart:"), bilgisayarKartImageView);
    
     // Kullanıcı ve bilgisayar kartlarını aynı satırda düzenle
        HBox kullaniciKartRow = new HBox();
        kullaniciKartRow.setSpacing(10); // Kartlar arası boşluk
        Region solBosluk = new Region(); 
        HBox.setHgrow(solBosluk, Priority.ALWAYS); // Sol boşluğu genişletmek için
        Region extraSolBosluk = new Region(); // Ekstra bir boşluk daha ekle
        HBox.setHgrow(extraSolBosluk, Priority.ALWAYS);

        kullaniciKartRow.getChildren().addAll(extraSolBosluk, kullaniciYeniKartBox, solBosluk, kullaniciKartlarBox);

        HBox bilgisayarKartRow = new HBox();
        bilgisayarKartRow.setSpacing(10); // Kartlar arası boşluk
        Region sagBosluk = new Region();
        HBox.setHgrow(sagBosluk, Priority.ALWAYS); // Sağ boşluğu genişletmek için
        Region extraSagBosluk = new Region(); // Ekstra bir boşluk daha ekle
        HBox.setHgrow(extraSagBosluk, Priority.ALWAYS);

        bilgisayarKartRow.getChildren().addAll(bilgisayarKartlarBox, sagBosluk, extraSagBosluk, bilgisayarYeniKartBox);


    
        // Kullanıcı ve bilgisayar kartlarını birleştir
        Label kullaniciKartLabel = new Label("Kullanıcı Kartları:");
        Label bilgisayarKartLabel = new Label("Bilgisayar Kartları:");
    
        VBox kullaniciKartBoxLocal = new VBox(5, kullaniciKartLabel, kullaniciKartRow);
        VBox bilgisayarKartBoxLocal = new VBox(5, bilgisayarKartLabel, bilgisayarKartRow);
    
        Region space = new Region();
        space.setMinWidth(50);
    
        HBox kartlarContainer = new HBox(20, kullaniciKartBoxLocal, space, bilgisayarKartBoxLocal);
        kartlarContainer.setStyle("-fx-alignment: center;");
        turGecmisiListesi.getItems().add(kartlarContainer);
    
               // Tur ilerlemesi
        secilenKartlar.clear();
        secilenKartlarLabel.setText("Seçilen Kartlar: ");
        mevcutTur++;  // Gelecek tur için sayacı artır

// *Oyun bitti kontrolü* burada yapılacak
    if (mevcutTur > turSayisi) {
        // Eğer oyun bitti ise
        oyunBittiGoster(kullaniciBilgisiBox);
    } else {
        turEkraniniGoster(primaryStage);  // Yeni tur ekranını göster
    }
    });
 
    
    kullaniciBilgisiBox.getChildren().add(tamamlaButton);

    Scene scene = new Scene(root, 800, 600);
    primaryStage.setScene(scene);
primaryStage.setFullScreen(true);
primaryStage.setFullScreenExitHint("");
}

    
// Kart bilgileri ekranı
    private void kartBilgileriEkrani(Stage primaryStage) {
    // Arka plan resmi
    BackgroundImage backgroundImage = new BackgroundImage(
        new Image("file:C:/Users/Ömer/Desktop/CardGame/src/images/kartbackground.png", 800, 600, false, true),
        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
        BackgroundPosition.CENTER,
        new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
);

    Background background = new Background(backgroundImage);

    // Root düzeni
    VBox root = new VBox(20);
    root.setStyle("-fx-alignment: center; -fx-padding: 20px;");
    root.setBackground(background); // Arka plan ayarla

    // Başlık
    Label infoLabel = new Label("Kart Bilgileri");
    infoLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: white; -fx-padding: 10px;");

    // Kart görsellerini ve isimlerini göstermek için HBox
    HBox kartPane = new HBox(20);
    kartPane.setStyle("-fx-alignment: center;");

    // Kart bilgilerini tanımla
    List<String> kartBilgileri = Arrays.asList(
            "file:C:/Users/Ömer/Desktop/CardGame/src/images/obus.png,Obüs",
            "file:C:/Users/Ömer/Desktop/CardGame/src/images/firkateyn.png,Fırkateyn",
            "file:C:/Users/Ömer/Desktop/CardGame/src/images/ucak.png,Uçak",
            "file:C:/Users/Ömer/Desktop/CardGame/src/images/siha.png,SİHA",
            "file:C:/Users/Ömer/Desktop/CardGame/src/images/sida.png,SİDA",
            "file:C:/Users/Ömer/Desktop/CardGame/src/images/kfs.png,KFS"
    );

    // Kartları ekrana yükle
    for (String kartBilgisi : kartBilgileri) {
        String[] bilgiler = kartBilgisi.split(",");
        String imagePath = bilgiler[0];
        String kartIsmi = bilgiler[1];

        // Kart görseli
        ImageView kartImage = new ImageView(new Image(imagePath));
        kartImage.setFitWidth(150); // Görselin genişliği
        kartImage.setFitHeight(150); // Görselin yüksekliği

        // Kart ismi
        Label isimLabel = new Label(kartIsmi);
        isimLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white; -fx-padding: 5px;");

        // Kart görseli ve ismi için VBox
        VBox kartBox = new VBox(5, kartImage, isimLabel);
        kartBox.setStyle("-fx-alignment: center;");

        // Kart kutusunu kartPane'e ekle
        kartPane.getChildren().add(kartBox);
    }

    // Geri butonu
    Button geriButton = new Button("Geri");
    geriButton.setStyle("-fx-font-size: 16px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
    geriButton.setOnAction(e -> girişEkranı(primaryStage));

    // Root düzenine her şeyi ekle
    root.getChildren().addAll(infoLabel, kartPane, geriButton);

    Scene scene = new Scene(root, 800, 600);
    primaryStage.setScene(scene);
    primaryStage.setFullScreen(true);
    primaryStage.setFullScreenExitHint("");
}

    private VBox createCardBox(ImageView imageView, String cardName) {
        VBox cardBox = new VBox(5);
        cardBox.getChildren().addAll(imageView, new Label(cardName));
        cardBox.setStyle("-fx-alignment: center; -fx-border-color: black; -fx-border-width: 2; -fx-padding: 10;");
        return cardBox;
    }

    private void oyunBittiGoster(VBox parent) {
        parent.getChildren().clear();
        Label oyunBittiLabel = new Label("Oyun Bitti! Tüm turlar oynandı.");
        oyunBittiLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: red;");
        parent.getChildren().add(oyunBittiLabel);
    }
    private String getImagePath(Object kart) {
        if (kart instanceof Ucak) {
            return "file:C:/Users/Ömer/Desktop/CardGame/src/images/ucak.png";
        } else if (kart instanceof Obus) {
            return "file:C:/Users/Ömer/Desktop/CardGame/src/images/obus.png";
        } else if (kart instanceof Firkateyn) {
            return "file:C:/Users/Ömer/Desktop/CardGame/src/images/firkateyn.png";
        }
        return "file:C:/Users/Ömer/Desktop/CardGame/src/images/default.png";  // Varsayılan görsel
    }
    
    
    private void baslangicKartlariOlustur() {
        Random random = new Random();
    
        // Kullanıcı kartları
        kullaniciKartlari.clear();
        for (int i = 0; i < 6; i++) {
            rastgeleKartEkle(kullaniciKartlari, rastgeleKartTuru());
        }
    
        // Bilgisayar kartları
        bilgisayarKartlari.clear();
        for (int i = 0; i < 6; i++) {
            rastgeleKartEkle(bilgisayarKartlari, rastgeleKartTuru());
        }
    }
    
    private void showAlert(String baslik, String mesaj) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(baslik);
        alert.setHeaderText(null);
        alert.setContentText(mesaj);
        alert.showAndWait();
    }


    
}