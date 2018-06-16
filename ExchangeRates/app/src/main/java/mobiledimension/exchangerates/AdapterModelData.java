package mobiledimension.exchangerates;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

/**
 * Created by Турал on 30.11.2017.
 */

class AdapterModelData extends ArrayAdapter<ModelData> {
    private List<ModelData> modelDataList;
    private LayoutInflater inflater;
    private int layout;

    AdapterModelData(Context context, int resources, List<ModelData> modelData) {
        super(context, resources, modelData);
        this.layout = resources;
        this.modelDataList = modelData;
        this.inflater = LayoutInflater.from(context);
    }

    private static class ViewHolder {
        TextView name_rate;
        TextView rate;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.name_rate = (TextView) convertView.findViewById(R.id.name_rate);
            viewHolder.rate = (TextView) convertView.findViewById(R.id.rate);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ModelData modelData = modelDataList.get(position);
        viewHolder.name_rate.setText(modelData.getName());
        viewHolder.rate.setText(Double.toString(modelData.getValue()));

        return convertView;
    }


    void refresh() {
        this.notifyDataSetChanged();
    }

}
