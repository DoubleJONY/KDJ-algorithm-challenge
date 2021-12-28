package com.doublejony.playground;

public enum DayDic {

    MONDAY("월요일", 1),
    TUESDAY("화요일", 1),
    WEDNESDAY("수요일", 1),
    THURSDAY("목요일", 1),
    FRIDAY("금요일", 1),
    SATUARDAY("토요일", 2),
    SUNDAY("일요일", 2);

    static int WORKING_DAYS = 1;
    static int WEEK_DAYS = 2;

    private final String kName;
    private final int category;

    DayDic(String kName, int category) {
        this.kName = kName;
        this.category = category;
    }

    public static String[] getKNamesWithWorkingDay() {
        return getKNamesWithCategory(WORKING_DAYS);
    }

    public static String[] getKNamesWithWeekend() {
        return getKNamesWithCategory(WEEK_DAYS);
    }

    private static String[] getKNamesWithCategory(int category) {
        String buffer = "";

        for (DayDic d : DayDic.values()) {
            if (d.category == category) {
                buffer += d.kName + " ";
            }
        }

        return buffer.split(" ");
    }
}
