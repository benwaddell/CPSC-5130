package edu.au.cpsc.checkpoint4;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.sql.*;
import java.time.LocalDate;

import static java.sql.DriverManager.getConnection;

public class QueryViewController {

    private String dbUrl = "jdbc:mysql://localhost/hospital";
    private String dbUsername = "root";
    private String dbPassword = "password";
    private Connection conn;
    private Queries queries = new Queries();
    private PreparedStatement ps;
    private String type;

    @FXML
    private Label connectionStatusLabel, connectionFailedLabel;

    @FXML
    private TextField urlTextField, usernameTextField, passwordTextField, patientFirstTextField, patientLastTextField,
        patientIDTextField, doctorIDTextField, doctorFirstTextField, doctorLastTextField, treatmentIDTextField;

    @FXML
    private Button connectButton, seedButton, executeQueryButton;

    @FXML
    private ComboBox queryTypeComboBox, queryComboBox;

    @FXML
    private TableView queryView;

    @FXML
    private DatePicker startDatePicker, endDatePicker;

    @FXML
    private HBox patientHBox, doctorHBox, treatmentHBox, dateHBox;

    public void initialize() {
        setQueryTypeComboBox();
        queryTypeComboBox.getSelectionModel().selectedItemProperty().addListener(c -> setQueryComboBox());
        queryComboBox.getSelectionModel().selectedItemProperty().addListener(c -> validateStatement());
    }

    @FXML
    protected void connectButtonAction() {
        dbUrl = urlTextField.getText();
        dbUsername = usernameTextField.getText();
        dbPassword = passwordTextField.getText();
        connect();
    }

