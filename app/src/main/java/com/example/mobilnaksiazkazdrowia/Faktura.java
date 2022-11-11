package com.example.mobilnaksiazkazdrowia;

import android.content.Context;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.util.Base64;
import android.webkit.WebView;
import android.widget.Toast;

public class Faktura {

    WebView fakturaWebView;
    PrintManager printManager;
    Faktura(WebView fakturaWebView, PrintManager printManager){
        this.fakturaWebView = fakturaWebView;
        this.printManager = printManager;
    }

    public void wydrukuj() {
        try {
            fakturaWebView.setActivated(true);
            fakturaWebView.getSettings().setAllowContentAccess(true);
            fakturaWebView.getSettings().setAllowFileAccess(true);
            fakturaWebView.getSettings().setDomStorageEnabled(true);
            fakturaWebView.getSettings().setJavaScriptEnabled(true);

            String encodedHtml = Base64.encodeToString(trescFakturyHTML.getBytes(), Base64.NO_PADDING);
            // webview.loadData(encodedHtml,"text/html","base64");

            fakturaWebView.loadDataWithBaseURL("file:///assets/", trescFakturyHTML, "text/html", "utf-8", null);

            fakturaWebView.loadDataWithBaseURL("file:///assets/", trescFakturyHTML, "text/html", "utf-8", null);
            // webview.loadUrl("file:///" + Environment.getExternalStorageDirectory() + "/Download/APLIKACJA/logotest2.jpg");
            // String jobName = this.getString(R.string.app_name) + " Document";

            PrintDocumentAdapter printAdapter;
            if (android.os.Build.VERSION.SDK_INT >= 21) {
                printAdapter = fakturaWebView.createPrintDocumentAdapter("test");
            } else {
                printAdapter = fakturaWebView.createPrintDocumentAdapter();
            }

            PrintAttributes.Builder builder = new PrintAttributes.Builder();
            builder.setMinMargins(PrintAttributes.Margins.NO_MARGINS);
            builder.setMediaSize(PrintAttributes.MediaSize.ISO_A4);

            printManager.print("test", printAdapter, builder.build());

        } catch (Exception e) {
            //Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }



    public String trescFakturyHTML=
            "<html> \n <head> \n <title> Tytul </title></head> \n"+
            "<table style=\"border-collapse: collapse; width: 95.7409%; height: 168px;\" border=\"1\">\n" +
            "<tbody>\n" +
            "<tr>\n" +
            "<td style=\"width: 50%; text-align: left;\">Sprzedawca</td>\n" +
            "<td style=\"width: 150.297%; text-align: left;\">Nabywca</td>\n" +
            "</tr>\n" +
            "<tr>\n" +
            "<td style=\"width: 50%;\"><strong>Weterynarz</strong></td>\n" +
            "<td style=\"width: 150.297%;\">&nbsp;</td>\n" +
            "</tr>\n" +
            "<tr>\n" +
            "<td style=\"width: 50%;\">ul. Przykladowa 12</td>\n" +
            "<td style=\"width: 150.297%;\">&nbsp;</td>\n" +
            "</tr>\n" +
            "<tr>\n" +
            "<td style=\"width: 50%;\">Bydgoszcz</td>\n" +
            "<td style=\"width: 150.297%;\">&nbsp;</td>\n" +
            "</tr>\n" +
            "<tr>\n" +
            "<td style=\"width: 50%;\">NIP:PL1231231231</td>\n" +
            "<td style=\"width: 150.297%;\">&nbsp;</td>\n" +
            "</tr>\n" +
            "</tbody>\n" +
            "</table>\n" +
            "<p>&nbsp;</p>\n" +
            "<table style=\"border-collapse: collapse; width: 100%;\" border=\"1\">\n" +
            "<tbody>\n" +
            "<tr>\n" +
            "<td style=\"width: 23.1849%; text-align: center;\">Nazwa towaru lub usługi</td>\n" +
            "<td style=\"width: 13.3838%; text-align: center;\">Cena netto</td>\n" +
            "<td style=\"width: 11.9635%; text-align: center;\">Stawka VAT</td>\n" +
            "<td style=\"width: 16.935%; text-align: center;\">Wartość netto</td>\n" +
            "<td style=\"width: 9.94323%; text-align: center;\">Wartość VAT</td>\n" +
            "<td style=\"width: 6.56563%; text-align: center;\">Wartość brutto</td>\n" +
            "</tr>\n" +
            "<tr>\n" +
            "<td style=\"width: 23.1849%; text-align: center;\">Szczepienie</td>\n" +
            "<td style=\"width: 13.3838%; text-align: center;\">150.00</td>\n" +
            "<td style=\"width: 11.9635%; text-align: center;\">zw</td>\n" +
            "<td style=\"width: 16.935%; text-align: center;\">150.00</td>\n" +
            "<td style=\"width: 9.94323%; text-align: center;\">0.00</td>\n" +
            "<td style=\"width: 6.56563%; text-align: center;\">150.00</td>\n" +
            "</tr>\n" +
            "</tbody>\n" +
            "</table>\n" +
            "<p>&nbsp;</p>\n" +
            "<table style=\"border-collapse: collapse; width: 57.2443%; height: 72px;\" border=\"1\" align=\" right\">\n" +
            "<tbody>\n" +
            "<tr style=\"height: 36px;\">\n" +
            "<td style=\"width: 17.0596%; text-align: center; height: 36px;\">&nbsp;</td>\n" +
            "<td style=\"width: 32.9404%; text-align: center; height: 36px;\">Wartość netto</td>\n" +
            "<td style=\"width: 24.0075%; text-align: center; height: 36px;\">Wartość VAT</td>\n" +
            "<td style=\"width: 25.9925%; text-align: center; height: 36px;\">Wartość brutto</td>\n" +
            "</tr>\n" +
            "<tr style=\"text-align: center;\">\n" +
            "<td style=\"width: 17.0596%; height: 18px;\">zw</td>\n" +
            "<td style=\"width: 32.9404%; height: 18px;\">150.00</td>\n" +
            "<td style=\"width: 24.0075%; height: 18px;\">0.00</td>\n" +
            "<td style=\"width: 25.9925%; height: 18px;\">150.00</td>\n" +
            "</tr>\n" +
            "<tr style=\"text-align: center;\">\n" +
            "<td style=\"width: 17.0596%; height: 18px;\">Razem</td>\n" +
            "<td style=\"width: 32.9404%; height: 18px;\">150.00</td>\n" +
            "<td style=\"width: 24.0075%; height: 18px;\">0.00</td>\n" +
            "<td style=\"width: 25.9925%; height: 18px;\">150.00</td>\n" +
            "</tr>\n" +
            "</tbody>\n" +
            "</table>"+
            "<p>&nbsp;</p>\n"+
            "<img src=\"/storage/emulated/0/Download/APLIKACJA/kodQRBadanegoPsa.bmp\"" + "style=\"width:300px;height:300px;\">"+
            "</html>";

}
