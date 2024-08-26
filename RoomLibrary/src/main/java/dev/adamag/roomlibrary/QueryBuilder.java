package dev.adamag.roomlibrary;

import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteQuery;

import java.util.ArrayList;
import java.util.List;

public class QueryBuilder {

    private StringBuilder query;
    private List<Object> args;

    public QueryBuilder(String tableName) {
        query = new StringBuilder("SELECT * FROM ").append(tableName);
        args = new ArrayList<>();
    }

    public QueryBuilder where(String condition, Object... params) {
        query.append(" WHERE ").append(condition);
        for (Object param : params) {
            args.add(param);
        }
        return this;
    }

    public QueryBuilder orderBy(String column, boolean ascending) {
        query.append(" ORDER BY ").append(column).append(ascending ? " ASC" : " DESC");
        return this;
    }

    public SupportSQLiteQuery build() {
        return new SimpleSQLiteQuery(query.toString(), args.toArray());
    }
}
