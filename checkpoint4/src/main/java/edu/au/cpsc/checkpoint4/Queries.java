package edu.au.cpsc.checkpoint4;

import java.util.ArrayList;
import java.util.List;

public class Queries {
    private List<Query> queries;

    public Queries() {
        generateQueries();
    }

    private void generateQueries() {
        queries = new ArrayList<Query>();

        Query oneOne = new Query("", "", "");
        Query oneTwo = new Query("", "","");
        Query oneThree = new Query("", "","");
        Query twoOne = new Query("", "","");
        Query twoTwo = new Query("", "","");
        Query twoThree = new Query("", "","");
        Query twoFour = new Query("", "","");
        Query twoFive = new Query("", "","");
        Query twoSix = new Query("", "","");
        Query twoSeven = new Query("", "","");
        Query twoEight = new Query("", "","");
        Query threeOne = new Query("", "","");
        Query threeTwo = new Query("", "","");
        Query threeThree = new Query("", "","");
        Query threeFour = new Query("", "","");
        Query threeFive = new Query("", "","");
        Query fourOne = new Query("", "","");
        Query fourTwo = new Query("", "","");
        Query fourThree = new Query("", "","");
        Query fourFour = new Query("", "","");
        Query fourFive = new Query("", "","");

        oneOne.setCategory("Room Utilization");
        oneOne.setDescription("List the rooms that are occupied.");
        oneOne.setStatement("SELECT \n" +
                "    r.RoomNumber,\n" +
                "    p.FirstName,\n" +
                "    p.LastName,\n" +
                "    a.AdmissionDate\n" +
                "FROM \n" +
                "    Admissions a\n" +
                "JOIN \n" +
                "    Patients p ON a.PatientID = p.PatientID\n" +
                "JOIN \n" +
                "    Rooms r ON a.RoomNumber = r.RoomNumber\n" +
                "WHERE \n" +
                "    a.DischargeDate IS NULL;\n");

        oneTwo.setCategory("Room Utilization");
        oneTwo.setDescription("List the rooms that are currently unoccupied.");
        oneTwo.setStatement("SELECT \n" +
                "    r.RoomNumber\n" +
                "FROM \n" +
                "    Rooms r\n" +
                "LEFT JOIN \n" +
                "    Admissions a ON r.RoomNumber = a.RoomNumber\n" +
                "    AND a.DischargeDate IS NULL\n" +
                "WHERE \n" +
                "    a.RoomNumber IS NULL;\n");

        oneThree.setCategory("Room Utilization");
        oneThree.setDescription("List all rooms in the hospital.");
        oneThree.setStatement("SELECT \n" +
                "    r.RoomNumber,\n" +
                "    p.FirstName,\n" +
                "    p.LastName,\n" +
                "    a.AdmissionDate\n" +
                "FROM \n" +
                "    Rooms r\n" +
                "LEFT JOIN \n" +
                "    Admissions a ON r.RoomNumber = a.RoomNumber\n" +
                "    AND a.DischargeDate IS NULL\n" +
                "LEFT JOIN \n" +
                "    Patients p ON a.PatientID = p.PatientID;\n");

        twoOne.setCategory("Patient Information");
        twoOne.setDescription("List all patients in the database.");
        twoOne.setStatement("SELECT *\n" +
                "FROM Patients;\n");

        twoTwo.setCategory("Patient Information");
        twoTwo.setDescription("List all patients currently admitted to the hospital.");
        twoTwo.setStatement("SELECT\n" +
                "    p.PatientID,\n" +
                "    p.FirstName,\n" +
                "    p.LastName\n" +
                "FROM Patients p\n" +
                "JOIN\n" +
                "    Admissions a ON p.PatientID = a.PatientID\n" +
                "WHERE\n" +
                "    a.DischargeDate IS NULL;\n");

        twoThree.setCategory("Patient Information");
        twoThree.setDescription("List all patients who were discharged in a given date range.");
        twoThree.setStatement("SELECT DISTINCT\n" +
                "    p.PatientID,\n" +
                "    p.FirstName,\n" +
                "    p.LastName\n" +
                "FROM Patients p\n" +
                "JOIN\n" +
                "    Admissions a ON p.PatientID = a.PatientID\n" +
                "WHERE \n" +
                "    a.DischargeDate BETWEEN ? AND ?;\n");

        twoFour.setCategory("Patient Information");
        twoFour.setDescription("List all patients who were admitted within a given date range.");
        twoFour.setStatement("SELECT DISTINCT\n" +
                "    p.PatientID,\n" +
                "    p.FirstName,\n" +
                "    p.LastName\n" +
                "FROM Patients p\n" +
                "JOIN\n" +
                "    Admissions a ON p.PatientID = a.PatientID\n" +
                "WHERE\n" +
                "    a.AdmissionDate BETWEEN ? AND ?;\n");

        twoFive.setCategory("Patient Information");
        twoFive.setDescription("For a given patient, list all admissions to the hospital " +
                "along with the diagnosis for each admission.");
        twoFive.setStatement("SELECT \n" +
                "    a.AdmissionID,\n" +
                "    a.AdmissionDate,\n" +
                "    a.DischargeDate,\n" +
                "    d.DiagnosisName\n" +
                "FROM \n" +
                "    Admissions a\n" +
                "JOIN \n" +
                "    Patients p ON a.PatientID = p.PatientID\n" +
                "JOIN \n" +
                "    Diagnoses d ON a.DiagnosisID = d.DiagnosisID\n" +
                "WHERE \n" +
                "    p.PatientID = ? \n" +
                "    OR (p.FirstName = ? AND p.LastName = ?);\n");

        twoSix.setCategory("Patient Information");
        twoSix.setDescription("For a given patient, list all treatments that were administered.");
        twoSix.setStatement("SELECT \n" +
                "    a.AdmissionDate,\n" +
                "    a.DischargeDate,\n" +
                "    t.TreatmentName,\n" +
                "    t.TreatmentType,\n" +
                "    t.OrderedTime,\n" +
                "    t.AdministeredTime\n" +
                "FROM \n" +
                "    Admissions a\n" +
                "JOIN \n" +
                "    Patients p ON a.PatientID = p.PatientID\n" +
                "JOIN \n" +
                "    Treatments t ON a.TreatmentID = t.TreatmentID\n" +
                "WHERE \n" +
                "    p.PatientID = ? \n" +
                "    OR (p.FirstName = ? AND p.LastName = ?)\n" +
                "ORDER BY\n" +
                "    a.AdmissionDate DESC,\n" +
                "    t.TreatmentName ASC;\n");

        twoSeven.setCategory("Patient Information");
        twoSeven.setDescription("List patients who were admitted to the hospital within 30 days of their last discharge date.");
        twoSeven.setStatement("SELECT DISTINCT\n" +
                "    p.PatientID,\n" +
                "    p.FirstName,\n" +
                "    p.LastName,\n" +
                "    d.DiagnosisName,\n" +
                "    CONCAT(e.FirstName, ' ', e.LastName) AS AdmittingDoctor\n" +
                "FROM \n" +
                "    Admissions a\n" +
                "JOIN \n" +
                "    Admissions a2 ON a.PatientID = a2.PatientID\n" +
                "    AND a.AdmissionDate > a2.DischargeDate\n" +
                "    AND a.AdmissionDate <= DATE_ADD(a2.DischargeDate, INTERVAL 30 DAY)\n" +
                "JOIN \n" +
                "    Patients p ON a.PatientID = p.PatientID\n" +
                "JOIN \n" +
                "    Diagnoses d ON a.DiagnosisID = d.DiagnosisID\n" +
                "JOIN \n" +
                "    Employees e ON a.PrimaryDoctorID = e.EmployeeID;\n");

        twoEight.setCategory("Patient Information");
        twoEight.setDescription("For each patient that has ever been admitted to the hospital, list their admissions.");
        twoEight.setStatement("SELECT\n" +
                "    p.PatientID,\n" +
                "    p.FirstName,\n" +
                "    p.LastName,\n" +
                "    COUNT(a.AdmissionID) AS TotalAdmissions,\n" +
                "    AVG(DATEDIFF(a.DischargeDate, a.AdmissionDate)) AS AvgDuration,\n" +
                "    MAX(DATEDIFF(a.DischargeDate, a.AdmissionDate)) AS MaxDuration,\n" +
                "    MAX(DATEDIFF(a.AdmissionDate, a2.DischargeDate)) AS LongestSpan,\n" +
                "    MIN(DATEDIFF(a.AdmissionDate, a2.DischargeDate)) AS ShortestSpan,\n" +
                "    AVG(DATEDIFF(a.AdmissionDate, a2.DischargeDate)) AS AverageSpan\n" +
                "FROM\n" +
                "    Patients p\n" +
                "JOIN\n" +
                "    Admissions a ON p.PatientID = a.PatientID\n" +
                "LEFT JOIN\n" +
                "    Admissions a2 ON a.PatientID = a2.PatientID\n" +
                "    AND a.AdmissionDate >= a2.DischargeDate\n" +
                "GROUP BY\n" +
                "    p.PatientID;\n");

        threeOne.setCategory("Diagnosis and Treatment Information");
        threeOne.setDescription("List the diagnoses given to patients.");
        threeOne.setStatement("SELECT\n" +
                "    d.DiagnosisID,\n" +
                "    d.DiagnosisName,\n" +
                "    COUNT(d.DiagnosisName) AS TotalOccurances\n" +
                "FROM\n" +
                "    Admissions a\n" +
                "JOIN\n" +
                "    Diagnoses d ON a.DiagnosisID = d.DiagnosisID\n" +
                "GROUP BY\n" +
                "    d.DiagnosisID\n" +
                "ORDER BY\n" +
                "    TotalOccurances DESC;\n");

        threeTwo.setCategory("Diagnosis and Treatment Information");
        threeTwo.setDescription("List the diagnoses given to hospital patients.");
        threeTwo.setStatement("SELECT\n" +
                "    d.DiagnosisID,\n" +
                "    d.DiagnosisName,\n" +
                "    COUNT(d.DiagnosisName) AS TotalOccurances\n" +
                "FROM\n" +
                "    Admissions a\n" +
                "JOIN\n" +
                "    Diagnoses d ON a.DiagnosisID = d.DiagnosisID\n" +
                "WHERE\n" +
                "    a.DischargeDate IS NULL\n" +
                "GROUP BY\n" +
                "    d.DiagnosisID\n" +
                "ORDER BY\n" +
                "    TotalOccurances DESC;\n");

        threeThree.setCategory("Diagnosis and Treatment Information");
        threeThree.setDescription("List the treatments performed on admitted patients.");
        threeThree.setStatement("SELECT\n" +
                "    t.TreatmentID,\n" +
                "    t.TreatmentName,\n" +
                "    COUNT(t.TreatmentName) AS TotalOccurances\n" +
                "FROM\n" +
                "    Admissions a\n" +
                "JOIN\n" +
                "    Treatments t ON a.TreatmentID = t.TreatmentID\n" +
                "GROUP BY\n" +
                "    t.TreatmentID\n" +
                "ORDER BY\n" +
                "    TotalOccurances DESC;\n");

        threeFour.setCategory("Diagnosis and Treatment Information");
        threeFour.setDescription("List the diagnoses associated with patients who have the highest occurrences of admissions to the " +
                "hospital.");
        threeFour.setStatement("SELECT\n" +
                "    p.PatientID,\n" +
                "    p.FirstName,\n" +
                "    p.LastName,\n" +
                "    d.DiagnosisID,\n" +
                "    d.DiagnosisName,\n" +
                "    COUNT(d.DiagnosisName) AS TotalOccurances\n" +
                "FROM\n" +
                "    Admissions a\n" +
                "JOIN\n" +
                "    Diagnoses d ON a.DiagnosisID = d.DiagnosisID\n" +
                "JOIN\n" +
                "    Patients p ON a.PatientID = p.PatientID\n" +
                "GROUP BY\n" +
                "    p.PatientID,\n" +
                "    d.DiagnosisID\n" +
                "ORDER BY\n" +
                "    TotalOccurances ASC;\n");

        threeFive.setCategory("Diagnosis and Treatment Information");
        threeFive.setDescription("For a given treatment occurrence, list the patient name and the doctor who ordered the treatment.");
        threeFive.setStatement("SELECT \n" +
                "    t.TreatmentID,\n" +
                "    t.TreatmentName,\n" +
                "    p.FirstName,\n" +
                "    p.LastName,\n" +
                "    CONCAT(e.FirstName, ' ', e.LastName) AS DoctorName\n" +
                "FROM \n" +
                "    Admissions a\n" +
                "JOIN \n" +
                "    Patients p ON a.PatientID = p.PatientID\n" +
                "JOIN \n" +
                "    Treatments t ON a.TreatmentID = t.TreatmentID\n" +
                "JOIN \n" +
                "    Employees e ON t.OrderedBy = e.EmployeeID\n" +
                "WHERE \n" +
                "    t.TreatmentID = ?;\n");

        fourOne.setCategory("Employee Information");
        fourOne.setDescription("List all workers at the hospital.");
        fourOne.setStatement("SELECT\n" +
                "    e.EmployeeID,\n" +
                "    e.FirstName,\n" +
                "    e.LastName,\n" +
                "    e.Role\n" +
                "FROM\n" +
                "    Employees e\n" +
                "ORDER BY\n" +
                "    e.LastName ASC, e.FirstName ASC;\n");

        fourTwo.setCategory("Employee Information");
        fourTwo.setDescription("List the primary doctors of patients with a high admission rate.");
        fourTwo.setStatement("SELECT DISTINCT\n" +
                "    e.EmployeeID,\n" +
                "    e.FirstName,\n" +
                "    e.LastName,\n" +
                "    e.Role\n" +
                "FROM\n" +
                "    Employees e\n" +
                "JOIN\n" +
                "    Admissions a ON e.EmployeeID = a.PrimaryDoctorID\n" +
                "WHERE\n" +
                "    a.PatientID IN (\n" +
                "        SELECT \n" +
                "            PatientID\n" +
                "        FROM \n" +
                "            Admissions\n" +
                "        GROUP BY \n" +
                "            PatientID, YEAR(AdmissionDate)\n" +
                "        HAVING \n" +
                "            COUNT(AdmissionID) >= 4\n" +
                "    )\n" +
                "ORDER BY\n" +
                "    e.LastName ASC, e.FirstName ASC;\n");

        fourThree.setCategory("Employee Information");
        fourThree.setDescription("For a given doctor, list all associated diagnoses in descending order of occurrence.");
        fourThree.setStatement("SELECT \n" +
                "    d.DiagnosisID,\n" +
                "    d.DiagnosisName,\n" +
                "    COUNT(d.DiagnosisName) AS TotalOccurances\n" +
                "FROM \n" +
                "    Admissions a\n" +
                "JOIN \n" +
                "    Employees e ON a.PrimaryDoctorID = e.EmployeeID\n" +
                "JOIN\n" +
                "    Diagnoses d ON a.DiagnosisID = d.DiagnosisID\n" +
                "WHERE \n" +
                "    e.employeeID = ?\n" +
                "    OR (e.FirstName = ? AND e.LastName = ?)\n" +
                "GROUP BY\n" +
                "    d.DiagnosisID\n" +
                "ORDER BY\n" +
                "    TotalOccurances DESC;\n");

        fourFour.setCategory("Employee Information");
        fourFour.setDescription("For a given doctor, list all treatments that they ordered.");
        fourFour.setStatement("SELECT \n" +
                "    t.TreatmentID,\n" +
                "    t.TreatmentName,\n" +
                "    COUNT(t.TreatmentName) AS TotalOccurances\n" +
                "FROM \n" +
                "    Treatments t\n" +
                "JOIN \n" +
                "    Employees e ON t.OrderedBy = e.EmployeeID\n" +
                "WHERE \n" +
                "    e.employeeID = ?\n" +
                "    OR (e.FirstName = ? AND e.LastName = ?)\n" +
                "GROUP BY\n" +
                "    t.TreatmentID\n" +
                "ORDER BY\n" +
                "    TotalOccurances DESC;\n");

        fourFive.setCategory("Employee Information");
        fourFive.setDescription("List employees who have been involved in the treatment of every admitted patient.");
        fourFive.setStatement("SELECT \n" +
                "    e.EmployeeID,\n" +
                "    e.FirstName,\n" +
                "    e.LastName,\n" +
                "    e.Role\n" +
                "FROM \n" +
                "    Employees e\n" +
                "WHERE \n" +
                "    NOT EXISTS (\n" +
                "        SELECT 1\n" +
                "        FROM Admissions a\n" +
                "        WHERE NOT EXISTS (\n" +
                "            SELECT 1\n" +
                "            FROM Treatments t\n" +
                "            WHERE (t.OrderedBy = e.EmployeeID OR t.AdministeredBy = e.EmployeeID)\n" +
                "            AND t.TreatmentID = a.TreatmentID\n" +
                "        )\n" +
                "    )\n" +
                "ORDER BY\n" +
                "    e.LastName ASC, e.FirstName ASC;\n");

        queries.add(oneOne);
        queries.add(oneTwo);
        queries.add(oneThree);
        queries.add(twoOne);
        queries.add(twoTwo);
        queries.add(twoThree);
        queries.add(twoFour);
        queries.add(twoFive);
        queries.add(twoSix);
        queries.add(twoSeven);
        queries.add(twoEight);
        queries.add(threeOne);
        queries.add(threeTwo);
        queries.add(threeThree);
        queries.add(threeFour);
        queries.add(threeFive);
        queries.add(fourOne);
        queries.add(fourTwo);
        queries.add(fourThree);
        queries.add(fourFour);
        queries.add(fourFive);
    }

    public List<Query> getQueries() {
        return queries;
    }

}
