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
package jp.primecloud.auto.service.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.primecloud.auto.entity.crud.User;
import jp.primecloud.auto.entity.crud.UserAuth;


public class ManagementUserDto implements Serializable {


    /** TODO: フィールドコメントを記述 */
    private static final long serialVersionUID = 3636683948507828492L;


    private User user;

    private HashMap<Long, UserAuth> authMap;



    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public HashMap<Long, UserAuth> getAuthMap() {
        return authMap;
    }

    public void setAuthMap(HashMap<Long, UserAuth> authMap) {
        this.authMap = authMap;
    }

}
