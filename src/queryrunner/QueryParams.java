package queryrunner;

import java.util.TreeMap;

public class QueryParams {
    TreeMap<String, String> paramName;
    TreeMap<String, Boolean> paramLike;

    public QueryParams() {
        paramName = new TreeMap<>();
        paramLike = new TreeMap<>();
    }


    public String getName(int queryNum, int paramNum) {
        return paramName.get(key(queryNum, paramNum));
    }

    public boolean isLike(int queryNum, int paramNum) {
        return paramLike.get(key(queryNum, paramNum));
    }

    public void addParam(
            int queryNum,
            int paramNum,
            String paramName,
            boolean isLike) {
        String k = key(queryNum, paramNum);

        this.paramName.put(k, paramName);
        this.paramLike.put(k, isLike);
    }

    private String key(int queryNum, int paramNum) {
        return queryNum + "-" + paramNum;
    }
}
