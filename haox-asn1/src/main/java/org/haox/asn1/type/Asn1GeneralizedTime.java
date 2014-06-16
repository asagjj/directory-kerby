package org.haox.asn1.type;

import org.haox.asn1.UniversalTag;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class Asn1GeneralizedTime extends Asn1Simple<Date>
{
    public Asn1GeneralizedTime() {
        this(null);
    }

    /**
     * time in milliseconds
     */
    public Asn1GeneralizedTime(long time) {
        super(UniversalTag.GENERALIZED_TIME, new Date(time));
    }

    public Asn1GeneralizedTime(Date date) {
        super(UniversalTag.UTC_TIME, date);
    }

    protected void toValue() throws IOException {
        String dateStr = new String(getBytes(), StandardCharsets.US_ASCII);
        SimpleDateFormat sdf;
        String fixedDateStr = dateStr;

        boolean withZ = dateStr.endsWith("Z");
        String timeZonePart = getTimeZonePart(dateStr);
        boolean withZone = timeZonePart != null;
        String millSecs = getMillSeconds(dateStr);

        fixedDateStr = dateStr.substring(0, 16) + millSecs;
        if (withZ) {
            sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            sdf.setTimeZone(new SimpleTimeZone(0, "Z"));
        } else if (withZone) {
            fixedDateStr += timeZonePart;
            sdf = new SimpleDateFormat("yyyyMMddHHmmssSSSz");
            sdf.setTimeZone(new SimpleTimeZone(0, "Z"));
        } else {
            sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            sdf.setTimeZone(new SimpleTimeZone(0, TimeZone.getDefault().getID()));
        }

        try {
            setValue(sdf.parse(fixedDateStr));
        } catch (ParseException e) {
            throw new IOException("Failed to parse as generalized time string " + dateStr);
        }
    }

    @Override
    protected void toBytes() {
        Date date = getValue();
        SimpleDateFormat dateF = new SimpleDateFormat("yyyyMMddHHmmss'Z'");
        dateF.setTimeZone(new SimpleTimeZone(0, "Z"));

        String str = dateF.format(date);
        byte[] bytes = str.getBytes(StandardCharsets.US_ASCII);
        setBytes(bytes);
    }

    /**
     * Extract the fractional part in seconds and convert into integer by (frac * 1000) as milli seconds
     */
    private String getMillSeconds(String dateStr) {
        char[] millDigits = new char[] {'0', '0', '0'};

        int iPos = dateStr.indexOf('.');
        if (iPos > 0) {
            if (iPos != 14) {
                throw new IllegalArgumentException("Bad generalized time string, " +
                        "with improper milli seconds " + dateStr);
            }

            char chr;
            int j = 0;
            for (int i = 15; i < dateStr.length() && j < millDigits.length; i++) {
                chr = dateStr.charAt(i);
                if ('0' <= chr && chr <= '9') {
                    millDigits[j++] = chr;
                } else break;
            }
        }

        return new String(millDigits);
    }

    /**
     * Extract the timezone part if any
     */
    private String getTimeZonePart(String dateStr) {
        int iPos = dateStr.indexOf('+');
        if (iPos == -1) {
            iPos = dateStr.indexOf('-');
        }
        if (iPos > 0 && iPos != dateStr.length() - 5) {
            throw new IllegalArgumentException("Bad generalized time string, " +
                    "with improper timezone part " + dateStr);
        }

        if (iPos > 0) {
            return dateStr.substring(iPos);
        }
        return null;
    }
}
