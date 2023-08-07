package svj.file.irdb;

/**
 * <BR/>
 * 1) id
 * 2) vendor - Производитель - Самсунг, LG,...
 * 3) deviceClassType   - Тип - Кондер, ТВ
 * 4) functionName   - название функции (чисто для информации)
 * 5) protocol   - название протокола
 * 6) deviceNumber     - номер устройства
 * 7) subdeviceNumber  - номер подустройства
 * 7) function   - номер функции
 */
public class IrTemplateObj {
     private final String id;
     private final String vendor;
     private final String deviceClassType;
     private final String functionName;
     private final String protocol;
     private final int deviceNumber;
     private final int subdeviceNumber;
     private final int function;


    public IrTemplateObj(String id, String vendor, String deviceClassType, String functionName, String protocol,
                         String deviceNumberStr, String subdeviceNumberStr, String functionStr) {
        this.id = id;
        this.vendor = vendor;
        this.deviceClassType = deviceClassType;
        this.functionName = functionName;
        this.protocol = protocol;
        this.deviceNumber = Integer.parseInt(deviceNumberStr);
        this.subdeviceNumber = Integer.parseInt(subdeviceNumberStr);
        this.function = Integer.parseInt(functionStr);
    }

    public String getId() {
        return id;
    }

    public String getVendor() {
        return vendor;
    }

    public String getDeviceClassType() {
        return deviceClassType;
    }

    public String getFunctionName() {
        return functionName;
    }

    public String getProtocol() {
        return protocol;
    }

    public int getDeviceNumber() {
        return deviceNumber;
    }

    public int getSubdeviceNumber() {
        return subdeviceNumber;
    }

    public int getFunction() {
        return function;
    }

    @Override
    public String toString() {
        return "IrTemplateObj{" +
                "id='" + id + '\'' +
                ", vendor='" + vendor + '\'' +
                ", deviceClassType='" + deviceClassType + '\'' +
                ", functionName='" + functionName + '\'' +
                ", protocol='" + protocol + '\'' +
                ", deviceNumber=" + deviceNumber +
                ", subdeviceNumber=" + subdeviceNumber +
                ", function=" + function +
                '}';
    }
}
