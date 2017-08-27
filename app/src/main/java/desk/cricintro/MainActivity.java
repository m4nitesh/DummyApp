package desk.cricintro;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import desk.cricintro.adapters.RestroAdapter;
import desk.cricintro.models.Restaurant;
import desk.cricintro.models.ZomatoSearchModel;
import desk.cricintro.presenter.ServerApiPresenter;

public class MainActivity extends AppCompatActivity {

    private ServerApiPresenter mPresenter;

    private static final String TAG = "MainActivity";

    private RecyclerView restroRecycler;
    private EditText editQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        restroRecycler = (RecyclerView) findViewById(R.id.recycler_restro);
        editQuery = (EditText) findViewById(R.id.edit_text_query);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        restroRecycler.setLayoutManager(layoutManager);


        mPresenter = new ServerApiPresenter();


    }


    public void searchResults(View view) {
        if (editQuery.getText().length() > 0){
            findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
            mPresenter.getZomatoSearchApiPresenter(editQuery.getText().toString(), new ServerApiPresenter.ForResultsApiToView() {
                @Override
                public void onResult(final ZomatoSearchModel data) {

                    findViewById(R.id.progress_bar).setVisibility(View.GONE);

                    if (data.getResultsFound() == 0){
                        return;
                    }

                    Map<String,ArrayList<Restaurant>> mapCuisines = getMapForCuisines(data);



                    ArrayList<Restaurant> listWithHeader = new ArrayList<Restaurant>();
                    for (Map.Entry<String, ArrayList<Restaurant>> entry : mapCuisines.entrySet()) {
                        String key = entry.getKey();
                        Restaurant resHeader = new Restaurant();
                        resHeader.setHeader(key);
                        resHeader.setType(1);
                        listWithHeader.add(resHeader);
                        listWithHeader.addAll(entry.getValue());
                    }

                    RestroAdapter adapter = new RestroAdapter(getApplicationContext(),listWithHeader);
                    restroRecycler.setAdapter(adapter);


                }

                @Override
                public void onError() {
                    findViewById(R.id.progress_bar).setVisibility(View.GONE);
                    Log.e(TAG, "onError: ");
                }
            });
        }else {
            Toast.makeText(getApplicationContext(),"Enter Some Text",Toast.LENGTH_SHORT).show();
        }


    }


    private Map<String,ArrayList<Restaurant>> getMapForCuisines(ZomatoSearchModel data){

        Map<String,ArrayList<Restaurant>> mapCuisines = new HashMap<String, ArrayList<Restaurant>>();
        for (int i = 0; i < data.getRestaurants().size(); i++) {
            String[] arrCuisine = data.getRestaurants().get(i).getRestaurant().getCuisines().split(",");
            for (int j = 0; j < arrCuisine.length; j++) {
                arrCuisine[j] = arrCuisine[j].trim();
                if (mapCuisines.containsKey(arrCuisine[j])){
                    mapCuisines.get(arrCuisine[j]).add(data.getRestaurants().get(i));
                }else {
                    ArrayList<Restaurant> temp = new ArrayList<Restaurant>();
                    temp.add(data.getRestaurants().get(i));
                    mapCuisines.put(arrCuisine[j],temp);
                }
            }
        }

        return mapCuisines;
    }
}
