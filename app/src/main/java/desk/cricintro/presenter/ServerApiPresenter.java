package desk.cricintro.presenter;


import desk.cricintro.apis.ServerApi;
import desk.cricintro.models.ZomatoSearchModel;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ServerApiPresenter {

    private static final String TAG = "ServerApiPresenter";
    private static final String BASE_URL =  "https://developers.zomato.com/api/v2.1/";

    private static final String KEY = "dbf48189aa6bc7848018b130867e1678";

    public void getZomatoSearchApiPresenter(String query,final ForResultsApiToView presenter){

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        ServerApi.ZomatoSearchService service = retrofit.create(ServerApi.ZomatoSearchService.class);
        Observable<ZomatoSearchModel> modelObservable = service.getSearchResults(KEY,query);

        modelObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ZomatoSearchModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        presenter.onError();
                    }

                    @Override
                    public void onNext(ZomatoSearchModel data) {
                        presenter.onResult(data);
                    }
                });


    }


    public interface ForResultsApiToView{
        void onResult(ZomatoSearchModel data);
        void onError();
    }

}