    @FXML
    protected void seedButtonAction() {
        try {
            Statement stmt = conn.createStatement();

            String table1 = "CREATE TABLE Patients (\n" +
                    "    PatientID INT PRIMARY KEY,\n" +
                    "    FirstName VARCHAR(100) NOT NULL,\n" +
                    "    LastName VARCHAR(100) NOT NULL,\n" +
                    "    EmergencyContact VARCHAR(255) NOT NULL,\n" +
                    "    Insurance VARCHAR(255) NOT NULL\n" +
                    ");\n";

            String table2 = "CREATE TABLE Employees (\n" +
                    "    EmployeeID INT PRIMARY KEY,\n" +
                    "    FirstName VARCHAR(100) NOT NULL,\n" +
                    "    LastName VARCHAR(100) NOT NULL,\n" +
                    "    Role VARCHAR(20)\n" +
                    ");\n";

            String table3 = "CREATE TABLE Rooms (\n" +
                    "    RoomNumber INT PRIMARY KEY\n" +
                    ");\n";

            String table4 = "CREATE TABLE Treatments (\n" +
                    "    TreatmentID INT PRIMARY KEY,\n" +
                    "    TreatmentName VARCHAR(50) NOT NULL,\n" +
                    "    TreatmentType VARCHAR(50),\n" +
                    "    OrderedTime DATETIME,\n" +
                    "    AdministeredTime DATETIME,\n" +
                    "    OrderedBy INT,\n" +
                    "    AdministeredBy INT,\n" +
                    "    FOREIGN KEY (OrderedBy) REFERENCES Employees(EmployeeID),\n" +
                    "    FOREIGN KEY (AdministeredBy) REFERENCES Employees(EmployeeID)\n" +
                    ");\n";

            String table5 = "CREATE TABLE Diagnoses (\n" +
                    "    DiagnosisID INT PRIMARY KEY,\n" +
                    "    DiagnosisName VARCHAR(100) NOT NULL\n" +
                    ");\n";

            String table6 = "CREATE TABLE Admissions (\n" +
                    "    AdmissionID INT PRIMARY KEY,\n" +
                    "    AdmissionDate DATE,\n" +
                    "    DischargeDate DATE,\n" +
                    "    PatientID INT,\n" +
                    "    PrimaryDoctorID INT,\n" +
                    "    TreatmentID INT,\n" +
                    "    DiagnosisID INT,\n" +
                    "    RoomNumber INT,\n" +
                    "    FOREIGN KEY (PatientID) REFERENCES Patients(PatientID),\n" +
                    "    FOREIGN KEY (PrimaryDoctorID) REFERENCES Employees(EmployeeID),\n" +
                    "    FOREIGN KEY (TreatmentID) REFERENCES Treatments(TreatmentID),\n" +
                    "    FOREIGN KEY (DiagnosisID) REFERENCES Diagnoses(DiagnosisID),\n" +
                    "    FOREIGN KEY (RoomNumber) REFERENCES Rooms(RoomNumber)\n" +
                    ");\n";

            String insert1 = "INSERT INTO Patients (PatientID, FirstName, LastName, EmergencyContact, Insurance)\n" +
                    "VALUES\n" +
                    "    (1, 'Liam', 'Bennett', '555-1234', 'Blue Cross Health'),\n" +
                    "    (2, 'Chloe', 'Anderson', '555-5678', 'United Healthcare'),\n" +
                    "    (3, 'Ethan', 'Mitchell', '555-8765', 'Cigna Insurance'),\n" +
                    "    (4, 'Sophia', 'Parker', '555-4321', 'Aetna Health'),\n" +
                    "    (5, 'Mason', 'Cooper', '555-6789', 'Kaiser Permanente');\n";

            String insert2 = "INSERT INTO Employees (EmployeeID, FirstName, LastName, Role)\n" +
                    "VALUES \n" +
                    "    (101, 'Ryan', 'Walker', 'Doctor'),\n" +
                    "    (102, 'Ella', 'Harris', 'Nurse'),\n" +
                    "    (103, 'Jackson', 'Scott', 'Technician'),\n" +
                    "    (104, 'Grace', 'Peterson', 'Staff'),\n" +
                    "    (105, 'Henry', 'Baker', 'Doctor'),\n" +
                    "    (106, 'Lily', 'Reed', 'Administrator');\n";

            String insert3 = "INSERT INTO Rooms (RoomNumber)\n" +
                    "VALUES \n" +
                    "    (1), (2), (3), (4), (5), (6), (7), (8), (9), (10),\n" +
                    "    (11), (12), (13), (14), (15), (16), (17), (18), (19), (20);\n";

            String insert4 = "INSERT INTO Treatments (TreatmentID, TreatmentName, TreatmentType, OrderedTime, AdministeredTime, OrderedBy, AdministeredBy)\n" +
                    "VALUES \n" +
                    "    (201, 'Antibiotic', 'Medication', '2024-11-01 08:00:00', '2024-11-01 08:30:00', 101, 102),\n" +
                    "    (202, 'Physical Therapy', 'Procedure', '2024-11-02 09:00:00', '2024-11-02 10:00:00', 105, 104),\n" +
                    "    (203, 'Flu Vaccine', 'Medication', '2024-11-03 10:00:00', '2024-11-03 10:15:00', 105, 102),\n" +
                    "    (204, 'Surgery', 'Procedure', '2024-11-04 11:00:00', '2024-11-04 13:00:00', 101, 105),\n" +
                    "    (205, 'Ibuprofen', 'Medication', '2024-11-05 12:00:00', '2024-11-05 12:15:00', 101, 102),\n" +
                    "    (206, 'Anesthetic', 'Medication', '2024-11-05 12:00:00', '2024-11-05 12:15:00', 101, 102);\n";

            String insert5 = "INSERT INTO Diagnoses (DiagnosisID, DiagnosisName)\n" +
                    "VALUES \n" +
                    "    (301, 'Common Cold'),\n" +
                    "    (302, 'ACL Tear'),\n" +
                    "    (303, 'Influenza'),\n" +
                    "    (304, 'Inflammation');\n";

            String insert6 = "INSERT INTO Admissions (AdmissionID, AdmissionDate, DischargeDate, PatientID, PrimaryDoctorID, TreatmentID, DiagnosisID, RoomNumber)\n" +
                    "VALUES \n" +
                    "    (401, '2024-11-01', '2024-11-01', 2, 101, 201, 301, 1),\n" +
                    "    (402, '2024-11-02', '2024-11-03', 1, 101, 206, 302, 4),\n" +
                    "    (403, '2024-11-02', '2024-11-03', 1, 101, 204, 302, 4),\n" +
                    "    (404, '2024-11-03', '2024-11-03', 3, 105, 203, 303, 8),\n" +
                    "    (405, '2024-11-04', '2024-11-04', 4, 105, 205, 304, 10),\n" +
                    "    (406, '2024-11-05', '2024-11-10', 1, 101, 202, 302, 5),\n" +
                    "    (407, '2024-11-12', NULL, 1, 101, 202, 302, 5);\n";

            stmt.addBatch(table1);
            stmt.addBatch(table2);
            stmt.addBatch(table3);
            stmt.addBatch(table4);
            stmt.addBatch(table5);
            stmt.addBatch(table6);
            stmt.addBatch(insert1);
            stmt.addBatch(insert2);
            stmt.addBatch(insert3);
            stmt.addBatch(insert4);
            stmt.addBatch(insert5);
            stmt.addBatch(insert6);

            stmt.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
            connect();
        }
    }

