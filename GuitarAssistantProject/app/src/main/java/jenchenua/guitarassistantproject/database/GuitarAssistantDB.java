package jenchenua.guitarassistantproject.database;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class GuitarAssistantDB {
    public static final String CONTENT_AUTHORITY = "jenchenua.guitarassistant";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_SCALE = "scale";
    public static final String PATH_PATTERN = "pattern";
    public static final String PATH_PENTATONICS = "pentatonic";

    public static final class ScaleEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_SCALE).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_SCALE;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_SCALE;

        public static final String TABLE_NAME = "scale";

        public static final String COLUMN_NAME = "name";

        public static final String COLUMN_POSITION_1 = "position_1";

        public static final String COLUMN_POSITION_2 = "position_2";

        public static final String COLUMN_POSITION_3 = "position_3";

        public static final String COLUMN_POSITION_4 = "position_4";

        public static final String COLUMN_POSITION_5 = "position_5";

        public static Uri buildLocationUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
