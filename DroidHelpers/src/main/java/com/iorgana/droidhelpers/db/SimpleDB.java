package com.iorgana.droidhelpers.db;


import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * This class used to save/retrieve Object or List of Object
 * This class not used for save/retrieve abstract classes like Drawable
 */
public class SimpleDB {
    private final SharedPreferences sharedPreferences;
    private final static String PREF_NAME = "simple_db_pref";
    private final static String PREF_LIST_OBJECT = "pref_list_object";
    private final static String PREF_OBJECT = "pref_object";

    public SimpleDB(Context context){
        this.sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    /**
     * Save a List of Object
     * --------------------------------------------------------------------
     * @param listObject: object
     */
    public <T> void saveListObject(List<T> listObject){
        Gson gson = new Gson();
        String json = gson.toJson(listObject);
        sharedPreferences.edit().putString(PREF_LIST_OBJECT, json).apply();
    }
    public <T> void saveListObject(List<T> listObject, String key){
        Gson gson = new Gson();
        String json = gson.toJson(listObject);
        sharedPreferences.edit().putString(key, json).apply();
    }

    /**
     * Save an Object
     * --------------------------------------------------------------------
     * @param object: Generic type: can pass any object type
     */
    public <T> void saveObject(T object){
        Gson gson = new Gson();
        String json = gson.toJson(object);
        sharedPreferences.edit().putString(PREF_OBJECT, json).apply();
    }
    public <T> void saveObject(T object, String key){
        Gson gson = new Gson();
        String json = gson.toJson(object);
        sharedPreferences.edit().putString(key, json).apply();
    }

    /**
     * Get a List of Object
     * --------------------------------------------------------------------
     * @param classType: Object Type
     * @return object:
     */
    public <T> T getObject(Class<T> classType){
        String json = sharedPreferences.getString(PREF_OBJECT, null);
        Gson gson = new Gson();
        return gson.fromJson(json, classType);
    }
    public <T> T getObject(Class<T> classType, String key){
        String json = sharedPreferences.getString(key, null);
        Gson gson = new Gson();
        return gson.fromJson(json, classType);
    }

    /**
     * Get a List of Object
     * --------------------------------------------------------------------
     * @param classType: Object Type
     * @return List: List of Object
     */
    public <T> List<T> getListObject(Class<T> classType){
        String json = sharedPreferences.getString(PREF_LIST_OBJECT, null);
        Gson gson = new Gson();
        Type type = TypeToken.getParameterized(List.class, classType).getType();
        return gson.fromJson(json, type);
    }
    public <T> List<T> getListObject(Class<T> classType, String key){
        String json = sharedPreferences.getString(key, null);
        Gson gson = new Gson();
        Type type = TypeToken.getParameterized(List.class, classType).getType();
        return gson.fromJson(json, type);
    }
}
