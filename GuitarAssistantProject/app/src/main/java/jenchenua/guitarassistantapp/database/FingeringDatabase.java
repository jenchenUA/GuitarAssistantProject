package jenchenua.guitarassistantapp.database;

import android.provider.BaseColumns;

public class FingeringDatabase {
    public static final String NAME_COLUMN = "name";

    public static final String BOX_1_COLUMN = "box_1";
    public static final String BOX_2_COLUMN = "box_2";
    public static final String BOX_3_COLUMN = "box_3";
    public static final String BOX_4_COLUMN = "box_4";
    public static final String BOX_5_COLUMN = "box_5";

    public static class ScaleEntry implements BaseColumns {
        public static final String TABLE_NAME = "Scale";

        public static final String NAME_COLUMN = "name";
    }

    public static class PatternEntry implements BaseColumns {
        public static final String TABLE_NAME = "Pattern";
    }

    public static class AEntry implements BaseColumns {
        public static final String TABLE_NAME = "A";
    }

    public static class BEntry implements BaseColumns {
        public static final String TABLE_NAME = "B";
    }
}
