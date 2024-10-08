package it.eg.sloth.framework.common.base;

import it.eg.sloth.framework.common.exception.ExceptionCode;
import it.eg.sloth.framework.common.exception.FrameworkException;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Project: sloth-framework
 * Copyright (C) 2019-2025 Enrico Grillini
 * <p>
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * <p>
 *
 * @author Enrico Grillini
 */
public class TimeStampUtil {

    public static final String DEFAULT_FORMAT = "dd/MM/yyyy";

    private static final String[] FIXED_HOLIDAY = new String[]{"01/01", "06/01", "25/04", "01/05", "02/06", "15/08", "01/11", "08/12", "25/12", "26/12"};
    private static final int[] EASTER_BASE_HOLIDAY = new int[]{1};
    private static final String DAY_TRUNC_FORMAT = "dd/MM";

    public enum DayType {
        FESTIVO, PREFESTIVO, LAVORATIVO
    }


    /**
     * Converte una data in String
     *
     * @param date
     * @return
     */
    public static String formatTimestamp(Timestamp date) {
        return formatTimestamp(date, null, null);
    }

    public static String formatTimestamp(Timestamp date, String format) {
        return formatTimestamp(date, null, format);
    }

    /**
     * Converte una data in String
     *
     * @param date
     * @param format
     * @return
     */
    public static String formatTimestamp(Timestamp date, Locale locale, String format) {
        if (BaseFunction.isNull(date)) {
            return StringUtil.EMPTY;
        }

        if (format == null) {
            format = DEFAULT_FORMAT;
        }

        SimpleDateFormat dfOutput = locale == null ? new SimpleDateFormat(format) : new SimpleDateFormat(format, locale);

        return dfOutput.format(date);
    }

    public static Timestamp parseTimestamp(String strDate, String format) throws FrameworkException {
        return parseTimestamp(strDate, null, format);
    }

    /**
     * Converte una stringa in un Timestamp
     *
     * @param strDate
     * @param format
     * @return
     * @throws ParseException
     */
    public static Timestamp parseTimestamp(String strDate, Locale locale, String format) throws FrameworkException {
        if (BaseFunction.isBlank(strDate)) {
            return null;
        }

        if (format == null) {
            format = DEFAULT_FORMAT;
        }

        try {
            DateFormat formatter = locale == null ? new SimpleDateFormat(format, new DateFormatSymbols()) : new SimpleDateFormat(format, locale);
            formatter.setLenient(false);

            return new Timestamp(formatter.parse(strDate).getTime());
        } catch (ParseException e) {
            throw new FrameworkException(ExceptionCode.PARSE_ERROR, e);
        }
    }

    /**
     * Converte una stringa in un Timestamp
     *
     * @param strDate
     * @return
     * @throws ParseException
     */
    public static Timestamp parseTimestamp(String strDate) throws FrameworkException {
        return parseTimestamp(strDate, DEFAULT_FORMAT);
    }

    /**
     * Converte un Calendar in Timestamp
     *
     * @param value
     * @return
     */
    public static final Timestamp toTimestamp(Calendar value) {
        if (BaseFunction.isNull(value))
            return null;
        else
            return new Timestamp(value.getTimeInMillis());
    }

    /**
     * Converte un java.util.Date in Timestamp
     *
     * @param value
     * @return
     */
    public static final Timestamp toTimestamp(Date value) {
        if (BaseFunction.isNull(value))
            return null;
        else
            return new Timestamp(value.getTime());
    }

    /**
     * Converte un Calendar in Timestamp
     *
     * @param value
     * @return
     */
    public static final Calendar toCalendar(Timestamp value) {
        if (BaseFunction.isNull(value)) {
            return null;
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(value.getTime());
            return calendar;
        }
    }

    public static final Integer getYear(Timestamp value) {
        Calendar calendar = toCalendar(value);
        if (calendar != null) {
            return calendar.get(Calendar.YEAR);
        } else {
            return null;
        }
    }

    public static final Integer getWeekDay(Timestamp value) {
        Calendar calendar = toCalendar(value);
        if (calendar != null) {
            return calendar.get(Calendar.DAY_OF_WEEK);
        } else {
            return null;
        }
    }

    /**
     * Verifica se la data passata corrisponde ad un Lunedì
     *
     * @param value
     * @return
     */
    public static boolean isMonday(Timestamp value) {
        return getWeekDay(value) == Calendar.MONDAY;
    }