    @FXML
    private void executeQueryButtonAction() {
        queryView.getColumns().clear();
        prepareStatement();
        setQueryView(ps);
    }

    private void connect() {
        try {
            conn = getConnection(dbUrl, dbUsername, dbPassword);
        } catch (SQLException e) {
            connectionStatusLabel.setText("Not Connected");
            connectionStatusLabel.setTextFill(Color.RED);
            urlTextField.setDisable(false);
            usernameTextField.setDisable(false);
            passwordTextField.setDisable(false);
            seedButton.setDisable(true);
            connectionFailedLabel.setVisible(true);
            connectButton.setDisable(false);
            queryComboBox.setDisable(true);
            executeQueryButton.setDisable(true);
            queryTypeComboBox.setDisable(true);
            queryComboBox.setDisable(true);
            return;
        }
        connectionStatusLabel.setText("Connected");
        connectionStatusLabel.setTextFill(Color.GREEN);
        urlTextField.setDisable(true);
        usernameTextField.setDisable(true);
        passwordTextField.setDisable(true);
        seedButton.setDisable(false);
        connectionFailedLabel.setVisible(false);
        connectButton.setDisable(true);
        queryComboBox.setDisable(false);
        executeQueryButton.setDisable(false);
        queryTypeComboBox.setDisable(false);
        queryComboBox.setDisable(false);
    }

    private void setQueryTypeComboBox() {
        for (Query query : queries.getQueries()) {
            if (!queryTypeComboBox.getItems().contains(query.getCategory())) {
                queryTypeComboBox.getItems().add(query.getCategory());
            }
        }
    }

    private void setQueryComboBox() {
        queryComboBox.getItems().clear();
        for (Query query : queries.getQueries()) {
            if (query.getCategory().equals(queryTypeComboBox.getSelectionModel().getSelectedItem())) {
                queryComboBox.getItems().addAll(query.getDescription());
            }
        }
        queryComboBox.show();
        queryComboBox.hide();
    }

    private void validateStatement() {
        type = "";
        try {
            for (Query query : queries.getQueries()) {
                if (query.getDescription().equals(queryComboBox.getSelectionModel().getSelectedItem())) {
                    ps = conn.prepareStatement(query.getStatement());
                    if (query.getStatement().contains("?")) {
                        if (query.getDescription().contains("given date range")) {
                            type = "date";
                        } else if (query.getDescription().contains("given patient")) {
                            type = "patient";
                        } else if (query.getDescription().contains("given treatment")) {
                            type = "treatment";
                        } else if (query.getDescription().contains("given doctor")) {
                            type = "doctor";
                        }
                    }
                }
            }
            setExtraFields();
        } catch (SQLException e) {
            e.printStackTrace();
            connect();
        }
    }

    private void prepareStatement() {
        try {
            if (type.equals("date")) {
                ps.setString(1, startDatePicker.getValue().toString());
                ps.setString(2, endDatePicker.getValue().toString());
            } else if (type.equals("patient")) {
                ps.setString(1, patientIDTextField.getText());
                ps.setString(2, patientFirstTextField.getText());
                ps.setString(3, patientLastTextField.getText());
            } else if (type.equals("treatment")) {
                ps.setString(1, treatmentIDTextField.getText());
            } else if (type.equals("doctor")) {
                ps.setString(1, doctorIDTextField.getText());
                ps.setString(2, doctorFirstTextField.getText());
                ps.setString(3, doctorLastTextField.getText());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            connect();
        }
    }

    private void setExtraFields() {
        dateHBox.setVisible(false);
        patientHBox.setVisible(false);
        doctorHBox.setVisible(false);
        treatmentHBox.setVisible(false);

        if (type.equals("date")) {
            dateHBox.setVisible(true);
            startDatePicker.setValue(LocalDate.of(2024, 11, 01));
            endDatePicker.setValue(LocalDate.of(2024, 11, 03));
        } else if (type.equals("patient")) {
            patientHBox.setVisible(true);
        } else if (type.equals("doctor")) {
            doctorHBox.setVisible(true);
        } else if (type.equals("treatment")) {
            treatmentHBox.setVisible(true);
        }
    }

    private void setQueryView(PreparedStatement ps) {

        try {
            ResultSet rs = ps.executeQuery();
            ObservableList<ObservableList> data = FXCollections.observableArrayList();

            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>, ObservableValue<String>>(){
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                queryView.getColumns().addAll(col);
            }
            while(rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    if (rs.getString(i) == null) {
                        row.add("");
                    } else {
                        row.add(rs.getString(i));
                    }
                }
                data.add(row);
            }
            queryView.setItems(data);
        } catch (SQLException e) {
            e.printStackTrace();
            connect();
        }
    }

}