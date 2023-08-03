package svj.irc;

import org.harctoolbox.ircore.IrCoreUtils;

import java.io.File;
import java.util.*;

/**
 * <BR/>
 */
public class Props {

    private double frequency;
    private double dummyGap;
    private boolean invokeRepeatFinder;
    private boolean invokeCleaner;
    private double absoluteTolerance;
    private double relativeTolerance;
    private double minRepeatLastGap;
    private boolean invokeAnalyzer;
    private int outputFormatIndex;
    private boolean printDecodesToConsole;
    private boolean printAlternativeDecodes;
    private int analyzerBase;
    private boolean printAnalyzerIRPsToConsole;
    private boolean removeDefaultedParameters;
    private double frequencyTolerance;
    private double minLeadOut;
    private boolean ignoreLeadingGarbage;
    private String irpProtocolsPath;
    private String applicationHome;


    private HashMap<String,ArrayList<IPropertyChangeListener>> changeListeners;


    public Props() {
        frequency = 38000.0D;
        dummyGap = 50000.0D;
        frequencyTolerance = IrCoreUtils.DEFAULT_FREQUENCY_TOLERANCE;
        invokeRepeatFinder = true;
        invokeCleaner = true;
        invokeAnalyzer = true;
        printDecodesToConsole = false;
        printAlternativeDecodes = false;
        printAnalyzerIRPsToConsole = false;
        removeDefaultedParameters = true;
        ignoreLeadingGarbage = false;
        outputFormatIndex = 0;
        analyzerBase = 16;
        absoluteTolerance = IrCoreUtils.DEFAULT_ABSOLUTE_TOLERANCE;
        relativeTolerance = IrCoreUtils.DEFAULT_RELATIVE_TOLERANCE;
        minRepeatLastGap = IrCoreUtils.DEFAULT_MIN_REPEAT_LAST_GAP;
        minLeadOut = IrCoreUtils.DEFAULT_MINIMUM_LEADOUT;

        irpProtocolsPath = "IrpProtocols.xml";

        // home
        applicationHome = "/home/svj/Projects/SVJ/JavaSample2/resource";
        // ofice
        applicationHome = "/home/svj/projects/SVJ/JavaSample/resource";
        //applicationHome = Utils.findApplicationHome(commandLineArgs.applicationHome, IrScrutinizer.class, Version.appName);


        changeListeners = new HashMap<>(16);

    }

