package queryrunner;

import java.util.TreeMap;

/**
 * Class to abstract the logic for handling the params better.
 */
public class QueryParams {
    TreeMap<String, String> paramName;  // holds alist
    TreeMap<String, Boolean> paramLike;

    /**
     * Constructor
     */
    public QueryParams() {
        paramName = new TreeMap<>();
        paramLike = new TreeMap<>();
    }

    /**
     * retrieves the name of the param
     * @param queryNum query array index
     * @param paramNum parameter number for the index
     * @return
     */
    public String getName(int queryNum, int paramNum) {
        return paramName.get(key(queryNum, paramNum));
    }

    /**
     * retrieves whether the param is a like param
     * @param queryNum query array index
     * @param paramNum parameter number for the index
     * @return
     */
    public boolean isLike(int queryNum, int paramNum) {
        return paramLike.get(key(queryNum, paramNum));
    }

    /**
     * adds a name for the param and the status of the like
     * @param queryNum query array index
     * @param paramNum parameter number for the index
     * @param paramName param name to store
     * @param isLike if the param is a like query
     */
    public void addParam(
            int queryNum,
            int paramNum,
            String paramName,
            boolean isLike) {
        String k = key(queryNum, paramNum);

        this.paramName.put(k, paramName);
        this.paramLike.put(k, isLike);
    }

    /**
     * Access key
     * @param queryNum query array index
     * @param paramNum parameter number for the index
     * @return the key used to retrieve results
     */
    private String key(int queryNum, int paramNum) {
        return queryNum + "-" + paramNum;
    }
}
