/*
 * Copyright 2014 by SCSK Corporation.
 * 
 * This file is part of PrimeCloud Controller(TM).
 * 
 * PrimeCloud Controller(TM) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 * 
 * PrimeCloud Controller(TM) is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with PrimeCloud Controller(TM). If not, see <http://www.gnu.org/licenses/>.
 */
package jp.primecloud.auto.zabbix.model.usergroup;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * UsergroupのGetメソッド用パラメータクラスです。
 * </p>
 *
 */
public class UsergroupGetParam implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<String> usrgrpids;

    private String output;

    private Map<String, List<Object>> filter;

    /**
     * usrgrpidsを取得します。
     *
     * @return usrgrpids
     */
    public List<String> getUsrgrpids() {
        return usrgrpids;
    }

    /**
     * usrgrpidsを設定します。
     *
     * @param usrgrpids usrgrpids
     */
    public void setUsrgrpids(List<String> usrgrpids) {
        this.usrgrpids = usrgrpids;
    }

    /**
     * outputを取得します。
     *
     * @return output
     */
    public String getOutput() {
        return output;
    }

    /**
     * outputを設定します。
     *
     * @param output output
     */
    public void setOutput(String output) {
        this.output = output;
    }

    /**
     * filterを取得します。
     *
     * @return filter
     */
    public Map<String, List<Object>> getFilter() {
        return filter;
    }

    /**
     * filterを設定します。
     *
     * @param filter filter
     */
    public void setFilter(Map<String, List<Object>> filter) {
        this.filter = filter;
    }
}
