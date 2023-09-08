package svj.irc.test;

/**
 * Проверяем режим для S=-1
 * <BR/>
 */
public class SubdeviceValueTest {

    public static void main(String[] args) {

        SubdeviceValueTest handler = new SubdeviceValueTest();
    }

    /*
    public String irpToArray(String protocolName, Map<String, Long> parameters) throws Exception {
        try {
            Protocol protocol = getProtocol(protocolName);

            parameters.entrySet().removeIf((entry) -> {
                String name = entry.getKey();
                Long value = entry.getValue();
                return !protocol.getParameterSpecs().getParameterSpec(name).isWithinDomain(value);
            });

            IrSignal irSignal = protocol.toIrSignal(parameters);

            // формируем массив импульсов в десятичном виде, с разделителем - точка с запятой. впереди - частота
            String strIrArray = IrSequence.concatenate(irSignal.toIrSequences()).toString(false, ";");

            return Math.round(irSignal.getFrequencyWithDefault()) + "|" + strIrArray;

        } catch (Exception ex) {
            System.out.println("protocolName = '" + protocolName + "' parameters = " + parameters +
                    ". Error: " + ex.getMessage());
            ex.printStackTrace();
            //throw ex;
        }
    }
    */

}
