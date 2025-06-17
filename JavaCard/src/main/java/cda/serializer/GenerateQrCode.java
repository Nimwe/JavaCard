//package cda.serializer;
//
//import cda.classe.Contact;
//import cda.model.ExportModel;
//import com.google.zxing.BarcodeFormat;
//import com.google.zxing.EncodeHintType;
//import com.google.zxing.MultiFormatWriter;
//import com.google.zxing.client.j2se.MatrixToImageWriter;
//import com.google.zxing.common.BitMatrix;
//import com.google.zxing.pdf417.decoder.ec.ErrorCorrection;
//import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
//
//import java.io.File;
//import java.io.IOException;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
//public class GenerateQrCode {
//
//    //fonction pour creer le qrcode
//    public static void createQR(String data, String path, String charset, Map hashMap, int height, int width)throws Exception, IOException {
//        BitMatrix matrix = new MultiFormatWriter().encode(
//                new String(data.getBytes(charset), charset),
//                BarcodeFormat.QR_CODE, width, height);
//
//        MatrixToImageWriter.writeToPath(
//                matrix,
//                path.substring(path.lastIndexOf('.') + 1),
//                new File(path).toPath());
//
//    }
//
//
//    public static void main(String[] args) throws Exception {
//        ExportModel exportModel = new ExportModel();
//        ArrayList<Contact> objectsToSave = new ArrayList<>();
//
//        Contact contact1 = new Contact(
//                "Alice", "Dupont", "Ali", Contact.Gender.FEMALE, LocalDate.of(1990, 5, 12),
//                "https://picsum.photos/id/29/4000/2670", "0612345678", "0147852369", "alice.dupont@example.com", "https://github.com/aliced",
//                "123 Rue de Paris", 75001, "Paris", "TechCorp", "0147234567", "0147998888",
//                "contact@techcorp.com", "https://techcorp.com", "Développeuse Java expérimentée."
//        );
//
//        objectsToSave.add(contact1);
//
////        String data = exportModel.exportToVCard();
//        String path = "test.png";
//        String charset = "UTF-8";
//
//        Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
//        hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
//
//        createQR("www.com", path, charset, hashMap, 200, 200);
//    }
//}
