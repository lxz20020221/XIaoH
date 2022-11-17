package com.example.xiaohantv.Utils;

import java.lang.reflect.Method;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * SharedPreferences 工具类 <br>
 *
 * @author 小志
 */
public class SharedPreferencesUtils {

    /**
     * 保存在手机里面的文件名的默认值
     */
    private String file_Name = "Share_Data";
    private Context mContext;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    /**
     * 私有构造器防止被实例化
     *
     * @param context
     * @param fileName
     */
    public SharedPreferencesUtils(Context context, String fileName) {
        mContext = context;
        file_Name = fileName == null ? file_Name : fileName;
    }

    /**
     * 得到一个{@link SharedPreferencesUtils}对象 <br>
     * 之后必须调用一次{@link #commitInstance()}以完成初始化
     * @param context
     * @param fileName
     * @return
     */
    public static SharedPreferencesUtils getInstance(Context context,
                                                     String fileName) {
        return new SharedPreferencesUtils(context, fileName);
    }

    /**
     * 得到一个{@link SharedPreferencesUtils}对象 <br>
     * 之后必须调用一次{@link #commitInstance()}以完成初始化
     *
     * @param context
     * @return
     */
    public static SharedPreferencesUtils getInstance(Context context) {
        return new SharedPreferencesUtils(context, null);
    }

    /**
     * 得到文件名
     *
     * @return
     */
    public String getFile_Name() {
        return file_Name;
    }

    /**
     * 设置文件名
     *
     * @param file_Name
     * @return
     */
    public SharedPreferencesUtils setFile_Name(String file_Name) {
        this.file_Name = file_Name;
        sp = null;
        editor = null;
        return this;
    }

    /**
     * 提交初始化，提交后，文件名不可被更改，如需更改需再次提交
     *
     * @return
     */
    public SharedPreferencesUtils commitInstance() {
        sp = mContext.getSharedPreferences(file_Name, Context.MODE_PRIVATE);
        editor = sp.edit();
        return this;
    }

    /**
     * 保存数据，需要知道保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param key
     * @param value
     */
    public void put(String key, Object value) {
        isNull();
        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else {
            editor.putString(key, value.toString());
        }
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param key
     * @param valueCast
     * @return
     */
    public Object get(String key, Object valueCast) {
        isNull();
        if (valueCast instanceof String) {
            return sp.getString(key, (String) valueCast);
        } else if (valueCast instanceof Integer) {
            return sp.getInt(key, (Integer) valueCast);
        } else if (valueCast instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) valueCast);
        } else if (valueCast instanceof Float) {
            return sp.getFloat(key, (Float) valueCast);
        } else if (valueCast instanceof Long) {
            return sp.getLong(key, (Long) valueCast);
        }
        return null;
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param key
     */
    public void remove(String key) {
        isNull();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有数据
     */
    public void clear() {
        isNull();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param key
     * @return
     */
    public boolean contains(String key) {
        isNull();
        return sp.contains(key);
    }

    /**
     * 检查SharedPreferences是否为空
     */
    private void isNull() {
        if (sp == null) {
            throw new NullPointerException("相关对象为空,检查是否已调用commitInstance()方法");
        }
    }

    /**
     * 返回所有的键值对
     *
     * @return
     */
    public Map<String, ?> getAll() {
        isNull();
        return sp.getAll();
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return
         */
        private static Method findApplyMethod() {
            try {
                Class<Editor> clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (Exception e) {
            }
            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (Exception e) {
            }
            editor.commit();
        }
    }
}