/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package queryrunner;

import java.util.ArrayList;

/**
 * A container for the core query and the user inputs that are required by the
 * application.
 *
 * @author Group 5
 */
public class QueryData {

    /**
     * Default constructor
     * @param query the string sql query
     * @param parms a list of the params (name).
     * @param likeparms if the params are using the string like operator
     * @param isAction if the parameter is an update, insert, or delete
     * @param isParm if the query contains a parameter
     */
    QueryData(
            String query,
            String[] parms,
            boolean [] likeparms,
            boolean isAction,
            boolean isParm
    ) {
        m_queryString = query;
        m_arrayParms = parms;
        m_arrayLikeParms = likeparms;
        m_isAction = isAction;
        m_isParms = isParm;        
    }

    /**
     * Getter
     * @return the query string
     */
    String GetQueryString()
    {
        return m_queryString;
    }

    /**
     * Getts the parameter size
     * @return length of the params list
     */
    int GetParmAmount()
    {
        if (m_arrayParms == null)
            return 0;
        else
            return m_arrayParms.length;
    }


    /**
     * The parameter label
     * @param index the parameter text to get
     * @return the label
     */
    String GetParamText(int index)
    {
        return m_arrayParms[index];
    }

    /**
     * getter
     * @param index parameter index
     * @return if the parameter is a has the like operator
     */
    boolean GetLikeParam(int index)
    {
        return m_arrayLikeParms[index];
    }

    /**
     * gets the list of all likes
     * @return list of all likes
     */
    boolean [] GetAllLikeParams()
    {
        return m_arrayLikeParms;
    }

    /**
     * getter
     * @return if the query is an action query
     */
    boolean IsQueryAction()
    {
        return m_isAction;
    }

    /**
     * getter
     * @return if the query is an parameterized query
     */
    boolean IsQueryParm()
    {
        return m_isParms;
    }
     
    private String m_queryString;
    private String [] m_arrayParms;
    private boolean m_isAction;
    private boolean m_isParms;   
    private boolean [] m_arrayLikeParms;
}
