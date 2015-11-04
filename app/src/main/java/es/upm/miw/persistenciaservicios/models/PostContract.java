package es.upm.miw.persistenciaservicios.models;

import android.provider.BaseColumns;

/**
 * Created by raquel on 04/11/2015.
 */
public class PostContract {
    public static class PostTable implements BaseColumns {

        public static final String TABLE_NAME="favoritePosts";

        public static final String COL_NAME_ID=_ID;
        public static final String COL_NAME_USER_ID="userId";
        public static final String COL_NAME_TITLE="title";
        public static final String COL_NAME_BODY="body";
    }
}
