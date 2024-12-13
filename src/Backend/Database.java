package Backend;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;

public abstract class Database<T>{
    protected Gson gson;
    protected String fileName;

    public Database(String fileName) {
        this.fileName = fileName;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public void save(ArrayList<T> list){
        try {
            BufferedWriter writer=new BufferedWriter(new FileWriter(fileName));
            gson.toJson(list,writer);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<T> load(Type type){
        try {
            File file=new File(fileName);
            if(!file.exists()){
                return new ArrayList<>();
            }
            BufferedReader reader= new BufferedReader(new FileReader(fileName));
            ArrayList<T> list= gson.fromJson(reader, type);
            if(list==null){
                return new ArrayList<>();
            }
            return list;
        } catch (FileNotFoundException e) {
            return new ArrayList<T>();
        }
    }
    public abstract boolean add(T item);
    public abstract void remove(T item);
    public abstract ArrayList<T> getAll();
    public abstract T getById(String id);
}