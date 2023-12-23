package com.java.parawisata.javaparawisata.Controller;

import com.java.parawisata.javaparawisata.Entity.Auth;
import com.java.parawisata.javaparawisata.Entity.GlobalParameter;
import com.java.parawisata.javaparawisata.JavaParawisataApp;
import com.java.parawisata.javaparawisata.Repository.IAuthRepository;
import com.java.parawisata.javaparawisata.Repository.Impl.AuthRepositoryImpl;
import com.java.parawisata.javaparawisata.Service.IAuthService;
import com.java.parawisata.javaparawisata.Service.Impl.AuthServiceImpl;
import com.java.parawisata.javaparawisata.Utils.Components.ServiceGlobalComponents;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.ControlMessage;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;

public class LoginController implements Initializable {
    @FXML
    public MFXPasswordField password;

    @FXML
    public MFXTextField username;

    @FXML
    public MFXCheckbox isAdmin;

    private IAuthRepository authRepository;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void onBtnLogin(ActionEvent event) throws IOException {
        authRepository = new AuthRepositoryImpl();
        IAuthService authService = new AuthServiceImpl(authRepository);
        ControlMessage<Auth> response = authService.getTokenAuthenticate(this.username.getText(), this.password.getText());
        ServiceGlobalComponents.showAlertDialog(response);

        if (response.isSuccess) {
            ((Node) (event.getSource())).getScene().getWindow().hide();

            FXMLLoader fxmlLoader = new FXMLLoader(JavaParawisataApp.class.getResource("fxml/java-parawisata-view.fxml"));
            AnchorPane mainLayout = fxmlLoader.load();
            JavaParawisataController controller = fxmlLoader.getController();
            controller.onSetUser(response.data);
            controller.setMenus(response.data.getRole(), response.data.getUsername());
            Scene scene = new Scene(mainLayout);
            Stage stage = new Stage();
            stage.setTitle("JavaParawisata");
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();
        }
    }

    @FXML
    public void onSignUpClick(MouseEvent event) throws IOException {
        ((Node) (event.getSource())).getScene().getWindow().hide();

        FXMLLoader fxmlLoader = new FXMLLoader(JavaParawisataApp.class.getResource("fxml/sign-up-view.fxml"));
        AnchorPane mainLayout = fxmlLoader.load();
        Scene scene = new Scene(mainLayout);
        Stage stage = new Stage();
        stage.setTitle("Sign Up");
        stage.setScene(scene);
        stage.setResizable(false);
        // stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }
}
