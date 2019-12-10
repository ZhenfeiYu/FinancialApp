package ng.com.obkm.finance;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper{

    public static final String COST_MONEY = "cost_money";
    public static final String COST_DATE = "cost_date";
    public static final String COST_CATEGORY="cost_category";
    public static final String COST_TITLE = "cost_title";
    public static final String FINANCE_COST = "finance_cost";


    public DatabaseHelper(Context context) {
        super(context, "finance_daily", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists finance_cost("+
                "id interger primary key, "+
                "cost_category varchar,"+
                "cost_title varchar,"+
                "cost_date varchar, "+
                "cost_money varchar)");
    }

    public void insertCost(CostBean costBean){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COST_CATEGORY, costBean.costCategory);
        cv.put(COST_TITLE, costBean.costTitle);
        cv.put(COST_DATE, costBean.costDate);
        cv.put(COST_MONEY, costBean.costMoney);



        database.insert(FINANCE_COST, null, cv);


    }

    public Cursor getAllCostData(){
        SQLiteDatabase database = getWritableDatabase();
        return database.query(FINANCE_COST, null, null, null, null, null,  "cost_date ASC");
    }

    public void deleteAllData(){
        SQLiteDatabase database = getWritableDatabase();
        database.delete(FINANCE_COST, null, null);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
