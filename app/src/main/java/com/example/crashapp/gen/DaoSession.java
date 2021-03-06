package com.example.crashapp.gen;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.example.crashapp.service.entity.UserInformation;
import com.example.crashapp.service.entity.BaseBean;

import com.example.crashapp.gen.UserInformationDao;
import com.example.crashapp.gen.BaseBeanDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig userInformationDaoConfig;
    private final DaoConfig baseBeanDaoConfig;

    private final UserInformationDao userInformationDao;
    private final BaseBeanDao baseBeanDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        userInformationDaoConfig = daoConfigMap.get(UserInformationDao.class).clone();
        userInformationDaoConfig.initIdentityScope(type);

        baseBeanDaoConfig = daoConfigMap.get(BaseBeanDao.class).clone();
        baseBeanDaoConfig.initIdentityScope(type);

        userInformationDao = new UserInformationDao(userInformationDaoConfig, this);
        baseBeanDao = new BaseBeanDao(baseBeanDaoConfig, this);

        registerDao(UserInformation.class, userInformationDao);
        registerDao(BaseBean.class, baseBeanDao);
    }
    
    public void clear() {
        userInformationDaoConfig.clearIdentityScope();
        baseBeanDaoConfig.clearIdentityScope();
    }

    public UserInformationDao getUserInformationDao() {
        return userInformationDao;
    }

    public BaseBeanDao getBaseBeanDao() {
        return baseBeanDao;
    }

}
