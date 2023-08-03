package svj.irc;

import org.harctoolbox.analyze.*;
import org.harctoolbox.ircore.*;
import org.harctoolbox.irp.*;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.*;

/**
 * Преобразование массива импульсов, полученных от ИК пульта (кондиционера) в Ir команду.
 * <BR/>
 */
public class IrcConvertTest {

    private final static boolean decodeStrict = false;
    private final static boolean decodeRecursive = false;
    private final static boolean decodeOverride = false;

    private final static boolean analyzerEliminateConstantVars = false;
    private final static double analyzerMaxRoundingError = Burst.Preferences.DEFAULT_MAX_ROUNDING_ERROR;
    private final static double analyzerMaxUnits = Burst.Preferences.DEFAULT_MAX_UNITS;
    private final static double analyzerMaxMicroSeconds = Burst.Preferences.DEFAULT_MAX_MICROSECONDS;
    private final static String analyzerTimeBase = null;
    private final static boolean analyzerLsb = false;
    private final static boolean analyzerExtent = false;
    private final static List<Integer> analyzerParameterWidths = new ArrayList<>(0);
    private final static int analyzerMaxParameterWidth = FiniteBitField.MAXWIDTH;
    private final static boolean analyzerInvert = false;


    private         Props properties = new Props();

    /**
     * БД для IR протоколов
     */
    private IrpDatabase irpDatabase;

    /**
     * Декодер, который выделяет из сигнала - Номер устройства, Номер подустройства, Номер функции
     */
    private Decoder decoder;
    private Decoder.DecoderParameters decoderParameters;


