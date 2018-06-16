package mobiledimension.exchangerates;


import java.util.Comparator;

/**
 * Created by Турал on 30.11.2017.
 */
/*Данный класс - модель данных, хранящий название валюты и ее курс, с методами их сравнения между собой*/
class ModelData {

    static Comparator<ModelData> COMPARATOR_NAME = new Comparator<ModelData>() {
        @Override
        public int compare(ModelData o1, ModelData o2) {
            return o1.name.compareTo(o2.name);
        }
    };
    static Comparator<ModelData> COMPARATOR_VALUE_DESCENDING = new Comparator<ModelData>() {
        @Override
        public int compare(ModelData o1, ModelData o2) {
            return o1.value.compareTo(o2.value);
        }
    };
    static Comparator<ModelData> COMPARATOR_VALUE_ASCENDING = new Comparator<ModelData>() {
        @Override
        public int compare(ModelData o1, ModelData o2) {
            return o2.value.compareTo(o1.value);
        }

    };

    private String name;
    private Double value;

    ModelData(String name, Double value) {
        this.name = name;
        this.value = value;
    }

    String getName() {
        return this.name;
    }

    Double getValue() {
        return this.value;
    }
}
