package jenchenua.guitarassistantproject.database;

import android.provider.BaseColumns;

public class FingeringDatabase {
    public static class ScaleEntry implements BaseColumns {
        public static final String TABLE_NAME = "Scale";

        public static final String NAME_COLUMN = "name";

        public static final String BOX_1_COLUMN = "box_1";
        public static final String BOX_2_COLUMN = "box_2";
        public static final String BOX_3_COLUMN = "box_3";
        public static final String BOX_4_COLUMN = "box_4";
        public static final String BOX_5_COLUMN = "box_5";
    }
}
