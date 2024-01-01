package com.java.parawisata.javaparawisata.Controller;

import com.java.parawisata.javaparawisata.Entity.GlobalParameter;
import com.java.parawisata.javaparawisata.Entity.SignUp;
import com.java.parawisata.javaparawisata.JavaParawisataApp;
import com.java.parawisata.javaparawisata.Repository.ICustomerRepository;
import com.java.parawisata.javaparawisata.Repository.IParamRepository;
import com.java.parawisata.javaparawisata.Repository.Impl.CustomerRepositoryImpl;
import com.java.parawisata.javaparawisata.Repository.Impl.ParamRepositoryImpl;
import com.java.parawisata.javaparawisata.Service.ICustomerService;
import com.java.parawisata.javaparawisata.Service.IParamService;
import com.java.parawisata.javaparawisata.Service.Impl.CustomerServiceImpl;
import com.java.parawisata.javaparawisata.Service.Impl.ParamServiceImpl;
import com.java.parawisata.javaparawisata.Utils.Components.ServiceGlobalComponents;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.ControlMessage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SignUpController implements Initializable {
    @FXML
    public MFXComboBox<String> cbxIdentityType;

    @FXML
    public MFXTextField txtEmail;

    @FXML
    public MFXTextField txtIdentityID;

    @FXML
    public MFXTextField txtName;

    @FXML
    public MFXPasswordField txtPassword;

    @FXML
    public MFXTextField txtUsername;

    ICustomerRepository customerRepository;
    IParamRepository paramRepository;
    SignUp signUpData = new SignUp();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.onSetAllParam();
    }

    public void onSetAllParam() {
        GlobalParameter paramRq = new GlobalParameter();
        paramRq.setGroup("IdentityType");
        ControlMessage<List<GlobalParameter>> paramIdentity = parameterIdentity(paramRq);
        if (paramIdentity.isSuccess) {
            List<String> identityType = new ArrayList<>();
            paramIdentity.data.forEach(x -> {
                identityType.add(x.getText());
            });
            this.cbxIdentityType.setItems(FXCollections.observableArrayList(identityType));
        } else ServiceGlobalComponents.showAlertDialog(paramIdentity);
    }
    private ControlMessage<List<GlobalParameter>> parameterIdentity(GlobalParameter data) {
        this.paramRepository = new ParamRepositoryImpl();
        IParamService paramService = new ParamServiceImpl(this.paramRepository);
        return paramService.InquiryGlobalParam(data);
    }
    @FXML
    public void onActionCancel(ActionEvent event) throws IOException {
        AtomicBoolean result = ServiceGlobalComponents.showConfirmationDialog("Confirmation", "Yakin Ingin Keluar Halaman ?");
        if (result.get()) {
            ((Node) (event.getSource())).getScene().getWindow().hide();

            FXMLLoader fxmlLoader = new FXMLLoader(JavaParawisataApp.class.getResource("fxml/login-view.fxml"));
            AnchorPane mainLayout = fxmlLoader.load();
            Scene scene = new Scene(mainLayout);
            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.getIcons().add(new Image(JavaParawisataApp.class.getResourceAsStream("icons/bus.png")));
            stage.setScene(scene);
            stage.setResizable(false);
            // stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        };
    }

    @FXML
    public void onActionSave(ActionEvent event) throws IOException {
        this.onSetData();

        ControlMessage<SignUp> response = processSignUp(signUpData);
        if (response.isSuccess) {
            ((Node) (event.getSource())).getScene().getWindow().hide();

            FXMLLoader fxmlLoader = new FXMLLoader(JavaParawisataApp.class.getResource("fxml/login-view.fxml"));
            AnchorPane mainLayout = fxmlLoader.load();
            Scene scene = new Scene(mainLayout);
            Stage stage = new Stage();
            stage.getIcons().add(new Image(JavaParawisataApp.class.getResourceAsStream("icons/bus.png")));
            stage.setTitle("Login");
            stage.setScene(scene);
            stage.setResizable(false);
            // stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        }
        ServiceGlobalComponents.showAlertDialog(response);
    }

    private ControlMessage<SignUp> processSignUp(SignUp data) {
        this.customerRepository = new CustomerRepositoryImpl();
        ICustomerService customerService = new CustomerServiceImpl(this.customerRepository);
        return customerService.onAddCustomer(data);
    }

    private void onSetData() {
        if (this.txtName.getText().isBlank() || this.txtName.getText().isEmpty())
            signUpData.setName("");
        else signUpData.setName(this.txtName.getText());

        if (this.txtIdentityID.getText().isBlank() || this.txtIdentityID.getText().isEmpty())
            signUpData.setIdentityID("");
        else signUpData.setIdentityID(this.txtIdentityID.getText());

        if (this.txtEmail.getText().isBlank() || this.txtEmail.getText().isEmpty())
            signUpData.setEmail("");
        else signUpData.setEmail(this.txtEmail.getText());

        if (this.txtUsername.getText().isBlank() || this.txtUsername.getText().isEmpty())
            signUpData.setUsername("");
        else signUpData.setUsername(this.txtUsername.getText());

        if (this.txtPassword.getText().isBlank() || this.txtPassword.getText().isEmpty())
            signUpData.setPassword("");
        else signUpData.setPassword(this.txtPassword.getText());

        if (this.cbxIdentityType.getValue() == null || this.cbxIdentityType.getValue().isBlank() || this.cbxIdentityType.getValue().isEmpty())
            signUpData.setIdentityType("");
        else signUpData.setIdentityType(this.cbxIdentityType.getValue());
    }
}
