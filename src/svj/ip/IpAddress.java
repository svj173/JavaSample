package svj.ip;

import java.io.Serializable;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.Objects;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 17.10.2019 11:59
 */
public class IpAddress implements Serializable, Comparable<IpAddress> {
    // use long to show 0xFFFFFFFF aka unsigned int
    private long address;

    public IpAddress(String ipAddr) throws ParseException {

        int i = 0;
        String[] ipBuffer;
        int[] addr;

        if (ipAddr == null) {
            throw new NullPointerException();
        }

        ipBuffer = ipAddr.replace('.', '!').split("!", 4);
        if (ipBuffer.length < 4) {
            throw new ParseException(ipAddr, ipBuffer.length);
        }

        addr = new int[4];
        try {
            for (i = 0; i < 4; i++) {
                addr[i] = Integer.parseInt(ipBuffer[i]);
                if (addr[i] < 0 || addr[i] > 255) {
                    throw new ParseException(ipAddr, i);
                }
            }
        } catch (ParseException parExc) {
            throw parExc;
        } catch (Exception exception) {
            throw new ParseException(ipAddr, i);
        }

        address = addr[3] & 0xFF;
        address |= ((addr[2] << 8) & 0xFF00);
        address |= ((addr[1] << 16) & 0xFF0000);
        address |= ((addr[0] << 24) & 0xFF000000);

    }

    public static String numericToTextFormat(byte[] src) {
        return (src[0] & 0xff) + "." + (src[1] & 0xff) + "." + (src[2] & 0xff) + "." + (src[3] & 0xff);
    }

    public static byte[] hexStrToByteFormat(String ipAddr) throws ParseException {
        int i = 0;
        String[] ipBuffer;
        byte[] result;

        if (ipAddr == null) {
            throw new NullPointerException();
        }

        ipBuffer = ipAddr.split(":", 4);
        if (ipBuffer.length != 4) {
            byte[] bytes = ipAddr.getBytes();
            if (bytes.length == 4) {
                result = bytes;
            } else {
                throw new ParseException(ipAddr, bytes.length);
            }
        } else {
            result = new byte[4];
            try {
                for (i = 0; i < 4; i++) {
                    result[i] = (byte) Integer.parseUnsignedInt(ipBuffer[i], 16);
                }
            } catch (NumberFormatException nfExc) {
                throw new ParseException(ipAddr, i);
            }
        }

        return result;
    }

    public static String longToTextFormat(long val) {
        return numericToTextFormat(getAddress(val));
    }

    public static byte[] getAddress(long address) {
        byte[] addr = new byte[]{0, 0, 0, 0};

        addr[0] = (byte) ((address >>> 24) & 0xFF);
        addr[1] = (byte) ((address >>> 16) & 0xFF);
        addr[2] = (byte) ((address >>> 8) & 0xFF);
        addr[3] = (byte) (address & 0xFF);

        return addr;
    }

    /**
     * @param ip Адрес для сравнения.
     * @return -1 - этот обьект меньше чем IP; 0 - равны; +1 - этот обьект больше чем IP.
     */
    @Override
    public int compareTo(IpAddress ip) {
        if (ip == null) {
            return 1;
        } else {
            if (toLong() > ip.toLong()) {
                return 1;
            } else if (toLong() < ip.toLong()) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IpAddress address1 = (IpAddress) o;
        return address == address1.address;
    }

    @Override
    public int hashCode() {
        return Objects.hash(address);
    }

    /**
     * @throws UnknownHostException Увеличение адреса привело к несуществующему IP значению (переполнение адресов)
     */
    public void inc() throws UnknownHostException {
        System.out.println("Str address = "+getStringAddress());
        System.out.println("- Long address = "+address);
        long ic = address + 1;

        System.out.println("- Long address after inc = "+ic);

        //if (ic < 0L || ic > 0xFFFFFFFFL) {
        //    throw new UnknownHostException("" + ic + "L");
        //}

        // все нормально - присваиваем новое значение
        address = ic;
        System.out.println("- Str address after inc = "+getStringAddress());
    }

    // -------------------------------------- static -----------------------------------

    public void inc(long size) throws UnknownHostException {
        // расчитываем для промежуточного значения - чтобы при ошибке инкремента сохранилось старое значение.
        long ic = address + size;

        if (ic < 0L || ic > 0xFFFFFFFFL) {
            throw new UnknownHostException("" + ic + "L");
        }

        // все нормально - присваиваем новое значение
        address = ic;
    }

    public long toLong() {
        return address;
    }

    public String getStringAddress() {
        return numericToTextFormat(getAddress(address));
    }

    /**
     * @return Инфа об обьекте.
     */
    public String toString() {
        return getStringAddress();
    }


    public static void main(String[] args) {

        IpAddress ipAddress;

        String[] ips = new String[] { "192.168.0.3", "172.31.0.3", "10.31.0.3"};

        try {

            for ( String ip: ips) {
                ipAddress = new IpAddress(ip);
                ipAddress.inc();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