    public double getFrequency() {
        return frequency;
    }

    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }

    public double getDummyGap() {
        return dummyGap;
    }

    public void setDummyGap(double dummyGap) {
        this.dummyGap = dummyGap;
    }

    public boolean getInvokeRepeatFinder() {
        return invokeRepeatFinder;
    }

    public void setInvokeRepeatFinder(boolean invokeRepeatFinder) {
        this.invokeRepeatFinder = invokeRepeatFinder;
    }

    public boolean getInvokeCleaner() {
        return invokeCleaner;
    }

    public void setInvokeCleaner(boolean invokeCleaner) {
        this.invokeCleaner = invokeCleaner;
    }

    public double getAbsoluteTolerance() {
        return absoluteTolerance;
    }

    public void setAbsoluteTolerance(double absoluteTolerance) {
        this.absoluteTolerance = absoluteTolerance;
    }

    public double getRelativeTolerance() {
        return relativeTolerance;
    }

    public void setRelativeTolerance(double relativeTolerance) {
        this.relativeTolerance = relativeTolerance;
    }

    public double getMinRepeatLastGap() {
        return minRepeatLastGap;
    }

    public void setMinRepeatLastGap(double minRepeatLastGap) {
        this.minRepeatLastGap = minRepeatLastGap;
    }

    public boolean getInvokeAnalyzer() {
        return invokeAnalyzer;
    }

    public void setInvokeAnalyzer(boolean invokeAnalyzer) {
        this.invokeAnalyzer = invokeAnalyzer;
    }

    public int getOutputFormatIndex() {
        return outputFormatIndex;
    }

    public void setOutputFormatIndex(int outputFormatIndex) {
        this.outputFormatIndex = outputFormatIndex;
    }

    public boolean getPrintDecodesToConsole() {
        return printDecodesToConsole;
    }

    public void setPrintDecodesToConsole(boolean printDecodesToConsole) {
        this.printDecodesToConsole = printDecodesToConsole;
    }

    public boolean getPrintAlternativeDecodes() {
        return printAlternativeDecodes;
    }

    public void setPrintAlternativeDecodes(boolean printAlternativeDecodes) {
        this.printAlternativeDecodes = printAlternativeDecodes;
    }

    public int getAnalyzerBase() {
        return analyzerBase;
    }

    public void setAnalyzerBase(int analyzerBase) {
        this.analyzerBase = analyzerBase;
    }

    public boolean getPrintAnalyzerIRPsToConsole() {
        return printAnalyzerIRPsToConsole;
    }

    public void setPrintAnalyzerIRPsToConsole(boolean printAnalyzerIRPsToConsole) {
        this.printAnalyzerIRPsToConsole = printAnalyzerIRPsToConsole;
    }

    public boolean getRemoveDefaultedParameters() {
        return removeDefaultedParameters;
    }

    public void setRemoveDefaultedParameters(boolean removeDefaultedParameters) {
        this.removeDefaultedParameters = removeDefaultedParameters;
    }

    public double getFrequencyTolerance() {
        return frequencyTolerance;
    }

    public void setFrequencyTolerance(double frequencyTolerance) {
        this.frequencyTolerance = frequencyTolerance;
    }

    public double getMinLeadOut() {
        return minLeadOut;
    }

    public void setMinLeadOut(double minLeadOut) {
        this.minLeadOut = minLeadOut;
    }

    public boolean getIgnoreLeadingGarbage() {
        return ignoreLeadingGarbage;
    }

    public void setIgnoreLeadingGarbage(boolean ignoreLeadingGarbage) {
        this.ignoreLeadingGarbage = ignoreLeadingGarbage;
    }

    public String getIrpProtocolsPath() {
        return irpProtocolsPath;
    }


    public String mkPathAbsolute(String path) {
        return new File(path).isAbsolute() ? path
               : new File(new File(applicationHome), path).getAbsolutePath();
    }

    private void addPropertyChangeListener(String propertyName, IPropertyChangeListener listener) {
        ArrayList<IPropertyChangeListener> presentListeners = changeListeners.get(propertyName);
        if (presentListeners == null) {
            presentListeners = new ArrayList<>();
            changeListeners.put(propertyName, presentListeners);
        }

        if (!presentListeners.contains(listener))
            presentListeners.add(listener);
    }

    public void addFrequencyToleranceChangeListener(IPropertyChangeListener listener) {
        addPropertyChangeListener("frequencyTolerance", listener);
    }

    public void addAbsoluteToleranceChangeListener(IPropertyChangeListener listener) {
        addPropertyChangeListener("absoluteTolerance", listener);
    }

    public void addRelativeToleranceChangeListener(IPropertyChangeListener listener) {
        addPropertyChangeListener("relativeTolerance", listener);
    }

    public void addMinLeadOutChangeListener(IPropertyChangeListener listener) {
        addPropertyChangeListener("minLeadOut", listener);
    }

    public void addRemoveDefaultedParametersChangeListener(IPropertyChangeListener listener) {
        addPropertyChangeListener("removeDefaultedParameters", listener);
    }

    public void addPrintAlternativeDecodesChangeListener(IPropertyChangeListener listener) {
        addPropertyChangeListener("printAlternativeDecodes", listener);
    }

    public interface IPropertyChangeListener {
        public void propertyChange(String name, Object oldValue, Object newValue);
    }

}