    /**
     * Verifica se la data passata corrisponde ad un Martedì
     *
     * @param value
     * @return
     */
    public static boolean isTuesday(Timestamp value) {
        return getWeekDay(value) == Calendar.TUESDAY;
    }

    /**
     * Verifica se la data passata corrisponde ad un Mercoledì
     *
     * @param value
     * @return
     */
    public static boolean isWednesday(Timestamp value) {
        return getWeekDay(value) == Calendar.WEDNESDAY;
    }

    /**
     * Verifica se la data passata corrisponde ad un Giovedì
     *
     * @param value
     * @return
     */
    public static boolean isThursday(Timestamp value) {
        return getWeekDay(value) == Calendar.THURSDAY;
    }

    /**
     * Verifica se la data passata corrisponde ad un Venerdì
     *
     * @param value
     * @return
     */
    public static boolean isFriday(Timestamp value) {
        return getWeekDay(value) == Calendar.FRIDAY;
    }

    /**
     * Verifica se la data passata corrisponde ad un Sabato
     *
     * @param value
     * @return
     */
    public static boolean isSaturday(Timestamp value) {
        return getWeekDay(value) == Calendar.SATURDAY;
    }

    /**
     * Verifica se la data passata corrisponde ad una Domenica
     *
     * @param value
     * @return
     */
    public static boolean isSunday(Timestamp value) {
        return getWeekDay(value) == Calendar.SUNDAY;
    }

    /**
     * Ritorna la Pasqua dell'anno passatp
     *
     * @param year
     * @return
     */
    public static final Timestamp getEaster(int year) {
        if (year <= 1582) {
            throw new IllegalArgumentException("Algorithm invalid before April 1583");
        }
        int golden;
        int century;
        int x;
        int z;
        int d;
        int epact;
        int n;

        golden = (year % 19) + 1; /* E1: metonic cycle */
        century = (year / 100) + 1; /* E2: e.g. 1984 was in 20th C */
        x = (3 * century / 4) - 12; /* E3: leap year correction */
        z = ((8 * century + 5) / 25) - 5; /* E3: sync with moon's orbit */
        d = (5 * year / 4) - x - 10;
        epact = (11 * golden + 20 + z - x) % 30; /* E5: epact */
        if ((epact == 25 && golden > 11) || epact == 24)
            epact++;
        n = 44 - epact;
        n += 30 * (n < 21 ? 1 : 0); /* E6: */
        n += 7 - ((d + n) % 7);
        if (n > 31) /* E7: */
            return toTimestamp(new GregorianCalendar(year, 4 - 1, n - 31)); /* April */
        else
            return toTimestamp(new GregorianCalendar(year, 3 - 1, n)); /* March */
    }

    /**
     * Verifica se la data passata corrisponde ad un giorno festivo
     *
     * @param data
     * @param otherHoliday
     * @return
     */
    public static final boolean isHoliday(Timestamp data, Timestamp... otherHoliday) throws FrameworkException {
        if (BaseFunction.isNull(data)) {
            return false;
        }

        if (isSunday(data)) {
            return true;
        }

        // Festività Fisse
        for (String holiday : FIXED_HOLIDAY) {
            if (formatTimestamp(data, null, DAY_TRUNC_FORMAT).equals(holiday)) {
                return true;
            }
        }

        // Festività basate sulla pasqua
        Timestamp easter = getEaster(getYear(data));
        for (int day : EASTER_BASE_HOLIDAY) {
            if (BaseFunction.trunc(data).equals(add(easter, day))) {
                return true;
            }
        }

        // Altre festività
        if (otherHoliday != null) {
            for (Timestamp timestamp : otherHoliday) {
                if (timestamp != null && formatTimestamp(data, null, DAY_TRUNC_FORMAT).equals(formatTimestamp(timestamp, null, DAY_TRUNC_FORMAT))) {
                    return true;
                }
            }
        }
        return false;
    }

    public static final DayType getDayType(Timestamp data, Timestamp... otherHoliday) throws FrameworkException {
        if (isHoliday(data, otherHoliday)) {
            return DayType.FESTIVO;
        } else if (isSaturday(data)) {
            return DayType.PREFESTIVO;
        } else {
            return DayType.LAVORATIVO;
        }
    }

    /**
     * Ritorna la data di sistema
     *
     * @return
     */
    public static Timestamp sysdate() {
        return new Timestamp((new GregorianCalendar()).getTimeInMillis());
    }

