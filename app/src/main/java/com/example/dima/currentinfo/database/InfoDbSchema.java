package com.example.dima.currentinfo.database;

/**
 * Created by Dima on 26.11.2017.
 */

public class InfoDbSchema {

    public static final class InfoTable {
        public static final String NAME = "data";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String TEMP = "temp";
            public static final String LATITUDE = "latitude";
            public static final String LONGITUDE = "longitude";
            public static final String SENT = "sent";
        }
    }
}
