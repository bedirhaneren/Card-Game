import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.*;

public class GameUI extends Application {

    private int turSayisi;
    private int mevcutTur = 1;
    private List<Object> kullaniciKartlari = new ArrayList<>();
    private List<Object> bilgisayarKartlari = new ArrayList<>();
    private List<Object> secilenKartlar = new ArrayList<>();
    private Map<Integer, List<Object>> turBilgileri = new LinkedHashMap<>();
    private Label secilenKartlarLabel = new Label("Seçilen Kartlar: ");
    private ListView<String> turGecmisiListesi = new ListView<>();

    @Override
    public void start(Stage primaryStage) {
        girişEkranı(primaryStage);
    }

   // Giriş ekranı
private void girişEkranı(Stage primaryStage) {
    // Arka plan resmi
    BackgroundImage backgroundImage = new BackgroundImage(
            new Image("file:src/images/background.png", 800, 600, false, true),
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
    primaryStage.show();
}


    // Oyuna başla ekranı
    private void oyunBaslangicEkrani(Stage primaryStage) {
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image("file:src/images/background.png", 800, 600, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT
        );
        Background background = new Background(backgroundImage);

        ImageView logo = new ImageView(new Image("file:src/images/logo.png"));
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
    }

    private void turEkraniniGoster(Stage primaryStage) {
        BorderPane root = new BorderPane();
    
        // Bilgisayar kartlarını üst tarafa ekle
        HBox bilgisayarKartPane = new HBox(10);
        bilgisayarKartPane.setStyle("-fx-alignment: center;"); // Ortalanmış stil
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
        VBox kullaniciBilgisiBox = new VBox(10, secilenKartlarLabel, new Label("Tur Geçmişi:"), turGecmisiListesi);
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
    
            // Seçilen kartları kaydet ve geçmişe ekle
            turBilgileri.put(mevcutTur, new ArrayList<>(secilenKartlar));
            turGecmisiListesi.getItems().add("Tur " + mevcutTur + ": " + secilenKartlar);
    
            // Tur ilerlemesi
            secilenKartlar.clear();
            secilenKartlarLabel.setText("Seçilen Kartlar: ");
            mevcutTur++;
            if (mevcutTur > turSayisi) {
                oyunBittiGoster(kullaniciBilgisiBox);
            } else {
                turEkraniniGoster(primaryStage);
            }
        });
        kullaniciBilgisiBox.getChildren().add(tamamlaButton);
    
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
    }
    
// Kart bilgileri ekranı
private void kartBilgileriEkrani(Stage primaryStage) {
    // Arka plan resmi
    BackgroundImage backgroundImage = new BackgroundImage(
        new Image("file:src/images/kartbackground.png", 800, 600, false, true),
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
            "file:src/images/obus.png,Obüs",
            "file:src/images/firkateyn.png,Fırkateyn",
            "file:src/images/ucak.png,Uçak",
            "file:src/images/siha.png,SİHA",
            "file:src/images/sida.png,SİDA",
            "file:src/images/kfs.png,KFS"
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
        if (kart instanceof Ucak) return "file:src/images/ucak.png";
        else if (kart instanceof Obus) return "file:src/images/obus.png";
        else if (kart instanceof Firkateyn) return "file:src/images/firkateyn.png";
        return "file:src/images/default.png"; // Varsayılan görsel
    }
    
    private void baslangicKartlariOlustur() {
        Random random = new Random();
        int ucakSayisi = random.nextInt(3) + 1;
        int obusSayisi = random.nextInt(3) + 1;
        int firkateynSayisi = 6 - (ucakSayisi + obusSayisi);
    
        // Kullanıcı kartları
        kullaniciKartlari.clear();
        for (int i = 0; i < ucakSayisi; i++) kullaniciKartlari.add(new Ucak("Hava", "Ucak" + (i + 1)));
        for (int i = 0; i < obusSayisi; i++) kullaniciKartlari.add(new Obus("Kara", "Obus" + (i + 1)));
        for (int i = 0; i < firkateynSayisi; i++) kullaniciKartlari.add(new Firkateyn("Deniz", "Firkateyn" + (i + 1)));
    
        // Bilgisayar kartları
        bilgisayarKartlari.clear();
        for (int i = 0; i < 6; i++) {
            int secim = random.nextInt(3);
            if (secim == 0) bilgisayarKartlari.add(new Ucak("Hava", "Bilgisayar Ucak" + (i + 1)));
            else if (secim == 1) bilgisayarKartlari.add(new Obus("Kara", "Bilgisayar Obus" + (i + 1)));
            else bilgisayarKartlari.add(new Firkateyn("Deniz", "Bilgisayar Firkateyn" + (i + 1)));
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


