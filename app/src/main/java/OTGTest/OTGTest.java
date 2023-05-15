package OTGTest;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
/** Класс включает в себя несколько статических методов, позволяющих установить поддерживает ли устройство OTG
 @author Андрей Бушуев
 @version 0.2
 */
public class OTGTest {
    /**
     * Проверяет наличие функции <i>USB HOST</i> в системе
     *
     * @return <i>true</i> если поддерживается, <i>false</i> если нет
     */
    public static boolean usbHostTest(Context cn) {
        return cn.getPackageManager().hasSystemFeature((PackageManager.FEATURE_USB_HOST));
    }

    /**
     * Проверяет смартфон в базе phonedb.net
     * <b>Не может быть исполнен в основном потоке</b>
     * @return -1 не поддерживает, 0 не найден в базе, 1 поддерживает
     */
    public static int searchIntoPhoneDB() throws IOException {
        try {
            //Получение модели смартфона
            String PhoneModel = android.os.Build.MODEL;
            Log.e("Model", PhoneModel);
            //Поиск по базе
            Document searchPage = Jsoup.connect("https://phonedb.net/index.php?m=device&s=list").data("search_exp", PhoneModel).data("search_state_filter", "1").post();
            //Выделение ссылки на страницу с описанием
            Element Url = searchPage.select("div.content_block_title > a").first();
            //Если не удалось выделить ссылку, считаем что смартфона нет в баэе
            //TODO Улучшить обработь проверку на наличие смартфона в базе
            if (Url == null) return 0;
            String PhoneUrl = "https://phonedb.net/"+Url.attr("href").toString();

            Log.e("URL", PhoneUrl);
            //Загрузка страницы с характеристиками
            Document specificationsPage = Jsoup.connect(PhoneUrl).get();
            Log.e("Name", specificationsPage.title());
             Elements OTG = specificationsPage.select("a:containsOwn(USB Host)");
            if (OTG.size() != 0) return 1;
            return -1;
        } catch (IOException e) {
            Log.e("Err", e.getMessage());
            throw e;
        }
    }

    /**
     * Проверяет смартфон в базе устройств, с которыми ECG Dongle, ECG Dongle Full, PetNet, уже ранее работал
     *
     * @return true если смартфон есть в базе поддерживаемых, false если нет
     */
    public static boolean serchIntoSupportedDevices() throws IOException {
        //Получение модели смартфона
        String PhoneModel = android.os.Build.MODEL;
        Log.e("Model", PhoneModel);
        Document basePage;
        //Загрузка базы
        try {
            basePage = Jsoup.connect("https://cardio-cloud.ru/service/supportedDevicesList").get();
        }catch (IOException e) {
        Log.e("Err", e.getMessage());
        throw e;
        }
        /*

             */
        Element desc = basePage.selectFirst(String.format("div.row:contains(%s)", PhoneModel));
        if (desc == null) return false;//Устройства нет в базе
        Element Dongle = desc.selectFirst(":containsOwn(ECG Dongle)"); //Поддерживается ECG Dongle, если нет ссылка null
        Element DongleFull = desc.selectFirst(":containsOwn(ECG Dongle Full)"); //Поддерживается ECG Dongle Full, если нет ссылка null
        Element PetNet = desc.selectFirst(":containsOwn(PetNet)"); //Поддерживается PetNet, если нет ссылка null
        return (Dongle != null)||(DongleFull != null)||(PetNet != null);
    }

}
