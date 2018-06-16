package mobiledimension.exchangerates;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/*Этот подход использовать "ручной парсинг" и POJO класс сделан ради эксперимента.
 Можно было для json объекта rates использовать HashMap, в который Gson может парсить из "коробки", но тогда бы пришлось изобретать сортировки для HashMap. */
class PostModel {

    @SerializedName("base")
    @Expose
    private String base;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("rates")
    @Expose
    private JsonObject ratesJson;
    private ArrayList<ModelData> ratesList = new ArrayList<>();
    private List<String> currenciesArrayList = new ArrayList<>();
    private boolean flag = false;


    public String getBase() {
        return base;
    }

    public String getDate() {
        return date;
    }


    public List<String> getCurrenciesArrayList() {
        if (flag) return currenciesArrayList;
        else parseToModelDate();
        return currenciesArrayList;
    }

    public List<ModelData> getRates() {
        if (flag) return ratesList;
        else parseToModelDate();
        return ratesList;
    }


    private void parseToModelDate() {
        flag = true;
        for(Map.Entry<String, JsonElement> key : ratesJson.entrySet() ){
            Double value = key.getValue().getAsDouble();
            ratesList.add(new ModelData(key.getKey(), value));
            currenciesArrayList.add(key.getKey());
        }
    }


    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}