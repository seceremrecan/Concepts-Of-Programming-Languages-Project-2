
import java.util.UUID;
public class Motor {

    private String motorNo;
    private boolean calisiyor;
    /**
     * Varsayılan kurucu fonksiyon
     */


    public Motor() {

        this.motorNo = UUID.randomUUID().toString();
        /*Başlangıçta false */calisiyor = false;

    }
    /**
     *
     * @param motorNo UUID olarak motor id
     * @return motor instance
     */


    public Motor(String motorNo) {


        /*
         *Varolan bir motor no ile oluşturuluyor.
         */
        this.motorNo = motorNo;
        calisiyor = false; // false yapılıyor
    }

    public void calistir() {
/**
 * calisiyor true yapılıyor
 *
 */
        calisiyor = true;

    }
    /**
     * Motorun durdurulması //
     */
    public void durdur() {
        calisiyor = false;
    }

    public String getMotorNo() {
// motor no getir

        return motorNo;
    }
    @Override
    public String toString() {
// durum belirlenmesi //
        String durum = calisiyor ? "Motor Çalışıyor." : "Motor Çalışmıyor";
        return durum;
    }


}
