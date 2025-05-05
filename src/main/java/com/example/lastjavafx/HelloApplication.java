    package com.example.lastjavafx;

    import javafx.application.Application;
    import javafx.fxml.FXMLLoader;
    import javafx.scene.Scene;
    import javafx.stage.Stage;
    import javafx.scene.Parent;
    import java.io.IOException;

   /* public class HelloApplication extends Application {
        @Override
        public void start(Stage stage) throws IOException {
         //   FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Commande.fxml"));
         //   FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Panier.fxml"));
           // FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Paiement.fxml"));
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("AccueilClient.fxml"));
              // FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("PanierClient.fxml"));

            Scene scene = new Scene(fxmlLoader.load(), 920, 840);
         // Scene scenePanier = new Scene(fxmlLoader.load(), 620, 440);
            stage.setTitle("Hello!");
            stage.setScene(scene);
            //stage.setScene(scenePanier);
            stage.show();
        }

        public static void main(String[] args) {
            launch();
        }
    }*/
   public class HelloApplication extends Application {
       @Override
       public void start(Stage stage) throws Exception {
           try {
               Parent root = FXMLLoader.load(getClass().getResource("DashBoard.fxml"));
               Scene scene = new Scene(root);
               stage.setScene(scene);
               stage.setTitle("Dashboard");
               stage.show();
           } catch (Exception e) {
               e.printStackTrace();
           }
       }
       public static void main(String[] args) {
           launch(args);
       }
   }