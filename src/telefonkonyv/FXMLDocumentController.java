package telefonkonyv;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class FXMLDocumentController implements Initializable {
    
    private final String MENU_CONTACT = "Kontaktok";
    private final String MENU_LIST = "Lista";
    private final String MENU_EXPORT = "Exportálás";
    private final String MENU_EXIT = "Kilépés";
    DB db = new DB();
    
    @FXML
    private StackPane menuPane;
    @FXML
    private Pane exportPanel;
    @FXML
    private Pane contactPanel;
    @FXML
    private TableView contactTable;
    @FXML
    private Button addNewContactButton;
    @FXML
    private TextField firstNameInput;
    @FXML
    private TextField lastNameInput;
    @FXML
    private TextField emailInput;
    @FXML
    private TextField exportInput;
    @FXML
    private Button exportButton;
    @FXML
    private Pane alertPane;
    @FXML
    private Button alertButton;
    @FXML
    private Label alertLabel;
    @FXML
    private SplitPane mainPane;
    
    private final ObservableList <Person> data = FXCollections.observableArrayList();
    
    @FXML
    private void addContact (ActionEvent event)
    {
        String email = emailInput.getText();
        
        if (email.length()>3 && email.contains("@") && email.contains(".") && email.contains("com"))
        {
            Person newPerson = new Person(firstNameInput.getText(),lastNameInput.getText(), email);
            data.add(newPerson);
            db.addContact(newPerson);
            firstNameInput.clear();
            lastNameInput.clear();
            emailInput.clear();
        }
        else
        {
            alertMessage("Valós e-mail címet adj meg!");
            firstNameInput.clear();
            lastNameInput.clear();
            emailInput.clear();
        }
        
    }
    
    @FXML
    private void exportList (ActionEvent event)
    {
        String filename = exportInput.getText();
        filename = filename.replaceAll("\\s", "");
        
         if (filename!= null && !filename.equals(""))
         {
            PDFGeneration pdf = new PDFGeneration();
            pdf.PDFGeneration(filename, data);
            exportInput.clear();
         }
         else
         {
             alertMessage("Adj nevet az allomanynak!");
         }
    }
    
    @FXML
    private void errorReport (ActionEvent event)
    {
        mainPane.setOpacity(1);
        alertPane.setVisible(false);
    }
    
    private void alertMessage(String text)
    {
        mainPane.setOpacity(0.1);
        alertPane.setVisible(true);
        alertLabel.setText(text);
    }
    
    private void setTableData ()
    {
        TableColumn lastNameCol = new TableColumn("Vezetéknév");
        lastNameCol.setMinWidth(100);
        lastNameCol.setMaxWidth(200);
        lastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameCol.setCellValueFactory(new PropertyValueFactory <Person, String> ("lastName"));
        
        lastNameCol.setOnEditCommit
        (
                new EventHandler <TableColumn.CellEditEvent <Person, String>> ()
                {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Person, String> t) 
                    {
                        Person actualPerson = (Person) t.getTableView().getItems().get(t.getTablePosition().getRow());
                        actualPerson.setLastName(t.getNewValue());
                        db.updateContact(actualPerson);
                    }
                }
        );
        
        TableColumn firstNameCol = new TableColumn("Keresztnév");
        firstNameCol.setMinWidth(100);
        firstNameCol.setMaxWidth(200);
        firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        firstNameCol.setCellValueFactory(new PropertyValueFactory <Person, String> ("firstName"));
        
        firstNameCol.setOnEditCommit
        (
                new EventHandler <TableColumn.CellEditEvent <Person, String>> ()
                {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Person, String> t) 
                    {
                        Person actualPerson = (Person) t.getTableView().getItems().get(t.getTablePosition().getRow());
                        actualPerson.setFirstName(t.getNewValue());
                        db.updateContact(actualPerson);
                    }
                }
        );
        
        TableColumn emailCol = new TableColumn("Email");
        emailCol.setMinWidth(100);
        emailCol.setMaxWidth(200);
        emailCol.setCellFactory(TextFieldTableCell.forTableColumn());
        emailCol.setCellValueFactory(new PropertyValueFactory <Person, String> ("email"));        
        
        emailCol.setOnEditCommit
        (
                new EventHandler <TableColumn.CellEditEvent <Person, String>> ()
                {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Person, String> t) 
                    {
                        Person actualPerson = (Person) t.getTableView().getItems().get(t.getTablePosition().getRow());
                        actualPerson.setEmail(t.getNewValue());
                        db.updateContact(actualPerson);
                    }
                }
        );
        
        contactTable.getColumns().addAll(lastNameCol, firstNameCol, emailCol);
        
        data.addAll(db.getAllContacts());
        
        contactTable.setItems(data);
    }
    
    private void setMenu ()
    {
        TreeItem <String> root = new TreeItem<>("Menü");
        TreeView <String> treeView = new TreeView<>(root);
        treeView.setShowRoot(false);
        
        TreeItem <String> treeItemA = new TreeItem<>(MENU_CONTACT);
        TreeItem <String> treeItemB = new TreeItem<>(MENU_EXIT);
        
        Node treeItemAPictureA = new ImageView(new Image(getClass().getResourceAsStream("/contacts.png")));
        Node treeItemAPictureB = new ImageView(new Image(getClass().getResourceAsStream("/export.png")));
        
        root.getChildren().addAll(treeItemA, treeItemB);
        
        treeItemA.setExpanded(true);
        
        TreeItem <String> treeItemA1 = new TreeItem<>(MENU_LIST, treeItemAPictureA);
        TreeItem <String> treeItemA2 = new TreeItem<>(MENU_EXPORT, treeItemAPictureB);
        
        treeItemA.getChildren().addAll(treeItemA1, treeItemA2);
        
        menuPane.getChildren().add(treeView);
        
        treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed (ObservableValue observable, Object oldValue, Object newValue) {
                TreeItem <String> selectedItem = (TreeItem <String>) newValue;
                String selectedMenu;
                selectedMenu = selectedItem.getValue();
                
                if (null!=selectedMenu)
                {
                    switch (selectedMenu)
                    {
                        case MENU_CONTACT:
                            selectedItem.setExpanded(true);
                            break;
                        case MENU_LIST:
                            contactPanel.setVisible(true);
                            exportPanel.setVisible(false);
                            break;
                        case MENU_EXPORT:
                            contactPanel.setVisible(false);
                            exportPanel.setVisible(true);
                            break;
                        case MENU_EXIT:
                            System.exit(0);
                            break;
                    }
                }
            }
        }
                
        );
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        setTableData();
        setMenu();
        
    }      
}
