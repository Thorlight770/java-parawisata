package com.java.parawisata.javaparawisata.Utils.Helper;

import com.java.parawisata.javaparawisata.JavaParawisataApp;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.AdditionalMessage;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.ControlMessage;
import com.java.parawisata.javaparawisata.Utils.ControlMessage.MessageType;
import com.java.parawisata.javaparawisata.Utils.Database.DBConnection;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Helper {
    public static String thousandsSeparator(long data) {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();

        symbols.setGroupingSeparator(',');
        formatter.setDecimalFormatSymbols(symbols);
        return formatter.format(data);
    }

    public static String thousandsSeparator(String data) {
        data = data.substring(0, data.length() - 3);
        return thousandsSeparator(Long.parseLong(data));
    }

    public static void printReport(String fileReport, Map<String, Object> params) throws JRException, URISyntaxException {
        try {
            /**
             * Path path = Paths.get("fileReport/".concat(fileReport)).toAbsolutePath();
             * File report = new File(String.valueOf(Helper.class.getClassLoader().getResource("com/java/parawisata/javaparawisata/fileReport/".concat(fileReport))));;
             * JasperDesign jasperDesign = JRXmlLoader.load(report);
             * JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
             **/
            JasperReport jasperReport = JasperCompileManager.compileReport(Helper.class.getClassLoader().getResourceAsStream("com/java/parawisata/javaparawisata/fileReport/".concat(fileReport)));
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params,DBConnection.GetConnection());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (Exception ex) {
            throw ex;
        }
    }

    public static void printReport(String fileReport) throws JRException, URISyntaxException {
        Map<String, Object> params = new HashMap<>();
        printReport(fileReport, params);
    }
}
