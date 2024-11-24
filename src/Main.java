import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.*;

public class Main extends Application {

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
        VBox root = new VBox(10);

        // Tur sayısını sorma ekranı
        Label turSayisiLabel = new Label("Kaç tur oynanacak?");
        TextField turSayisiField = new TextField();
        Button baslaButton = new Button("Başla");
        root.getChildren().addAll(turSayisiLabel, turSayisiField, baslaButton);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Kart Oyunu");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Başla butonuna tıklandığında tur sayısını al ve oyunu başlat
        baslaButton.setOnAction(e -> {
            try {
                turSayisi = Integer.parseInt(turSayisiField.getText());
                if (turSayisi <= 0) {
                    showAlert("Hata", "Tur sayısı pozitif bir sayı olmalıdır!");
                    return;
                }
                baslangicKartlariOlustur();
                turEkraniniGoster(primaryStage);
            } catch (NumberFormatException ex) {
                showAlert("Hata", "Lütfen geçerli bir sayı girin!");
            }
        });
    }

    private void baslangicKartlariOlustur() {
        Random random = new Random();
        int ucakSayisi = random.nextInt(3) + 1;
        int obusSayisi = random.nextInt(3) + 1;
        int firkateynSayisi = 6 - (ucakSayisi + obusSayisi);

        // Kullanıcı kartları
        for (int i = 0; i < ucakSayisi; i++) kullaniciKartlari.add(new Ucak("Hava", "Ucak" + (i + 1)));
        for (int i = 0; i < obusSayisi; i++) kullaniciKartlari.add(new Obus("Kara", "Obus" + (i + 1)));
        for (int i = 0; i < firkateynSayisi; i++) kullaniciKartlari.add(new Firkateyn("Deniz", "Firkateyn" + (i + 1)));

        // Bilgisayar kartları
        for (int i = 0; i < 6; i++) {
            int secim = random.nextInt(3);
            if (secim == 0) bilgisayarKartlari.add(new Ucak("Hava", "Bilgisayar Ucak" + (i + 1)));
            else if (secim == 1) bilgisayarKartlari.add(new Obus("Kara", "Bilgisayar Obus" + (i + 1)));
            else bilgisayarKartlari.add(new Firkateyn("Deniz", "Bilgisayar Firkateyn" + (i + 1)));
        }
    }

    private void turEkraniniGoster(Stage primaryStage) {
        BorderPane root = new BorderPane();

        // Bilgisayar kartlarını görsellerle üst tarafa ekle
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

        // Kullanıcının seçtiği kartlar ve geçmiş
        VBox kullaniciBilgisiBox = new VBox(10, secilenKartlarLabel, new Label("Tur Geçmişi:"), turGecmisiListesi);
        root.setCenter(kullaniciBilgisiBox);

        // Kullanıcı kartlarını görsellerle altta ekrana ekle
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
        return "";
    }

    private void showAlert(String baslik, String mesaj) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(baslik);
        alert.setHeaderText(null);
        alert.setContentText(mesaj);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
