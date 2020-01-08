package com.example.crashapp.util;

import android.util.Log;

import com.example.crashapp.MyApplication;
import com.example.crashapp.gen.DaoSession;
import com.example.crashapp.gen.UserInformationDao;
import com.example.crashapp.service.entity.BaseBean;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

public class DaoSessionUtils<T extends BaseBean> {

    static DaoSession daoSession;

    public static DaoSession getDaoInstance() {

        if (daoSession == null) {
            daoSession = MyApplication.getDaoSession();
        }
        //清空所有数据表的缓存数据
        //daoSession.clear();
        return daoSession;
    }
    public static boolean insertStudent(BaseBean student) {
        boolean flag;
        flag = getDaoInstance().insert(student) != -1;//不等于-1是true 否则是false
        Log.i("MainActivity", "insertStudent: " + flag);
        return flag;
    }
    /**
     * insert() 插入数据
     */
    public static void insertDbBean(BaseBean bean) {
        try {
            getDaoInstance().insert(bean);

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("LUO", "插入本地数据失败：" + e.getMessage());
        }

    }


    /**
     * insertOrReplace()数据存在则替换，数据不存在则插入
     */
    public static void insertOrReplaceDbBean(BaseBean bean) {
        try {
            getDaoInstance().insertOrReplace(bean);

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("LUO", "插入或替换本地数据失败：" + e.getMessage());
        }

    }


    /**
     * delete()删除单个数据
     */
    public static void deleteDbBean(BaseBean bean) {
        try {
            getDaoInstance().delete(bean);

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("LUO", "删除本地数据失败：" + e.getMessage());
        }

    }


    /**
     * deleteAll()删除所有数据
     */
    public static void deleteAllDbBean(BaseBean bean) {
        try {
            getDaoInstance().deleteAll(bean.getClass());

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("LUO", "删除本地所有数据失败：" + e.getMessage());
        }

    }


    /**
     * update()修改本地数据
     */
    public static void updateDbBean(BaseBean bean) {
        try {
            getDaoInstance().update(bean);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("LUO", "修改本地所有数据失败：" + e.getMessage());
        }

    }


    /**
     * loadAll()查询本地所有数据
     */
    public static List<? extends BaseBean> queryAll(BaseBean bean) {
        List<? extends BaseBean> beanList = null;
        try {
            beanList =  getDaoInstance().loadAll(bean.getClass());
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("LUO", "查询本地所有数据失败：" + e.getMessage());
        }

        return beanList;
    }


    /**
     * 根据条件查询本地所有数据
     * 调用时传值方法whereConditions
     * List<WhereCondition> whereConditions = new ArrayList<>();
     * whereConditions.add(StudentDao.Properties.Name.eq("小明"));
     * whereConditions.add(StudentDao.Properties.Age.eq(22));
     */
    public static List<? extends BaseBean> queryConditionAll(BaseBean bean, List<WhereCondition> whereConditions) throws ClassCastException {
        List<? extends BaseBean> beanList = null;
        try {

            QueryBuilder queryBuilder = getDaoInstance().queryBuilder(bean.getClass());
            //把条件循环加入
            if (null != whereConditions) {
                for (WhereCondition whereCondition : whereConditions) {
                    queryBuilder.where(whereCondition);
                }

            }
            beanList = queryBuilder.build().list();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("LUO", "按条件查询本地数据失败：" + e.getMessage());
        }

        return beanList;
    }


    /**
     * 根据原始 SQL 数据查询
     * 手输写 SQL 语句sqlConditions
     */
    public static List<? extends BaseBean> querySqlAll(BaseBean bean, String sqlConditions) throws ClassCastException {
        List<? extends BaseBean> beanList = null;
        try {
            //查询条件
            WhereCondition.StringCondition stringCondition = new WhereCondition.StringCondition(sqlConditions);
            //查询QueryBuilder
            QueryBuilder<? extends BaseBean> queryBuilder = (QueryBuilder<? extends BaseBean>) getDaoInstance().queryBuilder(bean.getClass());
            //添加查询条件
            queryBuilder.where(stringCondition);

            beanList = queryBuilder.build().list();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("LUO", "sql按条件查询本地数据失败：" + e.getMessage());
        }

        return beanList;
    }
}