    public static void main ( String[] args ) {


        /*
1) вкл
4272;4508;401;1776;426;649;400;1776;401;1776;425;651;401;675;425;1752;425;650;401;674;401;1777;400;675;425;650;423;1753;426;1751;400;675;425;1752;400;675;401;1776;424;1753;401;1776;401;1776;425;650;401;1776;402;1775;418;1759;400;675;400;675;426;650;400;675;401;1776;401;675;424;651;400;1776;401;1776;400;1776;400;675;400;676;400;675;400;675;400;675;401;675;400;676;400;676;400;1776;424;1753;425;1752;400;1776;400;1777;401;5343;4282;4530;401;1777;400;675;400;1777;400;1777;400;676;400;676;400;1776;400;675;400;676;400;1776;400;676;399;676;400;1777;400;1777;400;676;399;1776;400;676;399;1777;400;1777;400;1777;399;1777;399;676;400;1776;400;1776;401;1776;400;675;400;676;399;676;400;675;399;1777;400;676;399;676;399;1778;400;1776;399;1777;400;676;399;676;400;676;399;676;399;676;399;676;400;676;399;677;399;1777;400;1777;400;1776;400;1777;399;1777;399;

0000 006C 0000 0032 00B0 00B0 0010 0044 0010 001A 0010 0044 0010 0044 0010 001A 0010 001A 0010 0044 0010 001A 0010 001A 0010 0044 0010 001A 0010 001A 0010 0044 0010 0044 0010 001A 0010 0044 0010 001A 0010 0044 0010 0044 0010 0044 0010 0044 0010 001A 0010 0044 0010 0044 0010 0044 0010 001A 0010 001A 0010 001A 0010 001A 0010 0044 0010 001A 0010 001A 0010 0044 0010 0044 0010 0044 0010 001A 0010 001A 0010 001A 0010 001A 0010 001A 0010 001A 0010 001A 0010 001A 0010 0044 0010 0044 0010 0044 0010 0044 0010 0044 0010 00B0

         */


        // вкл
        String str;
        //str = "4272;4508;401;1776;426;649;400;1776;401;1776;425;651;401;675;425;1752;425;650;401;674;401;1777;400;675;425;650;423;1753;426;1751;400;675;425;1752;400;675;401;1776;424;1753;401;1776;401;1776;425;650;401;1776;402;1775;418;1759;400;675;400;675;426;650;400;675;401;1776;401;675;424;651;400;1776;401;1776;400;1776;400;675;400;676;400;675;400;675;400;675;401;675;400;676;400;676;400;1776;424;1753;425;1752;400;1776;400;1777;401;5343;4282;4530;401;1777;400;675;400;1777;400;1777;400;676;400;676;400;1776;400;675;400;676;400;1776;400;676;399;676;400;1777;400;1777;400;676;399;1776;400;676;399;1777;400;1777;400;1777;399;1777;399;676;400;1776;400;1776;401;1776;400;675;400;676;399;676;400;675;399;1777;400;676;399;676;399;1778;400;1776;399;1777;400;676;399;676;400;676;399;676;399;676;399;676;400;676;399;677;399;1777;400;1777;400;1776;400;1777;399;1777;399;";
        // Samsung20, D=1, S=8, F=63
        str = "0000 006C 0000 0016 00AD 00AD 0016 0041 0016 0016 0016 0016 0016 0016 0016 0016 0016 0016 0016 0016 0016 0016 0016 0016 0016 0041 0016 0016 0016 0016 0016 0041 0016 0041 0016 0041 0016 0041 0016 0041 0016 0041 0016 0016 0016 0016 0016 08D3";


        try {
            // импульсы в код
            IrcConvertTest handler = new IrcConvertTest();
            handler.convertToIr(str);

            // код в импульсы  - протокол - утсройство - подустройство - функция
            // - задаем: Протокол=Samsung20, D=1, S=8, F=63
            // - Получаем
            // {38.4k,564}<1,-1|1,-3>(8,-8,D:6,S:6,F:8,1,^100m)*[D:0..63,S:0..63,F:0..255]
            // 0000 006C 0000 0016 00AD 00AD 0016 0041 0016 0016 0016 0016 0016 0016 0016 0016 0016 0016 0016 0016 0016 0016 0016 0016 0016 0041 0016 0016 0016 0016 0016 0041 0016 0041 0016 0041 0016 0041 0016 0041 0016 0041 0016 0016 0016 0016 0016 08D3
            // Scrut = A=0x804fc

            handler.convertToImpuls();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //private org.harctoolbox.guicomponents.IrpRenderBean irpMasterBean;

    public IrcConvertTest() throws Exception {
        setupIrpDatabase();
        setupDecoder();

        // константы - используются
        Map<String, Integer> map = new LinkedHashMap<>(8);
        map.put("0b", 2);
        map.put("%", 2);
        map.put("0q", 4);
        map.put("0", 8);
        map.put("0x", 16);
        IrCoreUtils.setRadixPrefixes(map);
    }


    private void convertToImpuls() {
        try {
            System.out.println("\n------------------------ convertToImpuls -------------------------");
            Protocol protocol = getProtocol("Samsung20");

            Map<String, Long> parameters = getParameters();
            System.out.println("parameters = " + parameters);

            IrSignal irSignal = protocol.toIrSignal(parameters);
            //NameEngine nameEngine = new NameEngine(params);
            //return this.toIrSignal(nameEngine);

            System.out.println("irSignal = " + irSignal);

            String result = formatIrSignal(irSignal, 0);
            System.out.println("line = " + result);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private Protocol getProtocol(String intialProtocol) throws Exception {
        System.out.println("intialProtocol = " + intialProtocol);

        //System.out.println("irpDatabase.getKeys = " + irpDatabase.getKeys());
        //System.out.println("irpDatabase.getNames = " + irpDatabase.getNames());


        // irpDatabase хранит список протоколо, где ключ - имя протокола в нижнем регистре
        String protocolName = irpDatabase.isKnown(intialProtocol) ? intialProtocol : "NEC1";
        System.out.println("protocolName = " + protocolName);

        Protocol protocol = irpDatabase.getProtocol(protocolName);
        System.out.println("protocol = " + protocol);
        return protocol;
    }

    /*
    public IrSignal render() throws IrpException, IrCoreException, ParseException {
        Map<String, Long> parameters = getParameters();
        IrSignal irSignal = protocol.toIrSignal(parameters);
        return irSignal;
    }
    */

    public Map<String, Long> getParameters() {
        Map<String, Long> parameters = new LinkedHashMap<>(4);

        parameters.put("D", 1L);
        parameters.put("S", 8L);
        parameters.put("F", 63L);
        //parameters.put("T", null);

        /*
        // Обрабокта дополнительныых параметров
        String addParams = protocol.hasNonStandardParameters() ? additionalParametersTextField.getText() : null;
        if (addParams != null && !addParams.trim().isEmpty()) {
            String[] str = addParams.trim().split("[ \t]+");
            for (String s : str) {
                String[] q = s.split("=");
                if (q.length == 2)
                    parameters.put(q[0], q[1]);
            }
        }
        */
        //RandomValueSet.initRng(); // just to be on the safe side
        //return new InputVariableSetValues(parameters, true, protocol);
        return parameters;
    }



    public void convertToIr (String strArray ) {

        System.out.println("\n------------------------ convertToIr -------------------------");
        //System.out.println("strArray.size = " + strArray);

        try {
            IrSignal irSignal = InterpretString.interpretString(strArray, properties.getFrequency(), properties.getDummyGap(),
                            properties.getInvokeRepeatFinder(), properties.getInvokeCleaner(),
                            properties.getAbsoluteTolerance(), properties.getRelativeTolerance(), properties.getMinRepeatLastGap());

            System.out.println("irSignal = " + irSignal);

            // выделили внятный сигнал - анализируем его
            if (irSignal != null) {
                Protocol protocol = scrutinizeIrSignal(irSignal, properties);
                System.out.println("protocol = " + protocol);

                // выделение декодера
                Decoder.SimpleDecodesSet decoders = setDecodeIrParameters(irSignal);
                System.out.println("decoders = " + decoders);
                if (decoders != null) {
                    for (Iterator<Decoder.Decode> it = decoders.iterator(); it.hasNext(); ) {
                        Decoder.Decode decode = it.next();
                        System.out.println("- decoder = " + decode);   // Samsung20: {D=1,S=8,F=63}
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Анализ сигнала. Должен выдать
     * - Protocol - {38.4k,573,msb}<1,-1|1,-3>(8,-8,A:20,1,-58.857m)*{A=525564}
     * - Decoder  - Samsung20: {D=1,S=8,F=63}, beg=0, end=43, reps=1
     * @param irSignal
     * @param properties
     * @throws InvalidArgumentException
     */
    public Protocol scrutinizeIrSignal(IrSignal irSignal, Props properties) throws InvalidArgumentException {
        Protocol protocol = null;

        if (irSignal == null || irSignal.isEmpty()) {
            System.out.println("Not scrutinizing empty signal.");
            return protocol;
        }

        String answer = "0001";
        while (irSignal.containsZeros()) {
            // какие-то преобразвоания данных в irSignal, где answer - это текст, вводимый пользвоателем из диалога.
            // по умолчанию равен "0001" - это значение и используем
            try {
                irSignal.replaceZeros(Integer.parseInt(answer, 16));
                // - этот преобразователь вызывается, если длина answer не равна 4 (константа Pronto.CHARS_IN_DIGIT).
                //irSignal.replaceZeros(Double.parseDouble(answer));
            } catch (NumberFormatException ex) {
                // при проблемах дальнейшую работу не прерываем
                ex.printStackTrace();
            }

            /*
            try {
                if (answer.length() == Pronto.CHARS_IN_DIGIT) {
                    irSignal.replaceZeros(Integer.parseInt(answer, 16));
                } else {
                    irSignal.replaceZeros(Double.parseDouble(answer));
                }
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
            */
        }

        // Занести в ГУИ. int introLength, int repeatLength, int nRepetitions, int endingLength
        //setRepeatParameters(irSignal.getIntroLength(), irSignal.getRepeatLength(), irSignal.getRepeatLength() >  0 ? 1 : 0, irSignal.getEndingLength());
        System.out.println("introLength = " + irSignal.getIntroLength());
        System.out.println("repeatLength = " + irSignal.getRepeatLength());
        System.out.println("nRepetitions = " + (irSignal.getRepeatLength() >  0 ? 1 : 0));
        System.out.println("endingLength = " + irSignal.getEndingLength());

        // анализ сигнала -попытка выделить Протокол, Устройство и пр.
        //if (properties.getInvokeAnalyzer()) {
        //    protocol = setAnalyzeParameters(irSignal);
        //}
        // Ignore repeatfinder request if irSignal already has repeat.
        if (properties.getInvokeRepeatFinder() && irSignal.getRepeatLength() == 0) {
            ModulatedIrSequence irSequence = irSignal.toModulatedIrSequence();
            System.out.println("irSequence = " + irSequence);
            protocol = setAnalyzeParameters(irSequence);
        } else {
            Analyzer analyzer = new Analyzer(irSignal, properties.getAbsoluteTolerance(), properties.getRelativeTolerance());
            System.out.println("analyzer = " + analyzer);
            // здесь выделяется Протокол
            protocol = setAnalyzeParameters(analyzer);
        }


        // гуи отображение
        displaySignal(irSignal);

        return protocol;
    }

    private Protocol setAnalyzeParameters(ModulatedIrSequence irSequence) throws InvalidArgumentException {
        Analyzer analyzer = new Analyzer(irSequence, irSequence.getFrequency(), properties.getInvokeRepeatFinder(),
                properties.getAbsoluteTolerance(), properties.getRelativeTolerance());
        return setAnalyzeParameters(analyzer);
    }

    private Protocol setAnalyzeParameters(IrSignal irSignal) throws InvalidArgumentException {
        Protocol protocol;
        // Ignore repeatfinder request if irSignal already has repeat.
        if (properties.getInvokeRepeatFinder() && irSignal.getRepeatLength() == 0) {
            ModulatedIrSequence irSequence = irSignal.toModulatedIrSequence();
            System.out.println("irSequence = " + irSequence);
            protocol = setAnalyzeParameters(irSequence);
        } else {
            Analyzer analyzer = new Analyzer(irSignal, properties.getAbsoluteTolerance(), properties.getRelativeTolerance());
            System.out.println("analyzer = " + analyzer);
            // здесь выделяется Протокол
            protocol = setAnalyzeParameters(analyzer);
        }
        return protocol;
    }

    private Protocol setAnalyzeParameters(Analyzer analyzer) {
        Burst.Preferences burstPrefs = new Burst.Preferences(analyzerMaxRoundingError, analyzerMaxUnits, analyzerMaxMicroSeconds);
        Analyzer.AnalyzerParams params = new Analyzer.AnalyzerParams(analyzer.getFrequency(), analyzerTimeBase,
                analyzerLsb ? BitDirection.lsb : BitDirection.msb,
                analyzerExtent, analyzerParameterWidths, analyzerMaxParameterWidth, analyzerInvert, burstPrefs, new ArrayList<>(0));
        List<Protocol> protocols = null;
        try {
            protocols = analyzer.searchBestProtocol(params);
        } catch (NoDecoderMatchException ex) {
            ex.printStackTrace();
        }

        Protocol protocol;
        if (protocols == null || protocols.isEmpty()) {
            clearAnalyzeParameters();
            protocol = null;
        } else {
            protocol = setAnalyzeParameters(protocols.get(0));
        }
        return protocol;
    }


    private Protocol setAnalyzeParameters(Protocol protocol) {
        System.out.println("protocol = " + protocol);
        if (protocol == null) {
            clearAnalyzeParameters();  // todo - нет такого
            return null;
        }else {
            System.out.println("protocol.class = " + protocol.getClass().getName());

            Protocol actualProtocol = analyzerEliminateConstantVars ? protocol.substituteConstantVariables() : protocol;
            System.out.println("actualProtocol = " + actualProtocol);

            System.out.println("actualProtocol.class = " + actualProtocol.getClass().getName());
            System.out.println("getDecoderName = " + actualProtocol.getDecoderName());     // Pwm2Decoder
            System.out.println("getIrp = " + actualProtocol.getIrp());
            System.out.println("getBitDirection = " + actualProtocol.getBitDirection());
            System.out.println("getDefinitions = " + actualProtocol.getDefinitions());
            System.out.println("getGeneralSpec = " + actualProtocol.getGeneralSpec());
            System.out.println("getParameterSpecs = " + actualProtocol.getParameterSpecs());
            System.out.println("getParseTree = " + actualProtocol.getParseTree());

            // Получаем строку вида: {38.4k,573,msb}<1,-1|1,-3>(8,-8,A:20,1,-58.857m)*{A=0x804fc}
            String line = actualProtocol.toIrpString(properties.getAnalyzerBase());

            // выводу в ГУИ
            System.out.println("line = " + line);
            setAnalyzeParameters(line);
            if (properties.getPrintAnalyzerIRPsToConsole())
                message(line);

            return actualProtocol;
        }
    }

    private void setAnalyzeParameters(String line) {
        //analyzerTextField.setText(line);
    }

    private void message(String line) {
        System.out.println(line);
    }

    private void clearAnalyzeParameters() {
        //analyzerTextField.setText(null);
        System.out.println("clearAnalyzeParameters");
    }

    private void displaySignal(IrSignal irSignal) {
        //setFrequencyParameter(irSignal);
        System.out.println("Frequency = " + irSignal.getFrequency());

        //setCaptureWindow(irSignal);
        String strSignal = formatIrSignal(irSignal, properties.getOutputFormatIndex());
        System.out.println("strSignal = " + strSignal);

        // рисует импульсы
        //irPlotter.plot(irSignal);
        
    }

    private String formatIrSignal(IrSignal irSignal, int formatIndex) {
        System.out.println("formatIndex = " + formatIndex);
        return OutputTextFormat.newOutputTextFormat(formatIndex).formatIrSignal(irSignal);
    }

    private Decoder.SimpleDecodesSet setDecodeIrParameters(IrSignal irSignal) {
        // decoderParameters - берутся из Props
        System.out.println("decoderParameters = " + decoderParameters);

        // берем декодеры которые могут определить что за протокол-устройство у этого сигнала

        //Decoder.AbstractDecodesCollection<? extends ElementaryDecode> decodes = decoder.decodeLoose(irSignal, decoderParameters);
    //    Decoder.AbstractDecodesCollection<? extends ElementaryDecode> decodes = getDecode(irSignal, decoderParameters);
    //    System.out.println("get decode = " + decodes);

        // здесь происходит определение протокола
    //    setDecodeResult(decodes);

        // todo
        Decoder.SimpleDecodesSet decodeList = decoder.decodeIrSignal(irSignal, decoderParameters);
        System.out.println("decodeList = " + decodeList);
        /*
        if (decodeList != null) {
            System.out.println("decodeList.size = " + decodeList.size());
            if (decodeList.size() > 0) {
                Iterator<Decoder.Decode> it = decodeList.iterator();
                while (it.hasNext()) {
                    Decoder.Decode decode = it.next();
                    System.out.println("- decode = " + decode);
                }
            }
            //System.out.println("decodeList.size = " + decodeList.());
        }
        */

        /*
decoderParameters
        public String toString() {
            StringJoiner sj = new StringJoiner(",", "{", "}");
            sj.add(Boolean.toString(this.strict));
            sj.add(Boolean.toString(this.allDecodes));
            sj.add(Boolean.toString(this.removeDefaultedParameters));
            sj.add(Boolean.toString(this.recursive));
            sj.add(Double.toString(this.frequencyTolerance));
            sj.add(Double.toString(this.absoluteTolerance));
            sj.add(Double.toString(this.relativeTolerance));
            sj.add(Double.toString(this.minimumLeadout));
            sj.add(Boolean.toString(this.override));
            sj.add(Boolean.toString(this.ignoreLeadingGarbage));
            return sj.toString();
        }

decoderParameters = {false,false,true,false,2000.0,100.0,0.3,20000.0,false,false}
- strict = f
- allDecodes = f
- removeDefaultedParameters = t
- recursive = f
- frequencyTolerance = 2000.0
- absoluteTolerance = 100
- relativeTolerance = 0.3
- minimumLeadout = 20000
- override = f
- ignoreLeadingGarbage = f

get decode = {Samsung20: {D=1,S=8,F=63},, beg=0, end=43, reps=1}
decodes list = {Samsung20: {D=1,S=8,F=63},, beg=0, end=43, reps=1}
set decode = Samsung20: {D=1,S=8,F=63}, beg=0, end=43, reps=1

decodeList = org.harctoolbox.irp.Decoder$SimpleDecodesSet@4550bb58
decodeList.size = 1
- decode = Samsung20: {D=1,S=8,F=63}

         */

        return decodeList;
    }

    public Decoder.AbstractDecodesCollection<? extends ElementaryDecode> getDecode(IrSignal irSignal, Decoder.DecoderParameters decoderParams) {
        Decoder.AbstractDecodesCollection<? extends ElementaryDecode> result;
        if (!false && (false || !irSignal.introOnly() && !irSignal.repeatOnly())) {
            //Decoder.SimpleDecodesSet dec = decoder.decodeIrSignal(irSignal, decoderParams);
            System.out.println("decoder 1");
            //result = decoder.decodeIrSignal(irSignal, decoderParams);
            result = decodeIrSignal(irSignal, decoderParams);
        } else {
            System.out.println("decoder 2");      // сюда попадаем
            ModulatedIrSequence sequence = irSignal.toModulatedIrSequence();
            //Decoder.DecodeTree dec2 = decoder.decode(sequence, decoderParams);
            //result = decoder.decode(sequence, decoderParams);
            result = decode2 (sequence, decoderParams);
        }
        System.out.println("decoders = " + result);
        return result;
    }

    //public Decoder.DecodeTree decode2 (ModulatedIrSequence irSequence, Decoder.DecoderParameters userSuppliedDecoderParameters) {
    public Decoder.SimpleDecodesSet decode2 (ModulatedIrSequence irSequence, Decoder.DecoderParameters userSuppliedDecoderParameters) {
        Map<Integer, Map<String, Decoder.TrunkDecodeTree>> map = new HashMap(16);
        //Decoder.DecodeTree decodes = this.decode(irSequence, 0, userSuppliedDecoderParameters, 0, map);
        Decoder.SimpleDecodesSet decodes = decode(irSequence, 0, userSuppliedDecoderParameters, 0, map);

        /*
        if (decodes.isEmpty() && userSuppliedDecoderParameters.isIgnoreLeadingGarbage()) {
            int newStart = irSequence.firstBigGap(0, userSuppliedDecoderParameters.getMinimumLeadout()) + 1;
            return newStart > 0 ? this.decode(irSequence, newStart, userSuppliedDecoderParameters, 0, map) : decodes;
        } else {
            return decodes;
        }
        */
        return decodes;
    }

    //private Decoder.DecodeTree decode(ModulatedIrSequence irSequence, int position,
    private Decoder.SimpleDecodesSet decode(ModulatedIrSequence irSequence, int position,
                                      Decoder.DecoderParameters userSuppliedDecoderParameters, int level,
                                      Map<Integer, Map<String, Decoder.TrunkDecodeTree>> map) {

        // Map<Integer, Map<String, Decoder.TrunkDecodeTree>> map - приходит сюда пустышкой для наполнения но потмо не сипользуется

        System.out.println(String.format("level = %1$d position = %2$d", level, position));

        //Decoder.DecodeTree decodeTree = new Decoder.DecodeTree(irSequence.getLength() - position);
        System.out.println("irSequence.getLength() = " + irSequence.getLength() + "; position = " + position
                + "; irSequence.getLength() - position = " + (irSequence.getLength() - position));

        List<Decoder.Decode> decodes = new ArrayList(8);

        //Decoder.SimpleDecodesSet simpleDecodesSet = new Decoder.SimpleDecodesSet(decodes);

        //if (decodeTree.size() == 0) {
        //    return decodeTree;
        //} else
        {
            Map<String, NamedProtocol> parsedProtocols = getProtocols();
            System.out.println("parsedProtocols = " + parsedProtocols);

            parsedProtocols.values().forEach((namedProtocol) -> {
                //System.out.println("- namedProtocol = " + namedProtocol.getName());
                try {
                    /*
                    if (debugProtocolNamePattern != null && debugProtocolNamePattern.matcher(namedProtocol.getName().toLowerCase(Locale.US)).matches()) {
                        logger.log(Level.FINEST, "Trying protocol {0}", namedProtocol.getName());
                    }
                    */

                    Map<String, Decoder.TrunkDecodeTree> p = (Map)map.get(position);
                    Decoder.Decode decode;
                    if (p != null && p.containsKey(namedProtocol.getName())) {
                        //System.out.println("- namedProtocol = " + namedProtocol.getName());
                        boolean b = true;
                        //decode = (Decoder.Decode)p.get(namedProtocol.getName());
                    } else {
                        //System.out.println("- namedProtocol = " + namedProtocol.getName() + "; position = " + position + "; level = " + level);
                        // Если протокол не подходит - здесь вылетает исключение SignalRecognitionException - !!!
                        // - Protocol Zenith7 did not decode: Neither intro- nor repeat sequence was matched
                        decode = tryNamedProtocol(namedProtocol, irSequence, position, userSuppliedDecoderParameters, level, map);
                        decodes.add(decode);
                        if (!map.containsKey(position)) {
                            map.put(position, new HashMap(4));
                        }

                        ((Map)map.get(position)).put(namedProtocol.getName(), decode);
                    }

                    //decodeTree.add(decode);

                } catch (SignalRecognitionException var10) {
                    // Именно в этом месте отпинывается изучаемый протокол
                    System.out.println(String.format("Protocol %1$s did not decode: %2$s", namedProtocol.getName(), var10.getMessage()));
                    //var10.printStackTrace();
                } catch (Protocol.ProtocolNotDecodableException var11) {
                    var11.printStackTrace();
                }

            });

            System.out.println("decodes.size = " + decodes.size());

            /*
            if (userSuppliedDecoderParameters.isAllDecodes()) {
                decodeTree.computePreferred(parsedProtocols);
            } else {
                decodeTree.reduce(parsedProtocols);
                if (decodeTree.isComplete()) {
                    decodeTree.removeIncompletes();
                }
            }

            decodeTree.sort();
            return decodeTree;
            */

            Decoder.SimpleDecodesSet simpleDecodesSet = new Decoder.SimpleDecodesSet(decodes);
            simpleDecodesSet.sort();
            return simpleDecodesSet;
        }
    }

    //private Decoder.TrunkDecodeTree tryNamedProtocol(NamedProtocol namedProtocol, ModulatedIrSequence irSequence,
    private Decoder.Decode tryNamedProtocol(NamedProtocol namedProtocol, ModulatedIrSequence irSequence,
                                                     int position, Decoder.DecoderParameters userSuppliedDecoderParameters,
                                                     int level, Map<Integer, Map<String, Decoder.TrunkDecodeTree>> map)
            throws SignalRecognitionException, Protocol.ProtocolNotDecodableException
    {
        //System.out.println("tryNamedProtocol: position = " + position);

        // Если протокол не подходит - здесь вылетает исключение SignalRecognitionException
        Decoder.Decode decode = namedProtocol.recognize(irSequence, position, userSuppliedDecoderParameters);

        return decode;

        /*
        if (userSuppliedDecoderParameters.isRemoveDefaultedParameters()) {
            // какая-то очистка
            decode.removeDefaulteds();
            // namedProtocol.removeDefaulteds(decode.map);
        }

        // у нас recursive = f
        if (userSuppliedDecoderParameters.recursive && decode.endPos != irSequence.getLength() - 1) {
            Decoder.DecodeTree rest = decode(irSequence, decode.getEndPos() + 1, userSuppliedDecoderParameters, level + 1, map);
            return new Decoder.TrunkDecodeTree(decode, rest);
        } else {
            return new Decoder.TrunkDecodeTree(decode, irSequence.getLength());
        }
        */
    }


    // Из Protocol
    /*
    public Decoder.Decode recognize(Protocol protocol, ModulatedIrSequence irSequence, int beginPos, boolean rejectNoRepeats,
                                    Decoder.DecoderParameters params) throws SignalRecognitionException {
        // boolean success = params.getFrequencyTolerance() < 0.0D ||
        //        IrCoreUtils.approximatelyEquals(this.getFrequencyWithDefault(), frequency, params.getFrequencyTolerance(), 0.0D);
        // - если не success -- SignalRecognitionException("Frequency does not match");
        protocol.checkFrequency(irSequence.getFrequencyWithDefault(), params);

        // definitions = new NameEngine(this.initialDefinitions);
        // - где initialDefinitions - это какая-то стартовая NameEngine
        protocol.initializeDefinitions();

        // - что сюда будем складывать?
        ParameterCollector names = new ParameterCollector();


        // beginPos = 0. вычисляем новое значение position
        int pos = decode(protocol, names, irSequence, beginPos, IrSignal.Pass.intro, params);
        int noRepeatsMatched = 0;

        // Цикл. Пока oldPos не равен pos
        while(true) {
            int oldPos = pos;

            try {
                pos = decode(protocol, names, irSequence, oldPos, IrSignal.Pass.repeat, params);
                if (pos == oldPos) {
                    break;
                }

                ++noRepeatsMatched;
            } catch (SignalRecognitionException var11) {
                System.out.println("Protocol did not parse: " + var11.getMessage());
                break;
            }
        }

        if (rejectNoRepeats) {
            if (noRepeatsMatched == 0) {
                throw new SignalRecognitionException("No repeat sequence matched; this was required through rejectNoRepeats");
            }

            if (noRepeatsMatched == 1 && protocol.isEmpty(IrSignal.Pass.intro)) {
                throw new SignalRecognitionException("Intro empty, rejectNoRepeats and only one repeat sequence matched; rejected");
            }
        }

        if (pos == beginPos) {
            // Был пробег по данным, но нчиего не изменилось - то есть не нашли что-то свое
            // здесь вылетают все непраивльные протоколы
            throw new SignalRecognitionException("Neither intro- nor repeat sequence was matched");
        } else {
            try {
                pos = decode(protocol, names, irSequence, pos, IrSignal.Pass.ending, params);
                if (params.isStrict() && pos < irSequence.getLength() - 1) {
                    throw new SignalRecognitionException("Sequence was not fully matched");
                }
            } catch (SignalRecognitionException var10) {
                if (params.isStrict()) {
                    throw var10;
                }

                System.out.println("Ending sequence not matched.");
            }

            Map<String, Long> parameters = names.collectedNames();
            protocol.getParameterSpecs().removeNotInParameterSpec(parameters);
            return new Decoder.Decode((NamedProtocol)null, parameters, beginPos, pos - 1, noRepeatsMatched);
        }
    }


    // Какрой-то анализ сигнала. С вычислением значения position
    private int decode(Protocol protocol, ParameterCollector names, IrSequence irSequence, int beginPos, IrSignal.Pass pass, Decoder.DecoderParameters params)
            throws SignalRecognitionException {

        RecognizeData recognizeData = new RecognizeData(protocol.getGeneralSpec(), protocol.getDefinitions(),
                protocol.getParameterSpecs(), irSequence, beginPos, protocol.interleavingOk(), names, params, pass);

        // Из текущего протокола формирует новый протокол
        Protocol reducedProtocol = protocol.normalForm(pass);


        reducedProtocol.decode(recognizeData);

        try {
            names.fixParameterSpecs(protocol.getParameterSpecs());
            recognizeData.checkConsistency();
            protocol.checkDomain(names);
        } catch (Exception var9) {
            throw new SignalRecognitionException(var9.toString());
        }

        // выдает новый position
        return recognizeData.getPosition();
    }

    protected void checkFrequency(Protocol protocol, Double frequency, Decoder.DecoderParameters params) throws SignalRecognitionException {
        logger.log(Level.FINER, "Expected frequency {0}, actual {1}, tolerance {2}", new Object[]{(int)protocol.getFrequencyWithDefault(), frequency.intValue(), params.getFrequencyTolerance().intValue()});
        boolean success = params.getFrequencyTolerance() < 0.0D || IrCoreUtils.approximatelyEquals(protocol.getFrequencyWithDefault(), frequency, params.getFrequencyTolerance(), 0.0D);
        logger.log(Level.FINER, "Frequency was checked, {0}OK.", success ? "" : "NOT ");
        if (!success) {
            throw new SignalRecognitionException("Frequency does not match");
        }
    }
 */

    // ------------------------------------------------------

    public Decoder.SimpleDecodesSet decodeIrSignal(IrSignal irSignal, Decoder.DecoderParameters parameters) {
        List<Decoder.Decode> decodes = new ArrayList(8);

        System.out.println("Start");

        Map<String, NamedProtocol> parsedProtocols = getProtocols();
        System.out.println("parsedProtocols = " + parsedProtocols);

        //parsedProtocols.values().forEach((namedProtocol) -> {
        for (NamedProtocol namedProtocol: parsedProtocols.values()) {
            System.out.println("- namedProtocol = " + namedProtocol.getName());
            try {
                /*
                if (debugProtocolNamePattern != null && debugProtocolNamePattern.matcher(namedProtocol.getName().toLowerCase(Locale.US)).matches()) {
                    logger.log(Level.FINEST, "Trying protocol {0}", namedProtocol.getName());
                }
                */

                Map<String, Long> params = namedProtocol.recognize(irSignal, parameters);
                if (parameters.isRemoveDefaultedParameters()) {
                    namedProtocol.removeDefaulteds(params);
                }

                // public Decode(NamedProtocol namedProtocol, Map<String, Long> map, int begPos, int endPos, int numberOfRepetitions)
                // this(namedProtocol, params, -1, -1, 0);
                Decoder.Decode decode = new Decoder.Decode(namedProtocol, params, -1, -1, 0);
                decodes.add(decode);
            } catch (SignalRecognitionException var6) {
                System.out.println(String.format("Protocol %1$s did not decode: %2$s", namedProtocol.getName(), var6.getMessage()));
            } catch (Protocol.ProtocolNotDecodableException var7) {
                var7.printStackTrace();
                throw new ThisCannotHappenException();
            }
        }
        System.out.println("decodes.size = " + decodes.size());

        Decoder.SimpleDecodesSet simpleDecodesSet = new Decoder.SimpleDecodesSet(decodes);

        /*
        if (parameters.isAllDecodes()) {
            simpleDecodesSet.computePreferred(this.parsedProtocols);
        } else {
            simpleDecodesSet.reduce(this.parsedProtocols);
        }
        */
        simpleDecodesSet.sort();
        System.out.println("simpleDecodesSet = " + simpleDecodesSet);
        return simpleDecodesSet;
    }


    // Получение parsedProtocols


    public Map<String, NamedProtocol> getProtocols () throws ThisCannotHappenException {
        Map<String, NamedProtocol> parsedProtocols = new LinkedHashMap(irpDatabase.size());

        for (String protocolName: irpDatabase.getKeys()) {
            try {
                NamedProtocol namedProtocol = irpDatabase.getNamedProtocol(protocolName);
                if (namedProtocol.isDecodeable()) {
                    parsedProtocols.put(protocolName, namedProtocol);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                throw new ThisCannotHappenException(ex);
            }
        }

        /*
        Collection<String> list = names != null ? names : irpDatabase.getKeys();
        ((Collection)list).forEach((protocolName) -> {
            try {
                NamedProtocol namedProtocol = irpDatabase.getNamedProtocol(protocolName);
                if (namedProtocol.isDecodeable()) {
                    parsedProtocols.put(protocolName, namedProtocol);
                }

            } catch (UnknownProtocolException | InvalidNameException | UnsupportedRepeatException | IrpInvalidArgumentException | NameUnassignedException var4) {
                throw new ThisCannotHappenException(var4);
            }
        });
        */

        return parsedProtocols;
    }


    /*
    public Decoder.AbstractDecodesCollection<? extends ElementaryDecode> decodeLoose(IrSignal irSignal, Decoder.DecoderParameters decoderParams) {
        if (!decoderParams.ignoreLeadingGarbage && (decoderParams.strict || !irSignal.introOnly() && !irSignal.repeatOnly())) {
            return decoder.decodeIrSignal(irSignal, decoderParams);
        } else {
            ModulatedIrSequence sequence = irSignal.toModulatedIrSequence();
            return decoder.decode(sequence, decoderParams);
        }
    }

        if (!false && (false || !irSignal.introOnly() && !irSignal.repeatOnly())) {

    */

//    private Collection<Decoder.Decode> decodeIrSequence(ModulatedIrSequence irSequence) {
//        Decoder.DecodeTree decodeTree = decoder.decode(irSequence, decoderParameters);
//        List<Decoder.Decode> decodes = new ArrayList<>(4);
//        for (Decoder.TrunkDecodeTree decode : decodeTree)
//            decodes.add(decode.getTrunk());
//        return decodes;
//    }

//    private Iterable<Decoder.Decode> decodeIrSignal(IrSignal irSignal) {
//        Decoder.SimpleDecodesSet decodes = decoder.decodeIrSignal(irSignal, decoderParameters);
//        return decodes;
//    }

    /**
     * Декодирвоание данных.
     * Если есть декодеры то выводится строка вида - Samsung20: {D=1,S=8,F=63}, beg=0, end=43, reps=1
     * @param decodes  Декодеры
     */
    private void setDecodeResult(Decoder.AbstractDecodesCollection<? extends ElementaryDecode> decodes) {
        System.out.println("decodes list = " + decodes);
        System.out.println("decodes list size = " + decodes.size());
        if (decodes.isEmpty()) {
            //decodeIRTextField.setText("");
            System.out.println("decodes is empty ");
            return;
        }

        StringBuilder decodeString = new StringBuilder(40);

        /*
        ElementaryDecode preferedDecode = decodes.getPreferred();
        System.out.println("setDecodeResult: preferedDecode 0 = " + preferedDecode);
        if (preferedDecode != null) {
            System.out.println("setDecodeResult: preferedDecode.class = " + preferedDecode.getClass().getName());
        }
        decodeString.append(preferedDecode);
        */
        ElementaryDecode preferedDecode = decodes.first();
        System.out.println("setDecodeResult: preferedDecode 0 = " + preferedDecode);
        decodeString.append(preferedDecode);

        if (decodes.size() == 2)
            decodeString.append(" + one more decode");
        else if (decodes.size() > 2)
            decodeString.append(" + ").append(Integer.toString(decodes.size() - 1)).append(" more decodes");

        //decodeIRTextField.setText(decodeString.toString());
        System.out.println("decode string = " + decodeString);

        if (properties.getPrintDecodesToConsole()) {
            //guiUtils.message(preferedDecode.toString());
            System.out.println("preferedDecode = " + preferedDecode);
        }

        if (properties.getPrintAlternativeDecodes()) {
            for (ElementaryDecode decode : decodes) {
                if (decode != preferedDecode) {
                    //guiUtils.message("Alternative decode: " + decode.toString());
                    System.out.println("Alternative decode = " + decode);
                }
            }
        }
    }


    private void setupDecoder() throws IrpParseException {
        // Какой-то внтуреннйи функционал. Оставил - т.к. неизвестно
        RepeatFinder.setDefaultAbsoluteTolerance(properties.getAbsoluteTolerance());
        RepeatFinder.setDefaultRelativeTolerance(properties.getRelativeTolerance());

        decoder = new Decoder(irpDatabase);
        decoderParameters = new Decoder.DecoderParameters(decodeStrict,
                properties.getPrintAlternativeDecodes(),
                properties.getRemoveDefaultedParameters(),
                decodeRecursive,
                properties.getFrequencyTolerance(),
                properties.getAbsoluteTolerance(),
                properties.getRelativeTolerance(),
                properties.getMinLeadOut(),
                decodeOverride,
                properties.getIgnoreLeadingGarbage()
        );
        //Command.setDecoderParameters(decoderParameters);     // org.harctoolbox.girr

        // todo - надо ли это?
        //RawIrSignal.setDecoderParameters(decoderParameters);

        //ParametrizedIrSignal.setDecoderParameters(decoderParameters);
        /*
        properties.addFrequencyToleranceChangeListener((String name1, Object oldValue, Object newValue) -> {
            decoderParameters.setFrequencyTolerance((Double) newValue);
        });
        properties.addAbsoluteToleranceChangeListener((String name1, Object oldValue, Object newValue) -> {
            decoderParameters.setAbsoluteTolerance((Double) newValue);
        });
        properties.addRelativeToleranceChangeListener((String name1, Object oldValue, Object newValue) -> {
            decoderParameters.setRelativeTolerance((Double) newValue);
        });
        properties.addMinLeadOutChangeListener((String name1, Object oldValue, Object newValue) -> {
            decoderParameters.setMinimumLeadout((Double) newValue);
        });
        properties.addRemoveDefaultedParametersChangeListener((String name1, Object oldValue, Object newValue) -> {
            decoderParameters.setRemoveDefaultedParameters((Boolean) newValue);
        });
        properties.addPrintAlternativeDecodesChangeListener((String name1, Object oldValue, Object newValue) -> {
            decoderParameters.setAllDecodes((Boolean) newValue);
        });
        */
    }

    // must come before initComponents
    private void setupIrpDatabase() throws IOException, IrpParseException, SAXException {
        String fileName =  properties.mkPathAbsolute(properties.getIrpProtocolsPath());
        System.out.println("fileName = " + fileName);
        irpDatabase = new IrpDatabase(fileName);

        // ???
        //Command.setIrpDatabase(irpDatabase);
    }

}
