package svj.irc;

import org.harctoolbox.ircore.IrSignal;
import org.harctoolbox.ircore.Pronto;

public enum OutputTextFormat {
    prontoHex,
    rawWithSigns,
    rawWithoutSigns;

    public String formatIrSignal(IrSignal irSignal) {
        return this == prontoHex ? Pronto.toString(irSignal)
             : this == rawWithSigns ? irSignal.toString(true)
             : irSignal.toString(false);
    }

    public static OutputTextFormat newOutputTextFormat(int n) {
        return OutputTextFormat.values()[n];
    }
}