    /**
     * Ritorna la data di sistema
     *
     * @return
     * @throws ParseException
     */
    public static Timestamp truncSysdate() throws FrameworkException {
        return BaseFunction.trunc(sysdate());
    }

    public static Timestamp getNextWorkDay(Timestamp timestamp, int day) throws FrameworkException {
        for (int i = 0; i < day; ) {
            timestamp = TimeStampUtil.add(timestamp, 1);
            if (!TimeStampUtil.isHoliday(timestamp) && !TimeStampUtil.isSaturday(timestamp)) {
                i++;
            }
        }

        return timestamp;
    }

    public static Timestamp getPrevWorkDay(Timestamp timestamp, int day) throws FrameworkException {
        for (int i = 0; i < day; ) {
            timestamp = TimeStampUtil.add(timestamp, -1);
            if (!TimeStampUtil.isHoliday(timestamp) && !TimeStampUtil.isSaturday(timestamp)) {
                i++;
            }
        }

        return timestamp;
    }

    public static final Timestamp add(Timestamp timestamp, int day) {
        if (timestamp == null) {
            return null;
        } else {
            Calendar calendar = new GregorianCalendar();
            calendar.setTimeInMillis(timestamp.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, day);

            return new Timestamp(calendar.getTimeInMillis());
        }
    }

    public static final Timestamp addMonths(Timestamp timestamp, int month) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(timestamp.getTime());
        calendar.add(Calendar.MONTH, month);

        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * Ritorna il primo giorno della settimana
     *
     * @return
     */
    public static Timestamp firstDayOfWeek() throws FrameworkException {
        return firstDayOfWeek(sysdate());
    }

    /**
     * Ritorna il primo giorno della settimana
     *
     * @param date
     * @return
     */
    public static Timestamp firstDayOfWeek(Timestamp date) throws FrameworkException {
        date = add(BaseFunction.trunc(date), -1); // In questo modo si corregge il fatto che, per gli inglesi, il primo giorno della settimana è Domenica

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        return add(date, -dayOfWeek + 2);
    }

    /**
     * Ritorna il l'ultimo giorno della settimana
     *
     * @return
     */
    public static Timestamp lastDayOfWeek() throws FrameworkException {
        return lastDayOfWeek(sysdate());
    }

    /**
     * Ritorna l'ultimo giorno della settimana
     *
     * @param date
     * @return
     */
    public static Timestamp lastDayOfWeek(Timestamp date) throws FrameworkException {
        return add(firstDayOfWeek(date), 6);
    }

    /**
     * Ritorna il primo giorno del mese
     *
     * @param date
     * @return
     */
    public static Timestamp firstDayOfMonth(Timestamp date) throws FrameworkException {
        return BaseFunction.trunc(date, "MM/yyyy");
    }

    /**
     * Ritorna il primo giorno del mese
     *
     * @return
     */
    public static Timestamp firstDayOfMonth() throws FrameworkException {
        return firstDayOfMonth(sysdate());
    }

    /**
     * Ritorna l'ultimo giorno del mese
     *
     * @param date
     * @return
     */
    public static Timestamp lastDayOfMonth(Timestamp date) throws FrameworkException {
        return add(addMonths(firstDayOfMonth(date), 1), -1);
    }

    /**
     * Ritorna l'ultimo giorno del mese
     *
     * @return
     */
    public static Timestamp lastDayOfMonth() throws FrameworkException {
        return lastDayOfMonth(sysdate());
    }

    /**
     * Ritorna il primo giorno dell'anno
     *
     * @param date
     * @return
     */
    public static Timestamp firstDayOfYear(Timestamp date) throws FrameworkException {
        return BaseFunction.trunc(date, "yyyy");
    }

    /**
     * Ritorna il primo giorno dell'anno
     *
     * @return
     */
    public static Timestamp firstDayOfYear() throws FrameworkException {
        return firstDayOfYear(sysdate());
    }

    /**
     * Ritorna l'ultimo giorno dell'anno
     *
     * @param date
     * @return
     */
    public static Timestamp lastDayOfYear(Timestamp date) throws FrameworkException {
        return add(addMonths(firstDayOfYear(date), 12), -1);
    }

    /**
     * Ritorna l'ultimo giorno dell'anno
     *
     * @return
     */
    public static Timestamp lastDayOfYear() throws FrameworkException {
        return lastDayOfYear(sysdate());
    }

}
