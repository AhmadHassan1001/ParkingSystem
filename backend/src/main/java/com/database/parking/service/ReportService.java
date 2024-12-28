package com.database.parking.service;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class ReportService {

    private final String url = "jdbc:mysql://localhost:3306/parking_management_system";
    private final String dbUserName = "admin";
    private final String dbPassword = "admin";

    public String[] generateAdminDashboardReport() throws JRException {
        try (Connection connection = DriverManager.getConnection(url, dbUserName, dbPassword)) {

            String rootDirectory = System.getProperty("user.dir");
            String path1 = rootDirectory + "/src/main/resources/reports/admin_dashboard_report_users.jrxml";
            String path2 = rootDirectory + "/src/main/resources/reports/admin_dashboard_report_lots.jrxml";

            InputStream reportStream1 = new FileInputStream(path1);
            JasperReport jasperReport1 = JasperCompileManager.compileReport(reportStream1);

            InputStream reportStream2 = new FileInputStream(path2);
            JasperReport jasperReport2 = JasperCompileManager.compileReport(reportStream2);

            // Execute the stored procedure
            String sql1 = "{CALL admin_dashboard_report_users()}";
            PreparedStatement statement1 = connection.prepareStatement(sql1);
            ResultSet resultSet1 = statement1.executeQuery();
            // Process the result set and create a data source for JasperReports
            List<Map<String, Object>> dataList1 = new ArrayList<>();
            while (resultSet1.next()) {
                Map<String, Object> data = new HashMap<>();
                data.put("user_id", resultSet1.getInt("user_id"));
                data.put("user_name", resultSet1.getString("user_name"));
                data.put("total_reservations", resultSet1.getInt("total_reservations"));
                data.put("total_spent", resultSet1.getDouble("total_spent"));
                dataList1.add(data);
            }

            // Execute the stored procedure
            String sql2 = "{CALL admin_dashboard_report_lots()}";
            PreparedStatement statement2 = connection.prepareStatement(sql2);
            ResultSet resultSet2 = statement2.executeQuery();
            // Process the result set and create a data source for JasperReports
            List<Map<String, Object>> dataList2 = new ArrayList<>();
            while (resultSet2.next()) {
                Map<String, Object> data = new HashMap<>();
                data.put("parking_lot_id", resultSet2.getInt("parking_lot_id"));
                data.put("parking_lot_name", resultSet2.getString("parking_lot_name"));
                data.put("parking_lot_total_revenue", resultSet2.getInt("parking_lot_total_revenue"));
                data.put("parking_lot_total_reservations", resultSet2.getDouble("parking_lot_total_reservations"));
                dataList2.add(data);
            }

            JRBeanCollectionDataSource dataSource1 = new JRBeanCollectionDataSource(dataList1);
            JRBeanCollectionDataSource dataSource2 = new JRBeanCollectionDataSource(dataList2);

            // Fill the report with data
            JasperPrint jasperPrint1 = JasperFillManager.fillReport(jasperReport1, new HashMap<>(), dataSource1);
            JasperPrint jasperPrint2 = JasperFillManager.fillReport(jasperReport2, new HashMap<>(), dataSource2);

            // Save the reports to files
            String outputPath1 = rootDirectory + "/reports/admin_dashboard_report_users.pdf";
            String outputPath2 = rootDirectory + "/reports/admin_dashboard_report_lots.pdf";

            JasperExportManager.exportReportToPdfFile(jasperPrint1, outputPath1);
            JasperExportManager.exportReportToPdfFile(jasperPrint2, outputPath2);

            return new String[] {outputPath1, outputPath2};

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String generateManagerDashboardReport(long managerId) {
        try (Connection connection = DriverManager.getConnection(url, dbUserName, dbPassword)) {

            String rootDirectory = System.getProperty("user.dir");
            String path = rootDirectory + "/src/main/resources/reports/manager_dashboard_report.jrxml";
        
            InputStream reportStream = new FileInputStream(path);
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
        
            // Execute the stored procedure
            String sql = "{CALL manager_dashboard_report(?)}";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, (int) managerId);
            ResultSet resultSet = statement.executeQuery();
            // Process the result set and create a data source for JasperReports
            List<Map<String, Object>> dataList = new ArrayList<>();
            while (resultSet.next()) {
                Map<String, Object> data = new HashMap<>();
                data.put("parking_lot_id", resultSet.getInt("parking_lot_id"));
                data.put("parking_lot_name", resultSet.getString("parking_lot_name"));
                data.put("occupied_spots", resultSet.getString("occupied_spots"));
                data.put("available_spots", resultSet.getString("available_spots"));
                data.put("occupancy_rate", resultSet.getDouble("occupancy_rate"));
                data.put("total_revenue", resultSet.getDouble("total_revenue"));
                dataList.add(data);
            }
        
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dataList);
        
            // Fill the report with data
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(), dataSource);
        
            // Save the report to a file
            String outputPath = rootDirectory + "/reports/manager_dashboard_report.pdf";
        
            JasperExportManager.exportReportToPdfFile(jasperPrint, outputPath);
        
            return outputPath;
        
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}