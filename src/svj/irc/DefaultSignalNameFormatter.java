package svj.irc;

import java.util.*;

public class DefaultSignalNameFormatter implements ISignalNameFormatter {

    public static String formatName(String protocolName, Map<String, Long> parameters) {
        return (new DefaultSignalNameFormatter()).format(protocolName, parameters);
    }

    private StringBuilder doParameter(Map<String, Long> parameters, String parameterName) {
        if (!parameters.containsKey(parameterName)) {
            return new StringBuilder(0);
        }

        StringBuilder str = new StringBuilder(64);
        str.append(parameterName).append(parameters.get(parameterName));
        parameters.remove(parameterName);
        return str;
    }

    @Override
    public String format(String protocolName, Map<String, Long> parameters) {
        @SuppressWarnings("unchecked")
        Map<String, Long> params = new HashMap<>(parameters);

        StringBuilder tail = new StringBuilder(64);
        tail.append(doParameter(params, "D"));
        tail.append(doParameter(params, "S"));
        tail.append(doParameter(params, "F"));
        tail.append(doParameter(params, "T"));

        StringBuilder mid = new StringBuilder(64);
        for (String param : params.keySet().toArray(new String[params.size()])) { // toArray to avoid concurrent modification
            mid.append(doParameter(params, param));
        }

        StringBuilder str = new StringBuilder(protocolName.replace(" ", "-"));
        str.append("_").append(mid).append(tail);

        return str.toString();
    }
}
